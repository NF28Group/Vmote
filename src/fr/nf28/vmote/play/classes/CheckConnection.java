package fr.nf28.vmote.play.classes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnection {

	CheckConnection(){
		
	}
	
	/*
	 * V�rifie si la Wi-Fi est connect�e  
	*/
	public static boolean isWifiConnected(Context context) {
		System.out.println("Passage dans isConnected de la classe CheckConnection");
	    ConnectivityManager connectivityManager = (ConnectivityManager)
	        context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = null;
	    if (connectivityManager != null) {
	        networkInfo =
	            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
	    }
	    return networkInfo == null ? false : networkInfo.isConnected();
	}
}
