package fr.nf28.vmote.series.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import fr.nf28.vmote.R;
import fr.nf28.vmote.db.episode.EpisodeDAO;
import fr.nf28.vmote.db.tvshow.TvShow;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
		ImageView tvShowPosterView = (ImageView) rowView.findViewById(R.id.tvShowPoster);
		tvShowPosterView.setImageBitmap(null);
		
		TvShow currentTvShow = list.get(position);
		
		FileInputStream fis;
		try {
			fis = context.openFileInput(currentTvShow.getPosterPath());
			tvShowPosterView.setImageBitmap(BitmapFactory.decodeStream(fis));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tvShowPosterView.postInvalidate();
		tvShowName.setText(list.get(position).getName());
		
	
		//Calculate the number of remaining episodes
		EpisodeDAO episodeAccessObject = new EpisodeDAO(getContext());
		int remainingEpisodes = episodeAccessObject.getEpisodesUnseenWithId(currentTvShow.getId());
		
		if(remainingEpisodes > 0){
			tvShowRemain.setText(remainingEpisodes + " episodes à voir");
			rowView.setBackgroundColor(Color.WHITE);
		}
		else{
			tvShowRemain.setText("Serie à jour");
		}
		
		episodeAccessObject.close();
		
		return rowView;
	}

}
