package fr.nf28.vmote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
	
	/** Tvshow Table **/
	private static final String TVSHOW_TABLE_NAME = "TvShow";

	private static final String TVSHOW_KEY = "id";
	private static final String TVSHOW_NAME = "name";
	private static final String TVSHOW_POSTERURL = "poster";
	private static final String TVSHOW_NETWORK = "network";
	private static final String TVSHOW_GENRE = "genre";
	private static final String TVSHOW_RUNTIME = "runtime";
	private static final String TVSHOW_RATING = "rating";
	private static final String TVSHOW_OVERVIEW = "overview";

	private static final String TVSHOW_TABLE_CREATE =
			"CREATE TABLE " + TVSHOW_TABLE_NAME + " (" +
					TVSHOW_KEY + " INTEGER PRIMARY KEY, " +
					TVSHOW_NAME + " TEXT, " +
					TVSHOW_POSTERURL + " TEXT, " +
					TVSHOW_NETWORK + " TEXT, " +
					TVSHOW_GENRE + " TEXT, " +
					TVSHOW_RUNTIME + " INTEGER, " +
					TVSHOW_RATING + " REAL, " +
					TVSHOW_OVERVIEW + " TEXT " +
			");";
	public static final String TVSHOW_TABLE_DROP = "DROP TABLE IF EXISTS " + TVSHOW_TABLE_NAME + ";";
	
	/** Episode Table **/
	private static final String EPISODE_TABLE_NAME = "Episode";

	private static final String EPISODE_KEY = "id";
	private static final String EPISODE_TVSHOW_ID = "tvShow_id";
	private static final String EPISODE_SEASONNUMBER = "seasonNumber";
	private static final String EPISODE_EPISODENUMBER = "episodeNumber";
	private static final String EPISODE_EPISODENAME = "episodeName";
	private static final String EPISODE_OVERVIEW = "overview";
	private static final String EPISODE_FIRSTAIRED = "firstAired";
	private static final String EPISODE_SEEN = "seen";
	
	private static final String EPISODE_TABLE_CREATE =
			"CREATE TABLE " + EPISODE_TABLE_NAME + " (" +
					EPISODE_KEY + " INTEGER PRIMARY KEY, " +
					EPISODE_TVSHOW_ID + " INTEGER, " +
					EPISODE_SEASONNUMBER + " INTEGER, " +
					EPISODE_EPISODENUMBER + " INTEGER, " +
					EPISODE_EPISODENAME + " TEXT, " +
					EPISODE_OVERVIEW + " TEXT, " +
					EPISODE_FIRSTAIRED + " TEXT, " +
					EPISODE_SEEN + " INTEGER, " +
			" FOREIGN KEY ("+ EPISODE_TVSHOW_ID +") REFERENCES "+ TVSHOW_TABLE_NAME +" ("+ TVSHOW_KEY +")" +
			");";
	public static final String EPISODE_TABLE_DROP = "DROP TABLE IF EXISTS " + EPISODE_TABLE_NAME + ";";
	

	/** Handler methods **/
	public DbHandler(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TVSHOW_TABLE_CREATE);
		db.execSQL(EPISODE_TABLE_CREATE);
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TVSHOW_TABLE_DROP);
		db.execSQL(EPISODE_TABLE_DROP);
		onCreate(db);
	}
}
