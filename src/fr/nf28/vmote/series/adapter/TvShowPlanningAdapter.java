package fr.nf28.vmote.series.adapter;

import java.io.ObjectStreamClass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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




public class TvShowPlanningAdapter extends ArrayAdapter<Object> {
	private final Context context;
	private final List<Object> list;
	
	private static final int ITEM_VIEW_TYPE_EPISODE = 0;
	private static final int ITEM_VIEW_TYPE_HEADER = 1;
	private static final int ITEM_VIEW_TYPE_COUNT = 2;

	public TvShowPlanningAdapter(Context context, int textViewResourceId, List<Object> objects) {
		super(context, textViewResourceId, objects);
		this.context = context; 
		this.list = objects;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(list.get(position) instanceof Episode){
			return ITEM_VIEW_TYPE_EPISODE;
		}else {
			return ITEM_VIEW_TYPE_HEADER;
		}
	}
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return ITEM_VIEW_TYPE_COUNT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final int type = getItemViewType(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		if(convertView != null) // If convertView != null, view is recycled, just get it
			rowView = convertView;
		else // Else inflate
			rowView = inflater.inflate(type == ITEM_VIEW_TYPE_HEADER ? R.layout.tvseries_planning_header_row : R.layout.tvseries_planning_row, parent, false);
		
		
		if(type == ITEM_VIEW_TYPE_EPISODE){
			Episode currentEpisode = (Episode) list.get(position);
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
		}
		else {
			TextView dateTextView = (TextView) rowView.findViewById(R.id.tvShowPlanningDate);
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
			Date airDate = new Date();
			try {
				airDate = sdf.parse((String)list.get(position));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM", Locale.FRANCE);
			String airDateString = dateFormat.format(airDate);		
			
			dateTextView.setText(airDateString);
		}
		
		
		return rowView;
	}



}
