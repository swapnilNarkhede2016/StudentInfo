package com.accion.assigment.studentinfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.accion.assigment.studentinfo.App;
import com.accion.assigment.studentinfo.R;
import com.squareup.phrase.Phrase;

import java.util.ArrayList;

import com.accion.assigment.studentinfo.model.Student;
import com.accion.assigment.studentinfo.utils.CircleTransformation;
import com.accion.assigment.studentinfo.utils.Utils;

/**
 * Created by swapnil on 9/4/16.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    private ArrayList<Student> mStudentList;
    private Context mContext;
    private OnItemClickListener mOnClickListener;
    public StudentAdapter(Context context, ArrayList<Student> studentList) {
        mStudentList = studentList;
        mContext = context;
    }

    @Override
    public StudentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_student_information_child, parent, false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(StudentHolder holder, int position) {
        Student student = mStudentList.get(position);
        String lastName = student.getLastName();
        String capitalisedName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
        CharSequence studentName = Phrase.from(mContext, R.string.student_name)
                                    .put("first_name", student.getFirstName())
                                    .put("last_name", capitalisedName)
                                    .format();

        holder.studentName.setText(studentName);

        CharSequence rollNumber = Phrase.from(mContext, R.string.roll_number)
                                    .put("roll_number", String.valueOf(student.getRollNo()))
                                    .format();

        holder.rollNumber.setText(rollNumber);

        int imgWidthAndHeight = (int) Utils.dp2Px(mContext, 30);
        App.getPicasso()
                .load(student.getProfilePic())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .resize(imgWidthAndHeight, imgWidthAndHeight)
                .transform(new CircleTransformation())
                .into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(Student student, View view);
    }

    public void setFilter(ArrayList<Student> studentList) {
        mStudentList = new ArrayList<>();
        mStudentList.addAll(studentList);
        notifyDataSetChanged();
    }

    public class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView avatar;
        TextView studentName;
        TextView rollNumber;
        public StudentHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            studentName = (TextView) itemView.findViewById(R.id.name);
            rollNumber = (TextView) itemView.findViewById(R.id.roll_number);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onItemClick(mStudentList.get(getLayoutPosition()), itemView);
        }
    }
}
