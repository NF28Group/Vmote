package fr.nf28.vmote.play.model;

import android.view.View;

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
	
	public void commandPlay(View rv){
		try {
			this.vlcConnection.pause(rv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandStop(View rv){
		try {
			this.vlcConnection.stop(rv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandNext(View rv){
		try {
			this.vlcConnection.next(rv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandPrevious(View rv){
		try {
			this.vlcConnection.previous(rv);
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
	

	public void commandVolume(int value){
		try {
			/*
			 * Pour avoir le r�sultat du progress en pourcentage de 0 � 200%
			*/	
			this.vlcConnection.volume(value*2.56*2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateMedia(View rv) {
		this.vlcConnection.updateMedia(rv);
		
	}

	public void checkMedia(View rv) {
		try {
			this.vlcConnection.checkMedia(rv);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
