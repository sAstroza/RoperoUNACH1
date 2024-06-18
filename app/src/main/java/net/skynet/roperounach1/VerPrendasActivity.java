package net.skynet.roperounach1;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VerPrendasActivity extends AppCompatActivity {
    private RecyclerView prendasRecyclerView;
    private DatabaseReference prendasRef;
    private List<Prenda> prendaList;
    private PrendaAdapter prendaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_prendas);

        prendasRecyclerView = findViewById(R.id.prendasRecyclerView);
        prendasRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        prendaList = new ArrayList<>();
        prendaAdapter = new PrendaAdapter(prendaList);
        prendasRecyclerView.setAdapter(prendaAdapter);

        prendasRef = FirebaseDatabase.getInstance().getReference("prendas");
        prendasRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prendaList.clear();
                for (DataSnapshot prendaSnapshot : snapshot.getChildren()) {
                    Prenda prenda = prendaSnapshot.getValue(Prenda.class);
                    prendaList.add(prenda);
                }
                prendaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}
