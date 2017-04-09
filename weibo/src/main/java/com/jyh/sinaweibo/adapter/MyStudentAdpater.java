package com.jyh.sinaweibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyh.sinaweibo.R;
import com.jyh.sinaweibo.adapter.base.BaseRecyclerAdapter;
import com.jyh.sinaweibo.model.Student;



public class MyStudentAdpater extends BaseRecyclerAdapter<Student> implements BaseRecyclerAdapter.OnLoadingHeaderCallBack
{

    public MyStudentAdpater(Context context) {
        super(context, ONLY_HEADER);

        setOnLoadingHeaderCallBack(this);
    }


    /*
    * 实现明细
    * */
    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {

        View studentDetailView= LayoutInflater.from(mContext).inflate(R.layout.studentitemlayout,parent,false);
        return new StudentDetailHolderView(studentDetailView);
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder holder, Student item, int position) {

        StudentDetailHolderView studentDetailHolderView=(StudentDetailHolderView)holder;
        TextView studentNameTvObj= (TextView) studentDetailHolderView.getHeaderView().findViewById(R.id.student_name_tv_id);
        TextView studentAgeTvObj= (TextView) studentDetailHolderView.getHeaderView().findViewById(R.id.student_age_tv_id);

        studentNameTvObj.setText(item.getName());
        studentAgeTvObj.setText(String.valueOf(item.getAge()));
    }

    /*
    * 针对头部界面实现
    * */
    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {

        View studentHeadView= LayoutInflater.from(mContext).inflate(R.layout.studentheaderlayout,parent,false);
        return new StudentHeaderHolderView(studentHeadView);
    }


    public class StudentDetailHolderView extends RecyclerView.ViewHolder
    {
        private View headerView;

        public StudentDetailHolderView(View itemView) {
            super(itemView);
            headerView=itemView;
        }

        public View getHeaderView() {
            return headerView;
        }
    }

    /*
    * 针对头部数据绑定
    * */
    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
        StudentHeaderHolderView studentHeaderHolderView=(StudentHeaderHolderView)holder;
        ImageView studentHeadImage= (ImageView) studentHeaderHolderView.getHeaderView().findViewById(R.id.studentHeadImageViewId);
        studentHeadImage.setImageResource(R.mipmap.student);
    }


    public class StudentHeaderHolderView extends RecyclerView.ViewHolder
    {
        private View headerView;

        public StudentHeaderHolderView(View itemView) {
            super(itemView);
            headerView=itemView;
        }

        public View getHeaderView() {
            return headerView;
        }
    }
}
