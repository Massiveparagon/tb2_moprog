package com.example.erwinwu_tb2.features.subject_crud.subject_list_show;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.erwinwu_tb2.R;

public class SubjectViewHolder extends RecyclerView.ViewHolder {

    TextView subjectNameTextView;
    TextView courseCodeTextView;
    TextView creditTextView;
    ImageView editIcon;
    ImageView deleteIcon;

    public SubjectViewHolder(View itemView) {
        super(itemView);

        subjectNameTextView = itemView.findViewById(R.id.subjectNameTextView);
        courseCodeTextView = itemView.findViewById(R.id.courseCodeTextView);
        creditTextView = itemView.findViewById(R.id.creditTextView);
        editIcon = itemView.findViewById(R.id.editIcon);
        deleteIcon = itemView.findViewById(R.id.deleteIcon);
    }
}
