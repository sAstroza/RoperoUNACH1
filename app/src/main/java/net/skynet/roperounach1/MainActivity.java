package net.skynet.roperounach1;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import net.skynet.roperounach1.VerPrendasActivity;
import net.skynet.roperounach1.PublicarPrendaActivity;
import net.skynet.roperounach1.VerPrendasActivity;



public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Button verPrendasButton;
    private Button publicarPrendaButton;
    private Button verMensajesButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Agregados desde ORIGINAL
        verPrendasButton = findViewById(R.id.verPrendasButton);
        publicarPrendaButton = findViewById(R.id.publicarPrendaButton);
        verMensajesButton = findViewById(R.id.verMensajesButton);

        verPrendasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VerPrendasActivity.class);
                startActivity(intent);
            }
        });

        publicarPrendaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PublicarPrendaActivity.class);
                startActivity(intent);
            }
        });

        verMensajesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VerMensajesActivity.class);
                startActivity(intent);
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
        // Verifica si el usuario está autenticado
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Si no está autenticado, redirige a la pantalla de inicio de sesión
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Cierra MainActivity para que el usuario no pueda volver con el botón de retroceso
        }
    }


}
