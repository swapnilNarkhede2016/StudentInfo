package com.accion.assigment.studentinfo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.accion.assigment.studentinfo.R;
import com.squareup.phrase.Phrase;

import java.util.ArrayList;

import com.accion.assigment.studentinfo.model.Marks;
import com.accion.assigment.studentinfo.model.Student;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by swapnil on 9/4/16.
 */
public class StudentMarksFragment extends Fragment {
    public static final String STUDENT_INFO = "student_info";

    private Student mStudent;

    @Bind(R.id.name)
    TextView mStudentNameTextView;

    @Bind(R.id.first_subject)
    TextView mFirstSubjectNameTextView;

    @Bind(R.id.second_subject)
    TextView mSecondSubjectNameTextView;

    @Bind(R.id.third_subject)
    TextView mThirdSubjectNameTextView;

    @Bind(R.id.first_subject_marks)
    TextView mFirstSubjectMarksTextView;

    @Bind(R.id.second_subject_marks)
    TextView mSecondSubjectMarksTextView;

    @Bind(R.id.third_subject_marks)
    TextView mThirdSubjectMarksTextView;



    public StudentMarksFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_marks, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mStudent = getArguments().getParcelable(STUDENT_INFO);

        initData();
    }

    private void initData() {
        String lastName = mStudent.getLastName();
        String capitalisedName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        CharSequence studentName = Phrase.from(getActivity(), R.string.student_name)
                                    .put("first_name", mStudent.getFirstName())
                                    .put("last_name", capitalisedName)
                                    .format();

        mStudentNameTextView.setText(studentName);

        ArrayList<Marks> marksList = mStudent.getMarkList();

        mFirstSubjectNameTextView.setText(getSubjectName(marksList.get(0).getSubjectName()));
        mSecondSubjectNameTextView.setText(getSubjectName(marksList.get(1).getSubjectName()));
        mThirdSubjectNameTextView.setText(getSubjectName(marksList.get(2).getSubjectName()));

        mFirstSubjectMarksTextView.setText(getSubjectMarks(marksList.get(0).getMarks()));
        mSecondSubjectMarksTextView.setText(getSubjectMarks(marksList.get(1).getMarks()));
        mThirdSubjectMarksTextView.setText(getSubjectMarks(marksList.get(2).getMarks()));
    }

    private CharSequence getSubjectMarks(int marks) {
        return Phrase.from(getActivity(), R.string.subject_marks)
                .put("marks", String.valueOf(marks))
                .format();
    }

    private CharSequence getSubjectName(String subjectName) {
        return Phrase.from(getActivity(), R.string.subject_name)
                                    .put("subject_name", subjectName)
                                    .format();
    }
}
