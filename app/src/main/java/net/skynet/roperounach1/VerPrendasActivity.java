package net.skynet.roperounach1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class VerPrendasActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference prendasRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_prendas);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prendasRef = FirebaseDatabase.getInstance().getReference().child("prendas");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Prenda> options =
                new FirebaseRecyclerOptions.Builder<Prenda>()
                        .setQuery(prendasRef, Prenda.class)
                        .build();

        FirebaseRecyclerAdapter<Prenda, PrendaViewHolder> adapter =
                new FirebaseRecyclerAdapter<Prenda, PrendaViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PrendaViewHolder holder, int position, @NonNull Prenda model) {
                        holder.tituloTextView.setText(model.getTitulo());
                        holder.descripcionTextView.setText(model.getDescripcion());
                        Picasso.get().load(model.getImagenUrl()).into(holder.prendaImageView);

                        holder.editButton.setOnClickListener(v -> {
                            Intent intent = new Intent(VerPrendasActivity.this, EditarPrendaActivity.class);
                            intent.putExtra("prendaId", getRef(position).getKey());
                            startActivity(intent);
                        });

                        holder.deleteButton.setOnClickListener(v -> {
                            prendasRef.child(getRef(position).getKey()).removeValue()
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(VerPrendasActivity.this, "Prenda eliminada", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(VerPrendasActivity.this, "Error al eliminar prenda", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        });
                    }

                    @NonNull
                    @Override
                    public PrendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prenda_item, parent, false);
                        return new PrendaViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class PrendaViewHolder extends RecyclerView.ViewHolder {
        public TextView tituloTextView, descripcionTextView;
        public ImageView prendaImageView;
        public ImageButton editButton, deleteButton;

        public PrendaViewHolder(@NonNull View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tituloTextView);
            descripcionTextView = itemView.findViewById(R.id.descripcionTextView);
            prendaImageView = itemView.findViewById(R.id.prendaImageView);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
