package fr.nf28.vmote.series.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import fr.nf28.vmote.R;
import fr.nf28.vmote.series.adapter.TvShowSearchAdapter;
import fr.nf28.vmote.series.model.SeriesModel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

//TODO à muter en fragment
public class SearchActivityTemp extends Activity {
	private SeriesModel model;

	private EditText searchInput;
	private Button searchButton;
	private ListView listResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		model = new SeriesModel(this);

		setContentView(R.layout.fragment_series_add_layout);   
		searchInput = (EditText) findViewById(R.id.search_input);
		searchButton = (Button) findViewById(R.id.search_button);
		listResult = (ListView) findViewById(R.id.list_result);


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
	}
}
