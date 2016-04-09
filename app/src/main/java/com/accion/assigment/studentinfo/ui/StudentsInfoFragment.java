package com.accion.assigment.studentinfo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.accion.assigment.studentinfo.R;
import com.accion.assigment.studentinfo.adapter.StudentAdapter;
import com.accion.assigment.studentinfo.model.Student;
import com.accion.assigment.studentinfo.utils.RecyclerItemDecorator;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by swapnil on 9/4/16.
 */
public class StudentsInfoFragment extends Fragment implements SearchView.OnQueryTextListener{
    public static final String STUDENT_LIST = "student_list";
    private static final String SEARCH_QUERY = "search_query_text";

    private ArrayList<Student> mStudentList;
    private OnStudentMarksSelected mCallback;
    private StudentAdapter mAdapter;
    private String mSearchQueryText;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.empty_view)
    TextView mEmptyView;

    public StudentsInfoFragment() {}

    public interface OnStudentMarksSelected {
        void showMarks(Student student);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        Bundle bundle = getArguments();
        if (bundle != null)
            mStudentList = bundle.getParcelableArrayList(STUDENT_LIST);

        //check before orientation change use was in search menu with query texts
        if (savedInstanceState != null) {
            mSearchQueryText = savedInstanceState.getString(SEARCH_QUERY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_information, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnStudentMarksSelected) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        if (!TextUtils.isEmpty(mSearchQueryText)) {
            item.expandActionView();
            searchView.setQuery(mSearchQueryText, true);
            searchView.clearFocus();
        }

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        mAdapter.setFilter(mStudentList);
                        mSearchQueryText = null;
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return true;
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SEARCH_QUERY, mSearchQueryText);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new StudentAdapter(getActivity(), mStudentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerItemDecorator(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        ValidateData();

        //handling the item selection from students list.
        mAdapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Student student, View view) {
                mCallback.showMarks(student);
            }
        });
    }

    /**
     * validate student data if it is empty display empty message
     * otherwise display student data
     */
    private void ValidateData() {
        if (mStudentList == null || mStudentList.isEmpty()) {
            mRecyclerView.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
        }
    }

    public void setStudents(ArrayList<Student> studentList) {
        mStudentList = studentList;

        ValidateData();
    }

    private ArrayList<Student> filter(String query) {
        ArrayList<Student> filteredModelList = new ArrayList<>();
        if (!TextUtils.isEmpty(query)) {
            query = query.toLowerCase();
            for (Student student : mStudentList) {
                final String text = student.getFirstName().toLowerCase();
                if (text.contains(query)) {
                    filteredModelList.add(student);
                }
            }

            mSearchQueryText = query;
        } else {
            filteredModelList = mStudentList;
        }

        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!TextUtils.isEmpty(newText)) {
            ArrayList<Student> filteredModelList = filter(newText);
            mAdapter.setFilter(filteredModelList);
        }

        return false;
    }


}
