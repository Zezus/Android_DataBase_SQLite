package com.example.database_sqlite;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.Callable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertPerson().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(getApplicationContext(), "Вставка завершена", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private io.reactivex.Observable<String> insertPerson() {
        return io.reactivex.Observable.fromCallable(new Callable<String>() {

            @Override
            public String call() throws Exception {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase database = databaseHelper.getWritableDatabase();

                try {
                    database.beginTransaction();

                    Person person = new Person();
                    person.setAge(18);
                    person.setEmail("this_is_email@mail.ru");
                    person.setFullName("Rustam");
                    person.setPhoneNumber("+77021111111");

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseHelper.FULL_NAME_COLUMN, person.getFullName());
                    contentValues.put(DatabaseHelper.AGE_COLUMN, person.getAge());
                    contentValues.put(DatabaseHelper.EMAIL_COLUMN, person.getEmail());
                    contentValues.put(DatabaseHelper.PHONE_NUMBER_COLUMN, person.getPhoneNumber());

                    database.insert(DatabaseHelper.PEOPLE_TABLE, null, contentValues);

                    /*database.update(DatabaseHelper.PEOPLE_TABLE, contentValues,
                            DatabaseHelper.ID_COLUMN + " LIKE ?",
                            new String[] {String.valueOf(1)});

                    database.delete(DatabaseHelper.PEOPLE_TABLE,
                            DatabaseHelper.ID_COLUMN + " LIKE ?",
                            new String[] {String.valueOf(1)});

                    Cursor cursor = database.query(
                            DatabaseHelper.PEOPLE_TABLE,
                            new String[]{DatabaseHelper.FULL_NAME_COLUMN, DatabaseHelper.EMAIL_COLUMN, DatabaseHelper.AGE_COLUMN},
                            " LIKE ?",
                            new String[]{String.valueOf(1)},
                            null,
                            null,
                            null //DataBaseHelper.AGE_COLUMN + " ASC"
                    );

                    cursor.moveToFirst();
                    while (cursor.moveToNext()) {
                        int ageColumnIndex = cursor.getColumnIndex(DatabaseHelper.AGE_COLUMN);
                        int age = cursor.getInt(ageColumnIndex);
                    }*/

                    database.setTransactionSuccessful();
                    //cursor.close();
                } catch (SQLException ex) {
                    Log.d(MainActivity.class.getSimpleName(), ex.getMessage());
                } finally {
                    database.endTransaction();
                }

                database.close();
                databaseHelper.close();

                return "";
            }
        });
    }
}
