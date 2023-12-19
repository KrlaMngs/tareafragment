package database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import models.Repo;

public class AlbumDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "repo.db";
    private static final int DATABASE_VERSION = 1;
    //Decalrara nombres de tablas y columnas
    public static final String REPO_TABLE = "repositories";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";


    public AlbumDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + REPO_TABLE + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DESCRIPTION + " TEXT " + ");"
        );

        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, "Tarea");
        values.put(COLUMN_DESCRIPTION, "Esta es una tarea de repositorios");


        db.insert(REPO_TABLE, null, values);

        values.clear();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public List<Repo> getAllRepositories() {
        List<Repo> repoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] colums = {COLUMN_ID,
                COLUMN_NAME,
                COLUMN_DESCRIPTION
        };

        Cursor cursor = db.query(
               REPO_TABLE,
                colums,
                null, null, null, null, null

                        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                    @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));


                    repoList.add(new Repo(id, name, description));
                } while (cursor.moveToNext());
            }
            //cursor.close();
        }
        return repoList;
    }

    public long insertRepo(Repo repo){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME, repo.getName());
        values.put(COLUMN_DESCRIPTION, repo.getDescription());


        long id = db.insert(REPO_TABLE, null,values);
        db.close();
        return id;

    }

}
