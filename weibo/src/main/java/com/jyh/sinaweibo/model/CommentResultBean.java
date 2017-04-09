package com.jyh.sinaweibo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cheng on 2016/12/23.
 */
public class CommentResultBean<T> implements IMobel<T>,Serializable
{
    private List<T> comments;

    private int total_number;
    private int previous_cursor;
    private String next_cursor;


    public List<T> getComments() {
        return comments;
    }

    public void setComments(List<T> comments) {
        this.comments = comments;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public int getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public String getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(String next_cursor) {
        this.next_cursor = next_cursor;
    }


    @Override
    public List<T> getList() {
        return this.comments;
    }

}
