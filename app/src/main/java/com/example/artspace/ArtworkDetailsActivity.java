package com.example.artspace;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

/**
 * Second screen: shows the details sent by MainActivity and lets the user
 * open a website (implicit Intent) or go back.
 */
public class ArtworkDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_details);

        TextView tvDetails = findViewById(R.id.tvDetails);
        MaterialButton btnVisitWebsite = findViewById(R.id.btnVisitWebsite);
        MaterialButton btnBack = findViewById(R.id.btnBack);

        // Receive the data sent by the explicit Intent.
        Intent intent = getIntent();
        String title = intent.getStringExtra(MainActivity.EXTRA_TITLE);
        String artist = intent.getStringExtra(MainActivity.EXTRA_ARTIST);
        int year = intent.getIntExtra(MainActivity.EXTRA_YEAR, 0);

        if (title == null || artist == null) {
            tvDetails.setText(R.string.no_data);
        } else {
            tvDetails.setText(getString(R.string.details_format, title, artist, year));
        }

        btnVisitWebsite.setOnClickListener(v -> openWebsite());

        // finish() closes this Activity and returns to MainActivity,
        // which still shows the same artwork.
        btnBack.setOnClickListener(v -> finish());
    }

    /** Implicit Intent: we only describe the action (VIEW) and the data (an HTTPS URL). */
    private void openWebsite() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.website_url)));
        try {
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            // No app on the device can handle the Intent.
            Toast.makeText(this, R.string.no_browser, Toast.LENGTH_LONG).show();
        }
    }
}
