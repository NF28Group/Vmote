package fr.nf28.vmote.play.model;

import android.util.Log;

public class PlayModel {
	
	private VLCConnection vlcConnection;
	
	private PlayModel() {
		setVlcConnection(VLCConnection.getInstance());
	}
	
	private static class PlayModelHolder {
		private final static PlayModel instance = new PlayModel();
	}
	
	public static PlayModel getInstance() {
		return PlayModelHolder.instance;
	}

	public VLCConnection getVlcConnection() {
		return vlcConnection;
	}

	public void setVlcConnection(VLCConnection vlcConnection) {
		this.vlcConnection = vlcConnection;
	}
	
	public void commandPause(){
		try {
			this.vlcConnection.pause();
	        Log.i("OK", "Pause");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	        Log.i("ERREUR", "Pause");
		}
	}
	
	public void commandPlay(){
		try {
			//TODO
			//this.vlcConnection.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandStop(){
		try {
			this.vlcConnection.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandNext(){
		try {
			this.vlcConnection.next();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandPrevious(){
		try {
			this.vlcConnection.previous();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
