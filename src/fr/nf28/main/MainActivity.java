package fr.nf28.main;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import fr.nf28.R;

import android.os.Bundle;

public class MainActivity extends SherlockFragmentActivity {
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        
        final ActionBar ab = getSupportActionBar();
    }
    
}
