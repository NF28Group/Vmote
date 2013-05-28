package fr.nf28.vmote.play.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.nf28.vmote.R;
import fr.nf28.vmote.play.interfaces.OnChangePageListener;
import fr.nf28.vmote.play.model.PlayModel;

public class PlayDetailsFragment extends AbstractPlayFragment {
	public static final String ARG_ITEM_ID = "details_play_fragment";
	
	private View rootView;
	private PlayModel model;
	
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
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_lecture_details_layout, container, false);
    	
    	
    	return rootView;
    }

	public PlayModel getModel() {
		return model;
	}

	public void setModel(PlayModel model) {
		this.model = model;
	}
}
