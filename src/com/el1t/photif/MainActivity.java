package com.el1t.photif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
        ImageView video = (ImageView) findViewById(R.id.video);   
        ImageView photo = (ImageView) findViewById(R.id.photo);
        ImageView gallery = (ImageView) findViewById(R.id.gallery);
        ImageView share = (ImageView) findViewById(R.id.share);
        ImageView logo1 = (ImageView) findViewById(R.id.logo1);
        
        Animation translate = new TranslateAnimation(0, 360,-5,-5);
        translate.setDuration(1500);
        translate.setInterpolator(new AccelerateDecelerateInterpolator());
        translate.setFillEnabled(true);
        translate.setFillBefore(true);
        translate.setFillAfter(true);
        logo1.startAnimation(translate);
        
		photo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, picker.class);
				startActivity(i);
			}
		});
		
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, share.class);
				startActivity(i);
			}
		});
    }
}