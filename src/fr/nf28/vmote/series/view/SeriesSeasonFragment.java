package fr.nf28.vmote.series.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.db.episode.EpisodeDAO;
import fr.nf28.vmote.db.tvshow.TvShow;
import fr.nf28.vmote.series.adapter.TvShowSeasonAdapter;

@SuppressLint("ValidFragment")
public class SeriesSeasonFragment extends AbstractSeriesFragment {
	public static final String ARG_ITEM_ID = "serie_season_fragment";

	private View rootView;
	private TvShow tvShow;
	private List<Integer> seasons;
	
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
    	
    	// Header TV Show
    	TextView titleView = (TextView) rootView.findViewById(R.id.tvShowTitle);
    	TextView channelView = (TextView) rootView.findViewById(R.id.tvShowChannel);
    	TextView genreView = (TextView) rootView.findViewById(R.id.tvShowGenre);
    	TextView runtimeView = (TextView) rootView.findViewById(R.id.tvShowLength);
    	ImageView tvShowPosterView = (ImageView) rootView.findViewById(R.id.tvShowPosterView);

    	titleView.setText(tvShow.getName());
    	channelView.setText(tvShow.getNetwork());
    	genreView.setText("Genre: " + tvShow.getGenre());
    	runtimeView.setText("Format: " + tvShow.getRuntime() + " min");
    	
    	FileInputStream fis;
		try {
			fis = rootView.getContext().openFileInput(tvShow.getPosterPath());
			tvShowPosterView.setImageBitmap(BitmapFactory.decodeStream(fis));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	//Seasons List
    	loadData();
    	
    	// Get the seasonListView
    	ListView seasonListView = (ListView) rootView.findViewById(R.id.tvShowSeasonListView);
 
    	TvShowSeasonAdapter adapter = new TvShowSeasonAdapter(rootView.getContext(), R.layout.tvseries_season_list_cell, seasons, tvShow.getId());
    	seasonListView.setAdapter(adapter);
    	
    	seasonListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
  		  @Override
  		  public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
  			  
  			  int seasonNumber = seasons.get(position);
  		      SeriesEpisodeFragment fragment = new SeriesEpisodeFragment(tvShow, seasonNumber);

  		      android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
  		      android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
  		      transaction.replace(R.id.applicationview_detail_container, fragment);
  		      transaction.commit();
  		  }
  		});
    	
    	return rootView;
    }
	
	public void loadData(){
		EpisodeDAO episodeAccessObject = new EpisodeDAO(rootView.getContext());
		seasons = new ArrayList<Integer>();
		seasons = episodeAccessObject.getSeasonNumberWithId(tvShow.getId());
		
		// Get number of seasons
		for(int i = 0; i < seasons.size(); i++){
			System.out.println("number: " + seasons.get(i));
		}

	}

}
