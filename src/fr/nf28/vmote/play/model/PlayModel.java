package fr.nf28.vmote.play.model;

import android.widget.TextView;

public class PlayModel {
	
	private VLCConnection vlcConnection;
	private boolean is_loop = false;
	private boolean is_repeat = false;
	
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandPlay(){
		try {
			this.vlcConnection.pause();
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
	
	public void commandRandom(){
		try {
			this.vlcConnection.random();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandRepeat(){
		try {
			if(is_loop){
				this.vlcConnection.repeat();
				is_loop = false;
				is_repeat = true;
			}
			else if(is_repeat){
				this.vlcConnection.loop();
				this.vlcConnection.loop();
				is_repeat = false;
			}
			else{
				this.vlcConnection.loop();
				is_loop = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void commandVolume(float value){
		try {
			this.vlcConnection.volume(value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setNameMedia(TextView tv) {
		this.vlcConnection.setNameMedia(tv);
		
	}
}
