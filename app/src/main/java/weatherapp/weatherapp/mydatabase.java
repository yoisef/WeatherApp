package weatherapp.weatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;




public class mydatabase extends SQLiteOpenHelper {

    public static final String Databasename="weatherdata.db";
    public static final String Tablename1="Current_weather";
    public static final String Tablename2="Forecast_weather";
    public static final String columna="ID";
    public static final String columnb="Country";
    public static final String columnc="City";
    public static final String columnd="data";
    public static final String columne="condition";
    public static final String columnf="temperture";


    public mydatabase(Context context) {
        super(context, Databasename, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String example= "CREATE TABLE " + Tablename1  + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Country TEXT ,City TEXT ,data TEXT ,condition TEXT ,temperture TEXT );";
        String example1= "CREATE TABLE " + Tablename2  + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Country TEXT ,City TEXT ,data TEXT ,condition TEXT);";

        db.execSQL(example);
        db.execSQL(example1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ Tablename1);
        db.execSQL("DROP TABLE IF EXISTS "+ Tablename2);
        onCreate(db);

    }

    public Boolean insertdata(String contry , String city ,String dataa,String con ,String temp)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(columnb,contry);
        contentValues.put(columnc,city);
        contentValues.put(columnd,dataa);
        contentValues.put(columne,con);
        contentValues.put(columnf,temp);

        long result=  db.insert(Tablename1,null,contentValues);

        if(result == -1)
        {
            return false;

        }
        else {
            return true;
        }
    }

    public List<currentobject> getdatafromcurrenttalex()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] mycolumns={
                columnb,
                columnc,
                columnd,
                columne,
                columnf
        };

        // Filter results WHERE "title" = 'My Title'


        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                columnf + " ASC";


        Cursor cursor = db.query(
                Tablename1,   // The table to query
                mycolumns,             // The array of columns to return (pass null to get all)
                null ,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null              // The sort order
        );

        List<currentobject> itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            int index=cursor.getColumnIndexOrThrow(columnb);
            String country=cursor.getString(index);
            int indexx=cursor.getColumnIndexOrThrow(columnc);
            String city=cursor.getString(indexx);
            int indexxx=cursor.getColumnIndexOrThrow(columnd);
            String data=cursor.getString(indexxx);
         itemIds.add(new currentobject(country,city,data));

        }
        cursor.close();

        return itemIds;
    }
}
