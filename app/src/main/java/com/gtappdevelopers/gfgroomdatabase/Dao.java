package com.gtappdevelopers.gfgroomdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Adding annotation to our Dao class
@androidx.room.Dao
public interface Dao {

    //below method is use to add data to database.
    @Insert
    void insert(CourseModal model);

    //below method is use to update the data in our database.
    @Update
    void update(CourseModal model);

    //below line is use to delete a specific course in our database.
    @Delete
    void delete(CourseModal model);

    //on below line we are making query to delete all courses from our databse.
    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    //beloe line is to read all the courses from our database.
    //in this we are ordering our courses in ascending order with our course name.
    @Query("SELECT * FROM course_table ORDER BY courseName ASC")
    LiveData<List<CourseModal>> getAllCourses();

}
