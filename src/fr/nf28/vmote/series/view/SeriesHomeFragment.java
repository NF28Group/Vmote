package fr.nf28.vmote.series.view;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.play.view.PlayMainFragment;
import fr.nf28.vmote.series.adapter.TvShow;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_series_main_layout, container, false);
    	
    	tvShowListView = (ListView) rootView.findViewById(R.id.tvShowsListView);
    	
    	initList();
    	
    	TvShowListAdapter listAdapter = new TvShowListAdapter(rootView.getContext(), R.layout.tvseries_main_list_cell, seriesList);
    	
    	tvShowListView.setAdapter(listAdapter);
    	tvShowListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		  @Override
    		  public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
    		      System.out.println("Serie : " + seriesList.get(position).getName());
    		      
    		      SeriesSeasonFragment fragment = new SeriesSeasonFragment(seriesList.get(position));

    		      android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
    		      FragmentTransaction transaction = fm.beginTransaction();
    		      transaction.replace(R.id.applicationview_detail_container, fragment);
    		      transaction.commit();
    		      
    		  }
    		});
    	return rootView;
    }

	public void initList(){
		seriesList = new ArrayList<TvShow>();

		TvShow tvShow1 = new TvShow(0, "Game Of Thrones", "", "HBO", "Fantastique", 0, 0, "");
		TvShow tvShow2 = new TvShow(0, "Breaking Bad", "","AMC", "Thriller", 0, 0, "");
		TvShow tvShow3 = new TvShow(0, "Big Bang Theory", "", "", "Comedie", 0, 0, "");
		TvShow tvShow4 = new TvShow(0, "How I Met Your Mother", "", "", "Comedie", 0, 0, "");
		TvShow tvShow5 = new TvShow(0, "Mentalist", "", "CBS","Policier", 0, 0, "");
		TvShow tvShow6 = new TvShow(0, "Prison Break", "","Fox", "Thriller", 0, 0, "");
		TvShow tvShow7 = new TvShow(0, "The Walking Dead", "", "AMC" , "Horreur", 0, 0, "");
		
		seriesList.add(tvShow1);
		seriesList.add(tvShow2);
		seriesList.add(tvShow3);
		seriesList.add(tvShow4);
		seriesList.add(tvShow5);
		seriesList.add(tvShow6);
		seriesList.add(tvShow7);
		
	}
}

