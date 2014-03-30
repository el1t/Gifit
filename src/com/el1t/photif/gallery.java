package com.el1t.photif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class gallery extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("de.wildcard.animatedgif.player");
		if(LaunchIntent != null)
			startActivity(LaunchIntent);
	}
	protected void onRestart() {
		super.onRestart();
		finish();
	}
}
