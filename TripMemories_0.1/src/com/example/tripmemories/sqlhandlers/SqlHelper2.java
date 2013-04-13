package com.example.tripmemories.sqlhandlers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlHelper2 extends SQLiteOpenHelper{

	  public static final String TABLE_PICTURES = "pictures";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_URI = "uri";
	  public static final String COLUMN_POSITIONX = "positionX";
	  public static final String COLUMN_POSITIONY = "positionY";
	  public static final String COLUMN_TIME = "time";
	  public static final String COLUMN_ALBUM  = "albumId";
	  private static final String DATABASE_NAME = "travel.db";
	  private static final int DATABASE_VERSION = 4;

	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_PICTURES + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " + COLUMN_NAME
	      + " text not null unique, " +
	      COLUMN_URI +
	      " text null, " 
	      + COLUMN_POSITIONX 
	      + " text null, "
	      + COLUMN_POSITIONY
	      + " text null, "
	      + COLUMN_TIME
	      + " text null, "
	      + COLUMN_ALBUM
	      + " text null"+
	       ");";

	  public SqlHelper2(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(SqlHelper2.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_PICTURES);
	    onCreate(db);
	  }

}
