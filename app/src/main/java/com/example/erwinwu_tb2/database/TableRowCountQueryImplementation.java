package com.example.erwinwu_tb2.database;

import static com.example.erwinwu_tb2.util.Constants.TABLE_STUDENT;
import static com.example.erwinwu_tb2.util.Constants.TABLE_STUDENT_SUBJECT;
import static com.example.erwinwu_tb2.util.Constants.TABLE_SUBJECT;

import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.erwinwu_tb2.model.TableRowCount;


public class TableRowCountQueryImplementation implements com.example.erwinwu_tb2.database.QueryContract.TableRowCountQuery {

    @Override
    public void getTableRowCount(com.example.erwinwu_tb2.database.QueryResponse<TableRowCount> response) {
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        try {
            long studentCount = DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_STUDENT);
            long subjectCount = DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_SUBJECT);
            long takenSubjectCount = DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_STUDENT_SUBJECT);

            TableRowCount tableRowCount = new TableRowCount(studentCount, subjectCount, takenSubjectCount);
            response.onSuccess(tableRowCount);

        } catch (Exception e){
            response.onFailure(e.getMessage());
        } finally {
            sqLiteDatabase.close();
        }

    }
}

