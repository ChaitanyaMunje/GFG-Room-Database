package com.gtappdevelopers.gfgroomdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //creating a variables for our recycler view.
    private RecyclerView coursesRV;
    private static final int ADD_COURSE_REQUEST = 1;
    private static final int EDIT_COURSE_REQUEST = 2;
    private ViewModal viewmodal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initializing our variable for our recycler view and fab.
        coursesRV = findViewById(R.id.idRVCourses);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        //adding on click listner for floating action button.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starting a new activity for adding a new course and passing a constant value in it.
                Intent intent = new Intent(MainActivity.this, NewCourseActivity.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });

        //setting layout manager to our adapter class.
        coursesRV.setLayoutManager(new LinearLayoutManager(this));
        coursesRV.setHasFixedSize(true);
        //initializing adapter for recycler view.
        final CourseRVAdapter adapter = new CourseRVAdapter();
        //setting adapter class for recycler view.
        coursesRV.setAdapter(adapter);
        //passing a data from view modal.
        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);
        //below line is use to get all the courses from view modal.
        viewmodal.getAllCourses().observe(this, new Observer<List<CourseModal>>() {
            @Override
            public void onChanged(List<CourseModal> models) {
                //when the data is changed in our models we are adding that list to our adapter class.
                adapter.submitList(models);
            }
        });
        //below method is use to add swipe to delete method for item of recycler view.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //on recycler view item swiped then we are deleting the item of our recycler view.
                viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).
                //below line is use to attact this to recycler view.
                        attachToRecyclerView(coursesRV);
        //below line is use to set item click listner for our item of recycler view.
        adapter.setOnItemClickListener(new CourseRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CourseModal model) {
                //after clicking on item of recycler view
                //we are opening a new activity and passing a data to our activity.
                Intent intent = new Intent(MainActivity.this, NewCourseActivity.class);
                intent.putExtra(NewCourseActivity.EXTRA_ID, model.getId());
                intent.putExtra(NewCourseActivity.EXTRA_COURSE_NAME, model.getCourseName());
                intent.putExtra(NewCourseActivity.EXTRA_DESCRIPTION, model.getCourseDescription());
                intent.putExtra(NewCourseActivity.EXTRA_DURATION, model.getCourseDuration());
                //below line is to start a new activity and adding a edit course constant.
                startActivityForResult(intent, EDIT_COURSE_REQUEST);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            String courseName = data.getStringExtra(NewCourseActivity.EXTRA_COURSE_NAME);
            String courseDescription = data.getStringExtra(NewCourseActivity.EXTRA_DESCRIPTION);
            String courseDuration = data.getStringExtra(NewCourseActivity.EXTRA_DURATION);
            CourseModal model = new CourseModal(courseName, courseDescription, courseDuration);
            viewmodal.insert(model);
            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewCourseActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String courseName = data.getStringExtra(NewCourseActivity.EXTRA_COURSE_NAME);
            String courseDesc = data.getStringExtra(NewCourseActivity.EXTRA_DESCRIPTION);
            String courseDuration = data.getStringExtra(NewCourseActivity.EXTRA_DURATION);
            CourseModal model = new CourseModal(courseName, courseDesc, courseDuration);
            model.setId(id);
            viewmodal.update(model);
            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }

    }
}