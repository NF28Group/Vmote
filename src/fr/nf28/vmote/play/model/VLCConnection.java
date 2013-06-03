package fr.nf28.vmote.play.model;

import java.util.concurrent.ExecutionException;

import fr.nf28.vmote.R;
import fr.nf28.vmote.lib.HttpRequest;
import fr.nf28.vmote.lib.JsonReader;
import fr.nf28.vmote.play.classes.CheckConnection;
import fr.nf28.vmote.play.classes.LaunchError;
import fr.nf28.vmote.play.classes.Media;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


public class VLCConnection {
	
	private static Media media;
	
	public static final String BASE_URL =
            "http://192.168.0.15:8080/requests/status.json";
    /* IP Milio :
 
	public static final String BASE_URL =
            "http://192.168.0.10:8080/requests/status.json";
    */

    private static final String PARAM_COMMAND = "command";
    //private static final String PARAM_INPUT = "input";

    private static final String COMMAND_PAUSE = "pl_pause";
    private static final int TASK_PAUSE = 1;
    private static final String COMMAND_STOP = "pl_stop";
    private static final int TASK_STOP = 2;
    private static final String COMMAND_NEXT = "pl_next";
    private static final int TASK_NEXT = 3;
    private static final String COMMAND_PREVIOUS = "pl_previous";
    private static final int TASK_PREVIOUS = 4;
    private static final String COMMAND_RANDOM = "pl_random";
    private static final int TASK_RANDOM = 5;
    private static final String COMMAND_LOOP = "pl_loop";
    private static final int TASK_LOOP = 6;
    private static final String COMMAND_REPEAT = "pl_repeat";
    private static final int TASK_REPEAT = 7;
    private static final String COMMAND_VOLUME = "volume&val=%VALUE%";
    private static final int TASK_VOLUME = 8;
    
	private VLCConnection() {
		media = new Media();
	}
	
    private static void validateResponse(HttpRequest request) throws Exception {
        if (!request.ok()) {
            throw new Exception("Request failed with code: " + request.code());
        }
    }
    

    /* 
     * Definition de la fonction lauchCheck
     * return : 
     * 		1 : OK
     * 		2 : Pas de Wi-Fi
     * 		3 : Pas VLC lance
     * 		4 : Pas de media dans VLC
     * 		
     * */
    public LaunchError lauchCheck(Context c, View rv){
    	LaunchError error = new LaunchError();
    	if(CheckConnection.isWifiConnected(c)){
    		LaunchCheck lauchCheck = new LaunchCheck();
    		lauchCheck.execute(rv);
    		try {
				return lauchCheck.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else{
    		/* Le Wi-Fi n'est pas connecte*/
    		error.setEtat(2);
    		error.setMessage("Veuillez vous connecter a un reseau Wi-Fi !");
    		return error;
    	}
    	return error;
    }
    
    private class LaunchCheck extends AsyncTask <View, LaunchError, LaunchError> {
    	@Override
    	protected LaunchError doInBackground(View... rv) {
        	LaunchError error = new LaunchError();
    		HttpRequest request = HttpRequest.get(BASE_URL, true,
                    PARAM_COMMAND, "test").readTimeout(2000).connectTimeout(2000);
            try {
    			validateResponse(request);
    		} 
            catch (Exception e) {
    			e.printStackTrace();
                error.setEtat(3);
                error.setMessage("Veuillez lancer VLC !");
    			return error;
    		}
            request.body();
            
            media = JsonReader.getCurrentMediaStatus();
            
            if(media.getName() == "0"){
                error.setEtat(4);
                error.setMessage("Veuillez lancer un m�dia sur VLC !");
    			return error;
            }
    		else{
                error.setEtat(1);
                error.setMessage("Pas de probl�me !");
                updateMedia(rv[0]);
    			return error;
    		}
    	}

        protected void onPostExecute(LaunchError result) {
        	System.out.println("onPostExecute result = " + result);
        }
    }
    
    /* D�finition de la check Media*/
	public void checkMedia(View rv) throws Exception {
    	new CheckMedia().execute(rv);
    }
    
	private class CheckMedia extends AsyncTask <View, Void, Void> {
    	@Override
    	protected Void doInBackground(View... rv) {
    		String current_media = JsonReader.getCurrentMediaStatus().getName();
    		if(current_media == "0"){
        		System.out.println("0");
    			media.setName("Pas de m�dia ouvert");
    			}
    		else{
    			media.setName(current_media);
    		}
    		updateMedia(rv[0]);
    		return null;
    	}

    }
	
	private class Command extends AsyncTask <Integer, Integer, Boolean> {
    	protected Boolean doInBackground(Integer... id_command) {
    		String command_to_execute;
    		
    		switch(id_command[0]){
    		case TASK_PAUSE : 
    			command_to_execute = COMMAND_PAUSE;
    			break;
    		case TASK_STOP : 
    			command_to_execute = COMMAND_STOP;
    			break;
    		case TASK_NEXT : 
    			command_to_execute = COMMAND_NEXT;
    			break;
    		case TASK_PREVIOUS : 
    			command_to_execute = COMMAND_PREVIOUS;
    			break;
    		case TASK_RANDOM : 
    			command_to_execute = COMMAND_RANDOM;
    			break;
    		case TASK_LOOP : 
    			command_to_execute = COMMAND_LOOP;
    			break;
    		case TASK_REPEAT : 
    			command_to_execute = COMMAND_REPEAT;
    			break;
    		case TASK_VOLUME : 
    			command_to_execute = COMMAND_VOLUME.replace("%VALUE%", String.valueOf(id_command[1]));;
    			break;
    		default : command_to_execute = "";    			
    		}
    		
	    	HttpRequest request = HttpRequest.get(BASE_URL, true,
	                PARAM_COMMAND, command_to_execute);
	        try {
				validateResponse(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	    		return false;
			}
			try {
		        request.body();
				Thread.sleep(1000);
				System.out.println(JsonReader.getCurrentMediaStatus());
				media.setName((JsonReader.getCurrentMediaStatus().getName() == "0")?"Stop":JsonReader.getCurrentMediaStatus().getName());
			} catch (InterruptedException e) {
				System.out.println("Erreur doInBackground dans class Command");
				e.printStackTrace();
			}
    		return true;
    	}
    	
        protected void onProgressUpdate(Integer... progress) {
        	System.out.println(progress);
        }


        protected void onPostExecute(Boolean result) {
        	System.out.println("onPostExecute class Command " +result);
        }
    }
    
    /* 
     * 
     * FONCTIONS DE PILOTAGE DU MEDIA
     * 
     */
	
    /* D�finition de la fonction PAUSE */
	public void pause(View rv) {
		Command pause_task = new Command();
		pause_task.execute(TASK_PAUSE);
    	updateMediaWhenTaskEnds(rv,pause_task);  	
    }
    
    /* D�finition de la fonction STOP */
	public void stop(View rv) {
		Command stop_task = new Command();
		stop_task.execute(TASK_STOP);
    	updateMediaWhenTaskEnds(rv,stop_task);  
    }
    
    /* D�finition de la fonction NEXT */
	public void next(View rv) {
		Command next_task = new Command();
		next_task.execute(TASK_NEXT);
    	updateMediaWhenTaskEnds(rv,next_task); 
    }
    
    /* D�finition de la fonction PREVIOUS */
	public void previous(View rv)  {
		Command previous_task = new Command();
		previous_task.execute(TASK_PREVIOUS);
    	updateMediaWhenTaskEnds(rv,previous_task); 
    }
    
    /* D�finition de la fonction RANDOM */
	public void random() {
		Command random_task = new Command();
		random_task.execute(TASK_RANDOM); 
    }
    
    /* D�finition de la fonction LOOP */
	public void loop() throws Exception {
		Command loop_task = new Command();
		loop_task.execute(TASK_LOOP); 
    }
    
    /* D�finition de la fonction REPEAT */
	public void repeat() {
		Command repeat_task = new Command();
		repeat_task.execute(TASK_REPEAT); 
    }
    
    /* D�finition de la fonction VOLUME */
	public void volume(int d) {
		Command volume_task = new Command();
		volume_task.execute(TASK_VOLUME,d);
    }

    /* 
     * 
     * FIN FONCTIONS DE PILOTAGE DU MEDIA
     * 
     */
    
    /* 
     * 
     * FONCTIONS DE MISE A JOUR DU MEDIA
     * 
     */
    
	public void updateMedia(View rv) {
		upateNameMedia((TextView) rv.findViewById(R.id.textNameMedia));
		updateVolumeMedia((SeekBar) rv.findViewById(R.id.seekBarPlaySound));
    }
	
	public void updateMediaWhenTaskEnds(View rv, Command task) {		
		while(true){
			try {
				System.out.println("updateMediaWhenTaskEnds get = " +task.get());
				System.out.println("updateMediaWhenTaskEnds getStatus = " +task.getStatus());
				if(task.get()){
					updateMedia(rv);
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
	private void upateNameMedia(TextView tv){
    	tv.setText(media.getName());
	}
	
	private void updateVolumeMedia(SeekBar sb){
		sb.setProgress(media.getVolume());
	}

    /* 
     * 
     * FIN FONCTIONS DE MISE A JOUR DU MEDIA
     * 
     */
	
	private static class ConnectionHolder {
		private final static VLCConnection instance = new VLCConnection();
	}
	
	public static VLCConnection getInstance() {
		return ConnectionHolder.instance;
	}

	public Media getMedia() {
		return media;
	}
}