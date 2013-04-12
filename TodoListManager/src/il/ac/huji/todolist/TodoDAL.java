package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TodoDAL {

	
	private DBHelper helper;
	private SQLiteDatabase db;
	private List<ITodoItem> _todoList;
	private ParseObject parseObject;
	private Cursor cursor;
	protected SimpleCursorAdapter todoAdapter;
	
	public TodoDAL(Context context) {
		
		_todoList = new ArrayList<ITodoItem>();
		
		Parse.initialize(context, "@string/parseApplication" ,"@string/clientKey"); 
		ParseUser.enableAutomaticUser();
		helper = new DBHelper(context, "todo_db",null,1);
		db = helper.getWritableDatabase();

		cursor = db.query("todo",new String[] {"title","due"},null, null,null, null, null);
		String[] from = new String[] { "title", "due" }; 
		int[] to = new int[] { R.id.txtTodoTitle, R.id.txtTodoDueDate};  
		 
		 todoAdapter = new SimpleCursorAdapter(context,R.layout.row,cursor,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		 
	}
	
	public boolean insert(ITodoItem todoItem) {
	
		try {
			parseObject = new ParseObject("todo");
			parseObject.put("title",todoItem.getTitle());
			parseObject.put("due", todoItem.getDueDate());
			parseObject.saveInBackground();
		 }
		 catch(Exception e) {
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
			_todoList.add(todoItem);
			return true;
		}
		catch(Exception e) {
			try {
				parseObject.delete();
			} catch (ParseException e1) {
				return false;
			}
			return false; 
		}
	 }
	
	
	public boolean update(ITodoItem todoItem) {
		return false; 
		 
	}
	 
	public boolean delete(ITodoItem todoItem) {
		try{
			
		}
		catch(Exception e) {
			return false;
		} 
		try{
			
			return true;
		}
		catch(Exception e) {
			return false;
		} 
	 }
	 
	public List<ITodoItem> all() {
		return _todoList;
	}

	public void setAdapter(ListView listView) {
		
		
	}

	public ListAdapter getAdapter() {
		return todoAdapter;
	}

}
