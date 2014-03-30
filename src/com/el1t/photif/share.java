package com.el1t.photif;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.el1t.photif.util.BitmapUtils;
import com.el1t.photif.login.ImgurAuthorization;
import com.el1t.photif.login.LoginActivity;
import com.el1t.photif.login.RefreshAccessTokenTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class share extends Activity {
	private InputStream image;
	private String mImgurUrl;
	private MyImgurUploadTask mImgurUploadTask;
	final private int ID = 1234;
	final private int FINISH = 283;
	private boolean repeat = false;
    private static Context context;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("Share.class started");
		share.context = getApplicationContext();
		//if(!ImgurAuthorization.getInstance().isLoggedIn())
		if(!repeat) {
			repeat = true;
			startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), ID);
		}
	}
	
    public static Context getAppContext() {
        return share.context;
    }
	
	protected void dialog() {
		//create the send intent
		Intent shareIntent =  new Intent(android.content.Intent.ACTION_SEND);

		//set the type
		shareIntent.setType("image/gif");
		  
		//add a subject
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share gif");
		  
		//add the message
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, mImgurUrl);
		  
		//start the chooser for sharing
		startActivityForResult(Intent.createChooser(shareIntent, "Share"), FINISH);
	}

	private class MyImgurUploadTask extends ImgurUploadTask {
		public MyImgurUploadTask(String image) {
			super(image);
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (mImgurUploadTask != null) {
				boolean cancelled = mImgurUploadTask.cancel(false);
				if (!cancelled)
					this.cancel(true);
			}
			mImgurUploadTask = this;
			mImgurUrl = null;
		}
		
		@Override
		protected void onPostExecute(String imageId) {
			super.onPostExecute(imageId);
			mImgurUploadTask = null;
			if (imageId != null) {
				mImgurUrl = "http://imgur.com/" + imageId;
			} else {
				mImgurUrl = null;
			}
			System.out.println("Sharing " + mImgurUrl);
			dialog();
		}
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
	    switch(requestCode) {
	    case ID:
	    	System.out.println("Request Received");
			Intent intent = getIntent();
		    String action = intent.getAction();
		    String type = intent.getType();
		    System.out.println("Uploading");
			if (Intent.ACTION_SEND.equals(action) && type != null) {
		        if (type.startsWith("image/")) {
		        	new MyImgurUploadTask(intent.getStringExtra("Uri")).execute();
		        }
		    }
			break;
	    case FINISH:
	    	finish();
	    }
	}
}
