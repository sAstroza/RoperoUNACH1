package net.skynet.roperounach1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private EditText mensajeEditText;
    private Button enviarMensajeButton;
    private RecyclerView mensajesRecyclerView;
    private MensajeAdapter mensajeAdapter;
    private List<Mensaje> mensajeList;
    private DatabaseReference mensajesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mensajeEditText = findViewById(R.id.mensajeEditText);
        enviarMensajeButton = findViewById(R.id.enviarMensajeButton);
        mensajesRecyclerView = findViewById(R.id.mensajesRecyclerView);

        mensajeList = new ArrayList<>();
        mensajeAdapter = new MensajeAdapter(mensajeList);
        mensajesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mensajesRecyclerView.setAdapter(mensajeAdapter);

        mensajesRef = FirebaseDatabase.getInstance().getReference("mensajes");

        enviarMensajeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje();
            }
        });

        cargarMensajes();
    }

    private void enviarMensaje() {
        String contenido = mensajeEditText.getText().toString().trim();
        if (TextUtils.isEmpty(contenido)) {
            Toast.makeText(this, "El mensaje no puede estar vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        String usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Mensaje mensaje = new Mensaje(usuarioId, contenido);
        mensajesRef.push().setValue(mensaje);

        mensajeEditText.setText("");
    }

    private void cargarMensajes() {
        mensajesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mensajeList.clear();
                for (DataSnapshot mensajeSnapshot : snapshot.getChildren()) {
                    Mensaje mensaje = mensajeSnapshot.getValue(Mensaje.class);
                    mensajeList.add(mensaje);
                }
                mensajeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejo de errores
            }
        });
    }
}
