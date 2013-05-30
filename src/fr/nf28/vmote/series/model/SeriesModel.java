package fr.nf28.vmote.series.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import fr.nf28.vmote.db.episode.Episode;
import fr.nf28.vmote.db.episode.EpisodeDAO;
import fr.nf28.vmote.db.tvshow.TvShow;
import fr.nf28.vmote.db.tvshow.TvShowDAO;
import fr.nf28.vmote.lib.ImageHelper;
import fr.nf28.vmote.series.adapter.TvShowSearchAdapter;
import fr.nf28.vmote.tvdb.SearchSeries;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class SeriesModel {
	private Context cxt;
	private SeriesModel that;
	PropertyChangeSupport pcs;

	
	public SeriesModel(Context cxt){
		this.cxt = cxt;
		this.that = this;
		this.pcs = new PropertyChangeSupport(this);
	}
	

	/** 
	 * Search a new tvshow
	 * @param seriesName
	 */
	public void searchSeries(String seriesName){
		ConnectivityManager connMgr = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			//Recherche asynchrone des séries et override de la méthode de callback pour mettre à jour la vue.
			new SearchSeriesAsyncTask(){
				@Override
				protected void onPostExecute(List<SearchSeries> result) { //Callback
					TvShowSearchAdapter adapter = new TvShowSearchAdapter(cxt, result, that);
					TvShowSearchAdapter old = null;
					pcs.firePropertyChange("listAdapter", old, adapter);
				}
			}.execute(seriesName);
		} else {
			Toast.makeText(cxt, "No network connection available.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void getSeries(SearchSeries ss){
		getSeries(ss.getSeriesId());
	}
	/**
	 * Get a new tvshow or refresh one
	 * @param seriesName
	 */
	public void getSeries(long seriesId){
		ConnectivityManager connMgr = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			new GetSeriesAsyncTask(){
				@Override
				protected void onPostExecute(List<Object> result) { //Callback
					//Add series to 
					TvShowDAO tsDao = new TvShowDAO(cxt);
					EpisodeDAO epDao = new EpisodeDAO(cxt);
					for(Object o : result){
						if(o instanceof TvShow){
							TvShow ts = (TvShow) o;
							String imagePath = "img-" + ts.getId();
							dllAndSaveImage(ts.getPosterUrl(), imagePath);
							ts.setPosterPath(imagePath);
							tsDao.insert(ts);
						}
						else if(o instanceof Episode){
							epDao.insert((Episode) o);
						}
					}
				}
			}.execute(seriesId);
		} else {
			Toast.makeText(cxt, "No network connection available.", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void dllAndSaveImage(String url, final String filepath){
		ConnectivityManager connMgr = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			new DownloadImageAsyncTask(){
				@Override
				protected void onPostExecute(Bitmap result) { //Callback
					ImageHelper.saveBitmap(result, cxt, filepath);
				}
			}.execute(url);
		} else {
			Toast.makeText(cxt, "No network connection available.", Toast.LENGTH_SHORT).show();
		}
	}
	
	/** PropertyChangeListener Pattern **/
	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}
}
