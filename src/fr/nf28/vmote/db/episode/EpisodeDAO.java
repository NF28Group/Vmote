package fr.nf28.vmote.db.episode;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.nf28.vmote.db.DAOBase;

public class EpisodeDAO extends DAOBase {
	
	public EpisodeDAO(Context pContext) {
		super(pContext);
	}

	private static final String EPISODE_TABLE_NAME = "Episode";

	private static final String EPISODE_KEY = "id";
	private static final String EPISODE_TVSHOW_ID = "tvShow_id";
	private static final String EPISODE_SEASONNUMBER = "seasonNumber";
	private static final String EPISODE_EPISODENUMBER = "episodeNumber";
	private static final String EPISODE_EPISODENAME = "episodeName";
	private static final String EPISODE_OVERVIEW = "overview";
	private static final String EPISODE_FIRSTAIRED = "firstAired";
	private static final String EPISODE_SEEN = "seen";


	/**
	 * @param m le m�tier � ajouter � la base
	 */
	public long insert(Episode e) {
		if(exists(e.getId())) { //UPDATE
			update(e);
			return -1;
		}
		else{ //INSERT
			ContentValues value = new ContentValues();
			
			value.put(EPISODE_KEY, e.getId());
			value.put(EPISODE_TVSHOW_ID, e.getTvShow_id());
			value.put(EPISODE_SEASONNUMBER, e.getSeasonNumber());
			value.put(EPISODE_EPISODENUMBER, e.getEpisodeNumber());
			value.put(EPISODE_EPISODENAME, e.getEpisodeName());
			value.put(EPISODE_OVERVIEW, e.getOverView());
			value.put(EPISODE_FIRSTAIRED, e.getFirstAiredString());
			value.put(EPISODE_SEEN, e.isSeen());
			
			return mDb.insert(EPISODE_TABLE_NAME, null, value);
		}
	}

	/**
	 * @param id l'identifiant de la s�rie � supprimer
	 */
	public void delete(long id) {
		mDb.delete(EPISODE_TABLE_NAME, EPISODE_KEY + " = ?", new String[] {String.valueOf(id)});
	}

	/**
	 * @param m le m�tier modifi�
	 */
	public void update(Episode e) {
		ContentValues value = new ContentValues();
		
		value.put(EPISODE_TVSHOW_ID, e.getTvShow_id());
		value.put(EPISODE_SEASONNUMBER, e.getSeasonNumber());
		value.put(EPISODE_EPISODENUMBER, e.getEpisodeNumber());
		value.put(EPISODE_EPISODENAME, e.getEpisodeName());
		value.put(EPISODE_OVERVIEW, e.getOverView());
		value.put(EPISODE_FIRSTAIRED, e.getFirstAiredString());
		value.put(EPISODE_SEEN, e.isSeen());

		mDb.update(EPISODE_TABLE_NAME, value, EPISODE_KEY  + " = ?", new String[] {String.valueOf(e.getId())});
	}

	/**
	 * Retourne toutes les s�ries
	 * @return
	 */
	public List<Episode> selectAll() {
		Cursor c = mDb.rawQuery("SELECT * FROM " + EPISODE_TABLE_NAME, new String[]{});
		
		return toEpisodes(c);
	}
	
	/**
	 * @param id l'identifiant de la s�rie � r�cup�rer
	 */
	public Episode select(long id) {
		Cursor c = mDb.rawQuery(
				"SELECT * FROM " + EPISODE_TABLE_NAME + 
				" WHERE " + EPISODE_KEY + " = ?", new String[]{String.valueOf(id)});
		
		return toEpisodes(c).get(0);
	}
	
	/**
	 * Retourne tous les �pisodes de la s�rie d'id tvShow_Id
	 * @param tvShow_id
	 * @return
	 */
	public List<Episode> selectByTvShow(long tvShow_id) {
		Cursor c = mDb.rawQuery(
				"SELECT * FROM " + EPISODE_TABLE_NAME + 
				" WHERE " + EPISODE_TVSHOW_ID + " = ?", new String[]{String.valueOf(tvShow_id)});
		
		return toEpisodes(c);
	}
	
	/**
	 * Retourne tous les �pisodes de la saison num�ro 
	 * @param seasonNumber
	 * @return
	 */
	public List<Episode> selectTvShowAndSeason(long tvShow_id, int seasonNumber) {
		Cursor c = mDb.rawQuery(
				"SELECT * FROM " + EPISODE_TABLE_NAME + 
				" WHERE " + EPISODE_TVSHOW_ID + " = ?" +
				" AND " + EPISODE_SEASONNUMBER + " = ?" +
			    " ORDER BY " + EPISODE_EPISODENUMBER + " DESC", new String[]{String.valueOf(tvShow_id), String.valueOf(seasonNumber)});
		
		return toEpisodes(c);
	}
	

	/**
	 * ! Cursor result must contain all columns 
	 * @param c Cursor : result of select request
	 * @return
	 */
	private List<Episode> toEpisodes(Cursor c){
		List<Episode> list = new ArrayList<Episode>();
		while (c.moveToNext()) {
			long id = c.getLong(c.getColumnIndex(EPISODE_KEY));
			long tvShow_id = c.getLong(c.getColumnIndex(EPISODE_TVSHOW_ID));
			int seasonNumber = c.getInt(c.getColumnIndex(EPISODE_SEASONNUMBER));
			int episodeNumber = c.getInt(c.getColumnIndex(EPISODE_EPISODENUMBER));
			String episodeName = c.getString(c.getColumnIndex(EPISODE_EPISODENAME));
			String overview = c.getString(c.getColumnIndex(EPISODE_OVERVIEW));
			
			String firstAired = c.getString(c.getColumnIndex(EPISODE_FIRSTAIRED));
			
			int seen_int = c.getInt(c.getColumnIndex(EPISODE_SEEN));
			boolean seen = seen_int == 0 ? false : true;
			
			list.add(new Episode (id, tvShow_id, seasonNumber, episodeNumber, episodeName, overview, firstAired, seen));
		}
		c.close();
		
		return list;
	}

	private boolean exists(long id){
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + EPISODE_TABLE_NAME + " WHERE id=?", new String[] {String.valueOf(id)});
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
	
	private List<Integer> toSeasonList(Cursor c){
		List<Integer> seasons = new ArrayList<Integer>();
		while (c.moveToNext()) {
			seasons.add(c.getInt(c.getColumnIndex(EPISODE_SEASONNUMBER)));
		}
		c.close();
		
		return seasons;
	}
	
	public List<Integer> getSeasonNumberWithId(long tvShow_id){
		Cursor c = mDb.rawQuery(
				"SELECT DISTINCT " + EPISODE_SEASONNUMBER +" FROM " + EPISODE_TABLE_NAME + 
				" WHERE " + EPISODE_TVSHOW_ID + " = ? ORDER BY " + EPISODE_SEASONNUMBER + " DESC", new String[]{String.valueOf(tvShow_id)});
		
		return toSeasonList(c);
	}
	
	public Integer getEpisodesUnseenWithId(long tvShow_id){
		Cursor c = mDb.rawQuery(
				"SELECT *" + " FROM " + EPISODE_TABLE_NAME + 
				" WHERE " + EPISODE_TVSHOW_ID + " = ? AND "  + EPISODE_SEEN + " = 0", new String[]{String.valueOf(tvShow_id)});
		return c.getCount();
	}
}
