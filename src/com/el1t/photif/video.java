package com.el1t.photif;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

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
//	        File videoFile = new File(videoUri.getPath());
//	        System.out.println("Path created");
	        MediaMetadataRetriever mmRetriever = new MediaMetadataRetriever();
	        System.out.println("please");
	        mmRetriever.setDataSource(videoUri.getPath());
	        System.out.println("absolute path received");
	        MediaPlayer mp = MediaPlayer.create(getBaseContext(), videoUri);
	        
	        OutputStream os;
			try {
				os = new BufferedOutputStream( new FileOutputStream("/external_sd/Pictures/gifit/movie.gif"));
				//imageStream = getContentResolver().openInputStream(selectedImage);
				//Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
				AnimatedGifEncoder e = new AnimatedGifEncoder();
				System.out.println("does this work");
				e.start(os);
				e.setDelay(100);
				for(int i = 0; i < mp.getDuration(); i+=333) {
					Bitmap bitmap = mmRetriever.getFrameAtTime(i);
					e.addFrame(bitmap);
				}
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
			finish();
	    }
	}
}
