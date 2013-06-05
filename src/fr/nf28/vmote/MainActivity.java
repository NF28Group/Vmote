package fr.nf28.vmote;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

import fr.nf28.vmote.R;
import fr.nf28.vmote.history.view.HistoryViewPagerFragment;
import fr.nf28.vmote.interfaces.OnChangePageListener;
import fr.nf28.vmote.play.view.ViewPagerFragment;
import fr.nf28.vmote.series.view.SeriesHomeFragment;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends SherlockFragmentActivity implements OnChangePageListener {
	private boolean useLogo = false;
	public boolean isConnected = false;
	private boolean showHomeUp = false;
	private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
	private Activity cxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);
		cxt = this;
		
		AbstractFragment fragment = new ViewPagerFragment();
		Bundle arguments = new Bundle();
		arguments.putString(ViewPagerFragment.ARG_ITEM_ID, "pager_play_fragment");
        fragment.setArguments(arguments);
        cxt.setTitle("Lecture");
		getSupportFragmentManager().beginTransaction()
			.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
			.addToBackStack(null)
			.commit();
	}


	@Override
	public void selectPage(int page) {
		// reflechissons...
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Used to put dark icons on light action bar
		//boolean isLight = SampleList.THEME == R.style.Theme_Sherlock_Light;
		
		menu.add("Lecture")
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AbstractFragment fragment = new ViewPagerFragment();
				Bundle arguments = new Bundle();
				arguments.putString(ViewPagerFragment.ARG_ITEM_ID, "pager_play_fragment");
		        fragment.setArguments(arguments);
		        cxt.setTitle("Lecture");
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
					.addToBackStack(null)
					.commit();
				return true;
			}
		})
		.setIcon(R.drawable.abs__ic_go)
		//.setActionView(R.layout.tvseries_menu)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

		menu.add("Historique")
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AbstractFragment fragment = new HistoryViewPagerFragment();
				Bundle arguments = new Bundle();
				arguments.putString(HistoryViewPagerFragment.ARG_ITEM_ID, "pager_history_fragment");
		        fragment.setArguments(arguments);
		        cxt.setTitle("Historique");
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
					.addToBackStack(null)
					.commit();
				return true;
			}
		})
		.setIcon(R.drawable.abs__ic_search)
		//.setActionView(R.layout.tvseries_menu)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW); //SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		menu.add("Series")
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				AbstractFragment fragment = new SeriesHomeFragment();
				Bundle arguments = new Bundle();
				arguments.putString(SeriesHomeFragment.ARG_ITEM_ID, "serie_home_fragment");
		        fragment.setArguments(arguments);
		    	cxt.setTitle("Series");
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
					.addToBackStack(null)
					.commit();
				return true;
			}
		})
		.setIcon(R.drawable.abs__ic_voice_search)
		//.setActionView(R.layout.tvseries_menu)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW); //SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
	}
}
