package net.skynet.roperounach1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class EditarPrendaActivity extends AppCompatActivity {
    private EditText tituloEditText, descripcionEditText;
    private ImageView prendaImageView;
    private Button guardarCambiosButton, seleccionarImagenButton;
    private DatabaseReference prendasRef;
    private StorageReference storageRef;
    private Uri imagenUri;
    private String prendaId;
    private Prenda prenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_prenda);

        tituloEditText = findViewById(R.id.editarTituloEditText);
        descripcionEditText = findViewById(R.id.editarDescripcionEditText);
        prendaImageView = findViewById(R.id.editarPrendaImageView);
        seleccionarImagenButton = findViewById(R.id.seleccionarImagenButton);
        guardarCambiosButton = findViewById(R.id.guardarCambiosButton);

        prendasRef = FirebaseDatabase.getInstance().getReference("prendas");
        storageRef = FirebaseStorage.getInstance().getReference("imagenes");

        prendaId = getIntent().getStringExtra("prendaId");
        if (prendaId == null) {
            Toast.makeText(this, "Error al cargar prenda", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarDatosPrenda();

        seleccionarImagenButton.setOnClickListener(v -> seleccionarImagen());
        guardarCambiosButton.setOnClickListener(v -> guardarCambios());
    }

    private void cargarDatosPrenda() {
        prendasRef.child(prendaId).get().addOnSuccessListener(dataSnapshot -> {
            prenda = dataSnapshot.getValue(Prenda.class);
            if (prenda != null) {
                tituloEditText.setText(prenda.getTitulo());
                descripcionEditText.setText(prenda.getDescripcion());
                Picasso.get().load(prenda.getImagenUrl()).into(prendaImageView);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(EditarPrendaActivity.this, "Error al cargar prenda", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenUri = data.getData();
            prendaImageView.setImageURI(imagenUri);
        }
    }

    private void guardarCambios() {
        String titulo = tituloEditText.getText().toString().trim();
        String descripcion = descripcionEditText.getText().toString().trim();

        if (imagenUri != null) {
            StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imagenUri));
            fileRef.putFile(imagenUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                actualizarPrenda(titulo, descripcion, uri.toString());
            }));
        } else {
            actualizarPrenda(titulo, descripcion, prenda.getImagenUrl());
        }
    }

    private void actualizarPrenda(String titulo, String descripcion, String imagenUrl) {
        prenda.setTitulo(titulo);
        prenda.setDescripcion(descripcion);
        prenda.setImagenUrl(imagenUrl);

        prendasRef.child(prendaId).setValue(prenda).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditarPrendaActivity.this, "Prenda actualizada", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EditarPrendaActivity.this, "Error al actualizar prenda", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
