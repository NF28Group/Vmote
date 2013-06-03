package fr.nf28.vmote.play.model;

import fr.nf28.vmote.play.classes.LaunchError;
import fr.nf28.vmote.play.classes.Media;
import fr.nf28.vmote.play.view.PlayDetailsFragment;
import fr.nf28.vmote.play.view.PlayMainFragment;
import fr.nf28.vmote.play.view.PlaySubtitlesFragment;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

public class PlayModel {
	
	private VLCConnection vlcConnection;
	private boolean is_loop = false;
	private boolean is_repeat = false;
	
	private PlayMainFragment mainView;
	private PlayDetailsFragment detailsView;
	private PlaySubtitlesFragment subtitleView;
	
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
			e.printStackTrace();
		}
	}
	

	public void commandVolume(int value){
		try {
			/*
			 * Pour avoir le r�sultat du progress en pourcentage de 0 � 200%
			*/	
			this.vlcConnection.volume((int) (value*2.56*2));
		} catch (Exception e) {
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
	
	public LaunchError launchCheck(Context c, View rv){
		return this.vlcConnection.lauchCheck(c,rv);
	}

	
	/*
	 * Methods for details and subtitle views
	 */
	public void ajustAudio(int value, EditText audio) {
		updateText(value, audio);
		
		//TODO call VLC
	}

	public void ajustSubtitle(int value, EditText subtitle) {
		updateText(value, subtitle);
		
		//TODO call VLC
	}
	
	private void updateText(int v, EditText et) {
		if(v < 0) {
			et.setText(" " + v + " ms");
		}
		else {
			et.setText("+ " + v + " ms");			
		}
	}

	public PlayMainFragment getMainView() {
		return mainView;
	}

	public void setMainView(PlayMainFragment mainView) {
		this.mainView = mainView;
	}

	public PlayDetailsFragment getDetailsView() {
		return detailsView;
	}

	public void setDetailsView(PlayDetailsFragment detailsView) {
		this.detailsView = detailsView;
	}

	public PlaySubtitlesFragment getSubtitleView() {
		return subtitleView;
	}

	public void setSubtitleView(PlaySubtitlesFragment subtitleView) {
		this.subtitleView = subtitleView;
	}
	
	public void setDetailsElement() {
		Media media = this.vlcConnection.getMedia();
		this.detailsView.getTitle().setText(media.getName());
	}
}
