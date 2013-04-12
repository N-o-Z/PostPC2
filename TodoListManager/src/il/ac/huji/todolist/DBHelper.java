package il.ac.huji.todolist;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;


public class DBHelper extends SQLiteOpenHelper {
	
	private static final String TABLE_NAME = "todo";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, due INTEGER);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		// TODO Auto-generated method stub

	}

}
