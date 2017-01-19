package com.veer.hiddenscreenshotsample;

import android.graphics.Bitmap;
import android.media.projection.MediaProjection;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.veer.hiddenshot.HiddenShot;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.take).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

        final Bitmap shot = HiddenShot.getInstance().buildShot(MainActivity.this);

        HiddenShot.getInstance().saveShot(MainActivity.this, shot, "view");
        Toast.makeText(MainActivity.this, "taken", Toast.LENGTH_SHORT).show();
      }
    });

    findViewById(R.id.takeAndShare).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

        HiddenShot.getInstance().buildShotAndShare(MainActivity.this);
        Toast.makeText(MainActivity.this, "taken", Toast.LENGTH_SHORT).show();
      }
    });

    final Button continousShot=(Button)findViewById(R.id.takeContinousShot);
    final Button stopContinousShot=(Button)findViewById(R.id.stopContinousShot);
    continousShot.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        continousShot.setEnabled(false);
        stopContinousShot.setEnabled(true);
        HiddenShot.getInstance().buildContinousShot(MainActivity.this, 500);
      }
    });
    stopContinousShot.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        continousShot.setEnabled(true);
        stopContinousShot.setEnabled(false);
        HiddenShot.getInstance().stopContinousShot();
      }
    });


  }

  @Override protected void onDestroy() {
    HiddenShot.getInstance().stopContinousShot();
    super.onDestroy();
  }
}
