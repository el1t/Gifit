package com.el1t.photif;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

public class convert extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();
	    System.out.println("Convert started");
		if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if (type.startsWith("image/")) {
	            handleSendImage(intent); // Handle single image being sent
	        }
	    } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
	        if (type.startsWith("image/")) {
	            handleSendMultipleImages(intent); // Handle multiple images being sent
	        }
	    } else {
	    	System.out.println("Other intent received: " + intent);
	    }
	}
	
	protected void handleSendImage(Intent i) {
		Uri imageUri = (Uri) i.getParcelableExtra(Intent.EXTRA_STREAM);
	    if (imageUri != null) {
	    	System.out.println(i.getData());
            //InputStream imageStream;
            OutputStream os;
			try {
				os = new BufferedOutputStream( new FileOutputStream("/external_sd/test.gif"));
				//imageStream = getContentResolver().openInputStream(selectedImage);
				//Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
				AnimatedGifEncoder e = new AnimatedGifEncoder();
				System.out.println("does this work");
				e.start(os);
				e.addFrame(decodeUri(imageUri));
				promptDelay(e);
				e.finish();
				os.close();
				System.out.println("haias");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    } else {
	    	System.out.println("ERROR: Null received send");
	    }
	    finish();
	}
	
	protected void handleSendMultipleImages(Intent i) {
    	System.out.println(i.getData());
        ArrayList<Uri> parcelables = i.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if(parcelables == null) {
     	    System.out.println("ERROR: Null received send_multiple");
        	finish();
        }
        //InputStream imageStream;
        OutputStream os;
		try {
			os = new BufferedOutputStream( new FileOutputStream("/external_sd/test.gif"));
			//imageStream = getContentResolver().openInputStream(selectedImage);
			//Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
			AnimatedGifEncoder e = new AnimatedGifEncoder();
			System.out.println("does this work");
			e.start(os);
			for(Uri parcel : parcelables) {
				Bitmap imageFinal = decodeUri(parcel);
				e.addFrame(imageFinal);
			}
			promptDelay(e);
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
	
	public void promptDelay(final AnimatedGifEncoder e) {
		final EditText input = new EditText(this);
		AlertDialog.Builder ab = new AlertDialog.Builder(this)
	    .setTitle("Delay")
	    .setMessage("Set Delay")
	    .setView(input)
	    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	           String value = input.getText().toString();
	           e.setDelay((int) (Float.parseFloat(value) * 1000));
	           return;
	        }
	    });
		AlertDialog a = ab.create();
		a.show();
		a.dismiss();
//		while(a.isShowing()) {
//			System.
//		}
		//a.dismiss();
		return;
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
