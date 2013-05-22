package fr.nf28.vmote.play.view;

import fr.nf28.vmote.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayMainFragment extends AbstractPlayFragment {
	public static final String ARG_ITEM_ID = "main_play_fragment";
	
	private View rootView;
	
	public PlayMainFragment() {}
	
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        //TODO switch pages interfaces

    }
	
    @Override
    public void onDetach() {
        super.onDetach();
    }
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_lecture_main_layout, container, false);
    	
	    
    	
    	return rootView;
    }
}