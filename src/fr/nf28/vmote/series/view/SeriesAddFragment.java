package fr.nf28.vmote.series.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import fr.nf28.vmote.R;
import fr.nf28.vmote.series.adapter.TvShowSearchAdapter;
import fr.nf28.vmote.series.model.SeriesModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;


public class SeriesAddFragment extends AbstractSeriesFragment {

	private View rootView;
	private SeriesModel model;

	private SearchView searchInput;
	private ListView listResult;

	public SeriesAddFragment(){}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_series_add_layout, container, false);
		model = new SeriesModel(getActivity());

		//searchInput = (EditText) rootView.findViewById(R.id.search_input);
		listResult = (ListView) rootView.findViewById(R.id.list_result);
		searchInput = (SearchView) rootView.findViewById(R.id.searchTvshowView);

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
				//InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				//imm.hideSoftInputFromWindow(rootView.getWindowToken(), 0);
				return true;
			}
		});

		model.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("listAdapter")){
					listResult.setAdapter((TvShowSearchAdapter) evt.getNewValue());
				}
			}
		});

		return rootView;
	}
}
