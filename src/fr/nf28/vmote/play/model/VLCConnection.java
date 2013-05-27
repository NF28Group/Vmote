package fr.nf28.vmote.play.model;

import android.util.Log;

import com.github.kevinsawicki.http.HttpRequest;

public class VLCConnection {

	private static final String BASE_URL =
            "http://192.168.0.10:8080/requests/status.xml";

    private static final String PARAM_COMMAND = "command";
    //private static final String PARAM_INPUT = "input";

    private static final String COMMAND_PLAY = "in_play";
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
    
    public void pause() throws Exception {
        HttpRequest request = HttpRequest.get(BASE_URL, true,
                PARAM_COMMAND, COMMAND_PAUSE);
        validateResponse(request);
        request.body();
        Log.i(COMMAND_PAUSE, "Pause");
    }
    
    public void stop() throws Exception {
        HttpRequest request = HttpRequest.get(BASE_URL, true,
                PARAM_COMMAND, COMMAND_STOP);
        validateResponse(request);
        request.body();
        System.out.println("Stop");
    }
    
    public void play() throws Exception {
        HttpRequest request = HttpRequest.get(BASE_URL, true,
                PARAM_COMMAND, COMMAND_PLAY);
        validateResponse(request);
        request.body();
        System.out.println("Play");
    }
    
    public void next() throws Exception {
        HttpRequest request = HttpRequest.get(BASE_URL, true,
                PARAM_COMMAND, COMMAND_NEXT);
        validateResponse(request);
        request.body();
        System.out.println("Next");
    }

    public void previous() throws Exception {
        HttpRequest request = HttpRequest.get(BASE_URL, true,
                PARAM_COMMAND, COMMAND_PREVIOUS);
        validateResponse(request);
        request.body();
        System.out.println("Previous");
    }
	
	private static class ConnectionHolder {
		private final static VLCConnection instance = new VLCConnection();
	}
	
	public static VLCConnection getInstance() {
		return ConnectionHolder.instance;
	}
	
	

}