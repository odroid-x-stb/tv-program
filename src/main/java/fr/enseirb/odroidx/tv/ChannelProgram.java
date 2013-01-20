package fr.enseirb.odroidx.tv;

import java.util.ArrayList;


public class ChannelProgram {
	
	// HashMap : channel name - channel number
	public static ArrayList<Channel> channels;
	
	private Channel channel;
	
	private String title;
	private String description;
	
	public ChannelProgram(String channelName, String title, String description) {
		this.title = title;
		this.description = description;
		
		boolean channelFound = false;
		for(Channel ch : channels) {
			if(channelName.replace("Programme ", "").equalsIgnoreCase(ch.getName())) {
				this.channel = ch;
				channelFound = true;
				break;
			}
		}
		if(!channelFound) {
			this.channel = new Channel(channelName.replace("Programme ", ""), 0, 0);
		}
		
	}
	
	public ChannelProgram(int channelNumber) {
			this.title = "";
			this.description = "";
			for(Channel ch : channels) {
				if(ch.getNumber() == channelNumber) {
					this.channel = ch;
					break;
				}
			}
	}
	
	public int getChannelNumber() {
		return this.channel.getNumber();
	}
	
	public String getChannelName() {
		return this.channel.getName();
	}
	
	public int getResourceId() {
		return this.channel.getResourceId();
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return this.channel.getNumber() + " - " + this.channel.getName() + " : " + title + " " + description +  "\n";
	}
	
	
}
