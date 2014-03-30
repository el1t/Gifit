package com.el1t.photif;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class gallery extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.package.de.wildcard.animatedgif.player");
		startActivity(LaunchIntent);
	}
}
