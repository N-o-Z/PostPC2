package il.ac.huji.todolist;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RBAdapter extends ArrayAdapter<String> {
	
	    private final int[] COLORS = new int[] { Color.RED, Color.BLUE };
	    
	    public RBAdapter(Context context, int resource, ArrayList<String> arrayList) {
	        super(context, resource, arrayList);
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	TextView view = (TextView)super.getView(position, convertView, parent);
			int colorPos = position % COLORS.length;
			view.setTextColor(COLORS[colorPos]);
			return view;
	    }
	}
