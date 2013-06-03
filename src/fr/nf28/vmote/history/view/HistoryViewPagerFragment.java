package fr.nf28.vmote.history.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import fr.nf28.vmote.R;
import fr.nf28.vmote.history.model.HistoryModel;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class HistoryViewPagerFragment extends AbstractHistoryFragment {
	public static final String ARG_ITEM_ID = "pager_history_fragment";
    static final int NUM_ITEMS = 2;
	
	private View rootView;
	private HistoryModel model;
	
	private Button buttonToVideos;
	private Button buttonToAudio;
	
    collectionPagerAdapter mCollectionPagerAdapter;
    ViewPager mViewPager;
	
	public HistoryViewPagerFragment() {}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setRetainInstance(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	this.setModel(HistoryModel.getInstance());
    	
    	rootView = inflater.inflate(
    			R.layout.fragment_history_top_layout, container, false);
    	        
        mViewPager = (ViewPager) rootView.findViewById(R.id.playPager);
        mViewPager.setAdapter(new collectionPagerAdapter(getChildFragmentManager()));
    	
        // buttons
        buttonToVideos = (Button) rootView.findViewById(R.id.goto_first);
        buttonToAudio = (Button) rootView.findViewById(R.id.goto_second);

        buttonToVideos.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(0);
			}
		});
        
        buttonToAudio.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(1);
			}
		});
        
        // init main
        mViewPager.setCurrentItem(0);
        
    	return rootView;
    }

	public Button getButtonToVideos() {
		return buttonToVideos;
	}

	public void setButtonToVideos (Button buttonToVideos) {
		this.buttonToVideos = buttonToVideos;
	}

	public Button getButtonToMusic() {
		return buttonToAudio;
	}

	public void setButtonToMusic(Button buttonToAudio) {
		this.buttonToAudio = buttonToAudio;
	}

	public class collectionPagerAdapter extends FragmentPagerAdapter {
	    public collectionPagerAdapter(FragmentManager fm) {
	        super(fm);
	    }
	
	    @Override
	    public Fragment getItem(int i) {
	        Fragment fragment = null;
	        Bundle arguments = new Bundle();
	        Log.i("SWIPE","dans getItem de history i:" + i);
	        switch (i) {
			case 0:
				fragment = HistoryVideoFragment.newInstance();
				arguments.putString(HistoryVideoFragment.ARG_ITEM_ID, "history_video_fragment");
				break;
			case 1:
				fragment = HistoryAudioFragment.newInstance();
				arguments.putString(HistoryAudioFragment.ARG_ITEM_ID, "history_audio_fragment");
				break;
			default: // back on main
				fragment = HistoryVideoFragment.newInstance();
				arguments.putString(HistoryVideoFragment.ARG_ITEM_ID, "history_video_fragment");
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
    
	public HistoryModel getModel() {
		return model;
	}

	public void setModel(HistoryModel model) {
		this.model = model;
	}
}