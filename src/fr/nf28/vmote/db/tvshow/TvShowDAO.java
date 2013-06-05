package fr.nf28.vmote.db.tvshow;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.nf28.vmote.db.DAOBase;

public class TvShowDAO extends DAOBase {
	
	public TvShowDAO(Context pContext) {
		super(pContext);
		// TODO Auto-generated constructor stub
	}

	private static final String TVSHOW_TABLE_NAME = "TvShow";

	private static final String TVSHOW_KEY = "id";
	private static final String TVSHOW_NAME = "name";
	private static final String TVSHOW_POSTERURL = "posterUrl";
	private static final String TVSHOW_POSTERPATH = "posterPath";
	private static final String TVSHOW_NETWORK = "network";
	private static final String TVSHOW_GENRE = "genre";
	private static final String TVSHOW_RUNTIME = "runtime";
	private static final String TVSHOW_RATING = "rating";
	private static final String TVSHOW_OVERVIEW = "overview";
	/**
	 * @param m le métier à ajouter à la base
	 */
	public long insert(TvShow t) {
		if(exists(t.getId())) { //UPDATE
			update(t);
			return -1;
		}
		else{ //INSERT
			ContentValues value = new ContentValues();
			value.put(TVSHOW_KEY, t.getId());
			value.put(TVSHOW_NAME, t.getName());
			value.put(TVSHOW_POSTERURL, t.getPosterUrl());
			value.put(TVSHOW_POSTERPATH, t.getPosterPath());
			value.put(TVSHOW_NETWORK, t.getNetwork());
			value.put(TVSHOW_GENRE, t.getGenre());
			value.put(TVSHOW_RUNTIME, t.getRuntime());
			value.put(TVSHOW_RATING, t.getRating());
			value.put(TVSHOW_OVERVIEW, t.getOverview());
			
			return mDb.insert(TVSHOW_TABLE_NAME, null, value);
		}
	}

	/**
	 * @param id l'identifiant de la série à supprimer
	 */
	public void delete(long id) {
		mDb.delete(TVSHOW_TABLE_NAME, TVSHOW_KEY + " = ?", new String[] {String.valueOf(id)});
	}

	/**
	 * @param m la série à modifier
	 */
	public void update(TvShow t) {
		ContentValues value = new ContentValues();
		value.put(TVSHOW_NAME, t.getName());
		value.put(TVSHOW_POSTERURL, t.getPosterUrl());
		value.put(TVSHOW_POSTERPATH, t.getPosterPath());
		value.put(TVSHOW_NETWORK, t.getNetwork());
		value.put(TVSHOW_GENRE, t.getGenre());
		value.put(TVSHOW_RUNTIME, t.getRuntime());
		value.put(TVSHOW_RATING, t.getRating());
		value.put(TVSHOW_OVERVIEW, t.getOverview());
		
		mDb.update(TVSHOW_TABLE_NAME, value, TVSHOW_KEY  + " = ?", new String[] {String.valueOf(t.getId())});
	}

	/**
	 * Retourne toutes les séries
	 * @return
	 */
	public List<TvShow> selectAll() {
		Cursor c = mDb.rawQuery("SELECT * FROM " + TVSHOW_TABLE_NAME, new String[]{});
		
		return toTvShows(c);
	}
	
	/**
	 * @param id l'identifiant de la série à récupérer
	 */
	public TvShow select(long id) {
		Cursor c = mDb.rawQuery("SELECT * FROM " + TVSHOW_TABLE_NAME + " WHERE " + TVSHOW_KEY + " = ?", new String[]{String.valueOf(id)});
		
		return toTvShows(c).get(0);
	}
	

	/**
	 * ! Cursor result must contain all columns 
	 * @param c Cursor : result of select request
	 * @return
	 */
	private List<TvShow> toTvShows(Cursor c){
		List<TvShow> list = new ArrayList<TvShow>();
		while (c.moveToNext()) {
			long id = c.getLong(c.getColumnIndex(TVSHOW_KEY));
			String name = c.getString(c.getColumnIndex(TVSHOW_NAME));
			String posterUrl = c.getString(c.getColumnIndex(TVSHOW_POSTERURL));
			String posterPath = c.getString(c.getColumnIndex(TVSHOW_POSTERPATH));
			String network = c.getString(c.getColumnIndex(TVSHOW_NETWORK));
			String genre = c.getString(c.getColumnIndex(TVSHOW_GENRE));
			int runtime = c.getInt(c.getColumnIndex(TVSHOW_RUNTIME));
			float rating = c.getFloat(c.getColumnIndex(TVSHOW_RATING));
			String overview = c.getString(c.getColumnIndex(TVSHOW_OVERVIEW));
			
			list.add(new TvShow (id, name, posterUrl, posterPath, network, genre, runtime, rating, overview));
		}
		c.close();
		
		return list;
	}
	
	public boolean exists(long id){
		Cursor cursor = mDb.rawQuery("SELECT * FROM " + TVSHOW_TABLE_NAME + " WHERE id=?", new String[] {String.valueOf(id)});
		boolean exists = (cursor.getCount() > 0);
		cursor.close();
		return exists;
	}
}
