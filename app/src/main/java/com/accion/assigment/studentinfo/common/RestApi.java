package com.accion.assigment.studentinfo.common;

import java.util.ArrayList;

import com.accion.assigment.studentinfo.model.Student;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by swapnil on 9/4/16.
 */
public interface RestApi {
    @GET("/jsondata.php")
    void getStudentList(Callback<ArrayList<Student>> cb);
}
