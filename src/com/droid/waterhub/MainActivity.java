package com.droid.waterhub;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;







import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {
	private Context context;
	//private static String url = "http://docs.blackberry.com/sampledata.json";
	private static String url = "http://technoturbine.com/lbs/index.php/services/get_all_categories";

	//private static final String Cat_name = "name";
	//private static final String Cat_Id = "id";
	
	//private static final String TAG_VCOLOR = "vehicleColor";
	//private static final String TAG_FUEL = "fuel";
	//private static final String TAG_TREAD = "treadType";
//	private static final String TAG_OPERATOR = "approvedOperators";
	private static final String TAG_NAME = "name";
	private static final String TAG_IMAGE = "image_icon";
	//private static final String TAG_POINTS = "experiencePoints";
	
	public Integer images_list[] = {R.drawable.imgshelter,R.drawable.imgshelter};
	

	ArrayList<HashMap<String, Integer>> jsonlist = new ArrayList<HashMap<String,Integer>>();
	
	ListView lv ;

	Spinner mSprPlaceType;
	 
    String[] mPlaceType=null;
    String[] mPlaceTypeName=null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new ProgressTask(MainActivity.this).execute();
	}

	private class ProgressTask extends AsyncTask<String, Void, Boolean> {
		private ProgressDialog dialog;

		private ListActivity activity;

		// private List<Message> messages;
		public ProgressTask(ListActivity activity) {
			this.activity = activity;
			context = activity;
			dialog = new ProgressDialog(context);
		}

		/** progress dialog to show user that the backup is processing. */

		/** application context. */
		private Context context;

		private String name;

		private String catId;

		protected void onPreExecute() {
			this.dialog.setMessage("Progress start");
			this.dialog.show();
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			
			 // Array of place types
	        mPlaceType = getResources().getStringArray(R.array.place_type);
	 
	        // Array of place type names
	        mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);
	 
	        // Creating an array adapter with an array of Place types
	        // to populate the spinner
	        ArrayAdapter<String> adapter_place = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, mPlaceTypeName);
	 
	        // Getting reference to the Spinner
	        mSprPlaceType = (Spinner) findViewById(R.id.spr_place_type);
	 
	        // Setting adapter on Spinner to set place types
	        mSprPlaceType.setAdapter(adapter_place);
	 
	        Button btnFind;
	        
	     // Getting reference to Find Button
	        btnFind = ( Button ) findViewById(R.id.btn_find);
	        
	     // Setting click event lister for the find button
            btnFind.setOnClickListener(new OnClickListener() {
 
                @Override
                public void onClick(View v) {
 
                    
                    	Intent intent = new Intent();
                    	intent.setClassName(getPackageName(), getPackageName() + ".MarkActivity"); 
                    	//intent.putExtra("radius",type); 
                    	//intent.putExtra("selected","1");
	                 startActivity(intent);
                   
                }
            });
			
			
			ListAdapter adapter = new SimpleAdapter(context, jsonlist,
					
					R.layout.list_item, new String[] {TAG_IMAGE }, new int[] {
					R.id.img_icon });
					

			setListAdapter(adapter);

			// selecting single ListView item
			 lv = getListView();
			
			 lv.setOnItemClickListener(new OnItemClickListener() { 
				 
	             public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3){  
	            	 int selectedPosition = mSprPlaceType.getSelectedItemPosition();
	                 String type = mPlaceType[selectedPosition];
	            	 Intent intent = new Intent();
	                 intent.setClassName(getPackageName(), getPackageName() + ".LocationActivity"); 
	                 intent.putExtra("selected",lv.getAdapter().getItem(arg2).toString()); 
	                 intent.putExtra("radius",type);
	                 startActivity(intent); 

	             }

	         });

			 
		}

		protected Boolean doInBackground(final String... args) {

			JSONParser jParser = new JSONParser();

			// getting JSON string from URL
			JSONArray json = jParser.getJSONFromUrl(url);

			for (int i = 0; i < json.length(); i++) {

				try {
					JSONObject c = json.getJSONObject(i);
					//String cat_name = c.getString(TAG_NAME);

					//String cat_id = c.getString(Cat_Id);
					//String vfuel = c.getString(TAG_FUEL);
					//String vtread = c.getString(TAG_TREAD);

					HashMap<String, Integer> map = new HashMap<String, Integer>();

					// adding each child node to HashMap key => value
					//map.put(TAG_NAME, cat_name);
					map.put(TAG_IMAGE, images_list[i]);
					//map.put(Cat_Id, cat_id);
					//map.put(TAG_FUEL, vfuel);
					//map.put(TAG_TREAD, vtread);
					jsonlist.add(map);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;

		}

	}

}
