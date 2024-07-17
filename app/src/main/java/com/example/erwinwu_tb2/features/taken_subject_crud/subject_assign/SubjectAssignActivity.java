package com.example.erwinwu_tb2.features.taken_subject_crud.subject_assign;

import static com.example.erwinwu_tb2.util.Constants.STUDENT_ID;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erwinwu_tb2.R;
import com.example.erwinwu_tb2.database.QueryContract;
import com.example.erwinwu_tb2.database.QueryResponse;
import com.example.erwinwu_tb2.database.TakenSubjectQueryImplementation;
import com.example.erwinwu_tb2.model.TakenSubject;

import java.util.ArrayList;
import java.util.List;

public class SubjectAssignActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView noDataFoundTextView;

    private List<TakenSubject> takenSubjectList = new ArrayList<>();
    private SubjectAssignListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_assign);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        recyclerView = findViewById(R.id.recyclerView);
        noDataFoundTextView = findViewById(R.id.noDataFoundTextView);

        int studentId = getIntent().getIntExtra(STUDENT_ID, -1);

        adapter = new SubjectAssignListAdapter(this, studentId, takenSubjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        QueryContract.TakenSubjectQuery query = new TakenSubjectQueryImplementation();
        query.readAllSubjectWithTakenStatus(studentId, new QueryResponse<List<TakenSubject>>() {
            @Override
            public void onSuccess(List<TakenSubject> data) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setVisibility(View.GONE);

                takenSubjectList.clear();
                takenSubjectList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setVisibility(View.GONE);
                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });

    }

    public void closeActivity(View view) {
        finish();
    }
}

