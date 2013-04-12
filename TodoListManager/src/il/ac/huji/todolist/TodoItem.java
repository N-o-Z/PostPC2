package il.ac.huji.todolist;

//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem implements ITodoItem {
	
//	private String _dateString;
	private Date _date;
	private String _title;
	
/*	public TodoItem(String title, Date date) {
		if(date == null) {
			_dateString = "No due date";			
			_date = null;
		}
		else {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyy");
			_dateString = formatter.format(date);
			_date = date;
		}
		
		_title = title;
	}
	
	
	public String getTitle() {
		
		return _title;
	}
	
	public Date getDate() {
		return _date;
	}
	
	public String getDateString() {
		
		return _dateString;
	}*/
	public TodoItem(String title, Date date) {
		_title = title;
		_date = date;
		
	}

	@Override
	public Date getDueDate() {
		return _date;
	}

	@Override
	public String getTitle() {
		return _title;
	}
}
