package fr.nf28.vmote.series.view;

import fr.nf28.vmote.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SeriesAddFragment extends AbstractSeriesFragment {
	
	private View rootView;
	
	public SeriesAddFragment(){}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_series_add_layout, container, false);
    	
    
    	
    	return rootView;
    }

}
