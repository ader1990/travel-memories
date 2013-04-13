package com.example.tripmemories;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.example.tripmemories.datasources.AlbumsDataSource;
import com.example.tripmemories.datasources.PicturesDataSource;
import com.example.tripmemories.models.Album;
import com.example.tripmemories.models.Pictures;
import com.example.tripmemories.views.ImageAdapter;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Picture;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class PictureActivity extends ListActivity {
	protected static final int CAMERA_CAPTURE = 1;
	private PicturesDataSource datasource;
	private Button addNewPhotoButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_main);

		this.setTitle("Pictures");

		datasource = new PicturesDataSource(this);
		datasource.open();

		List<Pictures> values = datasource.getAllPictures();
		try {
			// Use the SimpleCursorAdapter to show the
			// elements in a ListView
			ArrayAdapter<Pictures> adapter = new ArrayAdapter<Pictures>(this,
					android.R.layout.simple_list_item_1, values);
			setListAdapter(adapter);
		} catch (Exception e) {
			datasource.close();
		}

		addNewPhotoButton = (Button) findViewById(R.id.add);
		addNewPhotoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// use standard intent to capture an image
					Intent captureIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					// we will handle the returned data in onActivityResult
					startActivityForResult(captureIntent, CAMERA_CAPTURE);
				} catch (ActivityNotFoundException anfe) {
					// display an error message
					String errorMessage = "Whoops - your device doesn't support capturing images!";
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			// user is returning from capturing an image using the camera
			if (requestCode == CAMERA_CAPTURE) {
				// get the Uri for the captured image
				Uri picUri = data.getData();
				Pictures picture = null;
				final String picUriCopy = picUri.toString();
				
				picture = datasource.createPictures(picUriCopy, picUriCopy, 1, 2,
						new Date(), 3);

				ArrayAdapter<Pictures> adapter = (ArrayAdapter<Pictures>) getListAdapter();
				adapter.add(picture);
			}
		}
	}

	// Will be called via the onClick attribute
	// of the buttons in main.xml
	public void onClick(View view) {
		@SuppressWarnings("unchecked")
		ArrayAdapter<Pictures> adapter = (ArrayAdapter<Pictures>) getListAdapter();
		Pictures picture = null;
		switch (view.getId()) {
		case R.id.add:
			String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
			int nextInt = new Random().nextInt(3);
			// Save the new comment to the database
			picture = datasource.createPictures(new Random().nextDouble() + "",
					"", 1, 2, new Date(), 3);
			adapter.add(picture);
			break;
		case R.id.delete:
			if (getListAdapter().getCount() > 0) {
				picture = (Pictures) getListAdapter().getItem(0);
				datasource.deletePictures(picture);
				adapter.remove(picture);
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
