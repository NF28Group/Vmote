package fr.nf28.vmote.play.view;

import fr.nf28.vmote.R;
import fr.nf28.vmote.play.classes.LaunchError;
import fr.nf28.vmote.play.interfaces.OnChangePageListener;
import fr.nf28.vmote.play.model.PlayModel;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class PlayMainFragment extends AbstractPlayFragment {
	public static final String ARG_ITEM_ID = "main_play_fragment";
	
	private View rootView;
	private PlayModel model;
	
	@SuppressWarnings("unused")
	private OnChangePageListener changePageCallback = sDummyChangePageCallback;
	
	
	public PlayMainFragment() {}
	
	public static PlayMainFragment newInstance() {
		PlayMainFragment fragment = new PlayMainFragment();
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
    	this.model.setMainView(this);
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_lecture_main_layout, container, false);
    	
    	ImageButton button_play = (ImageButton) rootView.findViewById(R.id.buttonPlay);
    	ImageButton button_stop = (ImageButton) rootView.findViewById(R.id.buttonStop);
    	ImageButton button_previous = (ImageButton) rootView.findViewById(R.id.buttonBackward);
    	ImageButton button_next = (ImageButton) rootView.findViewById(R.id.buttonForward);
    	ImageButton button_shuffle = (ImageButton) rootView.findViewById(R.id.buttonShuffle);
    	ImageButton button_repeat = (ImageButton) rootView.findViewById(R.id.buttonRepeat);
    	ImageButton button_mute = (ImageButton) rootView.findViewById(R.id.buttonMute);
    	final SeekBar slider_volume = (SeekBar) rootView.findViewById(R.id.seekBarPlaySound);
    	
    	LaunchError launchError = model.launchCheck(getActivity(),rootView);
    	
    	if (launchError.getEtat() == 1) {
			//model.checkMedia(rootView);
        	
    	    button_play.setOnClickListener(new OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				model.commandPlay(rootView);
    			}
    		});
    	    
    	    button_stop.setOnClickListener(new OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				model.commandStop(rootView);
    			}
    		});
    	    
    	    button_previous.setOnClickListener(new OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				model.commandPrevious(rootView);
    			}
    		});
    	    
    	    button_next.setOnClickListener(new OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				model.commandNext(rootView);
    			}
    		});
    	    
    	    button_shuffle.setOnClickListener(new OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				model.commandRandom();
    			}
    		});
    	    
    	    button_repeat.setOnClickListener(new OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				model.commandRepeat();
    			}
    		});
    	    
    	    button_mute.setOnClickListener(new OnClickListener() {
    			
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				model.commandVolume(0);
    				slider_volume.setProgress(0);
    			}
    		});
    	    
    	    slider_volume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    			
    			@Override
    			public void onStopTrackingTouch(SeekBar seekBar) {
    				model.commandVolume(seekBar.getProgress());
    				
    			}
    			
    			@Override
    			public void onStartTrackingTouch(SeekBar seekBar) {
    				// TODO Auto-generated method stub
    			}
    			
    			@Override
    			public void onProgressChanged(SeekBar seekBar, int progress,
    					boolean fromUser) {	
    				
    			}
    		});
		}
    	else{
    		Toast.makeText(getActivity(), launchError.getMessage(), Toast.LENGTH_LONG)
    	     .show();
    	}
    	
    	return rootView;
    }

	public PlayModel getModel() {
		return model;
	}

	public void setModel(PlayModel model) {
		this.model = model;
	}
}