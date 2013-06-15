package fr.nf28.vmote.play.view;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
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
	
	private ImageButton btnAudioPrevious;
	private ImageButton btnAudioNext;
	private ImageButton btnSubtitlePrevious;
	private ImageButton btnSubtitleNext;
	private TextView tvAudioTrack;
	private TextView tvSubtitleTrack;


	
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
    	
    	btnAudioNext = (ImageButton) rootView.findViewById(R.id.audioRightArrow);
    	btnAudioPrevious = (ImageButton) rootView.findViewById(R.id.audioLeftArrow);
    	btnSubtitleNext = (ImageButton) rootView.findViewById(R.id.subtitleRightArrow);
    	btnSubtitlePrevious = (ImageButton) rootView.findViewById(R.id.subtitleLeftArrow);
    	tvAudioTrack = (TextView) rootView.findViewById(R.id.tvAudioList);
    	tvSubtitleTrack = (TextView) rootView.findViewById(R.id.tvSubtitleList);
    	
    	etAudio.setEnabled(false);
    	etSubtitle.setEnabled(false);
    	
    	sbAudio.setProgress(3000);
    	sbSubtitle.setProgress(3000);
    	sbAudio.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    		int value = 0;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
            	value = progress - 3000; // pas de valeurs négatives...
            	model.updateText(value, etAudio);
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
            	model.updateText(value, etAudio);
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
            	// solution de rechange car MyViewPager marche pas
            	if(!model.getVlcConnection().getMedia().isMovie())return;
            	model.ajustAudio(value);
            }
        });
    	
    	sbSubtitle.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    		int value = 0;
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                value = progress - 3000; // pas de valeurs négatives...
            	model.updateText(value, etSubtitle);
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {
            	model.updateText(value, etSubtitle);
            }
 
            public void onStopTrackingTouch(SeekBar seekBar) {
            	if(!model.getVlcConnection().getMedia().isMovie())return;
            	model.ajustSubtitle(value);
            }
        });
    	
    	// gestion des listes
    	if(this.model.getVlcConnection().getMedia().getIsMovie()) {
        	this.model.setSubtitlesElement();    		
    	}

    	
    	this.btnAudioPrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!model.getVlcConnection().getMedia().isMovie())return;
				model.setAudioPiste(true);
			}
		});
    	
		this.btnAudioNext.setOnClickListener(new OnClickListener() {
					
			@Override
			public void onClick(View v) {
				if(!model.getVlcConnection().getMedia().isMovie())return;
				model.setAudioPiste(false);
			}
		});
		
		this.btnSubtitlePrevious.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!model.getVlcConnection().getMedia().isMovie())return;
				model.setSubtitlePiste(true);
			}
		});
		
		this.btnSubtitleNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!model.getVlcConnection().getMedia().isMovie())return;
				model.setSubtitlePiste(false);
			}
		});

    	
    	return rootView;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	
    	this.model.setSubtitlesElement();
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

	public ImageButton getBtnAudioPrevious() {
		return btnAudioPrevious;
	}

	public void setBtnAudioPrevious(ImageButton btnAudioPrevious) {
		this.btnAudioPrevious = btnAudioPrevious;
	}

	public ImageButton getBtnAudioNext() {
		return btnAudioNext;
	}

	public void setBtnAudioNext(ImageButton btnAudioNext) {
		this.btnAudioNext = btnAudioNext;
	}

	public ImageButton getBtnSubtitlePrevious() {
		return btnSubtitlePrevious;
	}

	public void setBtnSubtitlePrevious(ImageButton btnSubtitlePrevious) {
		this.btnSubtitlePrevious = btnSubtitlePrevious;
	}

	public ImageButton getBtnSubtitleNext() {
		return btnSubtitleNext;
	}

	public void setBtnSubtitleNext(ImageButton btnSubtitleNext) {
		this.btnSubtitleNext = btnSubtitleNext;
	}

	public TextView getTvAudioTrack() {
		return tvAudioTrack;
	}

	public void setTvAudioTrack(TextView tvAudioTrack) {
		this.tvAudioTrack = tvAudioTrack;
	}

	public TextView getTvSubtitleTrack() {
		return tvSubtitleTrack;
	}

	public void setTvSubtitleTrack(TextView tvSubtitleTrack) {
		this.tvSubtitleTrack = tvSubtitleTrack;
	}
}