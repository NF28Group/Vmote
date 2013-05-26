package fr.nf28.vmote.series.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.series.adapter.TvShow;


@SuppressLint("ValidFragment")
public class SeriesSeasonFragment extends AbstractSeriesFragment {
	public static final String ARG_ITEM_ID = "serie_season_fragment";

	private View rootView;
	private TvShow tvShow;
	
	public SeriesSeasonFragment(){}
	public SeriesSeasonFragment(TvShow myTvShow) {
		this.tvShow = myTvShow;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_series_season_layout, container, false);
    	System.out.println("rootCreated");
    	
    	TextView titleView = (TextView) rootView.findViewById(R.id.tvShowTitle);
    	
    	
    	TextView channelView = (TextView) rootView.findViewById(R.id.tvShowChannel);
    	TextView genreView = (TextView) rootView.findViewById(R.id.tvShowGenre);

    	
    	titleView.setText(tvShow.getName());
    	channelView.setText(tvShow.getNetwork());
    	genreView.setText(tvShow.getGenre());

    	
    	return rootView;
    }

}
