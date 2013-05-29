package fr.nf28.vmote.series.adapter;

import java.util.List;

import fr.nf28.vmote.R;
import fr.nf28.vmote.series.model.SeriesModel;
import fr.nf28.vmote.tvdb.SearchSeries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class TvShowSearchAdapter extends ArrayAdapter<SearchSeries> {
	private final Context context;
	private final SeriesModel model;
	private final List<SearchSeries> list;

	public TvShowSearchAdapter(Context context, List<SearchSeries> list, SeriesModel model) {
		super(context, R.layout.tvseries_add_list_cell, list);
		this.context = context;
		this.list = list;
		this.model = model;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.tvseries_add_list_cell, parent, false);
		
		TextView seriesName = (TextView) rowView.findViewById(R.id.seriesName);
		TextView seriesOverview = (TextView) rowView.findViewById(R.id.seriesOverview);
		Button btn = (Button) rowView.findViewById(R.id.search_button);
		
		final SearchSeries ss = list.get(position);
		
		seriesName.setText(ss.getSeriesName());
		seriesOverview.setText(ss.getOverview());

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				model.getSeries(ss);
			}
		});

		return rowView;
	}
} 