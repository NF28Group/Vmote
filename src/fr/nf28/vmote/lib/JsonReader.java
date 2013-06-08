package fr.nf28.vmote.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.nf28.vmote.play.classes.Media;
import fr.nf28.vmote.play.model.VLCConnection;

public class JsonReader {
	
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
	
	private static JsonObject getJsonObject(){
		String json;
		try {
			json = readUrl(VLCConnection.BASE_URL);
			
	        JsonParser parser = new JsonParser();
			JsonObject obj = (JsonObject)parser.parse(json);
			return obj;
			
		} 
		catch (Exception e) {
			System.out.println("Error catched in getJsonObject");
			e.printStackTrace();
			return null;
		}

	}
	
	/*public static Media getCurrentMediaStatus(){
		Media current_media = new Media();
		JsonObject obj = getJsonObject();
		if(obj == null) return current_media;
		JsonElement state = obj.get("state");
		JsonElement volume = obj.get("volume");
		JsonObject information = (JsonObject) obj.get("information");
		if(information == null) return current_media;
		JsonObject category = (JsonObject) information.get("category");
		JsonObject meta = (JsonObject) category.get("meta");
        JsonElement filename = meta.get("filename");
        current_media.setName(filename.toString());
        current_media.setState(state.toString());
        current_media.setVolume(volume.toString());
        System.out.println(current_media.toString());
		return current_media;
	}*/
	
	/*
	 * je pense qui faut mettre chaque get dans des try catch pour faire ca propre...
	 * */
	 public static Media getCurrentMediaStatus(){
		Media current_media = new Media();
		JsonObject obj = getJsonObject();
		if(obj == null) return current_media;
		JsonElement state = obj.get("state");
		JsonElement volume = obj.get("volume");
		JsonObject information = (JsonObject) obj.get("information");
		if(information == null) return current_media;
		JsonObject category = (JsonObject) information.get("category");
		JsonObject meta = (JsonObject) category.get("meta");
        JsonElement filename = meta.get("filename");
        current_media.setName(filename.toString());
        current_media.setState(state.toString());
        current_media.setVolume(volume.toString());
        
        //page details
        if(isPresent("length", obj)) {
        	JsonElement duration = obj.get("length");
           	current_media.setDuree(duration.toString());
        }
        else {
           	current_media.setDuree("");
        }
        
        if(isPresent("date", meta)) {
            JsonElement date = meta.get("date");
           	current_media.setDate(date.toString());
        }
        else {
           	current_media.setDate("");
        }
        
        if(isPresent("artist", meta)) {
            JsonElement artist = meta.get("artist");
           	current_media.setArtist(artist.toString());
        }
        else {
           	current_media.setArtist("");
        }
        
        if(isPresent("album", meta)) {
            JsonElement album = meta.get("album");
           	current_media.setAlbum(album.toString());
        }
        else {
           	current_media.setAlbum("");
        }
        
        if(isPresent("HISTORY", meta)) {
            JsonElement history = meta.get("HISTORY");
           	current_media.setHistory(history.toString());
        }
        else {
           	current_media.setHistory("");
        }
        
        if(isPresent("gender", meta)) {
            JsonElement gender = meta.get("genre");
           	current_media.setGender(gender.toString());
        }
        else {
           	current_media.setGender("");
        }
        
        // change selon la langue...
        JsonElement trameLength = obj.get("");
        JsonElement trameHeight = obj.get("");
        JsonElement frameRate = obj.get("");
                
        
        System.out.println(current_media.toString());
		return current_media;
	}
	 
	// dépend de la langue de vlc...
	public static Media addSubtitles(Media media) {
		if(media == null) return null;
		
		
		return media;
	}
	 
	public static boolean isPresent(String element, JsonObject obj) {
		try  {
			obj.get(element);
		}
		catch(Exception e) {
			return false;
		}
		return true;
	}
}
