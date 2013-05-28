package fr.nf28.vmote.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class JsonReader {
	private static final String BASE_URL =
            "http://192.168.0.10:8080/requests/status.json";
	
	private static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }

	}
	
	public static void ToJson(){
		String json;
		try {
			json = readUrl(BASE_URL);
			Gson gson = new Gson(); 
			
			Media media = gson.fromJson(json, Media.class);
			Log.i("JSON", media.toString());
			System.out.println(media.toString());

			/*Fullscreen Fullscreen = gson.fromJson(json, Fullscreen.class);
			Log.i("JSON", Fullscreen.fullscreen);
			System.out.println(Fullscreen.fullscreen);*/
			/*Information information = gson.fromJson(json, Information.class);
			
			for (Category category : information.categories){
				for (Meta meta : category.metas){
					for (Filename filename : meta.filenames){
						Log.i("JSON", filename.name);
						System.out.println("ICI "+filename.name);
					}
				}
			}*/
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
