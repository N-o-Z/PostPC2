package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;

public class TodoDAL {

	
	private DBHelper helper;
	private SQLiteDatabase db;
	private List<ITodoItem> _todoList;
	private Cursor cursor;
	protected SimpleCursorAdapter todoAdapter;
	
	public TodoDAL(Context context) {
		
		_todoList = new ArrayList<ITodoItem>();
		
	//	Parse.initialize(context, "@string/parseApplication" ,"@string/clientKey"); 
	//	ParseUser.enableAutomaticUser();
		helper = new DBHelper(context, "todo_db",null,1);
		db = helper.getWritableDatabase();

		String[] from = new String[] { "title", "due" }; 
		int[] to = new int[] { R.id.txtTodoTitle, R.id.txtTodoDueDate};  
		 
		 todoAdapter = new SimpleCursorAdapter(context,R.layout.row,cursor,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

		 
	}
	
	public boolean insert(ITodoItem todoItem) {
		
		ParseObject parseObject = new ParseObject("todo");
		try {	
			parseObject.put("title",todoItem.getTitle());
			parseObject.put("due", todoItem.getDueDate());
			parseObject.saveInBackground();
		 }
		 catch(Exception e) {
			 Log.d("Parse","Failed inserting item:" + e.getMessage());
			return false; 
		 }	 
		try {
			ContentValues todoEntry = new ContentValues(); 
			todoEntry.put("title", todoItem.getTitle()); 
			todoEntry.put("due", todoItem.getDueDate().getTime()); 
			if(db.insert("todo", null, todoEntry) == -1) {	
				return false;
			}
			todoAdapter.notifyDataSetChanged();
			return true;
		}
		catch(Exception e) {
			try {
				Log.d("DB","Failed inserting item:" + e.getMessage());
				parseObject.delete();
			} catch (ParseException e1) {
				return false;
			}
			return false; 
		}
	 }
	
	
	public boolean update(ITodoItem todoItem) {
		try
		{
			ParseQuery query = new ParseQuery("todo");
			query.whereEqualTo("title", todoItem.getTitle());
			ParseObject parseItem = query.getFirst();
			parseItem.put("due", todoItem.getDueDate().getTime());
			parseItem.saveInBackground();
		}
		catch(Exception e) {
			return false;
		}
		try {
			ContentValues todoEntry = new ContentValues();
	    	todoEntry.put("due", todoItem.getDueDate().getTime());
	    	if (db.update("todo", todoEntry, "title=?", new String[]{todoItem.getTitle()}) == 0) 
	    	{
	    		return false;
	    	}
		}
		catch(Exception e) {
			return false;
		}
		return true; 
		 
	}
	 
	public boolean delete(ITodoItem todoItem) {
		try
		{
			ParseQuery query = new ParseQuery("todo");
			query.whereEqualTo("title", todoItem.getTitle());
			query.findInBackground(new FindCallback() {
				public void done(List<ParseObject> results, ParseException e) {
			        if (e != null) {
			        	//error
			        } else {
			          if(!results.isEmpty()) {
			        	  try {
							results.get(0).delete();
						} catch (ParseException e1) {
						
						}
			          }
			        }
			      }
			  });
		//	ParseObject parseItem = query.getFirst();
		//	parseItem.delete();
		}
		catch (Exception e)
		{
			Log.d("Parse","Failed deleting item:" + e.getMessage()+ " "+e.getStackTrace().toString());
			return false;
		}
		try {
			db.delete("todo", "title=?", new String[]{todoItem.getTitle()});
			
		}
		catch (Exception e)
		{
			Log.d("DB","Failed deleting item:" + e.getMessage());
			return false;
		}
		return true;
	 }
	 
	public List<ITodoItem> all() {
		_todoList = new ArrayList<ITodoItem>();
		Cursor cursor = db.query("todo", new String[]{"title", "due"}, null, null, null, null, null);
		if (cursor.moveToFirst())
		{
			do {
				_todoList.add(new TodoItem(cursor.getString(0), new Date(cursor.getLong(1))));
			} while (cursor.moveToNext());
		}
		return _todoList;
	}

}
