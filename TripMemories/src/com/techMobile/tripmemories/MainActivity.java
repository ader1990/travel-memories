package com.techMobile.tripmemories;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity implements OnClickListener {

	private Button apasa,apasa2;
	private TextView text;
	//keep track of camera capture intent
	final int CAMERA_CAPTURE = 1;
	//captured picture uri
	private Uri picUri;
	private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apasa2= (Button) findViewById(R.id.PrimuButon);
        textView = (TextView) findViewById(R.id.textView);
      //retrieve a reference to the UI button
      		Button captureBtn = (Button)findViewById(R.id.alDoileaButon);
      		//handle button clicks
      		captureBtn.setOnClickListener(this);
      		

        
 apasa2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
    }

	
	public void onClick(View v) {
		if (v.getId() == R.id.alDoileaButon) {
			try {
				//use standard intent to capture an image
				Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//we will handle the returned data in onActivityResult
				startActivityForResult(captureIntent, CAMERA_CAPTURE);
				}
			catch(ActivityNotFoundException anfe){
				//display an error message
				String errorMessage = "Whoops - your device doesn't support capturing images!";
				Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
				toast.show();
				}
		}
		}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			//user is returning from capturing an image using the camera
			if(requestCode == CAMERA_CAPTURE){
				//get the Uri for the captured image
				picUri = data.getData();
				final String picUriCopy=picUri.toString();
				textView.setText(picUriCopy);
			}
		}
		}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
