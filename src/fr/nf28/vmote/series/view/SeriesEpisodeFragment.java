package fr.nf28.vmote.series.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
	private EpisodeDAO episodeAccessObject;
	
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
    	
    	// LOAD DATA
    	episodeAccessObject = new EpisodeDAO(rootView.getContext());
    	loadData();
    	
    	// Get objects from views
    	TextView tvShowTitle = (TextView) rootView.findViewById(R.id.tvShowTitleEpisodeView);
    	TextView seasonNumberTextView = (TextView) rootView.findViewById(R.id.tvShowSeasonEpisodeView);
    	final ImageButton setAllSeenButton = (ImageButton) rootView.findViewById(R.id.tvShowEpisodesAllSeen);
    	
    	// List View configuration
    	ListView episodeListView = (ListView) rootView.findViewById(R.id.tvShowEpisodeListView);
    	final TvShowEpisodeAdapter adapter = new TvShowEpisodeAdapter(rootView.getContext(), R.layout.tvseries_episode_list_cell, episodeList);
    	episodeListView.setAdapter(adapter);

    	// Configure elements
    	tvShowTitle.setText(this.tvShow.getName());
    	seasonNumberTextView.setText("Saison " + seasonNumber);
    	
    	//Buttons
    	if(episodeAccessObject.isSeasonSeenWithIdAndSeason(tvShow.getId(), seasonNumber))
    		setAllSeenButton.setImageResource(R.drawable.checkmarkgrey);
		else
			setAllSeenButton.setImageResource(R.drawable.uncheckmarkgrey);

    	setAllSeenButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(episodeAccessObject.isSeasonSeenWithIdAndSeason(tvShow.getId(), seasonNumber)){
					episodeAccessObject.setAllEpisodesSeenInSeasonWithId(false, seasonNumber, tvShow.getId());
					setAllSeenButton.setImageResource(R.drawable.uncheckmarkgrey);
				}
				else{
					episodeAccessObject.setAllEpisodesSeenInSeasonWithId(true, seasonNumber, tvShow.getId());
					setAllSeenButton.setImageResource(R.drawable.checkmarkgrey);
				}
				//Update listView
				loadData();
				adapter.clear();
				adapter.addAll(episodeList);
				adapter.notifyDataSetChanged();
			}
		});

    	return rootView;
    }
	
	public void loadData(){
		episodeList = new ArrayList<Episode>();
		episodeList = episodeAccessObject.selectTvShowAndSeason(tvShow.getId(), seasonNumber);
	}
}
