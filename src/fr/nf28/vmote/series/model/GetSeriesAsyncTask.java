package fr.nf28.vmote.series.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.xmlpull.v1.XmlPullParserException;

import fr.nf28.vmote.tvdb.GetSeriesXmlParser;

import android.os.AsyncTask;
import android.util.Log;

//Implementation of AsyncTask used to download XML feed from stackoverflow.com.
public class GetSeriesAsyncTask extends AsyncTask<Long, Void, List<Object>> {
	private final static String API_KEY = "4F4FD3B2346328EE";
	private final static String URL_SEARCH_TVSHOW = "http://thetvdb.com/api/" + API_KEY + "/series/";
	private final static String language = "en";
	//http://thetvdb.com/api/4F4FD3B2346328EE/series/81189/all/fr.zip 

	@Override
	protected List<Object> doInBackground(Long... seriesIds) {
		try {
			long seriesId = seriesIds[0];

			String url = URL_SEARCH_TVSHOW + seriesId + "/all/" + language + ".zip";

			return loadXmlFromNetwork(url);
		} catch (IOException e) {
			return null; //getResources().getString(R.string.connection_error);
		} catch (XmlPullParserException e) {
			return null; //getResources().getString(R.string.xml_error);
		}
	}

	//@Override
	//protected void onPostExecute(List<SearchSeries> result){}

	// Uploads XML from stackoverflow.com, parses it, and combines it with
	// HTML markup. Returns HTML string.
	private List<Object> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
		InputStream stream = null;
		// Instantiate the parser
		GetSeriesXmlParser getSeriesXmlParser = new GetSeriesXmlParser();
		stream = downloadUrl(urlString);  
		stream = unzip(stream);
		
		List<Object> list = getSeriesXmlParser.parse(stream);
		stream.close();

		return list;
	}

	// Given a string representation of a URL, sets up a connection and gets
	// an input stream.
	private InputStream downloadUrl(String urlString) throws IOException {
		Log.i("Download", urlString);
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setReadTimeout(10000 /* milliseconds */);
		conn.setConnectTimeout(15000 /* milliseconds */);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		// Starts the query
		conn.connect();
		return conn.getInputStream();
	}


	public InputStream unzip(InputStream is) { 
		ZipEntry ze = null; 
		ZipInputStream zis = new ZipInputStream(is); 
		try  {		
			while ((ze = zis.getNextEntry()) != null) { 
				Log.v("Decompress", "Unzipping " + ze.getName()); 

				//while (zis.available() > 0){
	            //    zis.read();
				//}
				//zis.closeEntry(); 
				if(ze.getName().equals(language + ".xml")){
					break;
				}
			} 
			//zis.close(); 
		} catch(Exception e) { 
			Log.e("Decompress", "unzip", e); 
		} 

		return zis;
	} 
}
