package net.skynet.roperounach1;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VerMensajesActivity extends AppCompatActivity {

    private RecyclerView mensajesRecyclerView;
    private MensajeAdapter mensajeAdapter;
    private List<Mensaje> mensajeList;
    private DatabaseReference mensajesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_mensajes);

        mensajesRecyclerView = findViewById(R.id.mensajesRecyclerView);
        mensajesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mensajeList = new ArrayList<>();
        mensajeAdapter = new MensajeAdapter(mensajeList);
        mensajesRecyclerView.setAdapter(mensajeAdapter);

        mensajesRef = FirebaseDatabase.getInstance().getReference("mensajes");
        cargarMensajes();
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
