package net.skynet.roperounach1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PublicarPrendaActivity extends AppCompatActivity {
    private static final String TAG = "PublicarPrendaActivity";
    private EditText tituloEditText, descripcionEditText;
    private ImageView prendaImageView;
    private Button publicarButton, seleccionarImagenButton;
    private FirebaseAuth mAuth;
    private DatabaseReference prendasRef;
    private StorageReference storageRef;
    private Uri imagenUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_prenda);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Si no está autenticado, redirige a la pantalla de inicio de sesión
            Intent loginIntent = new Intent(PublicarPrendaActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Cierra PublicarPrendaActivity para que el usuario no pueda volver con el botón de retroceso
            return;
        }




        prendasRef = FirebaseDatabase.getInstance().getReference("prendas");
        storageRef = FirebaseStorage.getInstance().getReference("imagenes");

        tituloEditText = findViewById(R.id.tituloEditText);
        descripcionEditText = findViewById(R.id.descripcionEditText);
        prendaImageView = findViewById(R.id.prendaImageView);
        seleccionarImagenButton = findViewById(R.id.seleccionarImagenButton);
        publicarButton = findViewById(R.id.publicarButton);

        seleccionarImagenButton.setOnClickListener(v -> seleccionarImagen());
        publicarButton.setOnClickListener(v -> publicarPrenda());
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

    private void publicarPrenda() {
        String titulo = tituloEditText.getText().toString().trim();
        String descripcion = descripcionEditText.getText().toString().trim();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }
        String usuarioId = currentUser.getUid();

        if (imagenUri != null) {
            StorageReference fileRef = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imagenUri));
            fileRef.putFile(imagenUri)
                    .addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        Prenda prenda = new Prenda(usuarioId, titulo, descripcion, uri.toString());
                        String prendaId = prendasRef.push().getKey();
                        if (prendaId != null) {
                            prendasRef.child(prendaId).setValue(prenda)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(PublicarPrendaActivity.this, "Prenda publicada", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(PublicarPrendaActivity.this, "Error al publicar prenda", Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "Error al publicar prenda", task.getException());
                                        }
                                    });
                        } else {
                            Toast.makeText(PublicarPrendaActivity.this, "Error al generar ID de prenda", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Error al generar ID de prenda");
                        }
                    }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(PublicarPrendaActivity.this, "Error al subir imagen", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error al subir imagen", e);
                    });
        } else {
            Toast.makeText(this, "Seleccione una imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
