package com.example.tripmemories;

import java.util.List;
import java.util.Random;

import com.example.tripmemories.datasources.AlbumsDataSource;
import com.example.tripmemories.models.Album;
import com.example.tripmemories.views.ImageAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

public class AlbumActivity extends ListActivity {
	private AlbumsDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album_activity);

		datasource = new AlbumsDataSource(this);
		datasource.open();
		 GridView gridview = (GridView) findViewById(R.id.gridview);
		    gridview.setAdapter(new ImageAdapter(this));

		    gridview.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		            Toast.makeText(AlbumActivity.this, "" + position, Toast.LENGTH_SHORT).show();
		        }
		    });
		List<Album> values = datasource.getAllAlbums();
		try {
			// Use the SimpleCursorAdapter to show the
			// elements in a ListView
			ArrayAdapter<Album> adapter = new ArrayAdapter<Album>(this,
					android.R.layout.simple_list_item_1, values);
			setListAdapter(adapter);
		} catch (Exception e) {
			datasource.close();
		}
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Album> adapter = (ArrayAdapter<Album>) getListAdapter();
		Album album = null;
		switch (view.getId()) {
		case R.id.add:
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// Save the new comment to the database
			album = datasource.createAlbum(comments[nextInt]+new Random().nextDouble(), "");
			adapter.add(album);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				album = (Album) getListAdapter().getItem(0);
				datasource.deleteAlbum(album);
				adapter.remove(album);
			}
			break;
		case R.id.mainActivity:
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			datasource.close();
			this.finish();
			break;
		}

		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		datasource.open();
		super.onResume();
	}

	@Override
	protected void onPause() {
		datasource.close();
		super.onPause();
	}

}
