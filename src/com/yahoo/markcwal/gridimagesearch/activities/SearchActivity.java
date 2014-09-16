package com.yahoo.markcwal.gridimagesearch.activities;



import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.markcwal.gridimagesearch.R;
import com.yahoo.markcwal.gridimagesearch.adapters.ImageResultsAdapter;
import com.yahoo.markcwal.gridimagesearch.models.EndlessScrollListener;
import com.yahoo.markcwal.gridimagesearch.models.ImageResult;
import com.yahoo.markcwal.gridimagesearch.models.Settings;

public class SearchActivity extends Activity {
	
	private Settings settings;
	private EditText etSearch;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		settings = new Settings();
		setupViews();
		
		//creates data source
		imageResults = new ArrayList<ImageResult>();
		
		//attaches data source to adapter
		aImageResults = new ImageResultsAdapter(this, imageResults);
		
		//link adapter to GridView
		gvResults.setAdapter(aImageResults);
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(totalItemsCount); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
	        });
	}
	
	public void customLoadMoreDataFromApi(int offset)
	{
		String query = etSearch.getText().toString();
		
		AsyncHttpClient client = new AsyncHttpClient();
		String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?q=" + query + "&v=1.0&rsz=8";
		
		searchUrl = modifySearchUrl(searchUrl);
		
		searchUrl = searchUrl + "&start=" + offset;
		
		client.get(searchUrl, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response)
			{
				JSONArray imageResultsJson = null;
				
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					//imageResults.clear();
					
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
					
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
        getMenuInflater().inflate(R.menu.menu_simple, menu);
        return true;
    }
	
	public void onImageSearch(View v)
	{
		String query = etSearch.getText().toString();
		
		AsyncHttpClient client = new AsyncHttpClient();
		String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?q=" + query + "&v=1.0&rsz=8";
		
		//added so it resets counters on new search
		gvResults.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(totalItemsCount); 
	                // or customLoadMoreDataFromApi(totalItemsCount); 
		    }
	        });
		
		searchUrl = modifySearchUrl(searchUrl);
		
		client.get(searchUrl, new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response)
			{
				JSONArray imageResultsJson = null;
				
				try {
					imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					
					aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//Toast.makeText(this, "Search for: " + searchUrl, Toast.LENGTH_SHORT).show();
	}
	
	@SuppressWarnings("static-access")
	public String modifySearchUrl(String searchUrl)
	{
		String site = settings.getSite();
		String size = settings.getSize();
		String color = settings.getColor();
		String type = settings.getType();
		String url = searchUrl;
		if (!"".equals(site))
		{
			url = url + "&as_sitesearch=" + site;
		}
		if (!settings.DEFAULT.equals(size))
		{
			url = url + "&imgsz=" + size;
		}
		if (!settings.DEFAULT.equals(color))
		{
			url = url + "&imgcolor=" + color;
		}
		if (!settings.DEFAULT.equals(type))
		{
			url = url + "&imgtype=" + type;
		}
		
		return url;
	}
	
	public void onSettingsClick(MenuItem mi)
	{
		//Construct Intent
		Intent i = new Intent(this, SettingsActivity.class);
		
		//Pass arguments
		i.putExtra("settings", settings);
		
		//Execute Intent startActivityForResult
		startActivityForResult(i, 5);
	}
		
	public void setupViews()
	{
		etSearch = (EditText) findViewById(R.id.etSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
		
		gvResults.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Launch Image Display Activity
				
				//Create an Intent
				Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
				//Get the image result to display
				ImageResult result = imageResults.get(position);
				//pass image result to intent
				i.putExtra("url", result.fullUrl);
				//launch the new activity
				startActivity(i);
			}
			
		});
	}
			
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == 5)
		{
			if (resultCode == RESULT_OK)
			{
				//String value = data.getStringExtra("value");
				Settings mySettings = (Settings) data.getSerializableExtra("settings");
				this.settings = mySettings;
				//Toast.makeText(this, settings.value, Toast.LENGTH_SHORT).show();
			}
		}
			
	}
}
