package com.veer.hiddenshot;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.content.Context.ACTIVITY_SERVICE;
import static android.view.View.MeasureSpec;

/**
 * The type HiddenShot class.
 */
public class HiddenShot {
  private static final HiddenShot ourInstance = new HiddenShot();

  public static HiddenShot getInstance() {
    return ourInstance;
  }

  private boolean isActive = false;

  private Handler mhandler;

  private TakeShot takeShot;

  private HiddenShot() {
  }

  public Bitmap buildShot(Activity activity) {
    View v = activity.getWindow().getDecorView().getRootView();
    v.setDrawingCacheEnabled(true);
    v.buildDrawingCache(true);
    Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
    v.setDrawingCacheEnabled(false); // clear drawing cache
    return b;
  }

  public Uri saveShot(Context context, Bitmap image, String filename) {
    File bitmapFile = getOutputMediaFile(filename);
    if (bitmapFile == null) {
      Log.d(TAG, "Error creating media file, check storage permissions: ");
      return null;
    }
    try {
      FileOutputStream fos = new FileOutputStream(bitmapFile);
      image.compress(Bitmap.CompressFormat.PNG, 90, fos);
      fos.close();
      MediaScannerConnection.scanFile(context, new String[] { bitmapFile.getPath() },
          new String[] { "image/jpeg" }, null);
      return Uri.fromFile(bitmapFile);
    } catch (FileNotFoundException e) {
      Log.d(TAG, "File not found: " + e.getMessage());
      return null;
    } catch (IOException e) {
      Log.d(TAG, "Error accessing file: " + e.getMessage());
      return null;
    }
  }

  private File getOutputMediaFile(String filename) {
    // To be safe, you should check that the SDCard is mounted
    // using Environment.getExternalStorageState() before doing this.
    File mediaStorageDirectory = new File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            + File.separator);
    // Create the storage directory if it does not exist
    if (!mediaStorageDirectory.exists()) {
      if (!mediaStorageDirectory.mkdirs()) {
        return null;
      }
    }
    // Create a media file name
    String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
    File mediaFile;
    String mImageName = filename + timeStamp + ".jpg";
    mediaFile = new File(mediaStorageDirectory.getPath() + File.separator + mImageName);
    return mediaFile;
  }

  public void buildContinousShot(Activity activity, long millisecond) {
    isActive=true;
    mhandler = new Handler();
    takeShot=new TakeShot(activity,millisecond);
    mhandler.postDelayed(takeShot, millisecond);
  }

  class TakeShot implements Runnable {
    private final Activity activity;
    private long millisec;

    TakeShot(Activity activity,long millisec) {
      this.activity = activity;
      this.millisec=millisec;
    }

    @Override public void run() {
      if (isActive) {
        Bitmap bitmap = buildShot(activity);
        saveShot(activity, bitmap, "shot");
        mhandler.postDelayed(takeShot, millisec);
      }
    }
  }


  public void stopContinousShot()
  {
   if(isActive)
   {
     isActive=false;
     mhandler.removeCallbacks(takeShot);
   }
  }

  public void buildShotAndShare(Activity activity) {
    Bitmap bitmap = buildShot(activity);
    Uri uri = null;
    try {
      uri = saveShot(activity, bitmap, "screenshot");
    } catch (NullPointerException e) {
      Log.e("error", "null uri for file");
      return;
    }
    shareShot("Choose an app", activity, uri);
  }

  private void shareShot(String s, Activity activity, Uri uri) {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
    shareIntent.setType("image/*");
    try {
      activity.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
    } catch (android.content.ActivityNotFoundException ex) {

      Toast.makeText(activity, "There is no app that can handle sharing", Toast.LENGTH_LONG).show();
    }
  }

  public void buildShotAndShare(Activity activity,String shareMsg) {
    Bitmap bitmap = buildShot(activity);
    Uri uri = null;
    try {
      uri = saveShot(activity, bitmap, "screenshot");
    } catch (NullPointerException e) {
      Log.e("error", "null uri for file");
      return;
    }
    shareShot("Choose an app", activity, uri,shareMsg);
  }

  private void shareShot(String s, Activity activity, Uri uri,String message) {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
    shareIntent.putExtra(Intent.EXTRA_TEXT,message);
    shareIntent.setType("image/*");
    try {
      activity.startActivity(Intent.createChooser(shareIntent, "Choose an app"));
    } catch (android.content.ActivityNotFoundException ex) {

      Toast.makeText(activity, "There is no app that can handle sharing", Toast.LENGTH_LONG).show();
    }
  }
}