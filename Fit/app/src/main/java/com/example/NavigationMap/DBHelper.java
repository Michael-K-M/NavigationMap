package com.example.NavigationMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(@Nullable Context context){
        super(context, "Userdata.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table User(" +
                "ID INTEGER primary key autoincrement," +
                "Email TEXT, " +
                "FirstN TEXT, " +
                "Surname TEXT, " +
                "Goalcalories TEXT, " +
                "Goalweight TEXT, " +
                "Password TEXT)");

        db.execSQL("create Table UserHistory(" +
                "ID INTEGER primary key autoincrement," +
                "UserID Int," +
                "Hight TEXT, " +
                "Calories TEXT, " +
                "Weight TEXT, " +
                " FOREIGN KEY (UserID) REFERENCES User(ID))");

        db.execSQL("create Table UserLoggedIn(" +
                "ID INTEGER primary key autoincrement," +
                "UserID INTEGER," +
                " FOREIGN KEY (UserID) REFERENCES User(ID))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists User");
        db.execSQL("drop Table if exists UserHistory");
        db.execSQL("drop Table if exists UserLoggedIn");
    }

    public User getUser(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM User Where Email = '" + email + "' and Password = '" + password + "'", null);
        User user = null;
        if (c.moveToFirst()){
            do {
                user = new User();
                // Passing values
                user.Id = c.getInt(0);
                user.email = c.getString(1);
                user.FirstN = c.getString(2);
                user.Surname = c.getString(3);
                user.Goalcalories = c.getString(4);
                user.Goalweight = c.getString(5);
                user.password = c.getString(6);
                // Do something Here with values

                db = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("UserID", user.Id);
                db.delete("UserLoggedIn", null, null);
                db.insert("UserLoggedIn", null, contentValues);

            } while(c.moveToNext());
        }
        c.close();
        db.close();


        return user;
    }

    public User getUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM UserLoggedIn", null);
        int Id = 0;
        if (c.moveToFirst()){
            do {
                Id = c.getInt(1);
            } while(c.moveToNext());
        }

        if(Id != 0){
            c = db.rawQuery("SELECT * FROM User Where Id = '" + Id + "'", null);
            if (c.moveToFirst()){
                do {
                    User user = new User();
                    // Passing values
                    user.Id = c.getInt(0);
                    user.email = c.getString(1);
                    user.FirstN = c.getString(2);
                    user.Surname = c.getString(3);
                    user.Goalcalories = c.getString(4);
                    user.Goalweight = c.getString(5);
                    user.password = c.getString(6);
                    // Do something Here with values
                    c.close();
                    db.close();
                    return user;

                } while(c.moveToNext());
            }
            c.close();
            db.close();
        }
        return null;
    }

    public void logOut(){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.delete("UserLoggedIn", null, null);
    }

    public void addUser(User user){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("Email", user.email);
        contentValues.put("FirstN", user.FirstN);
        contentValues.put("Surname", user.Surname);
        contentValues.put("Goalcalories", user.Goalcalories);
        contentValues.put("Goalweight", user.Goalweight);
        contentValues.put("Password", user.password);
        long id = DB.insert("User", null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("UserID", id);
        DB.delete("UserLoggedIn", null, null);
        DB.insert("UserLoggedIn", null, contentValues);

    }
}
