package il.ac.huji.todolist;

import java.util.Date;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class TodoListManagerActivity extends Activity {
	
	private final int ADD_ITEM_RESULT = 1;
	private ListView listView; 
	private List<ITodoItem> arrayList;
	private TodoDAL _dal;
	private RBAdapter todoAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		Parse.initialize(this, this.getString(R.string.parseApplication), this.getString(R.string.clientKey));
		//Parse.initialize(this, "1FHGNdMfbFd3tgDdlDF4FqETPOBpWvQlfVTfteRe", "kmzUb62p9mtIy4XBuu4kZV29GyUyrANh3H9JDOvk");
		ParseUser.enableAutomaticUser();
		_dal = new TodoDAL(this);
		
		arrayList = _dal.all();
		
		listView = (ListView)findViewById(R.id.lstTodoItems); 
		todoAdapter = new RBAdapter(this,android.R.layout.simple_list_item_1,arrayList);
		registerForContextMenu(listView);
		
		
		listView.setAdapter(todoAdapter);
		
		
		
	}

	protected void onActivityResult(int reqCode, int resCode, Intent data) { 
			switch (reqCode) { 
				case ADD_ITEM_RESULT: { 
					switch (resCode) {
					case (RESULT_CANCELED) : {
						return;
					}
					case (RESULT_OK) : {
						_dal.insert(new TodoItem((String)data.getStringExtra("title"),(Date)data.getSerializableExtra("dueDate")));
						todoAdapter.add(new TodoItem((String)data.getStringExtra("title"),(Date)data.getSerializableExtra("dueDate")));
					}
				}
			} 
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo_list_manager, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) { 
		switch (item.getItemId()) {
			case R.id.menuItemAdd : {
				Intent intent = new Intent(this,AddNewTodoItemActivity.class);  
				startActivityForResult(intent, ADD_ITEM_RESULT); 
				return true;
			}
			default : {
				return false;
			}
		}
	}
	
	public void onCreateContextMenu( ContextMenu menu, View v, ContextMenuInfo info) { 
		super.onCreateContextMenu(menu, v, info); 
		getMenuInflater().inflate(R.menu.context_menu, menu);
		TodoItem item = (TodoItem)todoAdapter.getItem(((AdapterContextMenuInfo)info).position);
		menu.setHeaderTitle(item.getTitle());
		menu.getItem(1).setTitle(item.getTitle());
		if(!item.getTitle().startsWith("Call ")) {
			menu.removeItem(R.id.menuItemCall);
		}
	}

	public boolean onContextItemSelected(MenuItem item) { 
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		 switch (item.getItemId()) {
		 	case R.id.menuItemDelete: {
		 		_dal.delete((TodoItem)todoAdapter.getItem((info.position)));
		 		todoAdapter.remove(todoAdapter.getItem((info.position)));
		 		return true;
		 	}
		 	case R.id.menuItemCall : {
		 		String number = ((TodoItem)todoAdapter.getItem((info.position))).getTitle().substring(5);
		 		Uri uriNum = Uri.parse("tel:"+number);
		 		Intent intent = new Intent(Intent.ACTION_DIAL,uriNum);  
				startActivity(intent); 
				return true;
		 	}
		 	default: {
		 		return false;
		 	}
		 }
	}
}