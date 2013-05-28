package fr.nf28.vmote.play.view;

import fr.nf28.vmote.R;
import fr.nf28.vmote.play.interfaces.OnChangePageListener;
import fr.nf28.vmote.play.model.PlayModel;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class PlayMainFragment extends AbstractPlayFragment {
	public static final String ARG_ITEM_ID = "main_play_fragment";
	
	private View rootView;
	private PlayModel model;
	
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
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_lecture_main_layout, container, false);
    	
    	ImageButton button_pause = (ImageButton) rootView.findViewById(R.id.buttonPause);
    	ImageButton button_play = (ImageButton) rootView.findViewById(R.id.buttonPlay);
    	ImageButton button_stop = (ImageButton) rootView.findViewById(R.id.buttonStop);
    	ImageButton button_previous = (ImageButton) rootView.findViewById(R.id.buttonBackward);
    	ImageButton button_next = (ImageButton) rootView.findViewById(R.id.buttonForward);
    	ImageButton button_shuffle = (ImageButton) rootView.findViewById(R.id.buttonShuffle);
    	ImageButton button_repeat = (ImageButton) rootView.findViewById(R.id.buttonRepeat);

	    button_pause.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				model.commandPause();
			}
		});
	    
	    button_play.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				model.commandPlay();
			}
		});
	    
	    button_stop.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				model.commandStop();
			}
		});
	    
	    button_previous.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				model.commandPrevious();
			}
		});
	    
	    button_next.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				model.commandNext();
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
    	
    	return rootView;
    }

	public PlayModel getModel() {
		return model;
	}

	public void setModel(PlayModel model) {
		this.model = model;
	}
}