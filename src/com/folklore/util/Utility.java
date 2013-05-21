package com.folklore.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class Utility {

	public static boolean isAppInstalled(String uri, Context context) {
	    PackageManager pm = context.getPackageManager();
	    boolean installed = false;
	    try {
	        pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
	        installed = true;
	    } catch (PackageManager.NameNotFoundException e) {
	        installed = false;
	    }
	    return installed;
	}
	
}
