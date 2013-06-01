package fr.nf28.vmote.play.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.play.interfaces.OnChangePageListener;
import fr.nf28.vmote.play.model.PlayModel;

public class PlayDetailsFragment extends AbstractPlayFragment {
	public static final String ARG_ITEM_ID = "details_play_fragment";
	
	private View rootView;
	private PlayModel model;
	private TextView title;
	private TextView duration;
	private TextView length;
	private TextView height;
	private TextView frameRate;
	private TextView speed;
	private TextView sampling;
	
	
	@SuppressWarnings("unused")
	private OnChangePageListener changePageCallback = sDummyChangePageCallback;
	
	public PlayDetailsFragment() {}
	
	public static PlayDetailsFragment newInstance() {
		PlayDetailsFragment fragment = new PlayDetailsFragment();
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
    	this.model.setDetailsView(this);
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_lecture_details_layout, container, false);
    	
    	title = (TextView) rootView.findViewById(R.id.detailsPageElementTitle);
    	duration = (TextView) rootView.findViewById(R.id.detailsPageDuration);
    	length = (TextView) rootView.findViewById(R.id.detailsPageLength);
    	height = (TextView) rootView.findViewById(R.id.detailsPageHeight);
    	frameRate = (TextView) rootView.findViewById(R.id.detailsPageFrameRate);
    	speed = (TextView) rootView.findViewById(R.id.detailsPageSpeed);
    	sampling = (TextView) rootView.findViewById(R.id.detailsPageSampling);
    	
    	this.model.setDetailsElement();
    	
    	return rootView;
    }

	public PlayModel getModel() {
		return model;
	}

	public void setModel(PlayModel model) {
		this.model = model;
	}

	public TextView getTitle() {
		return title;
	}

	public void setTitle(TextView title) {
		this.title = title;
	}

	public TextView getDuration() {
		return duration;
	}

	public void setDuration(TextView duration) {
		this.duration = duration;
	}

	public TextView getLength() {
		return length;
	}

	public void setLength(TextView length) {
		this.length = length;
	}

	public TextView getHeight() {
		return height;
	}

	public void setHeight(TextView height) {
		this.height = height;
	}

	public TextView getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(TextView frameRate) {
		this.frameRate = frameRate;
	}

	public TextView getSpeed() {
		return speed;
	}

	public void setSpeed(TextView speed) {
		this.speed = speed;
	}

	public TextView getSampling() {
		return sampling;
	}

	public void setSampling(TextView sampling) {
		this.sampling = sampling;
	}
}
