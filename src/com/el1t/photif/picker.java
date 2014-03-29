package com.el1t.photif;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

public class picker extends Activity {
	private static final int SELECT_PHOTO = 100;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
		photoPickerIntent.setType("image/*");
		photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE , true);
		startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), SELECT_PHOTO);	
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
	    super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 

	    switch(requestCode) {
	    case SELECT_PHOTO:
	        if(resultCode == RESULT_OK){
	        	System.out.println("Picker.java: Recieved");
	    		Intent i = new Intent(picker.this, convert.class);
	    		i.setType("image/*");
	    		i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE , true);
	    		i.setAction(Intent.ACTION_SEND_MULTIPLE);
	    		i.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageReturnedIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM) );
	    		startActivity(Intent.createChooser(i, "Select Picture"));
	        	/*System.out.println(imageReturnedIntent.getData());
	            ArrayList<Uri> parcelables = imageReturnedIntent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
	            if(parcelables == null)
	            	return;
	            //InputStream imageStream;
	            OutputStream os;
				try {
					os = new BufferedOutputStream( new FileOutputStream("/external_sd/test.gif"));
					//imageStream = getContentResolver().openInputStream(selectedImage);
					//Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
					AnimatedGifEncoder e = new AnimatedGifEncoder();
					System.out.println("does this work");
					e.start(os);
					for(Parcelable parcel : parcelables) {
						Bitmap imageFinal = decodeUri((Uri) parcel);
						e.addFrame(imageFinal);
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
				}*/
	        }
	    }
	}
	
}
