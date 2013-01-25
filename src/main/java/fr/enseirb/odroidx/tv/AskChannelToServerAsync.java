package fr.enseirb.odroidx.tv;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class AskChannelToServerAsync extends AsyncTask<String, Integer, Boolean>{

	private ChannelSelectionActivity callerActivity;
	private String serverIp;
	private boolean serverReachable = false;
	private String tntServerTvPort;


	public AskChannelToServerAsync(ChannelSelectionActivity callerActivity, String serverIp, String tntServerTvPort) {
		this.callerActivity = callerActivity;
		this.serverIp = serverIp;
		this.tntServerTvPort = tntServerTvPort;
	}

	/**
	 * 
	 * @param urls The url which has to be reached to get tv program information
	 * @return List of programs for each channel
	 */
	@Override
	protected Boolean doInBackground(String... urls) {

		if((urls != null) && (urls.length == 1)) {

			serverReachable = true;
			try {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet getRequest = new HttpGet("http://".concat(serverIp).concat(":8080/dash-manager/tnt?channel=").concat(urls[0]));

				HttpResponse responseGet = client.execute(getRequest);

				Thread.sleep(2000);
				
			} catch (Exception e) {
				Log.e("ChannelSelectionActivity", "Server Unreachable ; trying with : " + "http://"+ serverIp + ":8080/dash-manager/tnt?channel=" + urls[0], e);
				serverReachable = false;
			}
		}

		return serverReachable;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
	}

	@Override
	protected void onPostExecute(Boolean serverReachable) {

		// Hide the progress dialog
		callerActivity.getProgressDialog().dismiss();
		
		callerActivity.setisAskingChannel(false);

		if(serverReachable) {
			Intent vlcLaunch = callerActivity.getPackageManager().getLaunchIntentForPackage("org.videolan.vlc");
			vlcLaunch.putExtra("isVodFile", true);
			vlcLaunch.putExtra("URL","http://"+ serverIp + ":" + tntServerTvPort); 

			try {
				Log.i("ChannelSelectionActivity", "Start VLC with URL : " + "http://"+ serverIp + ":" + tntServerTvPort);
				callerActivity.startActivity(vlcLaunch);
			}
			catch (Exception e) {
				Log.e("ChannelSelectionActivity", "Cannot launch VLC", e);
				Toast.makeText(callerActivity.getApplicationContext(), "Cannot launch VLC", Toast.LENGTH_LONG).show();
			}
		}
	}

}
