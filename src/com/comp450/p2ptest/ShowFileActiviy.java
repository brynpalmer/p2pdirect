package com.comp450.p2ptest;


import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
//Swipe reference
//http://androidexample.com/Swipe_screen_left__right__top_bottom/index.php?view=article_discription&aid=95&aaid=118
import android.view.MotionEvent;
import android.widget.Toast;

public class ShowFileActiviy extends Activity implements FileFragment.FileInfo {

	private String path = "";
	
	private GestureDetector mGestureDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_file_activiy);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new FileFragment()).commit();
		}
		path = getIntent().getStringExtra("file_path");
		
		//Swipe functionality from Adam Porter's coursera course
		//https://github.com/aporter/coursera-android/tree/master/Examples/TouchGestureViewFlipperTest
		mGestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						if (Math.abs(velocityX) > 10.0f || Math.abs(velocityY) > 10.0f ) {
							//Toast.makeText(getApplicationContext(), "Wow, did you swipe me your pervert ?", Toast.LENGTH_SHORT).show();							
						}
						return true;
					}
				});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_file_activiy, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public String getFilePath() {
		return path;
	}
}
