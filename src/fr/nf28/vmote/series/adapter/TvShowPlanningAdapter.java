package fr.nf28.vmote.series.adapter;

import java.io.ObjectStreamClass;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.nf28.vmote.R;
import fr.nf28.vmote.db.episode.Episode;
import fr.nf28.vmote.db.tvshow.TvShow;
import fr.nf28.vmote.db.tvshow.TvShowDAO;




public class TvShowPlanningAdapter extends ArrayAdapter<Episode> {
	private final Context context;
	private final List<Episode> list;
	
	private static final int ITEM_VIEW_TYPE_EPISODE = 0;
	private static final int ITEM_VIEW_TYPE_SEPARATOR = 1;
	private static final int ITEM_VIEW_TYPE_COUNT = 2;

	public TvShowPlanningAdapter(Context context, int textViewResourceId, List<Episode> objects) {
		super(context, textViewResourceId, objects);
		this.context = context; 
		this.list = objects;
	}
	
//	@Override
//	public int getItemViewType(int position) {
//		if(list.get(position) instanceof Episode){
//			return ITEM_VIEW_TYPE_EPISODE;
//		}else {
//			return ITEM_VIEW_TYPE_SEPARATOR;
//		}
//	}
//	@Override
//	public int getViewTypeCount() {
//		// TODO Auto-generated method stub
//		return ITEM_VIEW_TYPE_COUNT;
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		if(convertView != null) // If convertView != null, view is recycled, just get it
			rowView = convertView;
		else // Else inflate
			rowView = inflater.inflate(R.layout.tvseries_planning_row, parent, false);
		
		Episode currentEpisode = list.get(position);
		TvShow currentTvShow = new TvShowDAO(context).select(currentEpisode.getTvShow_id());
				
		TextView tvShowTextView = (TextView) rowView.findViewById(R.id.tvShowPlanningShowTitle);
		TextView episodeTextView = (TextView) rowView.findViewById(R.id.tvShowPlanningEpisodeTitle);
		TextView episodeNumberTextView = (TextView) rowView.findViewById(R.id.tvShowPlanningEpisodeNumber);
		TextView networkTextView = (TextView) rowView.findViewById(R.id.tvShowPlanningNetwork);
		
		tvShowTextView.setText(currentTvShow.getName());
		episodeTextView.setText(currentEpisode.getEpisodeName());
		episodeNumberTextView.setText(currentEpisode.getSeasonNumberString()+"x"+currentEpisode.getEpisodeNumberString());
		networkTextView.setText("Diffusé sur " + currentTvShow.getNetwork());
		
		System.out.println("Date" + position + " : " + currentEpisode.getFirstAiredString());
		
		return rowView;
	}


}
