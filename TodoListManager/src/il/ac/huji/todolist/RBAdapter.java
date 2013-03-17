package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RBAdapter extends ArrayAdapter<TodoItem> {
	
	    
	    public RBAdapter(Context context, int resource, ArrayList<TodoItem> arrayList) {
	        super(context, resource, arrayList);
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	LayoutInflater inflater = LayoutInflater.from(getContext()); 
	    	View view = inflater.inflate(R.layout.row, null);
	    	TextView title = (TextView)view.findViewById(R.id.txtTodoTitle);
	    	TextView date = (TextView)view.findViewById(R.id.txtTodoDueDate);
	    	TodoItem item = (TodoItem)getItem(position);
	    	
	    	title.setText(item.getTitle());
	    	date.setText(item.getDateString());
	    	if((item.getDate() != null) && ((item.getDate().compareTo(new Date()))<0)) {
	    		title.setTextColor(Color.RED);
	    		date.setTextColor(Color.RED);
	    	}
	    	//LinearLayout row = (LinearLayout)super.getView(position,convertView,parent);
	    	//TextView view = (TextView)super.getView(position, convertView, parent);
			//int colorPos = position % COLORS.length;
			//view.setTextColor(COLORS[colorPos]);
			return view;
	    }
	}
