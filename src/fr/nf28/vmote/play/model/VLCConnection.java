package fr.nf28.vmote.play.model;

import java.util.concurrent.ExecutionException;

import fr.nf28.vmote.R;
import fr.nf28.vmote.lib.HttpRequest;
import fr.nf28.vmote.lib.JsonReader;
import fr.nf28.vmote.play.media.Media;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;


public class VLCConnection {
	
	private static Media media;
	
	private static final String BASE_URL =
            "http://192.168.0.10:8080/requests/status.xml";

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
    
    /* Définition de la check Media*/
	public void checkMedia(View rv) throws Exception {
    	new CheckMedia().execute(rv);
    }
    
	private class CheckMedia extends AsyncTask <View, Void, Void> {
    	@Override
    	protected Void doInBackground(View... rv) {
    		String current_media = JsonReader.getNameMedia();
    		if(current_media == "0"){
        		System.out.println("0");
    			media.setName("Pas de média ouvert");
    			}
    		else{
    			media.setName(current_media);
    		}
    		updateMedia(rv[0]);
    		return null;
    	}

    }
	
	private class Command extends AsyncTask <Integer, Integer, Integer> {
    	protected Integer doInBackground(Integer... id_command) {
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
			}
	        request.body();
			media.setName(JsonReader.getNameMedia());
    		return 1;
    	}
    	
        protected void onProgressUpdate(Integer... progress) {
        	System.out.println(progress);
        }


        protected void onPostExecute(Integer result) {
        	System.out.println(result);
        }
    }
    
    /* 
     * 
     * FONCTIONS DE PILOTAGE DU MEDIA
     * 
     */
	
    /* Définition de la fonction PAUSE */
	public void pause(View rv) throws Exception {
		Command pause_task = new Command();
		pause_task.execute(TASK_PAUSE);
    	updateMediaWhenTaskEnds(rv,pause_task);  	
    }
    
    /* Définition de la fonction STOP */
	public void stop(View rv) throws Exception {
		Command stop_task = new Command();
		stop_task.execute(TASK_STOP);
    	updateMediaWhenTaskEnds(rv,stop_task);  
    }
    
    /* Définition de la fonction NEXT */
	public void next(View rv) throws Exception {
		Command next_task = new Command();
		next_task.execute(TASK_NEXT);
    	updateMediaWhenTaskEnds(rv,next_task); 
    }
    
    /* Définition de la fonction PREVIOUS */
	public void previous(View rv) throws Exception  {
		Command previous_task = new Command();
		previous_task.execute(TASK_PREVIOUS);
    	updateMediaWhenTaskEnds(rv,previous_task); 
    }
    
    /* Définition de la fonction RANDOM */
	public void random() throws Exception {
		Command random_task = new Command();
		random_task.execute(TASK_RANDOM); 
    }
    
    /* Définition de la fonction LOOP */
	public void loop() throws Exception {
		Command loop_task = new Command();
		loop_task.execute(TASK_LOOP); 
    }
    
    /* Définition de la fonction REPEAT */
	public void repeat() throws Exception {
		Command repeat_task = new Command();
		repeat_task.execute(TASK_REPEAT); 
    }
    
    /* Définition de la fonction VOLUME */
	public void volume(int d) throws Exception {
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
    }
	
	public void updateMediaWhenTaskEnds(View rv, Command task) {		
		while(true){
			try {
				System.out.println(task.get());
				System.out.println(task.getStatus());
				if(task.get() == 1){
					updateMedia(rv);
					break;
				}
				System.out.println(task.getStatus());
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
}
