package com.gtappdevelopers.gfgroomdatabase;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//adding annotation for our databse entities and db version.
@Database(entities = {CourseModal.class}, version = 1)
public abstract class CourseDatabase extends RoomDatabase {
    //below line is to create instance for our databse class.
    private static CourseDatabase instance;

    //below line is to create abstract variable for dao.
    public abstract Dao Dao();

    //on below line we are getting instance for our database.
    public static synchronized CourseDatabase getInstance(Context context) {
        //below line is to check if the instance is null or not.
        if (instance == null) {
            //if the instance is null we are creating a new instance
            instance =
                    //for creating a instance for our database we are creating a database builder and passing our database class with our database name.
                    Room.databaseBuilder(context.getApplicationContext(),
                            CourseDatabase.class, "course_database")
                            //below line is use to add fall back to destructive migration to our database.
                            .fallbackToDestructiveMigration()
                            //below line is to add callback to our database.
                            .addCallback(roomCallback)
                            //below line is to build our database.
                            .build();
        }
        //after creating an instance we are returning our instance
        return instance;
    }

    //below line is to create a callback for our room database.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //this method is called when database is created and below line is to populate our data.
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    //we are creating an async task class to perform task in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        PopulateDbAsyncTask(CourseDatabase instance) {
            Dao dao = instance.Dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
