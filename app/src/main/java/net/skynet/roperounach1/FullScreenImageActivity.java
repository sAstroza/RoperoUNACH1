package net.skynet.roperounach1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FullScreenImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        ImageView fullScreenImageView = findViewById(R.id.fullScreenImageView);
        Button closeButton = findViewById(R.id.closeButton);

        String imageUrl = getIntent().getStringExtra("imageUrl");
        Picasso.get().load(imageUrl).into(fullScreenImageView);

        closeButton.setOnClickListener(v -> finish());
    }
}
