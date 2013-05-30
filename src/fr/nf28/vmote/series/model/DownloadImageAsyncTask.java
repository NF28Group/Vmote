package fr.nf28.vmote.series.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
	private final static String URL_DLLIMAGE_TVSHOW = "http://thetvdb.com/banners/";
	
	@Override
	protected Bitmap doInBackground(String... urls) {

		// params comes from the execute() call: params[0] is the url.
		try {
			return downloadImageUrl(urls[0]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// Given a URL, establishes an HttpUrlConnection and retrieves
	// the web page content as a InputStream, which it returns as
	// a string.
	private Bitmap downloadImageUrl(String imageUrl) throws IOException {
		InputStream is = null;
		Bitmap bmp = null;
		
		try {
			System.out.println(URL_DLLIMAGE_TVSHOW + imageUrl);
			URL url = new URL(URL_DLLIMAGE_TVSHOW + imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			
			is = conn.getInputStream();
			bmp = BitmapFactory.decodeStream(is);
		}
		finally {
			if (is != null) {
				is.close();
			} 
		}
		
		return bmp;
	}
}
