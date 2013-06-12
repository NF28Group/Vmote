package fr.nf28.vmote.lib;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import fr.nf28.vmote.play.classes.Media;
import fr.nf28.vmote.play.classes.Subtitle;
import fr.nf28.vmote.play.classes.SubtitleList;
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
	
	 public static Media getCurrentMediaStatus(){
		Media current_media = new Media();
		String tmp;
		
		JsonObject obj = getJsonObject();
		if(obj == null) return current_media;
		JsonElement state = obj.get("state");
		JsonElement volume = obj.get("volume");
		JsonElement loop = obj.get("loop");
		JsonElement repeat = obj.get("repeat");
		JsonElement random = obj.get("random");
		JsonObject information = (JsonObject) obj.get("information");
		if(information == null) return current_media;
		JsonObject category = (JsonObject) information.get("category");
		JsonObject meta = (JsonObject) category.get("meta");
        JsonElement filename = meta.get("filename");
        current_media.setName(filename.toString());
        current_media.setState(state.toString());
        current_media.setVolume(volume.toString());
        current_media.setRepeat(repeat.toString());
        current_media.setLoop(loop.toString());
        current_media.setRandom(random.toString());

        tmp = (obj.get("length") == null)?"":obj.get("length").toString();
        current_media.setDuree(tmp);
        
        tmp = (meta.get("date") == null)?"":meta.get("date").toString().replace("\"", "");
        current_media.setDate(tmp);
        
        tmp = (meta.get("artist") == null)?"":meta.get("artist").toString().replace("\"", "");
        current_media.setArtist(tmp);
        
        tmp = (meta.get("album") == null)?"":meta.get("album").toString().replace("\"", "");
        current_media.setAlbum(tmp);
        
        tmp = (meta.get("HISTORY") == null)?"":meta.get("HISTORY").toString();
        current_media.setHistory(tmp);
        
        tmp = (meta.get("genre") == null)?"":meta.get("genre").toString().replace("\"", "");
        current_media.setGender(tmp);
        
        ArrayList<JsonObject> streamList = JsonReader.getStreamList(category);
        SubtitleList audioList = new SubtitleList();
        SubtitleList subtitleList = new SubtitleList();
        int size = streamList.size();
        JsonElement currElem;
        Subtitle currSub;
        for(int i=0; i<size ; i++) {
    		currElem = streamList.get(i).get("Type");
    		currSub = new Subtitle("Piste " + i, i);
    		if(currElem.toString().replace("\"", "").equals("Audio")) {
    			audioList.add(currSub);
    		}
    		else if(currElem.toString().replace("\"", "").equals("Subtitle")) {
    			subtitleList.add(currSub);
    		}
        }

        

    	/*JsonElement trameLength = obj.get("");
        JsonElement trameHeight = obj.get("");
        JsonElement frameRate = obj.get("");
        current_media.setFrameRate(frameRate.toString());
    	current_media.setHauteurtrame(trameHeight.toString());
    	current_media.setLargeurTrame(trameLength.toString());*/
        
        System.out.println(current_media.toString());
		return current_media;
	}
	
	public static ArrayList<JsonObject> getStreamList(JsonObject parent) {
		ArrayList<JsonObject> res = new ArrayList<JsonObject>();
		int i = 0;
		
		try {
			while(parent.has("Stream " +i)) {
				res.add((JsonObject) parent.get("Stream " +i));
				i++;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
