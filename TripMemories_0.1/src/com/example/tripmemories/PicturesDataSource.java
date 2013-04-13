package com.example.tripmemories;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class PicturesDataSource {

  // Database fields
  private SQLiteDatabase database;
  private SqlHelper2 dbHelper2;
  private String[] allColumns = { SqlHelper2.COLUMN_ID,
		  SqlHelper2.COLUMN_NAME, SqlHelper2.COLUMN_URI,SqlHelper2.COLUMN_POSITIONX,
		  SqlHelper2.COLUMN_POSITIONY,SqlHelper2.COLUMN_TIME,SqlHelper2.COLUMN_ALBUM};

  public PicturesDataSource(Context context) {
    dbHelper2 = new SqlHelper2(context);
  }

  public void open() throws SQLException {
    database = dbHelper2.getWritableDatabase();
  }

  public void close() {
    dbHelper2.close();
  }

  public Pictures createPictures(String name, String uri,float positionX,float positionY,Date time,long albumId) {
    ContentValues values = new ContentValues();
    values.put(SqlHelper2.COLUMN_NAME, name);
    values.put(SqlHelper2.COLUMN_URI, uri);
    values.put(SqlHelper2.COLUMN_POSITIONX, positionX);
    values.put(SqlHelper2.COLUMN_POSITIONY, positionY);
    values.put(SqlHelper2.COLUMN_TIME, time.toString());
    values.put(SqlHelper2.COLUMN_ALBUM, albumId);
    long insertId = database.insert(SqlHelper2.TABLE_PICTURES, null,
        values);
    Cursor cursor = database.query(SqlHelper2.TABLE_PICTURES,
        allColumns, SqlHelper2.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Pictures newPictures = cursorToPictures(cursor);
    cursor.close();
    return newPictures;
  }

  public void deletePictures(Pictures pictures) {
    long id = pictures.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(SqlHelper2.TABLE_PICTURES, SqlHelper2.COLUMN_ID
        + " = " + id, null);
  }

  public List<Pictures> getAllPictures() {
    List<Pictures> pictures = new ArrayList<Pictures>();

    Cursor cursor = database.query(SqlHelper2.TABLE_PICTURES,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Pictures pictures1 = cursorToPictures(cursor);
      pictures.add(pictures1);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return pictures;
  }

  private Pictures cursorToPictures(Cursor cursor) {
	  Pictures pictures = new Pictures();
	  pictures.setId(cursor.getLong(0));
	  pictures.setName(cursor.getString(1));
	  pictures.setUri(cursor.getString(2));
	  pictures.setPositionX(cursor.getFloat(3));
	  pictures.setPositionY(cursor.getFloat(4));
	  try{
		Date time;
		SimpleDateFormat s=	new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
		s.parse(cursor.getString(5));
		pictures.setTime(s.parse(cursor.getString(5)));
	  }catch(Exception e){}
	  //pictures.setTime(((Object) cursor).getData(5));
	  pictures.setAlbumId(cursor.getLong(6));
    return pictures;
  }
} 