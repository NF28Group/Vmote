package fr.nf28.vmote.play.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import fr.nf28.vmote.R;
import fr.nf28.vmote.play.model.PlayModel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ViewPagerFragment extends AbstractPlayFragment {
	public static final String ARG_ITEM_ID = "pager_play_fragment";
    static final int NUM_ITEMS = 10;
	
	private View rootView;
	private PlayModel model;
	
	private Button buttonToDetails;
	private Button buttonToMain;
	private Button buttonToSubtitles;
	
    collectionPagerAdapter mCollectionPagerAdapter;
    ViewPager mViewPager;
	
	public ViewPagerFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setRetainInstance(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	this.setModel(PlayModel.getInstance());
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_lecture_top_layout, container, false);
    	        
        mViewPager = (ViewPager) rootView.findViewById(R.id.playPager);
        mViewPager.setAdapter(new collectionPagerAdapter(getChildFragmentManager()));
    	
        // buttons
        buttonToDetails = (Button) rootView.findViewById(R.id.goto_first);
        buttonToMain = (Button) rootView.findViewById(R.id.goto_second);
        buttonToSubtitles = (Button) rootView.findViewById(R.id.goto_last);

        buttonToDetails.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(0);
			}
		});
        
        buttonToMain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(1);
			}
		});
        
        buttonToSubtitles.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(2);
			}
		});
        
        // init main
        mViewPager.setCurrentItem(1);
        
    	return rootView;
    }
    
 /*   @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);

    mViewPager = (ViewPager) view.findViewById(R.id.playPager);
    mViewPager.setAdapter(new collectionPagerAdapter(getChildFragmentManager()));
    }
*/
	public PlayModel getModel() {
		return model;
	}

	public void setModel(PlayModel model) {
		this.model = model;
	}

	public Button getButtonToDetails() {
		return buttonToDetails;
	}

	public void setButtonToDetails(Button buttonToDetails) {
		this.buttonToDetails = buttonToDetails;
	}

	public Button getButtonToMain() {
		return buttonToMain;
	}

	public void setButtonToMain(Button buttonToMain) {
		this.buttonToMain = buttonToMain;
	}

	public Button getButtonToSubtitles() {
		return buttonToSubtitles;
	}

	public void setButtonToSubtitles(Button buttonToSubtitles) {
		this.buttonToSubtitles = buttonToSubtitles;
	}

	public class collectionPagerAdapter extends FragmentPagerAdapter {
	    public collectionPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }
	
	    @Override
	    public Fragment getItem(int i) {
	        Fragment fragment = null;
	        Bundle arguments = new Bundle();
	        Log.i("SWIPE","dans getItem i:" + i);
	        switch (i) {
			case 0:
				fragment = PlayDetailsFragment.newInstance();
				arguments.putString(PlayDetailsFragment.ARG_ITEM_ID, "details_play_fragment");
				break;
			case 1:
				fragment = PlayMainFragment.newInstance();
				arguments.putString(PlayMainFragment.ARG_ITEM_ID, "main_play_fragment");
				break;
			default:
				fragment = PlaySubtitlesFragment.newInstance();
				arguments.putString(PlaySubtitlesFragment.ARG_ITEM_ID, "subtitles_play_fragment");
				break;
			}
	        fragment.setArguments(arguments);
	
	        return fragment;
	    }
	
	    @Override
	    public int getCount() {
	        return NUM_ITEMS;
	    }
	}
}