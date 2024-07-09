package net.skynet.roperounach1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        // Inicialización de botones
        verPrendasButton = findViewById(R.id.verPrendasButton);
        publicarPrendaButton = findViewById(R.id.publicarPrendaButton);
        verMensajesButton = findViewById(R.id.verMensajesButton);

        // Configuración de listeners para los botones
        verPrendasButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VerPrendasActivity.class);
            startActivity(intent);
        });

        publicarPrendaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PublicarPrendaActivity.class);
            startActivity(intent);
        });

        verMensajesButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VerMensajesActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Verificar si el usuario está autenticado al iniciar la actividad
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // No hay usuario autenticado, redirigir al LoginActivity para iniciar sesión
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish(); // Finalizar MainActivity para evitar que el usuario regrese con el botón "Atrás"
        } else {
            // Usuario autenticado, mostrar los botones de la actividad principal
            mostrarBotones();
        }
    }

    private void mostrarBotones() {
        verPrendasButton.setVisibility(View.VISIBLE);
        publicarPrendaButton.setVisibility(View.VISIBLE);
        verMensajesButton.setVisibility(View.VISIBLE);
    }
}
