package com.accion.assigment.studentinfo.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.accion.assigment.studentinfo.R;
import com.accion.assigment.studentinfo.model.Student;

/**
 * Created by swapnil on 9/4/16.
 */
public class StudentMarksActivity extends AppCompatActivity {
    public static final String STUDENT_INFO = "student";
    private Student mStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_marks);
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mStudent = bundle.getParcelable(STUDENT_INFO);
        }

        if (mStudent == null) finish();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        showFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFragment() {
        StudentMarksFragment newFragment = new StudentMarksFragment();
        Bundle args = new Bundle();
        args.putParcelable(StudentMarksFragment.STUDENT_INFO, mStudent);
        newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container, newFragment);

        transaction.commit();
    }


}
