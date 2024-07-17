package com.example.erwinwu_tb2.features.student_crud.student_list_show;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.example.erwinwu_tb2.R;

class StudentViewHolder extends RecyclerView.ViewHolder {

    TextView nameTextView;
    TextView registrationNumTextView;
    TextView emailTextView;
    TextView phoneTextView;
    ImageView editImageView;
    ImageView deleteImageView;

    StudentViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.nameTextView);
        registrationNumTextView = itemView.findViewById(R.id.registrationNumTextView);
        emailTextView = itemView.findViewById(R.id.emailTextView);
        phoneTextView = itemView.findViewById(R.id.phoneTextView);
        editImageView = itemView.findViewById(R.id.editImageView);
        deleteImageView = itemView.findViewById(R.id.deleteImageView);
    }
}
