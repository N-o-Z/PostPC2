package il.ac.huji.todolist;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;

public class TodoListManagerActivity extends Activity {
	
	final int ADD_ITEM_RESULT = 1;
	private ListView listView; 
	private RBAdapter todoAdapter;
	private ArrayList<TodoItem> arrayList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		
		arrayList = new ArrayList<TodoItem>();
		listView = (ListView)findViewById(R.id.lstTodoItems); 
		todoAdapter = new RBAdapter(this, android.R.layout.simple_list_item_1,arrayList); 
		listView.setAdapter(todoAdapter);
		registerForContextMenu(listView);
		
	}

	protected void onActivityResult(int reqCode, int resCode, Intent data) { 
			switch (reqCode) { 
				case ADD_ITEM_RESULT: { 
					switch (resCode) {
					case (RESULT_CANCELED) : {
						return;
					}
					case (RESULT_OK) : {
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
		/*		String str = edtNewItem.getText().toString();
				str = str.trim();
				edtNewItem.setText("");
				if((str != null) && (!str.equals(""))) {
					todoAdapter.add(str);
					return true;
				}
				else {
					return false;
				}*/
				return false;
			}
			default : {
				return false;
			}
		}
	}
	
	public void onCreateContextMenu( ContextMenu menu, View v, ContextMenuInfo info) { 
		super.onCreateContextMenu(menu, v, info); 
		getMenuInflater().inflate(R.menu.context_menu, menu);
		TextView title = (TextView)(v.findViewById(R.id.txtTodoTitle));
		menu.setHeaderTitle(title.getText());
	}

	public boolean onContextItemSelected(MenuItem item) { 
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		 switch (item.getItemId()) {
		 	case R.id.menuItemDelete: {
		 		todoAdapter.remove(todoAdapter.getItem((info.position)));
		 		return true;
		 	}
		 	default: {
		 		return false;
		 	}
		 }
	}
	
}