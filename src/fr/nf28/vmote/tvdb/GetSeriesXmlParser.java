package fr.nf28.vmote.tvdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import fr.nf28.vmote.db.episode.Episode;
import fr.nf28.vmote.db.tvshow.TvShow;


import android.util.Log;

public class GetSeriesXmlParser extends XmlParser{

	private static final String root = "Data";
	private static final String entrySeries = "Series";
	private static final String entryEpisode = "Episode";
	
	private static final String s_seriesId = "id";
	private static final String s_seriesName = "SeriesName";
	private static final String s_seriesPosterUrl = "poster";
	private static final String s_seriesNetwork = "Network";
	private static final String s_seriesGenre = "Genre";
	private static final String s_seriesRutime = "Runtime";
	private static final String s_seriesRating = "Rating";
	private static final String s_seriesOverview = "Overview";
	
	private static final String s_episodeId = "id";
	private static final String s_episodeTvShow_id = "seriesid";
	private static final String s_episodeSeasonNumber = "SeasonNumber";
	private static final String s_episodeEpisodeNumber = "EpisodeNumber";
	private static final String s_episodeEpisodeName = "EpisodeName";
	private static final String s_episodeOverview = "Overview";
	private static final String s_episodeFirstAired = "FirstAired";

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
			if (name.equals(entrySeries)) {
				Log.d("GetSeriesXmlParser", "Read series entry");
				TvShow ts = readSeriesEntry(parser);
				if(ts != null) {
					entries.add(ts);
				}
			}
			else if (name.equals(entryEpisode)) {
				Log.d("GetSeriesXmlParser", "Read episode entry");
				Episode e = readEpisodeEntry(parser);
				if(e != null) {
					entries.add(e);
				}
			}
			else {
				skip(parser);
			}
		}  
		
		Log.i("TvdbXmlParser", "End Parsing, " + entries.size() + " series read.");
		return entries;
	}

	private TvShow readSeriesEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, entrySeries);
		long seriesId = -1;
		String seriesName = null;
		String posterUrl = null;
		String network = null;
		String genre = null;
		int runtime = 0;
		float rating = 0;
		String overview = null;

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			
			if (name.equals(s_seriesId)) {
				seriesId = readLong(parser, s_seriesId);
			}
			else if (name.equals(s_seriesName)) {
				seriesName = readString(parser, s_seriesName);
			}
			else if (name.equals(s_seriesPosterUrl)) {
				posterUrl = readString(parser, s_seriesPosterUrl);
			}
			else if (name.equals(s_seriesNetwork)) {
				network = readString(parser, s_seriesNetwork);
			}
			else if (name.equals(s_seriesGenre)) {
				genre = readString(parser, s_seriesGenre);
			}
			else if (name.equals(s_seriesRutime)) {
				runtime = readInt(parser, s_seriesRutime);
			}
			else if (name.equals(s_seriesRating)) {
				rating = readFloat(parser, s_seriesRating);
			}
			else if (name.equals(s_seriesOverview)) {
				overview = readString(parser, s_seriesOverview);
			}
			else {
				skip(parser);
			}
		}

		if(seriesId == -1){
			return null;
		}
		else {
			return new TvShow(seriesId, seriesName, posterUrl, null, network, genre, runtime, rating, overview);
		}
	}
	
	private Episode readEpisodeEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, entryEpisode);
		long episodeId = -1;
		long tvShow_id = -1;
		int seasonNumber = 0;
		int episodeNumber = 0;
		String episodeName = null;
		String overview = null;
		String firstAired = null; // Première date de diffusion

		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) {
				continue;
			}
			String name = parser.getName();
			
			if (name.equals(s_episodeId)) {
				episodeId = readLong(parser, s_seriesId);
			}
			else if (name.equals(s_episodeTvShow_id)) {
				tvShow_id = readLong(parser, s_episodeTvShow_id);
			}
			else if (name.equals(s_episodeSeasonNumber)) {
				seasonNumber = readInt(parser, s_episodeSeasonNumber);
			}
			else if (name.equals(s_episodeEpisodeNumber)) {
				episodeNumber = readInt(parser, s_episodeEpisodeNumber);
			}
			else if (name.equals(s_episodeEpisodeName)) {
				episodeName = readString(parser, s_episodeEpisodeName);
			}
			else if (name.equals(s_episodeOverview)) {
				overview = readString(parser, s_episodeOverview);
			}
			else if (name.equals(s_episodeFirstAired)) {
				firstAired = readString(parser, s_episodeFirstAired);
			}
			else {
				skip(parser);
			}
		}

		if(episodeId == -1){
			return null;
		}
		else {
			return new Episode(episodeId, tvShow_id, seasonNumber, episodeNumber, episodeName, overview, firstAired, false);
		}
	}
}
