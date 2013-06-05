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
import android.widget.ImageButton;
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
    	rootView = inflater.inflate(R.layout.fragment_series_main_layout, container, false);
    	
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
    	ImageButton addTvShowButton = (ImageButton) rootView.findViewById(R.id.tvShowAddButton);
    	ImageButton planningButton = (ImageButton) rootView.findViewById(R.id.tvShowPlanningButton);
    	
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
		//Open the DAO
		TvShowDAO tvShowAccesObject = new TvShowDAO(rootView.getContext());
		seriesList = new ArrayList<TvShow>();
		
		//Get all tvshows
		seriesList = tvShowAccesObject.selectAll();
		
		//close DAO (Important)
		tvShowAccesObject.close();
	}
}

