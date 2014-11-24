package com.bharatl.imagesearcher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class SaveActivity extends Activity {
	Spinner spImgSize;
	Spinner spImgColor;
	Spinner spImgType;
	EditText siteFilter;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		loadSavedPreferences();
		
	}
	
	private void loadSavedPreferences() {
		SharedPreferences mSettings = getSharedPreferences("Settings", 0);
		String imgSize = mSettings.getString("imgSize", "medium");
		String imgColor = mSettings.getString("imgColor","null");
		String imgType = mSettings.getString("imgType","face");
		String site= mSettings.getString("siteFilter","yahoo.com");
		siteFilter = (EditText) findViewById(R.id.etSiteFilter);
		spImgSize = (Spinner)findViewById(R.id.spImageSize);
		spImgColor = (Spinner)findViewById(R.id.spColorFilter);
		spImgType = (Spinner)findViewById(R.id.spImgType);
		siteFilter.setText(site);
		
		
		
	}

	public void savePreferences(View v){
		spImgSize = (Spinner)findViewById(R.id.spImageSize);
		spImgColor = (Spinner)findViewById(R.id.spColorFilter);
		spImgType = (Spinner)findViewById(R.id.spImgType);
		siteFilter = (EditText) findViewById(R.id.etSiteFilter);
		SharedPreferences mSettings = getSharedPreferences("Settings", 0);
		SharedPreferences.Editor editor = mSettings.edit();
		
		editor.putString("imgSize", spImgSize.getSelectedItem().toString() );
		editor.putString("imgColor", spImgColor.getSelectedItem().toString() );
		editor.putString("imgType", spImgType.getSelectedItem().toString() );
		editor.putString("siteFilter", siteFilter.getText().toString());	
		editor.commit();
		
		Intent i = new Intent (this, ImageSearchActivity.class);
		startActivity(i);
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save, menu);
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
}
