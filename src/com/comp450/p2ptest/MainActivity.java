package com.comp450.p2ptest;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
//test
//test 2
public class MainActivity extends Activity implements PlaceholderFragment.PlaceHolderListener,PeerListListener {

	private static final String	TAG	= "P2P Main activity";
	
	Uri uri;
	WifiP2pManager mManager;
	Channel mChannel;
	BroadcastReceiver mReceiver;
	IntentFilter mIntentFilter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
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
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	registerReceiver(mReceiver, mIntentFilter);
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	unregisterReceiver(mReceiver);
    }

	@Override
	public void onDiscoverySelected() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDiscoverySelected()");
		mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
			
			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				Log.d(TAG, "onSucess()");
				Toast.makeText(getApplicationContext(), "Peers Avaliable!", Toast.LENGTH_SHORT).show();
				
			}
			
			@Override
			public void onFailure(int reason) {
				Log.d(TAG, "onFailure()");
				String message = reason == WifiP2pManager.P2P_UNSUPPORTED ? "P2P_UNSUPORTED" : "BUSY" ;
				
				Toast.makeText(getApplicationContext(), "No Peers Avaliable, reason: "+message, Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	public void onBrowserSelected() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onBrowserSelected()");
		
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.choose_file));
        try {
            startActivityForResult(intent, 6383);
        } catch (ActivityNotFoundException e) {
        	Toast.makeText(getApplicationContext(), "Couldn't open file browser", Toast.LENGTH_SHORT).show();
        }
	}
	 @Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 // If the file selection was successful
		 if (resultCode == RESULT_OK) {
			 if (data != null) {
				 // Get the URI of the selected file
				 uri = data.getData();
				 Log.i(TAG, "Uri = " + uri.toString());
				 try {
					 // Get the file path from the URI
					 final String path = FileUtils.getPath(this, uri);
					 Intent intent = new Intent(MainActivity.this, ShowFileActiviy.class);
					 intent.putExtra("file_path", path);
					 startActivity(intent);
				 } catch (Exception e) {
					 Log.e("FileSelectorTestActivity", "File select error", e);
				 }
			 }
		 }
	 }
	@Override
	public void onPeersAvailable(WifiP2pDeviceList peers) {
		// TODO Auto-generated method stub
		
	}

}
