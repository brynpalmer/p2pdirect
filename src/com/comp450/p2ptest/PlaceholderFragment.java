package com.comp450.p2ptest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

	private static final String		TAG							= "P2P Main fragment";
	
	public PlaceholderFragment() {
    }
    
    public interface PlaceHolderListener {
		public void onDiscoverySelected();

		void onBrowserSelected();
	}
    
    private PlaceHolderListener placeHolderListener;
    
    @Override
    public void onAttach(Activity activity) {
    	// TODO Auto-generated method stub
    	super.onAttach(activity);
    	placeHolderListener = (PlaceHolderListener)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        
        Button discoverPeerButton = (Button)rootView.findViewById(R.id.discovery_button);
        Button fileBrowserButton = (Button)rootView.findViewById(R.id.file_browser_button);
      
        discoverPeerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "I've been clicked!");
				placeHolderListener.onDiscoverySelected();
			
			}
		});
        
        fileBrowserButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "I've been clicked!");
				placeHolderListener.onBrowserSelected();
			
			}
		});
        
        return rootView;
    }
}