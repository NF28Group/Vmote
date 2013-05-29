package fr.nf28.vmote.series.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParserException;

import fr.nf28.vmote.tvdb.SearchSeries;
import fr.nf28.vmote.tvdb.TvdbXmlParser;

import android.os.AsyncTask;
import android.util.Log;

//Implementation of AsyncTask used to download XML feed from stackoverflow.com.
public class SearchSeriesAsyncTask extends AsyncTask<String, Void, List<SearchSeries>> {
	private final static String URL_SEARCH_TVSHOW = "http://thetvdb.com/api/GetSeries.php?";

	@Override
	protected List<SearchSeries> doInBackground(String... urls) {
		try {
			String searchName = urls[0];

			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("seriesname", searchName));
			params.add(new BasicNameValuePair("language", "fr"));
			String paramString = URLEncodedUtils.format(params, "utf-8");
			String url = URL_SEARCH_TVSHOW + paramString;

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
	private List<SearchSeries> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {
		InputStream stream = null;
		// Instantiate the parser
		TvdbXmlParser tvdbXmlParser = new TvdbXmlParser();
		stream = downloadUrl(urlString);  

		List<SearchSeries> list = tvdbXmlParser.parse(stream);
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
}