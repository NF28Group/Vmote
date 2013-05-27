package fr.nf28.vmote.tvdb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class TvdbXmlParser {
	// We don't use namespaces
	private static final String ns = null;
	private static final String root = "Data";
	private static final String entry = "Series";
	private static final String s_seriesId = "seriesid";
	private static final String s_seriesName = "SeriesName";
	private static final String s_overview = "Overview";

	/** Public methods **/

	public List<SearchSeries> parse(InputStream in) throws XmlPullParserException, IOException {
		try {
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			return readFeed(parser);
		} finally {
			in.close();
		}
	}

	/** Private methods **/

	private List<SearchSeries> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.i("TvdbXmlParser", "Strat Parsing");
		
		List<SearchSeries> entries = new ArrayList<SearchSeries>();

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
				seriesId = readSeriesId(parser);
			} else if (name.equals(s_seriesName)) {
				seriesName = readSeriesName(parser);
			} else if (name.equals(s_overview)) {
				overview = readOverview(parser);
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

	private long readSeriesId(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, s_seriesId);
		String s_seriesId_str = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, s_seriesId);
		return Long.valueOf(s_seriesId_str);
	}

	private String readSeriesName(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, s_seriesName);
		String seriesName = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, s_seriesName);
		return seriesName;
	}

	private String readOverview(XmlPullParser parser) throws IOException, XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, ns, s_overview);
		String banner = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, s_overview);
		return banner;
	}


	// For the tags, extracts their text values.
	private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
			case XmlPullParser.END_TAG:
				depth--;
				break;
			case XmlPullParser.START_TAG:
				depth++;
				break;
			}
		}
	}
}
