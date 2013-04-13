package com.example.tripmemories;

import java.util.List;
import java.util.Random;

import com.example.tripmemories.datasources.AlbumsDataSource;
import com.example.tripmemories.models.Album;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ListActivity {
	private AlbumsDataSource datasource;
	private Button addNewAlbumButton;
	private Button deleteAlbumButton;
	private Button changeActivityButton;

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent i = new Intent(MainActivity.this, PictureActivity.class);
		startActivity(i);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.setTitle("Albums");

		datasource = new AlbumsDataSource(this);
		datasource.open();

		final Activity a = this;
		List<Album> values = datasource.getAllAlbums();
		try {
			ArrayAdapter<Album> adapter = new ArrayAdapter<Album>(this,
					android.R.layout.simple_list_item_1, values);

			setListAdapter(adapter);
		} catch (Exception e) {
			datasource.close();
		}
		addNewAlbumButton = (Button) findViewById(R.id.add);
		addNewAlbumButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(a, CreateAlbumActivity.class);
				startActivity(i);
			}
		});

	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {

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
