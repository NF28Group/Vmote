package fr.nf28.vmote.series.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.db.episode.Episode;
import fr.nf28.vmote.db.episode.EpisodeDAO;
import fr.nf28.vmote.db.tvshow.TvShow;
import fr.nf28.vmote.series.adapter.TvShowEpisodeAdapter;

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
    	Button setAllSeenButton = (Button) rootView.findViewById(R.id.tvShowEpisodesAllSeen);
    	
    	tvShowTitle.setText(this.tvShow.getName());
    	seasonNumberTextView.setText("Saison " + seasonNumber);
    	
    	ListView episodeListView = (ListView) rootView.findViewById(R.id.tvShowEpisodeListView);
    	
    	// LOAD DATA
    	loadData();
    	
    	final TvShowEpisodeAdapter adapter = new TvShowEpisodeAdapter(rootView.getContext(), R.layout.tvseries_episode_list_cell, episodeList);
    	episodeListView.setAdapter(adapter);
    	
    	setAllSeenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EpisodeDAO episodeAccessObject = new EpisodeDAO(rootView.getContext());
				
				if(episodeAccessObject.isSeasonSeenWithIdAndSeason(tvShow.getId(), seasonNumber))
					episodeAccessObject.setAllEpisodesSeenInSeasonWithId(false, seasonNumber, tvShow.getId());
				else
					episodeAccessObject.setAllEpisodesSeenInSeasonWithId(true, seasonNumber, tvShow.getId());
				adapter.notifyDataSetChanged();
			}
		});

    	return rootView;
    }
	
	public void loadData(){
		EpisodeDAO episodeAccessObject = new EpisodeDAO(rootView.getContext());
		episodeList = new ArrayList<Episode>();
		episodeList = episodeAccessObject.selectTvShowAndSeason(tvShow.getId(), seasonNumber);
	}
}
