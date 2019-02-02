package m.ragaey.mohamed.petsapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import m.ragaey.mohamed.petsapp.Data.PetsContract.PetsEntry;

public class PetBdHelper extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_PETS_TABLE =
            "CREATE TABLE " + PetsEntry.TABLE_NAME + " ("
                    + PetsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + PetsEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                    + PetsEntry.COLUMN_PET_BREED + " TEXT, "
                    + PetsEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                    + PetsEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS" + PetsEntry.TABLE_NAME;


    public PetBdHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
