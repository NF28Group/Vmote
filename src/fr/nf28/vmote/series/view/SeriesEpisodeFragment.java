package fr.nf28.vmote.series.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.db.episode.Episode;
import fr.nf28.vmote.db.episode.EpisodeDAO;
import fr.nf28.vmote.db.tvshow.TvShow;
import fr.nf28.vmote.series.adapter.TvShowEpisodeAdapter;
import fr.nf28.vmote.series.adapter.TvShowSeasonAdapter;

@SuppressLint("ValidFragment")
public class SeriesEpisodeFragment extends AbstractSeriesFragment {
	private View rootView;
	private TvShow tvShow;
	private int seasonNumber;
	private List<Episode> episodeList;
	
	public SeriesEpisodeFragment(TvShow newTvShow, int newSeasonNumber){
		this.tvShow = newTvShow;
		this.seasonNumber = newSeasonNumber;
	}
	public SeriesEpisodeFragment(){}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	rootView = inflater.inflate(
    			R.layout.fragment_series_episode_layout, container, false);
    	
    	TextView tvShowTitle = (TextView) rootView.findViewById(R.id.tvShowTitleEpisodeView);
    	TextView seasonNumberTextView = (TextView) rootView.findViewById(R.id.tvShowSeasonEpisodeView);
    	
    	tvShowTitle.setText(this.tvShow.getName());
    	seasonNumberTextView.setText("Saison " + seasonNumber);
    	
    	ListView episodeListView = (ListView) rootView.findViewById(R.id.tvShowEpisodeListView);
    	
    	// LOAD DATA
    	loadData();
    	
    	TvShowEpisodeAdapter adapter = new TvShowEpisodeAdapter(rootView.getContext(), R.layout.tvseries_episode_list_cell, episodeList);
    	episodeListView.setAdapter(adapter);

    	return rootView;
    }
	
	public void loadData(){
		EpisodeDAO episodeAccessObject = new EpisodeDAO(rootView.getContext());
		episodeList = new ArrayList<Episode>();
		episodeList = episodeAccessObject.selectTvShowAndSeason(tvShow.getId(), seasonNumber);
	}
}
