package fr.enseirb.odroidx.tv;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.widget.Toast;

public class ChannelProgramAsync extends AsyncTask<String, Integer, ArrayList<ChannelProgram>>{
	
	/**
	 * Describing the error level of the task
	 */
	public final static int NO_ERROR = 0;
	public final static int ARG_ERROR = 1;
	public final static int LOAD_ERROR = 2;
	public final static int PARSE_ERROR = 3;
	private int error;
	
	private ChannelSelectionActivity callerActivity;
	
	private ArrayList<ChannelProgram> channels;
	
	public ChannelProgramAsync(ChannelSelectionActivity callerActivity) {
		this.callerActivity = callerActivity;
	}
	
	/**
	 * 
	 * @param urls The url which has to be reached to get tv program information
	 * @return List of programs for each channel
	 */
	@Override
	protected ArrayList<ChannelProgram> doInBackground(String... urls) {
		// Reset status
		error = NO_ERROR;
		
		// Channels
		channels = new ArrayList<ChannelProgram>();
		
		if((urls != null) && (urls.length == 1)) {
			Document HtmlPage = null;
			try {
				HtmlPage = Jsoup.connect(urls[0]).get();
				
			} catch (Exception e) {
				error = LOAD_ERROR;
			}
			if(HtmlPage != null) {
				Elements liClassic = HtmlPage.select("li.classic");
				Elements liOdd = HtmlPage.select("li.odd");
				if( (liClassic.size() ==0) || (liOdd.size() ==0) ) {
					error = PARSE_ERROR;
				}
				else {
					try {
						addChannelsFromElements(liClassic);
						addChannelsFromElements(liOdd);
					}
					catch (Exception e) {
						e.printStackTrace();
						error = PARSE_ERROR;
					}
				}
			}
			else {
				error = LOAD_ERROR;
			}
		}
		else {
			error = ARG_ERROR;
		}
		orderChannels();
		return channels;
	}
	
	private void addChannelsFromElements(Elements channelsElements) {
		Elements elts;
		for (Element liElement : channelsElements) {
			elts = liElement.select("a");
			String channelName = "";
			if(elts.size() > 0) {
				channelName = elts.first().text();
			}
			if((channelName == null) ||(channelName.equalsIgnoreCase(""))) {
				// It is a second line of program description - Do nothing
			}
			else {
				elts = liElement.select("a");
				String title = "";
				if(elts.size() > 1) {
					title = elts.get(1).text();
				}
				elts = liElement.select("em");
				String description = "";
				if(elts.size() > 0) {
					description = elts.first().text();
				}
				channels.add(new ChannelProgram(channelName
						, title
						, description));
			}
		}
	}
	
	private void orderChannels() {
		ArrayList<ChannelProgram> orderedChannels = new ArrayList<ChannelProgram>();
		for(int wantedChannelNumber=1;wantedChannelNumber<ChannelProgram.channels.size();wantedChannelNumber++) {
			for(ChannelProgram channelProgram : this.channels) {
				if(channelProgram.getChannelNumber() == wantedChannelNumber) {
					orderedChannels.add(channelProgram);
				}
			}
		}
		channels = orderedChannels;
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) {
    }

	@Override
    protected void onPostExecute(ArrayList<ChannelProgram> programs) {
		
		// Hide the progress dialog
		callerActivity.getProgressDialog().dismiss();
		
		// Show result
		callerActivity.setChannelPrograms(programs);
		
		// Show status to user
		int messageId = R.string.data_retrieved_successful;
		switch(error) {
		case ARG_ERROR:
			messageId = R.string.arg_error;
			break;
		case LOAD_ERROR:
			messageId = R.string.load_error;
			break;
		case PARSE_ERROR:
			messageId = R.string.parse_error;
			break;
		}
		Toast.makeText(callerActivity, messageId, Toast.LENGTH_SHORT).show();
    }

}
