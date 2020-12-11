package com.example.logreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {


    public  static final  int DB_VERSION = 7;
    public static final String DB_NAME = "Felhasznalo";


    public  static  final String FELHASZ_DB = "Felhasznalo";
    public  static  final String COL_ID = "id";
    public  static  final String COL_EMAIL = "email";
    public  static  final String COL_FELNEC = "felhnev";
    public  static  final String COL_FELJELSZO = "felszo";
    public  static  final String COL_TELJESNEV ="teljesnev";

    public DBhelper(Context context){super(context,DB_NAME,null,DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE " +FELHASZ_DB+ "("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_EMAIL + " VARCHAR(255) NOT NULL, " +
                COL_FELNEC + " VARCHAR(255) NOT NULL, " +
                COL_FELJELSZO + " VARCHAR(255) NOT NULL, " +
                COL_TELJESNEV + " VARCHAR(255) NOT NULL" +
                ")";
        db.execSQL(sql);
    }

    public boolean NevEllenor(String felhnev){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result= db.rawQuery(" SELECT COUNT(*) FROM "+FELHASZ_DB+" WHERE  "+COL_FELNEC+" = ? ",new String[]{felhnev});
        result.moveToFirst();
        return  result.getInt(0)>=1;
    }

    public Cursor adatLekerdezes(){
        SQLiteDatabase db= this.getReadableDatabase();
        return db.query(FELHASZ_DB,new String[]{COL_ID,COL_EMAIL,COL_FELNEC,COL_FELJELSZO,COL_TELJESNEV},null,null,null,null,null);
        //return db.rawQuery("SELECT * FROM "+TANULO_TABLE,null);
    }

    public Cursor EgyLeKerdezes(String teljesn){
        SQLiteDatabase db= this.getReadableDatabase();
        //return db.query(FELHASZ_DB,new String[]{COL_TELJESNEV},COL_FELNEC+" = ? ",new String[]{""+teljesn+""},null,null,null);
        return db.rawQuery("SELECT "+COL_TELJESNEV+" FROM "+FELHASZ_DB+" WHERE "+COL_FELNEC+" = ? OR "+COL_EMAIL+ " = ? ",new String[]{teljesn,teljesn});
        //return db.rawQuery("SELECT * FROM "+TANULO_TABLE,null);
    }


    public boolean JelszoEllenor(String felszo){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result= db.rawQuery("SELECT COUNT(*) FROM "+FELHASZ_DB+" WHERE  "+COL_FELJELSZO+" = ?",new String[]{felszo});
        result.moveToFirst();
        return  result.getInt(0)>=1;
    }

    public boolean Nev(String felhnev,String jelszo){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result= db.rawQuery(" SELECT COUNT(*) FROM "+FELHASZ_DB+" WHERE  ("+COL_FELNEC+" = ? OR "+COL_EMAIL+" = ?) AND "+COL_FELJELSZO+ " = ? ",new String[]{felhnev,felhnev,jelszo});
        result.moveToFirst();
        return  result.getInt(0)>=1;
    }

    public boolean TNevKiir(String felhnev){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result= db.rawQuery(" SELECT COUNT( "+COL_TELJESNEV+" )FROM "+FELHASZ_DB+" WHERE  "+COL_FELNEC+" = ? OR "+COL_EMAIL+" = ? ",new String[]{felhnev,felhnev});
        result.moveToFirst();
        return  result.getInt(0)==1;
    }





    /*public  boolean teljesN(String nev,String email,String teljes){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT "+COL_TELJESNEV+ " WHERE "+COL_FELNEC+" = ? OR "+COL_EMAIL+" =? OR "+COL_TELJESNEV+" = ?",new String[]{nev}{email}{teljes} );
        result.moveToFirst();
        return result.getInt(0)==1;

    }
*/

    public boolean regiszt(String email,String felhnev,String felszo,String teljesnev){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_FELNEC, felhnev);
        values.put(COL_FELJELSZO, felszo);
        values.put(COL_TELJESNEV, teljesnev);
        long result=db.insert(FELHASZ_DB,null,values);
        return  result != -1;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS "+FELHASZ_DB;
        db.execSQL(sql);
        onCreate(db);
    }
}
