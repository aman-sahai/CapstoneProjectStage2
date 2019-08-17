package com.example.searchjob;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Savejob.class}, version = 1, exportSchema = false)
public abstract class AppData extends RoomDatabase
{

    private static volatile AppData INSTANCE;

    static AppData getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppData.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppData.class, context.getString(R.string.word_db))
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;


    }

    public abstract MyDao myDao();


}
