package com.accion.assigment.studentinfo.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.accion.assigment.studentinfo.R;
import com.accion.assigment.studentinfo.controller.StudentController;
import com.accion.assigment.studentinfo.events.StudentInfoFailureEvent;
import com.accion.assigment.studentinfo.events.StudentInfoSuccessEvent;
import com.accion.assigment.studentinfo.model.Student;
import com.accion.assigment.studentinfo.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by swapnil on 9/4/16.
 */
public class StudentsInfoActivity extends AppCompatActivity implements StudentsInfoFragment.OnStudentMarksSelected {

    private static final String STUDENT_LIST = "studentList";

    private ArrayList<Student> mStudentList;
    private StudentController mController;
    private ProgressDialog mProgress;

    @Bind(R.id.relative_layout)
    RelativeLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        if (savedInstanceState != null) {
            mStudentList = savedInstanceState.getParcelableArrayList(STUDENT_LIST);
        }

        if (mStudentList == null) {
            fetchStudentInfo();
        } else {
            showFragment();
        }
    }

    /**
     * fetching student information from the server
     */
    private void fetchStudentInfo() {
        if (mController == null) {
            mController = new StudentController();
        }

        if (!Utils.isOnline(this)) {
           Snackbar snackbar =  Snackbar.make(mLayout, R.string.no_internet_msg, Snackbar.LENGTH_SHORT);
           View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
            snackbar.show();
        } else {
            if (mProgress == null) {
                mProgress = Utils.progress(this, getString(R.string.loading_info_msg));
            }
            mController.fetchStudentInformation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            fetchStudentInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handle success event
     * @param event
     */
    public void onEventMainThread(StudentInfoSuccessEvent event) {
        if (mProgress != null && !isFinishing()) {
            mProgress.dismiss();
            mProgress = null;
        }
        mStudentList = event.getStudentInfo();

        sortListByName();

        showFragment();
    }

    /**
     * Handle failure event
     * @param event
     */
    public void onEventMainThread(StudentInfoFailureEvent event) {
        if (!isFinishing()) {
            if (mProgress != null) {
                mProgress.dismiss();
            }

            Snackbar snackbar =  Snackbar.make(mLayout, R.string.error_loading_data, Snackbar.LENGTH_SHORT);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
            snackbar.show();

            showFragment();
        }
    }

    /**
     * Sorting by assending order by students first name
     */
    private void sortListByName() {
        Collections.sort(mStudentList, new Comparator<Student>() {
            @Override
            public int compare(Student student1, Student student2) {
                return student1.getFirstName().compareToIgnoreCase(student2.getFirstName());
            }
        });
    }

    /**
     * display fragment
     */
    private void showFragment() {
        FragmentManager fm = getSupportFragmentManager();
        StudentsInfoFragment studentFragment = (StudentsInfoFragment) fm.findFragmentById(R.id.container);

        if (studentFragment == null) {
            studentFragment = new StudentsInfoFragment();
            Bundle args = new Bundle();
            args.putParcelableArrayList(StudentsInfoFragment.STUDENT_LIST, mStudentList);
            studentFragment.setArguments(args);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.container, studentFragment);

            transaction.commit();
        } else {
            if (!isFinishing())
            studentFragment.setStudents(mStudentList);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STUDENT_LIST, mStudentList);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }


    /**
     * Start activity to display result of selected student from the student list.
     * @param student
     */
    @Override
    public void showMarks(Student student) {
        Intent intent = new Intent(this, StudentMarksActivity.class);
        intent.putExtra(StudentMarksActivity.STUDENT_INFO, student);
        startActivity(intent);
    }
}
