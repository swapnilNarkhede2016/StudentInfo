package com.accion.assigment.studentinfo.controller;

import java.util.ArrayList;

import com.accion.assigment.studentinfo.App;
import com.accion.assigment.studentinfo.common.RestApi;
import com.accion.assigment.studentinfo.events.StudentInfoFailureEvent;
import com.accion.assigment.studentinfo.events.StudentInfoSuccessEvent;
import com.accion.assigment.studentinfo.model.Student;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by swapnil on 9/4/16.
 */
public class StudentController {
    private RestApi mRestApi;
    private EventBus mEventBus;

    public StudentController() {
        mRestApi = App.getApi();
        mEventBus = EventBus.getDefault();
    }

    public void fetchStudentInformation() {
        mRestApi.getStudentList(mCallBack);
    }

    private Callback<ArrayList<Student>> mCallBack = new Callback<ArrayList<Student>>() {
        @Override
        public void success(ArrayList<Student> studentList, Response response) {
            StudentInfoSuccessEvent event = new StudentInfoSuccessEvent(studentList);
            mEventBus.post(event);
        }

        @Override
        public void failure(RetrofitError error) {
            mEventBus.post(new StudentInfoFailureEvent());
        }
    };
}
