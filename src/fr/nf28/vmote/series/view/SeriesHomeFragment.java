package fr.nf28.vmote.series.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.nf28.vmote.R;

public class SeriesHomeFragment extends AbstractSeriesFragment {
	public static final String ARG_ITEM_ID = "serie_home_fragment";

	private View rootView;
	
	public SeriesHomeFragment() {}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_series_main_layout, container, false);
    	
	    
    	
    	return rootView;
    }
}
