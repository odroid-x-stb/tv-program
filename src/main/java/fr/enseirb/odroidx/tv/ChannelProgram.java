/**
 * Copyright (C) 2012 Sebastien Dubouchez <sdubouchez@enseirb-matmeca.fr>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
