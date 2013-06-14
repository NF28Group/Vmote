package fr.nf28.vmote.play.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
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
    static final int NUM_ITEMS = 3;
	
	private View rootView;
	private PlayModel model;
	
	private Button buttonToDetails;
	private Button buttonToMain;
	private Button buttonToSubtitles;
	
	CollectionPagerAdapter mCollectionPagerAdapter;
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
        mViewPager.setAdapter(new CollectionPagerAdapter(getChildFragmentManager()));
    	
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
				if(model.getVlcConnection().getMedia().isMovie()) {
					mViewPager.setCurrentItem(2);
				}
			}
		});
        
        // init main
        mViewPager.setCurrentItem(1);
        
    	return rootView;
    }
    
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
	

/* marche pas : inflate fail
	public class MyViewPager extends ViewPager {
		public MyViewPager(Context context, AttributeSet attrs) {	
			super(context, attrs);
		}
		
		@Override
		protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
			if(!model.getVlcConnection().getMedia().getIsMovie() 
					&& this.getCurrentItem()==1
					&& dx<0) {
				Log.i("SWIPE", "Le swipe c'est NON");
				return false;
			}
			return super.canScroll(v, checkV, dx, x, y);    
		}
	}
	*/
	public class CollectionPagerAdapter extends FragmentPagerAdapter {
	    public CollectionPagerAdapter(FragmentManager fm) {
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
			case 2:
				fragment = PlaySubtitlesFragment.newInstance();
				arguments.putString(PlaySubtitlesFragment.ARG_ITEM_ID, "subtitles_play_fragment");
				break;	
			default: // back on main
				fragment = PlayMainFragment.newInstance();
				arguments.putString(PlayMainFragment.ARG_ITEM_ID, "main_play_fragment");
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