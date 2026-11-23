package com.example.artspace;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

/**
 * Main screen of ArtSpace.
 * Shows one artwork at a time (image, title, artist, year, counter)
 * and lets the user move between the six artworks.
 */
public class MainActivity extends AppCompatActivity {

    // ---- Keys ---------------------------------------------------------------
    /** Key used to save/restore the current artwork index (rotation, etc.). */
    private static final String KEY_CURRENT_INDEX = "current_index";

    /** Keys of the extras sent to ArtworkDetailsActivity. */
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_ARTIST = "extra_artist";
    public static final String EXTRA_YEAR = "extra_year";

    // ---- Artwork data (parallel arrays: same index = same artwork) ----------
    private static final int[] IMAGES = {
            R.drawable.artwork_01_deep_field_whispers,
            R.drawable.artwork_02_crimson_nebula_veil,
            R.drawable.artwork_03_spiral_of_quiet_light,
            R.drawable.artwork_04_stellar_nursery,
            R.drawable.artwork_05_cosmic_dust_currents,
            R.drawable.artwork_06_distant_cluster_glow
    };

    private static final String[] TITLES = {
            "Deep Field Whispers",
            "Crimson Nebula Veil",
            "Spiral of Quiet Light",
            "Stellar Nursery",
            "Cosmic Dust Currents",
            "Distant Cluster Glow"
    };

    private static final String[] ARTISTS = {
            "Mira Calder",
            "Ryo Tanabe",
            "Layla Haddad",
            "Oskar Lindgren",
            "Nadia Petrov",
            "Elias Moreau"
    };

    private static final int[] YEARS = {2021, 2019, 2020, 2018, 2022, 2017};

    // ---- State & views ------------------------------------------------------
    private int currentIndex = 0;

    private TextView tvCounter;
    private ImageView ivArtwork;
    private TextView tvTitle;
    private TextView tvArtistYear;
    private MaterialButton btnPrevious;
    private MaterialButton btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();

        // After rotation Android recreates the Activity and passes the Bundle
        // we filled in onSaveInstanceState(). If it is not null, restore the index.
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX, 0);
        }

        setupListeners();
        showArtwork(); // draws the first artwork, or the restored one
    }

    /** Called before the Activity is destroyed (e.g. on rotation): save the index. */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private void bindViews() {
        tvCounter = findViewById(R.id.tvCounter);
        ivArtwork = findViewById(R.id.ivArtwork);
        tvTitle = findViewById(R.id.tvTitle);
        tvArtistYear = findViewById(R.id.tvArtistYear);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setupListeners() {
        btnPrevious.setOnClickListener(v -> {
            if (currentIndex > 0) {                 // never go below 0
                currentIndex--;
                showArtwork();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentIndex < IMAGES.length - 1) { // never go past the last item
                currentIndex++;
                showArtwork();
            }
        });

        // Clicking the artwork opens ArtworkDetailsActivity (explicit Intent).
        ivArtwork.setOnClickListener(v -> openDetails());
    }

    /** Updates every view so it matches currentIndex. */
    private void showArtwork() {
        // Safety net: keep the index inside the valid range 0..5
        // (protects against a corrupted/out-of-range saved value).
        if (currentIndex < 0) {
            currentIndex = 0;
        } else if (currentIndex > IMAGES.length - 1) {
            currentIndex = IMAGES.length - 1;
        }

        ivArtwork.setImageResource(IMAGES[currentIndex]);
        ivArtwork.setContentDescription(getString(
                R.string.artwork_description, TITLES[currentIndex], ARTISTS[currentIndex]));
        tvTitle.setText(TITLES[currentIndex]);
        tvArtistYear.setText(getString(
                R.string.artist_year_format, ARTISTS[currentIndex], YEARS[currentIndex]));
        tvCounter.setText(getString(
                R.string.counter_format, currentIndex + 1, IMAGES.length));

        // Previous is disabled on the first artwork, Next on the last one.
        btnPrevious.setEnabled(currentIndex > 0);
        btnNext.setEnabled(currentIndex < IMAGES.length - 1);
    }

    /** Explicit Intent: we name the target class (ArtworkDetailsActivity) directly. */
    private void openDetails() {
        Intent intent = new Intent(MainActivity.this, ArtworkDetailsActivity.class);
        intent.putExtra(EXTRA_TITLE, TITLES[currentIndex]);
        intent.putExtra(EXTRA_ARTIST, ARTISTS[currentIndex]);
        intent.putExtra(EXTRA_YEAR, YEARS[currentIndex]);
        startActivity(intent);
    }
}
