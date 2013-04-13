package com.example.tripmemories;

import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity {
	private AlbumsDataSource datasource;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		datasource = new AlbumsDataSource(this);
		datasource.open();

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
			album = datasource.createAlbum(comments[nextInt], "");
			adapter.add(album);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				album = (Album) getListAdapter().getItem(0);
				datasource.deleteAlbum(album);
				adapter.remove(album);
			}
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
