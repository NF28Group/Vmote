package fr.nf28.vmote.play.model;

import fr.nf28.vmote.R;
import fr.nf28.vmote.play.classes.LaunchError;
import fr.nf28.vmote.play.classes.Media;
import fr.nf28.vmote.play.view.PlayDetailsFragment;
import fr.nf28.vmote.play.view.PlayMainFragment;
import fr.nf28.vmote.play.view.PlaySubtitlesFragment;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class PlayModel {
	
	private VLCConnection vlcConnection;
	private boolean is_loop = false;
	private boolean is_repeat = false;
	private boolean is_shuffle = false;
	
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
	
	public void commandRandom(View rv){
    	ImageButton button_random = (ImageButton) rv.findViewById(R.id.buttonShuffle);
		try {
			this.vlcConnection.random(rv);
			if(is_shuffle)
				button_random.setImageResource(R.drawable.shuffle);
			else
				button_random.setImageResource(R.drawable.shuffle_on);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void commandRepeat(View rv){
    	ImageButton button_loop = (ImageButton) rv.findViewById(R.id.buttonRepeat);
		try {
			if(is_loop){
				this.vlcConnection.repeat(rv);
				is_loop = false;
				is_repeat = true;
		    	button_loop.setImageResource(R.drawable.loop);
			}
			else if(is_repeat){
				this.vlcConnection.loop(rv);
				this.vlcConnection.loop(rv);
				is_repeat = false;
		    	button_loop.setImageResource(R.drawable.loop);
			}
			else{
				this.vlcConnection.loop(rv);
				is_loop = true;
		    	button_loop.setImageResource(R.drawable.loop_on);
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
		this.detailsView.setTitle(media.getName());
		if(media.getAlbum() == null) {
			this.detailsView.setAlbum("");
		}
		else {
			this.detailsView.setAlbum(media.getAlbum());
		}
		if(media.getArtist() == null) {
			this.detailsView.setArtist("");
		}
		else {
			this.detailsView.setArtist(media.getArtist());
		}
		if(media.getDate() == null) {
			this.detailsView.setDate("");
		}
		else {
			this.detailsView.setDate(media.getDate());
		}
		
		if(media.getDuree() != null) {
			int duration = Integer.parseInt(media.getDuree());
			int hours = duration/3600;
			int remainder = duration - hours*3600;
			int minutes = remainder/60;
			int seconds = remainder - minutes*60;
			String duree;
			if(hours != 0) {
				duree = hours + ":" + minutes + ":" + seconds;
			}
			else {
				duree = minutes + ":" + seconds;
			}
			this.detailsView.setDuration(duree);
		}
		else {
			this.detailsView.setDuration("");
		}
		
		if(media.getHistory() == null) {
			this.detailsView.setHistory("");
		}
		else {
			this.detailsView.setHistory(media.getHistory());
		}
		
		if(media.getGender() == null) {
			this.detailsView.setGender("");
		}
		else {
			this.detailsView.setGender(media.getGender());
		}
	}
}
