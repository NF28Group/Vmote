package fr.nf28.vmote.series.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import fr.nf28.vmote.R;

public class SeriesEpisodeFragment extends AbstractSeriesFragment {
	private View rootView;
	
	public SeriesEpisodeFragment(){}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	rootView = inflater.inflate(
    			R.layout.fragment_series_episode_layout, container, false);

    	return rootView;
    }
}
