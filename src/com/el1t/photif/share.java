package com.el1t.photif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class share extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		//create the send intent  
		Intent shareIntent =  new Intent(android.content.Intent.ACTION_SEND);  
		  
		//set the type  
		shareIntent.setType("image/gif");  
		  
		//add a subject  
		shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,   
		 "Insert Subject Here");  
		  
		//build the body of the message to be shared  
		String shareMessage = "Insert message body here.";  
		  
		//add the message  
		shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,   
		 shareMessage);  
		  
		//start the chooser for sharing  
		startActivity(Intent.createChooser(shareIntent,   
		 "Insert share chooser title here"));
	}
}
