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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


public class SeriesAddFragment extends AbstractSeriesFragment {
	
	private View rootView;
	private SeriesModel model;

	private EditText searchInput;
	private Button searchButton;
	private ListView listResult;
	
	public SeriesAddFragment(){}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	rootView = inflater.inflate(R.layout.fragment_series_add_layout, container, false);
   
    	model = new SeriesModel(getActivity());
    	
		searchInput = (EditText) rootView.findViewById(R.id.search_input);
		searchButton = (Button) rootView.findViewById(R.id.search_button);
		listResult = (ListView) rootView.findViewById(R.id.list_result);


		searchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String searchName = searchInput.getText().toString();
				model.searchSeries(searchName);
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
