package com.el1t.photif;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class video extends Activity {
	static final int REQUEST_VIDEO_CAPTURE = 1;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dispatchTakeVideoIntent();
	}
	
	private void dispatchTakeVideoIntent() {
	    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	    if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
	    }
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
	    	System.out.println("Video intent received");
	        Uri videoUri = data.getData();
	        System.out.println(videoUri);
	        System.out.println(videoUri.getPath());
	        System.out.println("Video uri created");
	        MediaMetadataRetriever mmRetriever = new MediaMetadataRetriever();
	        System.out.println("please");
	        mmRetriever.setDataSource(getBaseContext(), videoUri);
	        System.out.println("absolute path received");
	        MediaPlayer mp = MediaPlayer.create(getBaseContext(), videoUri);
	        
	        OutputStream os, temp;
			try {
				os = new BufferedOutputStream( new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Pictures/gifit/movie.gif"));
				//imageStream = getContentResolver().openInputStream(selectedImage);
				//Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
				AnimatedGifEncoder e = new AnimatedGifEncoder();
				System.out.println("does this work");
				e.start(os);
				e.setRepeat(0);
				e.setDelay(500);
//				temp = new BufferedOutputStream( new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Pictures/gifit/temp1.png"));
//				mmRetriever.getFrameAtTime(1).compress(Bitmap.CompressFormat.PNG, 100, temp);
//				temp.close();
//				temp = new BufferedOutputStream( new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Pictures/gifit/temp2.png"));
//				mmRetriever.getFrameAtTime(2000000).compress(Bitmap.CompressFormat.PNG, 100, temp);
//				temp.close();
//				finish();
				for(long i = 1; i < mp.getDuration()*1000; i+=333000) {
					Bitmap bitmap = mmRetriever.getFrameAtTime(i, MediaMetadataRetriever.OPTION_CLOSEST);
					temp = new BufferedOutputStream( new FileOutputStream(Environment.getExternalStorageDirectory().getPath() + "/Pictures/gifit/temp" + i + ".png"));
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, temp);
					System.out.println("Index " + i);
					temp.close();
					e.addFrame(decodeUri(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/gifit/temp" + i + ".png"))));
				}
				mmRetriever.release();
				mp.release();
				e.finish();
				os.close();
				System.out.println("haias");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Toast.makeText(video.this, "Finished!", Toast.LENGTH_SHORT).show();
			finish();
	    }
	}
	private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 300;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE
               || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);

    }
}
