package upm.pmd.grupo14.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Class that implements the database handler for the app, including column names and queries
 */
public class NewsDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME= "char.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_ARTICLES = "articles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ARTICLE_ID = "id_art";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_SUBTITLE = "subtitle";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_RESUME = "resume";
    public static final String COLUMN_BODY = "body";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_UPDATE = "update";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_IMAGE_MEDIA = "image_media";
    public static final String COLUMN_THUMBNAIL = "thumbnail";
    public static final String OBLIG_FIELD_TYPE = " TEXT NOT NULL, ";
    public static final String OPT_FIELD_TYPE = " TEXT";
    public static final String DB_CREATE = "CREATE TABLE " +
            TABLE_ARTICLES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ARTICLE_ID + OBLIG_FIELD_TYPE +
            COLUMN_TITLE + OBLIG_FIELD_TYPE +
            COLUMN_SUBTITLE + OBLIG_FIELD_TYPE +
            COLUMN_CATEGORY + OBLIG_FIELD_TYPE +
            COLUMN_RESUME + OBLIG_FIELD_TYPE +
            COLUMN_BODY + OBLIG_FIELD_TYPE +
            COLUMN_USER + OBLIG_FIELD_TYPE +
            COLUMN_UPDATE + " INTEGER NOT NULL, " +
            COLUMN_IMAGE + OPT_FIELD_TYPE + ", " +
            COLUMN_THUMBNAIL + OPT_FIELD_TYPE + ", " +
            COLUMN_IMAGE_MEDIA + OPT_FIELD_TYPE +");";


    /**
     * Constructor for the class
     * @param context Enviroment of the database
     */
    public NewsDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Executes the query to create the table of the database
     * @param sqLiteDatabase Database where is going to be created
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DB_CREATE);
    }

    /**
     * Upgrades the version of the database droping the old table and creating a new one
     * @param sqLiteDatabase Database which is going to be upgraded
     * @param oldVersion Old version number
     * @param newVersion New version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(NewsDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(sqLiteDatabase);
    }
}
