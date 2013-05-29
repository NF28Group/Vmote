package fr.nf28.vmote.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DAOBase {
	protected final static int VERSION = 3;
	protected final static String DB_FILE = "database.db";  	// Le nom du fichier qui représente ma base

	protected SQLiteDatabase mDb = null;
	protected SQLiteOpenHelper handler = null;

	public DAOBase(Context pContext) {
		this.handler = new DbHandler(pContext, DB_FILE, null, VERSION);
		open();
	}

	public SQLiteDatabase open() {
		// Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
		mDb = handler.getWritableDatabase();
		return mDb;
	}

	public void close() {
		mDb.close();
	}

	public SQLiteDatabase getDb() {
		return mDb;
	}
}
