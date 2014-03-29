package com.el1t.photif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        TextView play = (TextView) findViewById(R.id.play);      
        
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, picker.class);
				i.setType("image/*");
				i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE , true);
				i.setAction(Intent.ACTION_SEND_MULTIPLE);
				startActivity(Intent.createChooser(i, "Select Picture"));
			}
		});
    }
}