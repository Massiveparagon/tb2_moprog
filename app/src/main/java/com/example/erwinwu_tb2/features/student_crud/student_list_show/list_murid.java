package com.example.erwinwu_tb2.features.student_crud.student_list_show;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.erwinwu_tb2.R;
import com.example.erwinwu_tb2.database.QueryContract;
import com.example.erwinwu_tb2.database.QueryResponse;
import com.example.erwinwu_tb2.database.StudentQueryImplementation;
import com.example.erwinwu_tb2.database.TableRowCountQueryImplementation;
import com.example.erwinwu_tb2.features.student_crud.StudentCrudListener;
import com.example.erwinwu_tb2.features.student_crud.student_create.StudentCreateDialogFragment;
import com.example.erwinwu_tb2.features.subject_crud.subject_list_show.SubjectListActivity;
import com.example.erwinwu_tb2.model.Student;
import com.example.erwinwu_tb2.model.TableRowCount;
import com.example.erwinwu_tb2.util.Constants;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class list_murid extends AppCompatActivity implements StudentCrudListener {

    private RecyclerView recyclerView;
    private TextView noDataFoundTextView;
    private FloatingActionButton fab;
    private TextView studentCountTextView;
    private TextView subjectCountTextView;
    private TextView takenSubjectCountTextView;

    private List<Student> studentList = new ArrayList<>();
    private StudentListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_murid);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialization();

        adapter = new StudentListAdapter(this, studentList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        showStudentList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentCreateDialogFragment studentCreateDialogFragment = StudentCreateDialogFragment.newInstance("Create Student", list_murid.this);
                studentCreateDialogFragment.show(getSupportFragmentManager(), Constants.CREATE_STUDENT);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTableRowCount();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_show_subject){
            startActivity(new Intent(this, SubjectListActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStudentListUpdate(boolean isUpdated) {
        if(isUpdated) {
            showStudentList();
            showTableRowCount();
        }
    }

    private void showStudentList() {
        QueryContract.StudentQuery query = new StudentQueryImplementation();
        query.readAllStudent(new QueryResponse<List<Student>>() {
            @Override
            public void onSuccess(List<Student> data) {
                recyclerView.setVisibility(View.VISIBLE);
                noDataFoundTextView.setVisibility(View.GONE);

                studentList.clear();
                studentList.addAll(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {
                recyclerView.setVisibility(View.GONE);
                noDataFoundTextView.setVisibility(View.VISIBLE);
            }
        });
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

    private void initialization(){
        recyclerView = findViewById(R.id.recyclerView);
        noDataFoundTextView = findViewById(R.id.noDataFoundTextView);
        fab = findViewById(R.id.fab);

        studentCountTextView = findViewById(R.id.studentCount);
        subjectCountTextView = findViewById(R.id.subjectCount);
        takenSubjectCountTextView = findViewById(R.id.takenSubjectCount);
    }
}
