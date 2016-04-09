package com.accion.assigment.studentinfo.events;

import java.util.ArrayList;

import com.accion.assigment.studentinfo.model.Student;


/**
 * Created by swapnil on 9/4/16.
 */
public class StudentInfoSuccessEvent {
    private ArrayList<Student> mStudentInfo;

    public StudentInfoSuccessEvent(ArrayList<Student> studentList) {
        mStudentInfo = studentList;
    }

    public ArrayList<Student> getStudentInfo() {
        return mStudentInfo;
    }
}
