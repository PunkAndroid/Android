package com.android.test.gridviewtest1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			OnItemSelectedListener, OnItemClickListener {
		private ImageView imageView;
		private int[] resIds = new int[] { R.drawable.item1, R.drawable.item2,
				R.drawable.item3, R.drawable.item4, R.drawable.item5,
				R.drawable.item6, R.drawable.item7, R.drawable.item8 };

		public PlaceholderFragment() {
		}

		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            GridView gridView = (GridView) rootView.findViewById(R.id.gridview);
            List<Map<String,Object>> cells = new ArrayList<Map<String,Object>>();
            for(int i=0;i<resIds.length;i++){
            	Map<String,Object> cell = new HashMap<String,Object>();
            	cell.put("imageview", resIds[i]);
            	cells.add(cell);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), cells, R.layout.fragment_main, new String[]{ "imageview" }, new int[]{R.id.imageview});
            gridView.setAdapter(simpleAdapter);
            imageView = (ImageView) rootView.findViewById(R.id.imageview);
            gridView.setOnItemSelectedListener(this);
            gridView.setOnItemClickListener(this);
            imageView.setImageResource(resIds[0]);
            return rootView;
        }

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			imageView.setImageResource(resIds[position]);
		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			imageView.setImageResource(resIds[position]);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}
	}

}
