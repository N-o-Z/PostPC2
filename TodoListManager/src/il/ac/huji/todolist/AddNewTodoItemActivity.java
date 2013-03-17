package il.ac.huji.todolist;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class AddNewTodoItemActivity extends Activity {
 
	private Button btnCancel, btnOK;
	private DatePicker datePicker;
	private EditText edtNewItem; 
	private TextView titleView;
	private TextView dateView;
	
	public void onCreate(Bundle unused) { 
		super.onCreate(unused); 
		setContentView(R.layout.date_layout);
		btnOK = (Button)findViewById(R.id.btnOK);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		datePicker = (DatePicker)findViewById(R.id.datePicker);
		edtNewItem = (EditText)findViewById(R.id.edtNewItem);
		titleView = (TextView)findViewById(R.id.txtTodoTitle);
		dateView = (TextView)findViewById(R.id.txtTodoDueDate);
		
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 
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
				// TODO Auto-generated method stub 
				setResult(RESULT_CANCELED); 
				finish();
			}
		});
		
		
	

	//	setTitle(foo);
	} 	
}
