package il.ac.huji.todolist;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RBAdapter extends ArrayAdapter<ITodoItem> {
	
	    
	    public RBAdapter(Context context, int resource, List<ITodoItem> arrayList) {
	        super(context, resource, arrayList);
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	LayoutInflater inflater = LayoutInflater.from(getContext()); 
	    	View view = inflater.inflate(R.layout.row, null);
	    	TextView title = (TextView)view.findViewById(R.id.txtTodoTitle);
	    	TextView date = (TextView)view.findViewById(R.id.txtTodoDueDate);
	    	TodoItem item = (TodoItem)getItem(position);
	    	
	    	//set title
	    	title.setText(item.getTitle());
	    	
	    	Date itemDate = item.getDueDate();
	    	//set date
	    	if(itemDate == null) {
	    		date.setText("No due date");			
			}
			else {
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy",Locale.US);
				String dateString = formatter.format(itemDate);
				date.setText(dateString);
			}
	    	
	    	if((item.getDueDate() != null) && ((item.getDueDate().compareTo(new Date()))<0)) {
	    		title.setTextColor(Color.RED);
	    		date.setTextColor(Color.RED);
	    	}
			return view;
	    }
	}
