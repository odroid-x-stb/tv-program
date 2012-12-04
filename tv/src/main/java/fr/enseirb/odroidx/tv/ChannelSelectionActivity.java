package fr.enseirb.odroidx.tv;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class ChannelSelectionActivity extends Activity {

    /**
     * UI attributes
     */
    private ListView allChannels;
    private ChannelAdapter channelAdapter;
    private ProgressDialog progressDialog;
    private ImageView reloadButton;
    
    /**
     * Async task to get data
     */
    private ChannelProgramAsync asyncDataGetter;
    private boolean isGettingPrograms;
    
    {
    	isGettingPrograms = false;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.channel_selection);
        
        // Set ListView and his adapter
        allChannels = (ListView) findViewById(R.id.all_channels);
        channelAdapter = new ChannelAdapter(getApplicationContext(), R.layout.channel_row, new ArrayList<ChannelProgram>());
        allChannels.setAdapter(channelAdapter);
        
        reloadButton = (ImageView) findViewById(R.id.reload);
        reloadButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isGettingPrograms) {
					getPrograms();
				}
			}
		});
		
        
        fillChannelNamesHashMap();
        
        getPrograms();
        
    }

	public void getPrograms() {
		
		isGettingPrograms = true;
		
    	// Show progress dialog
        progressDialog = ProgressDialog.show(this, "Loading", "Data is loading");
        
        // Launch async task to get programs
        asyncDataGetter = new ChannelProgramAsync(this);
        asyncDataGetter.execute(getString(R.string.tv_program_url_now));        
    }
    
    public ProgressDialog getProgressDialog() {
    	return progressDialog;
    }
    
    public void setChannelPrograms(ArrayList<ChannelProgram> newChannelPrograms) {
    	channelAdapter.clear();
    	for (ChannelProgram channelProgram : newChannelPrograms) {
    		channelAdapter.add(channelProgram);
    	}
    	channelAdapter.notifyDataSetChanged();
    	isGettingPrograms = false;
    }
    
    private void fillChannelNamesHashMap() {
		if(ChannelProgram.channels == null) {
			ChannelProgram.channels = new ArrayList<Channel>();
		}
		if(ChannelProgram.channels.size() == 0) {
			ChannelProgram.channels.add(new Channel("TF1", 1, R.drawable.tf1));
			ChannelProgram.channels.add(new Channel("France 2", 2, R.drawable.france2));
			ChannelProgram.channels.add(new Channel("France 3", 3, R.drawable.france3));
			ChannelProgram.channels.add(new Channel("Canal+", 4, R.drawable.canalplus));
			ChannelProgram.channels.add(new Channel("France 5", 5, R.drawable.france5));
			ChannelProgram.channels.add(new Channel("M6", 6, R.drawable.m6));
			ChannelProgram.channels.add(new Channel("Arte", 7, R.drawable.arte));
			ChannelProgram.channels.add(new Channel("D8", 8, R.drawable.d8));
			ChannelProgram.channels.add(new Channel("W9", 9, R.drawable.w9));
			ChannelProgram.channels.add(new Channel("TMC", 10, R.drawable.tmc));
			ChannelProgram.channels.add(new Channel("NT1", 11, R.drawable.nt1));
			ChannelProgram.channels.add(new Channel("NRJ 12", 12, R.drawable.nrj12));
			ChannelProgram.channels.add(new Channel("LCP", 13, R.drawable.lcp));
			ChannelProgram.channels.add(new Channel("France 4", 14, R.drawable.france4));
			ChannelProgram.channels.add(new Channel("BFM TV", 15, R.drawable.bfmtv));
			ChannelProgram.channels.add(new Channel("i>Télé", 16, R.drawable.itele));
			ChannelProgram.channels.add(new Channel("D17", 17, R.drawable.d17));
			ChannelProgram.channels.add(new Channel("Gulli", 18, R.drawable.gulli));
			ChannelProgram.channels.add(new Channel("France Ô", 19, R.drawable.franceo));
		}
	}
    

}

