package com.example.erwinwu_tb2.features.subject_crud.subject_list_show;

import static com.example.erwinwu_tb2.util.Constants.CREATE_SUBJECT;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erwinwu_tb2.R;
import com.example.erwinwu_tb2.database.QueryContract;
import com.example.erwinwu_tb2.database.QueryResponse;
import com.example.erwinwu_tb2.database.TableRowCountQueryImplementation;
import com.example.erwinwu_tb2.features.subject_crud.SubjectCrudListener;
import com.example.erwinwu_tb2.features.subject_crud.subject_create.SubjectCreateDialogFragment;
import com.example.erwinwu_tb2.model.Subject;
import com.example.erwinwu_tb2.model.TableRowCount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class SubjectListActivity extends AppCompatActivity implements SubjectCrudListener {

    private RecyclerView recyclerView;
    private TextView noDataFoundTextView;
    private TextView studentCountTextView;
    private TextView subjectCountTextView;
    private TextView takenSubjectCountTextView;
    private FloatingActionButton fab;

    private List<Subject> subjectList = new ArrayList<>();
    private SubjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subject_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initialization();

        adapter = new SubjectListAdapter(this, subjectList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        showTableRowCount();
        showSubjectList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubjectCreateDialogFragment dialogFragment = SubjectCreateDialogFragment.newInstance("Create Subject", SubjectListActivity.this);
                dialogFragment.show(getSupportFragmentManager(), CREATE_SUBJECT);

            }
        });
    }

    @Override
    public void onSubjectListUpdate(boolean isUpdate) {
        if(isUpdate) {
            showTableRowCount();
            showSubjectList();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void showTableRowCount() {
        QueryContract.TableRowCountQuery query = new TableRowCountQueryImplementation();
        query.getTableRowCount(new QueryResponse<TableRowCount>() {
            @Override
            public void onSuccess(TableRowCount data) {
                studentCountTextView.setText(getString(R.string.student_count, data.getStudentRow()));
                subjectCountTextView.setText(getString(R.string.subject_count, data.getSubjectRow()));
                takenSubjectCountTextView.setText(getString(R.string.taken_subject_count, data.getTakenSubjectRow()));
            }

            @Override
            public void onFailure(String message) {
                studentCountTextView.setText(getString(R.string.table_row_count_failed));
                subjectCountTextView.setText(message);
                takenSubjectCountTextView.setText("");
            }
        });
    }

    private void showSubjectList(){
        QueryContract.SubjectQuery query = new com.example.erwinwu_tb2.database.SubjectQueryImplementation();
        query.readAllSubject(new QueryResponse<List<Subject>>() {
            @Override
            public void onSuccess(List<Subject> data) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setVisibility(View.GONE);

                subjectList.clear();
                subjectList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setVisibility(View.GONE);
                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initialization(){
        recyclerView = findViewById(R.id.recyclerView);
        noDataFoundTextView = findViewById(R.id.noDataFoundTextView);

        studentCountTextView = findViewById(R.id.studentCount);
        subjectCountTextView = findViewById(R.id.subjectCount);
        takenSubjectCountTextView = findViewById(R.id.takenSubjectCount);

        fab = findViewById(R.id.fab);
    }

}

