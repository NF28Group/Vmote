package fr.nf28.vmote.series.view;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.series.adapter.TvShowPlanningAdapter;
import fr.nf28.vmote.db.episode.EpisodeDAO;

public class SeriesPlanningFragment extends AbstractSeriesFragment {
	private View rootView;
	private List<Object> episodeList;
	
	public SeriesPlanningFragment(){}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_series_planning_layout, container, false);
    	
    	EpisodeDAO episodeAccessObject = new EpisodeDAO(rootView.getContext());
    	episodeList = episodeAccessObject.selectPlanningList();
    	episodeAccessObject.close();
    	
    	ListView planningListView = (ListView) rootView.findViewById(R.id.tvShowPlanningListView);
    	TvShowPlanningAdapter adapter = new TvShowPlanningAdapter(rootView.getContext(), R.layout.tvseries_planning_row, episodeList);
    	planningListView.setAdapter(adapter);
    	
    	return rootView;
    }
}
