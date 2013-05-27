package fr.nf28.vmote.play.model;

import android.os.AsyncTask;
import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

public class VLCConnection {

	private static final String BASE_URL =
            "http://192.168.0.10:8080/requests/status.xml";

    private static final String PARAM_COMMAND = "command";
    //private static final String PARAM_INPUT = "input";

    //private static final String COMMAND_PLAY = "in_play";
    private static final String COMMAND_STOP = "pl_stop";
    private static final String COMMAND_PAUSE = "pl_pause";
    private static final String COMMAND_NEXT = "pl_next";
    private static final String COMMAND_PREVIOUS = "pl_previous";
    
	private VLCConnection() {	
	}
	
    private static void validateResponse(HttpRequest request) throws Exception {
        if (!request.ok()) {
            throw new Exception("Request failed with code: " + request.code());
        }
    }
    
    /* Définition de la fonction PAUSE */
    @SuppressWarnings("unchecked")
	public void pause() throws Exception {
    	new Pause().execute();
    }
    
    @SuppressWarnings("rawtypes")
	private class Pause extends AsyncTask {
    	@Override
    	protected Object doInBackground(Object... arg0) {    		
	    	HttpRequest request = HttpRequest.get(BASE_URL, true,
	                PARAM_COMMAND, COMMAND_PAUSE);
	        try {
				validateResponse(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        request.body();
	        Log.i(COMMAND_PAUSE, "Pause");
    		return null;
    	}

    }
    
    /* Définition de la fonction STOP */
    @SuppressWarnings("unchecked")
	public void stop() throws Exception {
    	new Stop().execute();
    }
    
    @SuppressWarnings("rawtypes")
	private class Stop extends AsyncTask {
    	@Override
    	protected Object doInBackground(Object... arg0) {
            HttpRequest request = HttpRequest.get(BASE_URL, true,
                    PARAM_COMMAND, COMMAND_STOP);
            try {
				validateResponse(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            request.body();
            System.out.println("Stop");
    		return null;
    	}

    }
    
    /* Définition de la fonction NEXT */
    @SuppressWarnings("unchecked")
	public void next() throws Exception {
    	new Next().execute();
    }
    
    @SuppressWarnings("rawtypes")
	private class Next extends AsyncTask {
    	@Override
    	protected Object doInBackground(Object... arg0) {
            HttpRequest request = HttpRequest.get(BASE_URL, true,
                    PARAM_COMMAND, COMMAND_NEXT);
            try {
				validateResponse(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            request.body();
            System.out.println("Next");
    		return null;
    	}

    }
    
    /* Définition de la fonction PREVIOUS */
    @SuppressWarnings("unchecked")
	public void previous() throws Exception {
    	new Previous().execute();
    }
    
    @SuppressWarnings("rawtypes")
	private class Previous extends AsyncTask {
    	@Override
    	protected Object doInBackground(Object... arg0) {
    		HttpRequest request = HttpRequest.get(BASE_URL, true,
                    PARAM_COMMAND, COMMAND_PREVIOUS);
            try {
				validateResponse(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            request.body();
            System.out.println("Next");
    		return null;
    	}

    }
	
	private static class ConnectionHolder {
		private final static VLCConnection instance = new VLCConnection();
	}
	
	public static VLCConnection getInstance() {
		return ConnectionHolder.instance;
	}
	
	

}