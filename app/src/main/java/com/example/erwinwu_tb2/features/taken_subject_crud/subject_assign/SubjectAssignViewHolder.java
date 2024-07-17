package com.example.erwinwu_tb2.features.taken_subject_crud.subject_assign;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.erwinwu_tb2.R;

public class SubjectAssignViewHolder extends RecyclerView.ViewHolder {

    CheckBox checkBox;
    TextView subjectNameTextView;
    TextView courseCodeTextView;
    TextView creditTextView;

    public SubjectAssignViewHolder(View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkbox);
        subjectNameTextView = itemView.findViewById(R.id.subjectNameTextView);
        courseCodeTextView = itemView.findViewById(R.id.courseCodeTextView);
        creditTextView = itemView.findViewById(R.id.creditTextView);
    }
}
