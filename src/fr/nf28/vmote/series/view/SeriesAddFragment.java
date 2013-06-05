package fr.nf28.vmote.series.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import fr.nf28.vmote.R;
import fr.nf28.vmote.series.adapter.TvShowSearchAdapter;
import fr.nf28.vmote.series.model.SeriesModel;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


public class SeriesAddFragment extends AbstractSeriesFragment {

	private Context that;
	private View rootView;
	private SeriesModel model;

	private SearchView searchInput;
	private ProgressBar progress;
	private ListView listResult;

	public SeriesAddFragment(){}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.that = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_series_add_layout, container, false);
		model = new SeriesModel(that);

		//searchInput = (EditText) rootView.findViewById(R.id.search_input);
		listResult = (ListView) rootView.findViewById(R.id.list_result);
		searchInput = (SearchView) rootView.findViewById(R.id.searchTvshowView);
		progress = (ProgressBar) rootView.findViewById(R.id.progressBar);
		
	
		searchInput.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				String searchName = searchInput.getQuery().toString();
				model.searchSeries(searchName);
				
				InputMethodManager imm = (InputMethodManager)that.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
				return true;
			}
		});

		model.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String event = evt.getPropertyName();
				if(event.equals("listAdapter")){
					listResult.setAdapter((TvShowSearchAdapter) evt.getNewValue());
				}
				else if(event.equals("startProgressBar")){
					Log.d("ProgressBar", "strart");
					progress.setVisibility(ProgressBar.VISIBLE);
				}
				else if(event.equals("stopProgressBar")){
					Log.d("ProgressBar", "stop");
					progress.setVisibility(ProgressBar.INVISIBLE);
				}
			}
		});
		
		

		return rootView;
	}
}
