package com.bharatl.imagesearcher;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {
	
	
	ArrayList<ImgSearch> images ;
	EditText etSearch;
	Button btSearch;
	ImageSearchAdapter aImages;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		btSearch = (Button) findViewById(R.id.btSearch);
		images = new ArrayList<ImgSearch>();
		aImages = new ImageSearchAdapter(this, images);
		
		GridView gView = (GridView) findViewById(R.id.gridView1);
		gView.setAdapter(aImages);		
		gView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				ImgSearch imgsearch = (ImgSearch) arg0.getItemAtPosition(arg2);
				Intent intent = new Intent(ImageSearchActivity.this, FullScreenActivity.class);
				intent.putExtra("fullUrl", imgsearch.getFullUrl());
				startActivity(intent);
				
			}
			
		});
		
		gView.setOnScrollListener( new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				loadImages(page);	
				
			}	
			
		});
				
			
	}
	
	public void searchImages(View v) {
		
		int startPage = 0;
		
		loadImages(startPage);
		
		
	}

	
	
	private void loadImages(int page) {
		SharedPreferences mSettings = getSharedPreferences("Settings", 0);
		String imgSize = mSettings.getString("imgSize", "medium");
		String imgColor = mSettings.getString("imgColor","null");
		String imgType = mSettings.getString("imgType","face");
		String siteFilter= mSettings.getString("siteFilter","yahoo.com");
		
		etSearch = (EditText) findViewById(R.id.etSearch);
		//Ajax call https://ajax.googleapis.com/ajax/services/search/images?v=1.0&
		
		String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+
		etSearch.getText().toString()+"&rsz=8&imgsz="+imgSize+"&imgcolor="+imgColor+"&imgtype="+imgType+"&as_sitesearch="+
		siteFilter+"&start="+page;
		if (page == 0){
			images.clear();
			
		}
		AsyncHttpClient client =  new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				
				
				try {
					
					JSONObject result = response.getJSONObject("responseData");
					JSONArray imgArray = result.getJSONArray("results");
					
					for (int i=0 ; i<imgArray.length(); i++){
						
						ImgSearch img = new ImgSearch();
						
						JSONObject imgData =  imgArray.getJSONObject(i);
						
						img.setUrl(imgData.getString("tbUrl"));
						img.setCaption(imgData.getString("title"));
						img.setFullUrl(imgData.getString("url"));
						img.setWidth(imgData.getInt("tbWidth"));
						img.setHeight(imgData.getInt("tbHeight"));
						
						images.add(img);							
					}
					
					aImages.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SaveActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
