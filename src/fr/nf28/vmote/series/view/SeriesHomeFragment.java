package fr.nf28.vmote.series.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.db.tvshow.TvShow;
import fr.nf28.vmote.db.tvshow.TvShowDAO;
import fr.nf28.vmote.series.adapter.TvShowListAdapter;


public class SeriesHomeFragment extends AbstractSeriesFragment {
	public static final String ARG_ITEM_ID = "serie_home_fragment";

	private View rootView;
	private ListView tvShowListView;
	private List<TvShow> seriesList;
	
	public SeriesHomeFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	rootView = inflater.inflate(
    			R.layout.fragment_series_main_layout, container, false);
    	
    	tvShowListView = (ListView) rootView.findViewById(R.id.tvShowsListView);
    	tvShowListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    	
    	// Initialize List of TV Shows
    	loadData();
    	
    	// Instantiate ListAdapter
    	TvShowListAdapter listAdapter = new TvShowListAdapter(rootView.getContext(), R.layout.tvseries_main_list_cell, seriesList);
    	
    	// Set Adapter
    	tvShowListView.setAdapter(listAdapter);
    	
    	// Set onClickListener -> Changer fragment
    	tvShowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		  @Override
    		  public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {    
    		      SeriesSeasonFragment fragment = new SeriesSeasonFragment(seriesList.get(position));

    		      android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
    		      FragmentTransaction transaction = fm.beginTransaction();
    		      transaction.replace(R.id.applicationview_detail_container, fragment);
    		      transaction.commit();
    		  }
    	});
    	
    	tvShowListView.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				v.setBackgroundColor(getResources().getColor(R.color.abs__background_holo_light));
				return false;
			}
		});
    	
    	
    	
    	// Button configuration
    	Button addTvShowButton = (Button) rootView.findViewById(R.id.tvShowAddButton);
    	Button planningButton = (Button) rootView.findViewById(R.id.tvShowPlanningButton);
    	
    	// Add button
    	addTvShowButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SeriesAddFragment fragment = new SeriesAddFragment();

  		      	android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
  		      	FragmentTransaction transaction = fm.beginTransaction();
  		      	transaction.replace(R.id.applicationview_detail_container, fragment);
  		      	transaction.commit();
				
				
			}
		});
    	
    	// Planning Button
    	planningButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SeriesPlanningFragment fragment = new SeriesPlanningFragment();

  		      	android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
  		      	FragmentTransaction transaction = fm.beginTransaction();
  		      	transaction.replace(R.id.applicationview_detail_container, fragment);
  		      	transaction.commit();
			}
		});
    	
    	
    	return rootView;
    }

	public void loadData(){
		//TODO Get the real code
		
		seriesList = new ArrayList<TvShow>();
		
		TvShowDAO tvShowAccesObject = new TvShowDAO(rootView.getContext());
		
		seriesList = tvShowAccesObject.selectAll();
		
//		TvShow tvShow1 = new TvShow(0, "Game Of Thrones", "", "", "HBO", "Fantastique", 52, 0, "");
//		TvShow tvShow2 = new TvShow(0, "Breaking Bad", "", "","AMC", "Thriller", 42, 0, "");
//		TvShow tvShow3 = new TvShow(0, "Big Bang Theory", "", "", "", "Comedie", 20, 0, "");
//		TvShow tvShow4 = new TvShow(0, "How I Met Your Mother", "", "", "", "Comedie", 20, 0, "");
//		TvShow tvShow5 = new TvShow(0, "Mentalist", "", "", "CBS","Policier", 42, 0, "");
//		TvShow tvShow6 = new TvShow(0, "Prison Break", "","", "Fox", "Thriller", 42, 0, "");
//		TvShow tvShow7 = new TvShow(0, "The Walking Dead", "","",  "AMC" , "Horreur", 42, 0, "");
//		
//		seriesList.add(tvShow1);
//		seriesList.add(tvShow2);
//		seriesList.add(tvShow3);
//		seriesList.add(tvShow4);
//		seriesList.add(tvShow5);
//		seriesList.add(tvShow6);
//		seriesList.add(tvShow7);
		
	}
}

