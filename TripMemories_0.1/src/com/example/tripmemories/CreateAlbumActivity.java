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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateAlbumActivity extends Activity {
	private AlbumsDataSource datasource;
	private EditText nameEdit;
	private EditText descriptionEdit;
	private Button saveButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_album);

		this.setTitle("Albums");

		datasource = new AlbumsDataSource(this);
		datasource.open();

		nameEdit = (EditText) findViewById(R.id.name_in);
		descriptionEdit = (EditText) findViewById(R.id.description_in);
		saveButton = (Button) findViewById(R.id.save);
		final Activity a = this;
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String albumName = nameEdit.getText().toString();
				if (albumName.length() > 0) {
					try {
						String albumDescription = descriptionEdit.getText()
								.toString();
						Album album = datasource.createAlbum(albumName,
								albumDescription);
						Intent i = new Intent(a, MainActivity.class);
						startActivity(i);
						datasource.close();
						a.finish();
					} catch (Exception e) {
						Toast.makeText(CreateAlbumActivity.this,
								e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(CreateAlbumActivity.this,
							"Please enter a name.", Toast.LENGTH_SHORT).show();
				}

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
