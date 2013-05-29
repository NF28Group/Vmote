package fr.nf28.vmote.series.adapter;

import java.util.List;

import fr.nf28.vmote.R;
import fr.nf28.vmote.db.episode.Episode;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TvShowEpisodeAdapter extends ArrayAdapter<Episode> {
	private final Context context;
	private final List<Episode> list;

	public TvShowEpisodeAdapter(Context context, int textViewResourceId, List<Episode> objects) {
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
			rowView = inflater.inflate(R.layout.tvseries_episode_list_cell, parent, false);
		
		TextView episodeTitleLabel = (TextView) rowView.findViewById(R.id.tvShowEpisodeLabel);
		Button checkButton = (Button) rowView.findViewById(R.id.tvShowEpisodeCheck);
		
		episodeTitleLabel.setText(list.get(position).getEpisodeName());
		
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
