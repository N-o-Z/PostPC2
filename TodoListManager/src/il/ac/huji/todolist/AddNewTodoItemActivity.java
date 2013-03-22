package il.ac.huji.todolist;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class AddNewTodoItemActivity extends Activity {
 
	private Button btnCancel, btnOK;
	private DatePicker datePicker;
	private EditText edtNewItem; 
	
	public void onCreate(Bundle unused) { 
		super.onCreate(unused); 
		setContentView(R.layout.date_layout);
		btnOK = (Button)findViewById(R.id.btnOK);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		datePicker = (DatePicker)findViewById(R.id.datePicker);
		edtNewItem = (EditText)findViewById(R.id.edtNewItem);
		
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(edtNewItem.getText().toString().trim().equals("")) {
					setResult(RESULT_CANCELED); 
					finish();
				}
				 
				Calendar date = Calendar.getInstance();
				int year = datePicker.getYear();
				int month = datePicker.getMonth();
				int day = datePicker.getDayOfMonth();
				
				date.set(year,month,day);
				Intent result = new Intent(); 
				result.putExtra("title",edtNewItem.getText().toString());
				result.putExtra("dueDate",date.getTime());
				setResult(RESULT_OK, result); 
				finish();
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED); 
				finish();
			}
		});
	} 	
}
