package fr.nf28.vmote.series.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
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
			//Recherche asynchrone des s�ries et override de la m�thode de callback pour mettre � jour la vue.
			pcs.firePropertyChange("startProgressBar", null, null);
			new SearchSeriesAsyncTask(){
				@Override
				protected void onPostExecute(List<SearchSeries> result) { //Callback
					if(result != null){
						TvShowSearchAdapter adapter = new TvShowSearchAdapter(cxt, result, that);
						TvShowSearchAdapter old = null;
						pcs.firePropertyChange("listAdapter", old, adapter);
					}
					else {
						//Erreur r�seau
						Toast.makeText(cxt, "Erreur r�seau", Toast.LENGTH_SHORT).show();
					}
					pcs.firePropertyChange("stopProgressBar", null, null);
				}
			}.execute(seriesName);
		} else {
			Toast.makeText(cxt, "No network connection available.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void getSeries(SearchSeries ss){
		getSeries(ss.getSeriesId(), false);
	}
	/**
	 * Get a new tvshow or refresh one
	 * @param seriesName
	 */
	private void getSeries(long seriesId, final boolean update){
		ConnectivityManager connMgr = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			pcs.firePropertyChange("startProgressBar", 1, 0);
			new GetSeriesAsyncTask(){
				@Override
				protected void onPostExecute(List<Object> result) { //Callback
					if(result != null){
						TvShow ts = null;
						//Add series to 
						TvShowDAO tsDao = new TvShowDAO(cxt);
						EpisodeDAO epDao = new EpisodeDAO(cxt);
						for(Object o : result){
							if(o instanceof TvShow){
								ts = (TvShow) o;
								if(tsDao.select(ts.getId()) == null){
									String imagePath = "img-" + ts.getId();
									ts.setPosterPath(imagePath);
									dllAndSaveImage(ts.getPosterUrl(), imagePath);
									tsDao.insert(ts);
								}
							}
							else if(o instanceof Episode){
								Episode ep = (Episode) o;
								if(epDao.select(ep.getId()) == null){
									epDao.insert(ep);
								}
							}
						}
						if(!update && ts != null){
							Toast.makeText(cxt, ts.getName() + " ajout�", Toast.LENGTH_SHORT).show();
						}
					}
					else {
						//Erreur r�seau
						Toast.makeText(cxt, "Erreur r�seau", Toast.LENGTH_SHORT).show();
					}
					pcs.firePropertyChange("stopProgressBar", null, null);
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
					if(result != null){
						ImageHelper.saveBitmap(result, cxt, filepath);
					}
					else {
						//
					}
				}
			}.execute(url);
		} else {
			Toast.makeText(cxt, "No network connection available.", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void updateAllSeries(){
		TvShowDAO tvShowDao = new TvShowDAO(cxt);
		List<TvShow> seriesList = new ArrayList<TvShow>();
		seriesList = tvShowDao.selectAll();
		
		for(TvShow s : seriesList){
			getSeries(s.getId(), true);
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
