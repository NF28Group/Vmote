package fr.nf28.vmote.history.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.nf28.vmote.R;
import fr.nf28.vmote.history.model.HistoryModel;
import fr.nf28.vmote.interfaces.OnChangePageListener;

public class HistoryAudioFragment extends AbstractHistoryFragment {
	public static final String ARG_ITEM_ID = "history_audio_fragment";
	
	@SuppressWarnings("unused")
	private OnChangePageListener changePageCallback = sDummyChangePageCallback;

	private HistoryModel model;
	private View rootView;
	
	public HistoryAudioFragment() {}
	
	public static HistoryAudioFragment newInstance() {
		HistoryAudioFragment fragment = new HistoryAudioFragment();
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
    	this.setModel(HistoryModel.getInstance());
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_history_audio_layout, container, false);
    	
    	return rootView;
    }

	public HistoryModel getModel() {
		return model;
	}

	public void setModel(HistoryModel model) {
		this.model = model;
	}
}