package fr.nf28.vmote.play.view;

import com.devsmart.android.ui.HorizontalListView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import fr.nf28.vmote.R;
import fr.nf28.vmote.interfaces.OnChangePageListener;
import fr.nf28.vmote.play.model.PlayModel;

public class PlaySubtitlesFragment extends AbstractPlayFragment {
	public static final String ARG_ITEM_ID = "subtitles_play_fragment";
	
	private View rootView;
	private PlayModel model;
	private EditText etAudio;
	private EditText etSubtitle;
	private SeekBar sbAudio;
	private SeekBar sbSubtitle;
	
	private HorizontalListView subtitleList;
	private HorizontalListView audioList;
	
	@SuppressWarnings("unused")
	private OnChangePageListener changePageCallback = sDummyChangePageCallback;
		
	
	public PlaySubtitlesFragment() {}
	
	public static PlaySubtitlesFragment newInstance() {
		PlaySubtitlesFragment fragment = new PlaySubtitlesFragment();
	    return fragment;
	}

	private static OnChangePageListener 
	sDummyChangePageCallback = new OnChangePageListener() {

		@Override
		public void selectPage(int page) {
			Log.i("SWIPE", "ERREUR...interface swipe");
		}	

	};
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        

        try {
        	changePageCallback = (OnChangePageListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnChangePageListener");
        }
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        changePageCallback = sDummyChangePageCallback;
    }
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	this.setModel(PlayModel.getInstance());
    	this.model.setSubtitleView(this);
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_lecture_subtitles_layout, container, false);
    	
    	etAudio = (EditText) rootView.findViewById(R.id.etSubtitlePageSynchroAudio);
    	etSubtitle = (EditText) rootView.findViewById(R.id.etSubtitlePageSynchroSubtitle);
    	sbAudio = (SeekBar) rootView.findViewById(R.id.seekBarAudio);
    	sbSubtitle = (SeekBar) rootView.findViewById(R.id.seekBarSubtitle);
    	
    	subtitleList = (HorizontalListView) rootView.findViewById(R.id.subtitleSelectList);
    	audioList = (HorizontalListView) rootView.findViewById(R.id.audioSelectList);
    	
    	etAudio.setEnabled(false);
    	etSubtitle.setEnabled(false);
    	
    	sbAudio.setProgress(3000);
    	sbSubtitle.setProgress(3000);
    	sbAudio.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    		int value = 0;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            	value = progress -3000; // pas de valeurs négatives...
            	model.ajustSubtitle(value, etAudio);
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
            	model.ajustSubtitle(value, etAudio);
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    	
    	sbSubtitle.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    		int value = 0;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                value = progress -3000; // pas de valeurs négatives...
            	model.ajustSubtitle(value, etSubtitle);
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
            	model.ajustSubtitle(value, etSubtitle);
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    	
    	// gestion des listes
    	this.model.setSubtitlesElement();
    	
    	return rootView;
    }

	public PlayModel getModel() {
		return model;
	}

	public void setModel(PlayModel model) {
		this.model = model;
	}

	public SeekBar getSbSubtitle() {
		return sbSubtitle;
	}

	public void setSbSubtitle(SeekBar sbSubtitle) {
		this.sbSubtitle = sbSubtitle;
	}

	public SeekBar getSbAudio() {
		return sbAudio;
	}

	public void setSbAudio(SeekBar sbAudio) {
		this.sbAudio = sbAudio;
	}

	public EditText getEtSubtitle() {
		return etSubtitle;
	}

	public void setEtSubtitle(EditText etSubtitle) {
		this.etSubtitle = etSubtitle;
	}

	public EditText getEtAudio() {
		return etAudio;
	}

	public void setEtAudio(EditText etAudio) {
		this.etAudio = etAudio;
	}

	public HorizontalListView getAudioList() {
		return audioList;
	}

	public void setAudioList(HorizontalListView audioList) {
		this.audioList = audioList;
	}

	public HorizontalListView getSubtitleList() {
		return subtitleList;
	}

	public void setSubtitleList(HorizontalListView subtitleList) {
		this.subtitleList = subtitleList;
	}
}