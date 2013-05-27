package fr.nf28.vmote;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import fr.nf28.vmote.R;
import fr.nf28.vmote.history.view.HistoryVideoFragment;
import fr.nf28.vmote.play.view.ViewPagerFragment;
import fr.nf28.vmote.series.view.SeriesHomeFragment;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
	private boolean useLogo = false;
    private boolean showHomeUp = false;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        
        final ActionBar ab = getSupportActionBar();
        
        // set defaults for logo & home up
        ab.setDisplayHomeAsUpEnabled(showHomeUp);
        ab.setDisplayUseLogoEnabled(useLogo);

        // set up tabs nav
        ab.addTab(ab.newTab().setText("Lecture").setTabListener(this).setTag(1));
        ab.addTab(ab.newTab().setText("Historique").setTabListener(this).setTag(2));
        ab.addTab(ab.newTab().setText("Series").setTabListener(this).setTag(3));

        // default to tab navigation
        showTabsNav();

    }

    
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "tab : " + tab.getText(), Toast.LENGTH_SHORT).show();
		int sel = (Integer) tab.getTag();
		
		AbstractFragment fragment;
		Bundle arguments;
		
		switch (sel) {
		case 1: // play
			fragment = new ViewPagerFragment();
			arguments = new Bundle();
			arguments.putString(ViewPagerFragment.ARG_ITEM_ID, "pager_play_fragment");
	        fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
				.addToBackStack(null)
				.commit();
			
			break;
		case 2: // histo
			fragment = new HistoryVideoFragment();
			arguments = new Bundle();
			arguments.putString(HistoryVideoFragment.ARG_ITEM_ID, "history_video_fragment");
	        fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
				.addToBackStack(null)
				.commit();
			break;
		case 3: // series
			fragment = new SeriesHomeFragment();
			arguments = new Bundle();
			arguments.putString(SeriesHomeFragment.ARG_ITEM_ID, "serie_home_fragment");
	        fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
				.replace(R.id.applicationview_detail_container, fragment, TAG_FRAGMENT)
				.addToBackStack(null)
				.commit();
			break;
		default:
			break;
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {		
	}

    private void showTabsNav() {
        ActionBar ab = getSupportActionBar();
        if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
    }
    
}
