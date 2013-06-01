package fr.nf28.vmote.series.adapter;

import java.util.List;

import fr.nf28.vmote.R;
import fr.nf28.vmote.db.episode.EpisodeDAO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TvShowSeasonAdapter extends ArrayAdapter<Integer> {
	private final Context context;
	private final List<Integer> list;
	private final long tvShow_id;

	public TvShowSeasonAdapter(Context context, int textViewResourceId, List<Integer> objects, long tvShow_id) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.list = objects;
		this.tvShow_id = tvShow_id;
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
		
		Integer seasonNumber = list.get(position);
		if(seasonNumber != 0){
			seasonLabel.setText("Saison " + seasonNumber);
		}
		else {
			seasonLabel.setText("Specials");
		}
		
		
		//TODO Change the imageView with the good value of boolean (all see/unseen)
		EpisodeDAO episodeAccessObject = new EpisodeDAO(context);
		
		if(episodeAccessObject.isSeasonSeenWithIdAndSeason(tvShow_id, seasonNumber)){
			checkImageView.setImageResource(R.drawable.checkicon3);
		} 
		else {
			checkImageView.setImageResource(0);
		}
		
		return rowView;
	}
}
