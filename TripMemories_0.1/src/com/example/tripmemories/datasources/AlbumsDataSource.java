package com.example.tripmemories.datasources;

import java.util.ArrayList;
import java.util.List;

import com.example.tripmemories.models.Album;
import com.example.tripmemories.sqlhandlers.SqlHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AlbumsDataSource {

  // Database fields
  private SQLiteDatabase database;
  private SqlHandler dbHelper;
  private String[] allColumns = { SqlHandler.COLUMN_ID,
		  SqlHandler.COLUMN_NAME, SqlHandler.COLUMN_DESCRIPTION};

  public AlbumsDataSource(Context context) {
    dbHelper = new SqlHandler(context);
  }

  public void open() throws SQLException {
    database = dbHelper.getWritableDatabase();
  }

  public void close() {
    dbHelper.close();
  }

  public Album createAlbum(String name, String description) {
    ContentValues values = new ContentValues();
    values.put(SqlHandler.COLUMN_NAME, name);
    values.put(SqlHandler.COLUMN_DESCRIPTION, description);
    long insertId = database.insert(SqlHandler.TABLE_ALBUMS, null,
        values);
    Cursor cursor = database.query(SqlHandler.TABLE_ALBUMS,
        allColumns, SqlHandler.COLUMN_ID + " = " + insertId, null,
        null, null, null);
    cursor.moveToFirst();
    Album newAlbum = cursorToAlbum(cursor);
    cursor.close();
    return newAlbum;
  }

  public void deleteAlbum(Album album) {
    long id = album.getId();
    System.out.println("Comment deleted with id: " + id);
    database.delete(SqlHandler.TABLE_ALBUMS, SqlHandler.COLUMN_ID
        + " = " + id, null);
  }

  public List<Album> getAllAlbums() {
    List<Album> albums = new ArrayList<Album>();

    Cursor cursor = database.query(SqlHandler.TABLE_ALBUMS,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Album album = cursorToAlbum(cursor);
      albums.add(album);
      cursor.moveToNext();
    }
    // Make sure to close the cursor
    cursor.close();
    return albums;
  }

  private Album cursorToAlbum(Cursor cursor) {
	  Album album = new Album();
	  album.setId(cursor.getLong(0));
	  album.setName(cursor.getString(1));
	  album.setDescription(cursor.getString(2));
    return album;
  }
} 