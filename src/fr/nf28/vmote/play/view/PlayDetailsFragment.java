package fr.nf28.vmote.play.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.interfaces.OnChangePageListener;
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
	private TextView date;
	private TextView artist;
	private TextView history;
	private TextView album;
	private TextView gender;
	
	
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
    	
    	title = (TextView) rootView.findViewById(R.id.detailsPageElementTitleSet);
    	duration = (TextView) rootView.findViewById(R.id.detailsPageDurationSet);
    	length = (TextView) rootView.findViewById(R.id.detailsPageLengthSet);
    	height = (TextView) rootView.findViewById(R.id.detailsPageHeightSet);
    	frameRate = (TextView) rootView.findViewById(R.id.detailsPageFrameRateSet);
    	date = (TextView) rootView.findViewById(R.id.detailsPageDateSet);
    	artist = (TextView) rootView.findViewById(R.id.detailsPageArtistSet);
    	album = (TextView) rootView.findViewById(R.id.detailsPageAlbumSet);
    	gender = (TextView) rootView.findViewById(R.id.detailsPageGenderSet);
    	history = (TextView) rootView.findViewById(R.id.detailsPageHistorySet);

    	// bad here
    	//this.model.setDetailsElement();
    	
    	return rootView;
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	
    	this.model.setDetailsElement();
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

	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	public TextView getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration.setText(duration);
	}

	public TextView getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length.setText(length);
	}

	public TextView getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height.setText(height);
	}

	public TextView getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(String frameRate) {
		this.frameRate.setText(frameRate);
	}

	public TextView getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date.setText(date);
	}

	public TextView getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist.setText(artist);
	}

	public TextView getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history.setText(history);
	}

	public TextView getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album.setText(album);
	}

	public TextView getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender.setText(gender);
	}
}
