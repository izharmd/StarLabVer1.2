package com.bws.starlab.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.bws.starlab.Models.UserModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper _dbHelper;

    // The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.bws.starlab/databases/";

    private static String DB_NAME = "starlab.db";

    private static int DB_VERSION = 2;

    private SQLiteDatabase myDataBase = null;

    private final Context myContext;
    private static final String LOGCAT = null;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        // TODO Auto-generated constructor stub
    }

    public static DatabaseHelper getInstance(Context context) {
        if (_dbHelper == null) {
            _dbHelper = new DatabaseHelper(context);
        }
        return _dbHelper;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
            // upgrade to version 4 - not the correct method

        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are going to overwrite that
            // database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");
            }
        }
    }


    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {

            String myPath = DB_PATH + DB_NAME;

            File file = new File(myPath);
            if (file.exists() && !file.isDirectory())
                checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public SQLiteDatabase openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        return myDataBase;
    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    // return a cursor for a query
    public Cursor QueryDatabase(String query) {

        try {
            return myDataBase.rawQuery(query, null);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    public Cursor QueryDatabase(String query, String[] queryArgs) {

        try {
            return myDataBase.rawQuery(query, queryArgs);
        } catch (SQLException ex) {
            throw ex;
        }

    }

    // run a insert / update statement
    public void ExecuteScalar(String sql) throws SQLException {

        try {

            myDataBase.execSQL(sql);

        } catch (SQLException ex) {
            throw ex;
        } finally {

        }
    }


    public void updateDB() {
        int version = myDataBase.getVersion();
        boolean update = false;
        boolean isCheck;

        try {


        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public boolean deleteAllUserDtails() {

        myDataBase.execSQL("delete from UserDtails");
       // myDataBase.close();
        return true;
    }

    public Cursor GetUser() {

        String sql = "SELECT * FROM UserDtails";
        return QueryDatabase(sql);
    }

    public Cursor getAssetsList(){
        String sql = "SELECT * FROM starLab_Assets";
        return  QueryDatabase(sql);
    }

    public boolean insertUserDetils(UserModel userModel) {
        boolean dataSaved = false;
        try {
            // delete existing rows

            // insert a row - we will always have only 1 row
            ContentValues newEntry = new ContentValues();

            newEntry.put("UserName", userModel.getUserName());
            newEntry.put("Password", userModel.getPassword());

            // insert
            long retVal = myDataBase.insertOrThrow("UserDtails", null, newEntry);

            if (retVal > 0)
                dataSaved = true;

        } catch (SQLException ex) {

            throw ex;
        }

        return dataSaved;
    }
}
