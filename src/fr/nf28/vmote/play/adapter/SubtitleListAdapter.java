package fr.nf28.vmote.play.adapter;

import fr.nf28.vmote.R;
import fr.nf28.vmote.play.classes.SubtitleList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SubtitleListAdapter extends BaseAdapter {
		private SubtitleList dataObjects;
		
		public SubtitleListAdapter(SubtitleList objects) {
			dataObjects = objects;
		}
	
        @Override  
        public int getCount() {  
            return dataObjects.getList().size();  
        }  
  
        @Override  
        public Object getItem(int position) {  
            return null;  
        }  
  
        @Override  
        public long getItemId(int position) {  
            return 0;  
        }  
  
        @Override  
        public View getView(int position, View convertView, ViewGroup parent) {  
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtitle_list_item, null);  
            TextView title = (TextView) retval.findViewById(R.id.tvSubtitleList);  
            title.setText(dataObjects.getList().get(position).toString()); // affiche le text de subtitle
              
            return retval;  
        }    
}