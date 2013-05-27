package fr.nf28.vmote.series.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import fr.nf28.vmote.series.adapter.TvShowSearchAdapter;
import fr.nf28.vmote.tvdb.SearchSeries;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class SeriesModel {
	private Context cxt;
	PropertyChangeSupport pcs;

	
	public SeriesModel(Context cxt){
		this.cxt = cxt;
		this.pcs = new PropertyChangeSupport(this);
	}
	
	public void searchSeries(String seriesName){
		ConnectivityManager connMgr = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			//Recherche asynchrone des séries et override de la méthode de callback pour mettre à jour la vue.
			new SearchSeriesAsyncTask(){
				@Override
				protected void onPostExecute(List<SearchSeries> result) {
					TvShowSearchAdapter adapter = new TvShowSearchAdapter(cxt, result);
					TvShowSearchAdapter old = null;
					pcs.firePropertyChange("listAdapter", old, adapter);
				}
			}.execute(seriesName);
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
