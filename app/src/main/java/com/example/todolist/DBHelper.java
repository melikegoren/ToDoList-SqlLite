package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TASK_TABLE = "TASK_TABLE";
    public static final String COLUMN_TASK = "TASK_NAME";
    public static final String COLUMN_ID = "ID";


    public DBHelper(@Nullable Context context) { //DBHelper ı çağırdığımızda ihtiyacımız olan sadece context.
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TASK_TABLE + "(" + COLUMN_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT , " +COLUMN_TASK + " TEXT)";
        sqLiteDatabase.execSQL(createTableStatement); // oluşturduğumuz tabloyu çağıracak.

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean addOne(EnterTask enterTask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TASK, enterTask.getTask());

        long insert = db.insert(TASK_TABLE,null, cv); //nullColumnHack: eğer tabloya boş bir satır eklemeye çalışırsak sqllite bize izin vermez bunu engellemek için default olarak bi column adı biz eklemeliyiz ama zorunlu değil bu.
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean deleteOne(EnterTask enterTask){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TASK_TABLE +" WHERE "+ COLUMN_ID + " = " + enterTask.getId();

        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<EnterTask> getEveryone(){
        List<EnterTask> returnList =  new ArrayList<>();
        String queryString = "SELECT * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null); //cursor: okunacak datanın üzerindeki imleç gibi bir şey

        if(cursor.moveToFirst()){
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                EnterTask newCustomer = new EnterTask(customerID,customerName);
                returnList.add(newCustomer);
            }while(cursor.moveToNext());
        }
        else {
            //failure. do not add anything to the list.
        }
        cursor.close();
        db.close();

        return returnList;
    }
}
