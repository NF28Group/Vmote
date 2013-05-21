package fr.nf28.vmote;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import fr.nf28.vmote.R;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class MainActivity extends SherlockFragmentActivity implements ActionBar.TabListener {
	private boolean useLogo = false;
    private boolean showHomeUp = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        
        final ActionBar ab = getSupportActionBar();
        
        // set defaults for logo & home up
        ab.setDisplayHomeAsUpEnabled(showHomeUp);
        ab.setDisplayUseLogoEnabled(useLogo);

        // set up tabs nav
        ab.addTab(ab.newTab().setText("Lecture").setTabListener(this));
        ab.addTab(ab.newTab().setText("Historique").setTabListener(this));
        ab.addTab(ab.newTab().setText("Series").setTabListener(this));

        // default to tab navigation
        showTabsNav();

        // create a couple of simple fragments as placeholders
       //final int MARGIN = 16;
        /*
        leftFrag = new RoundedColourFragment(getResources().getColor(
                R.color.android_green), 1f, MARGIN, MARGIN / 2, MARGIN, MARGIN);
        rightFrag = new RoundedColourFragment(getResources().getColor(
                R.color.honeycombish_blue), 2f, MARGIN / 2, MARGIN, MARGIN,
                MARGIN);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.root, leftFrag);
        ft.add(R.id.root, rightFrag);
        ft.commit();
        */
    }

    
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "tab : " + tab.getText(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

    private void showTabsNav() {
        ActionBar ab = getSupportActionBar();
        if (ab.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS) {
            ab.setDisplayShowTitleEnabled(false);
            ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        }
    }
    
}
