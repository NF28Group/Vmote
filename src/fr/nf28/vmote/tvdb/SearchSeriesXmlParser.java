package fr.nf28.vmote.tvdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class SearchSeriesXmlParser extends XmlParser{
	private static final String root = "Data";
	private static final String entry = "Series";
	private static final String s_seriesId = "seriesid";
	private static final String s_seriesName = "SeriesName";
	private static final String s_overview = "Overview";


	/** Private methods **/

	@Override
	protected List<Object> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.i("TvdbXmlParser", "Strat Parsing");
		
		List<Object> entries = new ArrayList<Object>();

		parser.require(XmlPullParser.START_TAG, ns, root);
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			
			// Starts by looking for the entry tag
			if (name.equals(entry)) {
				SearchSeries s = readEntry(parser);
				if(s != null) {
					entries.add(s);
				}
			} else {
				skip(parser);
			}
		}  
		
		Log.i("TvdbXmlParser", "End Parsing, " + entries.size() + " series read.");
		return entries;
	}

	// Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
	// to their respective "read" methods for processing. Otherwise, skips the tag.
	private SearchSeries readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, entry);
		long seriesId = -1;
		String seriesName = null;
		String overview = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			
			if (name.equals(s_seriesId)) {
				seriesId = readLong(parser, s_seriesId);
			} else if (name.equals(s_seriesName)) {
				seriesName = readString(parser, s_seriesName);
			} else if (name.equals(s_overview)) {
				overview = readString(parser, s_overview);
			} else {
				skip(parser);
			}
		}

		if(seriesId == -1){
			return null;
		}
		else {
			return new SearchSeries(seriesId, seriesName, overview);
		}
	}
}
