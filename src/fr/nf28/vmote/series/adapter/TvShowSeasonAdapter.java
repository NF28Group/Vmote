package fr.nf28.vmote.series.adapter;

import java.util.List;

import fr.nf28.vmote.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TvShowSeasonAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final List<String> list;

	public TvShowSeasonAdapter(Context context, int textViewResourceId, List<String> objects) {
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
			rowView = inflater.inflate(R.layout.tvseries_season_list_cell, parent, false);
		
		TextView seasonLabel = (TextView) rowView.findViewById(R.id.tvShowSeasonLabel);
		ImageView checkImageView = (ImageView) rowView.findViewById(R.id.tvShowSeasonCheck);
		
		seasonLabel.setText(list.get(position));
		
		//TODO Change the imageView with the good value of boolean (all see/unseen)
//		if(//Test boolean){
//			checkImageView.setImageResource(...);
//		} 
//		else {
//			checkImageView.setImageResource(...);
//		}
		
		return rowView;
	}
}
