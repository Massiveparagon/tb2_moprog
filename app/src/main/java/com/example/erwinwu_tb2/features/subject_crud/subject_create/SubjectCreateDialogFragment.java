package com.example.erwinwu_tb2.features.subject_crud.subject_create;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.erwinwu_tb2.R;
import com.example.erwinwu_tb2.database.QueryContract;
import com.example.erwinwu_tb2.database.QueryResponse;
import com.example.erwinwu_tb2.features.subject_crud.SubjectCrudListener;
import com.example.erwinwu_tb2.model.Subject;
import com.example.erwinwu_tb2.util.Constants;

public class SubjectCreateDialogFragment extends DialogFragment {

    private EditText subjectNameEditText;
    private EditText subjectCodeEditText;
    private EditText subjectCreditEditText;
    private Button createButton;
    private Button cancelButton;

    private static SubjectCrudListener subjectCrudListener;

    public SubjectCreateDialogFragment() {
    }

    public static SubjectCreateDialogFragment newInstance(String title, SubjectCrudListener listener){
        subjectCrudListener = listener;
        SubjectCreateDialogFragment subjectCreateDialogFragment = new SubjectCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        subjectCreateDialogFragment.setArguments(args);

        subjectCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return subjectCreateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subject_create_dialog, container, false);

        subjectNameEditText = view.findViewById(R.id.subjectNameEditText);
        subjectCodeEditText = view.findViewById(R.id.subjectCodeEditText);
        subjectCreditEditText = view.findViewById(R.id.subjectCreditEditText);
        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        String title = getArguments().getString(Constants.TITLE);
        getDialog().setTitle(title);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = subjectNameEditText.getText().toString();
                int subjectCode = Integer.parseInt(subjectCodeEditText.getText().toString());
                double subjectCredit = Double.parseDouble(subjectCreditEditText.getText().toString());

                final Subject subject = new Subject(-1, subjectName, subjectCode, subjectCredit);

                QueryContract.SubjectQuery query = new com.example.erwinwu_tb2.database.SubjectQueryImplementation();
                query.createSubject(subject, new QueryResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean data) {
                        getDialog().dismiss();
                        subjectCrudListener.onSubjectListUpdate(true);
                        Toast.makeText(getContext(), "Subject created successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String message) {
                        subjectCrudListener.onSubjectListUpdate(false);
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }
}
