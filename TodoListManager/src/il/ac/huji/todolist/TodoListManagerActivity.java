package il.ac.huji.todolist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

public class TodoListManagerActivity extends Activity {
	
	private ListView listView; 
	private RBAdapter todoAdapter;
	private EditText edtNewItem; 
	private ArrayList<String> arrayList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list_manager);
		
		arrayList = new ArrayList<String>();
		listView = (ListView)findViewById(R.id.lstTodoItems); 
		todoAdapter = new RBAdapter(this, android.R.layout.simple_list_item_1,arrayList); 
		listView.setAdapter(todoAdapter);
		
		edtNewItem = (EditText)findViewById(R.id.edtNewItem);
		
		registerForContextMenu(listView); 
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
				String str = edtNewItem.getText().toString();
				str = str.trim();
				edtNewItem.setText("");
				if(!str.equals("")) {
					todoAdapter.add(str);
					return true;
				}
				else {
					return false;
				}
			}
			case R.id.menuItemDelete: {
				int index = listView.getSelectedItemPosition();
				if(index != AdapterView.INVALID_POSITION) {
					arrayList.remove(index);
					todoAdapter.notifyDataSetChanged();
			 		return true;
				}
		 	}
			default : {
				return false;
			}
		}
	}
}