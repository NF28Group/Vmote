package fr.nf28.vmote.play.model;

import fr.nf28.vmote.R;
import fr.nf28.vmote.play.adapter.SubtitleListAdapter;
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
	private boolean is_loop;
	private boolean is_repeat;
	private boolean is_shuffle;
	private boolean is_play;
	
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
    	ImageButton button_play = (ImageButton) rv.findViewById(R.id.buttonPlay);
		try {
			this.vlcConnection.pause(rv);
			if(is_play){
				button_play.setImageResource(R.drawable.play);
				is_play = false;
			}
			else{
				button_play.setImageResource(R.drawable.pause);
				is_play = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commandStop(View rv){
		try {
			this.vlcConnection.stop(rv);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commandNext(View rv){
		try {
			this.vlcConnection.next(rv);
		} catch (Exception e) {
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
			if(is_shuffle){
				button_random.setImageResource(R.drawable.shuffle);
				is_shuffle = false;
			}
			else{
				button_random.setImageResource(R.drawable.shuffle_on);
				is_shuffle = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commandRepeat(View rv){
    	ImageButton button_repeat = (ImageButton) rv.findViewById(R.id.buttonRepeat);
		try {
			if(is_loop){
				button_repeat.setImageResource(R.drawable.repeat);
				this.vlcConnection.repeat(rv);
				is_repeat = true;
				is_loop = false;
			}
			else if(is_repeat){
				button_repeat.setImageResource(R.drawable.loop);
				this.vlcConnection.repeat(rv);
				is_repeat = false;
				is_loop = false;
			}
			else{
				button_repeat.setImageResource(R.drawable.loop_on);
				this.vlcConnection.loop(rv);
				is_loop = true;
				is_repeat = false;
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
	
	public void commandAudioDelay(int val){
		try {
			this.vlcConnection.audioDelay(val);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void commandSubDelay(int val){
		try {
			this.vlcConnection.subDelay(val);
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
	public void ajustAudio(int value) {		
		this.commandAudioDelay(value);
	}

	public void ajustSubtitle(int value) {
		this.commandSubDelay(value);
	}
	
	public void updateText(int value, EditText et) {
		if(value < 0) {
			et.setText(" " + value + " ms");
		}
		else {
			et.setText("+ " + value + " ms");			
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
		if(media.getName()==null) {
			this.detailsView.setTitle("");
		}
		else {
			this.detailsView.setTitle(media.getName());
		}
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
	
	/*
	 * Met à jour les listes de pistes audio et sous-titres
	 */
	public void setSubtitlesElement() {
		Media media = this.vlcConnection.getMedia();
		
		SubtitleListAdapter subAdapter = new SubtitleListAdapter(media.getSubtitleList());
		SubtitleListAdapter audioAdapter = new SubtitleListAdapter(media.getAudioList());
		
		this.subtitleView.getSubtitleList().setAdapter(subAdapter);
		this.subtitleView.getAudioList().setAdapter(audioAdapter);
	}

	public void initializeBoolean() {
		is_loop = this.vlcConnection.getMedia().getLoop().equals("true");
		is_repeat = this.vlcConnection.getMedia().getRepeat().equals("true");
		is_shuffle = this.vlcConnection.getMedia().getRandom().equals("true");
		is_play = this.vlcConnection.getMedia().getState().replace("\"", "").equals("playing");

		System.out.println("is_loop = " + is_loop);
		System.out.println("is_repeat = " + is_repeat);
		
	}
}
