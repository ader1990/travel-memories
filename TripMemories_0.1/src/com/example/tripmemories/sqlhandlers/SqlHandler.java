package com.example.tripmemories.sqlhandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlHandler extends SQLiteOpenHelper{

	  public static final String TABLE_ALBUMS = "albums";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_DESCRIPTION = "description";

	  private static final String DATABASE_NAME = "travel.db";
	  private static final int DATABASE_VERSION = 4;

	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_ALBUMS + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_NAME
	      + " text not null unique, " +
	      COLUMN_DESCRIPTION +
	      " text null" +
	      ");";

	  public SqlHandler(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(SqlHandler.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALBUMS);
	    onCreate(db);
	  }

}
