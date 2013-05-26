package fr.nf28.vmote.series.adapter;

import java.util.List;

import fr.nf28.vmote.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TvShowListAdapter extends ArrayAdapter<TvShow> {
	private final Context context;
	private final List<TvShow> list;

	public TvShowListAdapter(Context context, int textViewResourceId, List<TvShow> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.list = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		if(convertView != null) // If convertView != null, view is recycled, just get it
			rowView = convertView;
		else // Else inflate
			rowView = inflater.inflate(R.layout.tvseries_main_list_cell, parent, false);
		
		TextView seriesName = (TextView) rowView.findViewById(R.id.tvShowName);
		TextView seriesOverview = (TextView) rowView.findViewById(R.id.tvShowText);
		
		seriesName.setText(list.get(position).getName());
		seriesOverview.setText("3 episodes restants");
		
		return rowView;
	}

}
