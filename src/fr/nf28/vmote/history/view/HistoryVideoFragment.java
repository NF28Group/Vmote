package fr.nf28.vmote.history.view;

import fr.nf28.vmote.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HistoryVideoFragment extends AbstractHistoryFragment {
	public static final String ARG_ITEM_ID = "history_video_fragment";

	private View rootView;
	
	public HistoryVideoFragment() {}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_history_video_layout, container, false);
    	
	    
    	
    	return rootView;
    }
}