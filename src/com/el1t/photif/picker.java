package com.el1t.photif;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

public class picker extends Activity {
	private static final int SELECT_PHOTO = 100;
	protected void onCreate(Bundle savedInstanceState) {
		Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
		photoPickerIntent.setType("image/*");
		startActivityForResult(photoPickerIntent, SELECT_PHOTO); 

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) { 
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){  
	            Uri selectedImage = imageReturnedIntent.getData();
	            InputStream imageStream;
	            OutputStream os;
				try {
					os = new FileOutputStream("/mnt/extSdCard/test.gif");
					imageStream = getContentResolver().openInputStream(selectedImage);
					 Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
					 AnimatedGifEncoder e = new AnimatedGifEncoder();
					 e.start(os);
					 e.addFrame(yourSelectedImage);
					 e.finish();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }
	}
}
