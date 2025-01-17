package com.mapbox.mapboxandroiddemo.examples.labs;

import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mapbox.mapboxandroiddemo.R;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

/**
 * Enter picture-in-picture mode with a map being persisted. Only works on devices running Android O and above.
 */
public class PictureInPictureActivity extends AppCompatActivity {

  private MapView mapView;
  private FloatingActionButton addPictureFab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Mapbox access token is configured here. This needs to be called either in your application
    // object or in the same activity which contains the mapview.
    Mapbox.getInstance(this, getString(R.string.access_token));

    // This contains the MapView in XML and needs to be called after the access token is configured.
    setContentView(R.layout.activity_picture_in_picture);

    mapView = findViewById(R.id.mapView);
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync(new OnMapReadyCallback() {
      @Override
      public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.SATELLITE_STREETS, new Style.OnStyleLoaded() {
          @Override
          public void onStyleLoaded(@NonNull Style style) {
            addPictureFab = findViewById(R.id.add_window_fab);
            addPictureFab.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                try {
                  enterPictureInPictureMode();
                } catch (Exception exception) {
                  Toast.makeText(PictureInPictureActivity.this, R.string.no_picture_in_picture_support,
                    Toast.LENGTH_SHORT).show();
                }
              }
            });
          }
        });
      }
    });
  }

  @Override
  public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
    super.onPictureInPictureModeChanged(isInPictureInPictureMode);

    addPictureFab.setVisibility(isInPictureInPictureMode ? View.GONE : View.VISIBLE);

    if (isInPictureInPictureMode) {
      // Hide the controls in picture-in-picture mode.
      getSupportActionBar().hide();
    } else {
      // Restore the playback UI based on the playback status.
      getSupportActionBar().show();
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    mapView.onResume();
  }

  @Override
  protected void onStart() {
    super.onStart();
    mapView.onStart();
  }

  @Override
  protected void onStop() {
    super.onStop();
    mapView.onStop();
  }

  @Override
  public void onPause() {
    super.onPause();
    mapView.onPause();
  }

  @Override
  public void onLowMemory() {
    super.onLowMemory();
    mapView.onLowMemory();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mapView.onDestroy();
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    mapView.onSaveInstanceState(outState);
  }
}
