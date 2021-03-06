package com.el1t.photif;

import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

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
	        if(resultCode == RESULT_OK) {
	        	System.out.println("Picker.java: Received");
	        	ClipData photos = imageReturnedIntent.getClipData();
	        	if(photos == null) {
		    		Intent intent = new Intent(picker.this, convert.class);
		    		intent.setType("image/*");
		    		intent.setAction(Intent.ACTION_SEND);
		    		intent.putExtra(Intent.EXTRA_STREAM, imageReturnedIntent.getData());
		    		promptDelay(intent);
	        	} else {
		        	ArrayList<Uri> photoArray = new ArrayList<Uri>();
		        	for(int i = 0; i < photos.getItemCount(); i++)
		        		photoArray.add(photos.getItemAt(i).getUri());
		    		Intent intent = new Intent(picker.this, convert.class);
		    		intent.setType("image/*");
		    		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		    		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, photoArray);
		    		promptDelay(intent);
	        	}
	        }
	    }
	    //finish();
	}
	AlertDialog a;
	public void promptDelay(final Intent intent) {
		final EditText input = new EditText(this);
		AlertDialog.Builder ab = new AlertDialog.Builder(this, android.R.style.Theme_Holo_Dialog)
	    .setTitle("Set Delay Between Images (Seconds)")
	    .setView(input)
	    .setPositiveButton("Done", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	           String value = input.getText().toString();
	           if(!value.equals(""))
	        	   intent.putExtra("delay", (int) (Float.parseFloat(value) * 1000));
	           getWindow().setSoftInputMode(
	        		      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	           startActivity(Intent.createChooser(intent, "Select Picture"));
	           finish();
	        }
	    });
		a = ab.create();
		a.show();
//		while(a.isShowing()) {
//			System.
//		}
		//a.dismiss();
		return;
	}
}
