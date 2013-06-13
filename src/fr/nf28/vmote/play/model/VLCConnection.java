package fr.nf28.vmote.play.model;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.nf28.vmote.R;
import fr.nf28.vmote.lib.HttpRequest;
import fr.nf28.vmote.lib.JsonReader;
import fr.nf28.vmote.play.classes.CheckConnection;
import fr.nf28.vmote.play.classes.LaunchError;
import fr.nf28.vmote.play.classes.Media;
import fr.nf28.vmote.series.model.SeriesModel;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


public class VLCConnection {
	
	private static Media media;
	
	public static String BASE_IP = null;
	
	public static String BASE_URL =
            "http://"+BASE_IP+":8080/requests/status.json";
    /* 
IP Milio :
 
	public static final String BASE_URL =
            "http://192.168.0.10:8080/requests/status.json";
            
IP Nico B. :
 
	public static final String BASE_URL =
            "http://192.168.0.12:8080/requests/status.json";
    */

    private static final String PARAM_COMMAND = "command";

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
    private static final String COMMAND_SUBDELAY = "subdelay&val=%VALUE%";
    private static final int TASK_SUBDELAY = 9;
    private static final String COMMAND_AUDIODELAY = "audiodelay&val=%VALUE%";
    private static final int TASK_AUDIODELAY = 10;
    
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
    		error.setMessage(c.getString(R.string.noWifi));
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
    		System.out.println("On utilise l'adresse : " + BASE_URL);
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
    		}    	}

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
    			media.setName("Pas de media ouvert");
    			}
    		else{
    			media.setName(current_media);
    		}
    		updateMedia(rv[0]);
    		return null;
    	}

    }
    
    /* D�finition de la CheckIpOnNetork*/
	public void checkIpOnNetwork() throws Exception {
    	new CheckIpOnNetwork().execute();
    }
    
	private class CheckIpOnNetwork extends AsyncTask <Void, Void, Void> {
    	@Override
    	protected Void doInBackground(Void... rv) {

    		ArrayList<String> hosts = new ArrayList<String>();

    	    InetAddress inetAddress = null;
    	    for(int i=10; i<20; i++){
    	        Log.i("IPPPPPPPPPPPPPPPPPPPPPPPPPPPPP", "Trying: " + "192.168.0." + String.valueOf(i));
    	        try {
    	            inetAddress = InetAddress.getByName("192.168.0." + String.valueOf(i));
    	            if(inetAddress.isReachable(10000)){    	            	
    	                hosts.add(inetAddress.getHostName());
    	                Log.i("IPPPPPPPPPPPP", inetAddress.getHostName());
    	            }
    	        } catch (UnknownHostException e) {
    	            e.printStackTrace();
    	        } catch (IOException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	    
    	    for (String s : hosts) {
                Log.i("IPPPPPPPPPPPP", s);
    	    }
    	    
			return null;
    	}

    }
	
	private class Command extends AsyncTask <Integer, Integer, Boolean> {
    	protected Boolean doInBackground(Integer... id_command) {
    		String command_to_execute;
    		double d;
    		
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
    			command_to_execute = COMMAND_VOLUME.replace("%VALUE%", String.valueOf(id_command[1]));
    			break;
    		case TASK_AUDIODELAY:
    			d = id_command[1];
    			command_to_execute = COMMAND_AUDIODELAY.replace("%VALUE%", String.valueOf(d/1000));
    			break;
    		case TASK_SUBDELAY:
    			d = id_command[1];
    			command_to_execute = COMMAND_SUBDELAY.replace("%VALUE%", String.valueOf(d/1000));
    			break;
    		default : command_to_execute = "";    			
    		}
    		
	    	HttpRequest request = HttpRequest.get(BASE_URL, true,
	                PARAM_COMMAND, command_to_execute).readTimeout(2000).connectTimeout(2000);
	        try {
				validateResponse(request);
			} catch (Exception e) {
				System.out.println("Time out");
				e.printStackTrace();
	    		return false;
			}
			try {
		        request.body();
				Thread.sleep(1000);
				System.out.println(JsonReader.getCurrentMediaStatus());
				media = JsonReader.getCurrentMediaStatus();
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
	public void random(View rv) {
		Command random_task = new Command();
		random_task.execute(TASK_RANDOM); 
    }
    
    /* D�finition de la fonction LOOP */
	public void loop(View rv) throws Exception {
		Command loop_task = new Command();
		loop_task.execute(TASK_LOOP);
    }
    
    /* D�finition de la fonction REPEAT */
	public void repeat(View rv) {
		Command repeat_task = new Command();
		repeat_task.execute(TASK_REPEAT); 
    }
    
    /* D�finition de la fonction VOLUME */
	public void volume(int d) {
		Command volume_task = new Command();
		volume_task.execute(TASK_VOLUME,d);
    }
	
	/* Definition de la fonction DELAY SUBTITLE */
	public void subDelay(int s) {
		Command subdelay_task = new Command();
		subdelay_task.execute(TASK_SUBDELAY,s);
    }

	/* Definition de la fonction DELAY AUDIO */
	public void audioDelay(int s) {
		Command audiodelay_task = new Command();
		audiodelay_task.execute(TASK_AUDIODELAY,s);
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
		upateNameMedia((TextView) rv.findViewById(R.id.textNameMedia), (TextView) rv.findViewById(R.id.playArtistAlbumLabel));
		updateVolumeMedia((SeekBar) rv.findViewById(R.id.seekBarPlaySound));
		updateStateMedia((ImageButton) rv.findViewById(R.id.buttonPlay),(ImageButton) rv.findViewById(R.id.buttonRepeat),(ImageButton) rv.findViewById(R.id.buttonShuffle));
		updateImageMedia((ImageView) rv.findViewById(R.id.mediaImage));
    }

	public void updateMediaWhenTaskEnds(View rv, Command task) {		
		while(true){
			try {
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

	
	private void updateImageMedia(ImageView img) {
		if(media.isMovie())
			img.setImageResource(R.drawable.video);
		else
			img.setImageResource(R.drawable.music);
	}
	
	private void upateNameMedia(TextView name, TextView info){
    	name.setText(media.getName());
    	
    	String tmp_str = "";
    	if(media.getArtist().equals("") && media.getAlbum().equals(""))
    		tmp_str = "";
    	else if(media.getArtist().equals("") && !(media.getAlbum().equals("")))
    		tmp_str = media.getAlbum();
    	else if(media.getAlbum().equals("") && !(media.getArtist().equals("")))
    		tmp_str = media.getArtist();
    	else if(!(media.getAlbum().equals("")) && !(media.getArtist().equals("")))
    		tmp_str = media.getArtist() + " - " + media.getAlbum();
    	
		info.setText(tmp_str);
	}
	
	private void updateVolumeMedia(SeekBar sb){
		sb.setProgress(media.getVolume());
	}
	
	private void updateStateMedia(ImageButton btn_play, ImageButton btn_repeat, ImageButton btn_suffle){
		if(media.getState().replace("\"", "").equals("playing"))
			btn_play.setImageResource(R.drawable.pause);
		else
			btn_play.setImageResource(R.drawable.play);
		
		if(media.getLoop().equals("true"))
			btn_repeat.setImageResource(R.drawable.loop_on);
		else if(media.getRepeat().equals("true"))
			btn_repeat.setImageResource(R.drawable.repeat);
		else
			btn_repeat.setImageResource(R.drawable.loop);
			
		if(media.getRandom().equals("true"))
			btn_suffle.setImageResource(R.drawable.shuffle_on);
		else if(media.getRandom().equals("false"))
			btn_suffle.setImageResource(R.drawable.shuffle);
		
		SeriesModel.autoAddTvShow(media.getName());
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