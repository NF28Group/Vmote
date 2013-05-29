package fr.nf28.vmote.series.adapter;

import java.util.List;

import fr.nf28.vmote.R;
import fr.nf28.vmote.db.tvshow.TvShow;

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
		else // Else, first time => inflate
			rowView = inflater.inflate(R.layout.tvseries_main_list_cell, parent, false);
		
		//Fill textView in view with good values
		TextView tvShowName = (TextView) rowView.findViewById(R.id.tvShowName);
		TextView tvShowRemain = (TextView) rowView.findViewById(R.id.tvShowText);
		
		tvShowName.setText(list.get(position).getName());
		//TODO : Calculate the number of remaining episodes
		tvShowRemain.setText("3 episodes restants");
		
		return rowView;
	}

}
