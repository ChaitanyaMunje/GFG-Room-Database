package com.gtappdevelopers.gfgroomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//below line is for setting table name.
@Entity(tableName = "course_table")
public class CourseModal {

    //below line is to auto increment id for each course.
    @PrimaryKey(autoGenerate = true)
    //variable for our id.
    private int id;
    //below line is a variable for course name.
    private String courseName;
    //below line is use for course description.
    private String courseDescription;
    //below line is use for course duration.
    private String courseDuration;

    //below line we are creating constructor class.
    //inside constructor class we are not passing our id because it is incrementing automatically
    public CourseModal(String courseName, String courseDescription, String courseDuration) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration;
    }

    //on below line we are creating getter and setter methods.
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
