package com.gtappdevelopers.gfgroomdatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CourseRVAdapter extends ListAdapter<CourseModal, CourseRVAdapter.ViewHolder> {
    //creating a variable for on item click listner.
    private OnItemClickListener listener;

    //creating a constructor class for our adapter class.
    CourseRVAdapter() {
        super(DIFF_CALLBACK);
    }

    //creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<CourseModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<CourseModal>() {
        @Override
        public boolean areItemsTheSame(CourseModal oldItem, CourseModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(CourseModal oldItem, CourseModal newItem) {
            //below line is to check the course name, description and course duration.
            return oldItem.getCourseName().equals(newItem.getCourseName()) &&
                    oldItem.getCourseDescription().equals(newItem.getCourseDescription()) &&
                    oldItem.getCourseDuration().equals(newItem.getCourseDuration());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //below line is use to inflate our layout file for each item of our recycler view.
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //below line of code is use to set data to each item of our recycler view.
        CourseModal model = getCourseAt(position);
        holder.courseNameTV.setText(model.getCourseName());
        holder.courseDescTV.setText(model.getCourseDescription());
        holder.courseDurationTV.setText(model.getCourseDuration());
    }

    //creating a method to get course modal for a specific position.
    public CourseModal getCourseAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //view holder class to create a variable for each view.
        TextView courseNameTV, courseDescTV, courseDurationTV;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //initializing each view of our recycler view.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseDescTV = itemView.findViewById(R.id.idTVCourseDescription);
            courseDurationTV = itemView.findViewById(R.id.idTVCourseDuration);
            //adding on click listner for each item of recycler view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //inside on click listner we are passing position to our item of recycler view.
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseModal model);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}


