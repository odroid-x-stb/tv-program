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

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChannelAdapter extends ArrayAdapter<ChannelProgram> {

	private ArrayList<ChannelProgram> items;
	private Context context;

	public ChannelAdapter(Context context, int textViewResourceId, ArrayList<ChannelProgram> items) {
		super(context, textViewResourceId, items);

		this.context = context;
		this.items = items;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.channel_row, null);
		}
		TextView channelName = (TextView) view.findViewById(R.id.channel_name);
		channelName.setText(items.get(position).getChannelName());
		int resourceId = items.get(position).getResourceId();
		if( resourceId != 0 ) {
			Drawable myIcon = context.getResources().getDrawable(resourceId);
			channelName.setCompoundDrawablesWithIntrinsicBounds(null, myIcon, null, null);
		}
		
		TextView programDescription = (TextView) view.findViewById(R.id.program_description);
		programDescription.setText(items.get(position).getTitle() + "\n" + items.get(position).getDescription());
		return view;
	}
	

}
