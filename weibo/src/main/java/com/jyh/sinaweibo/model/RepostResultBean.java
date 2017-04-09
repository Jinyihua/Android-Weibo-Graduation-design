package com.jyh.sinaweibo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cheng on 2016/12/24.
 */
public class RepostResultBean<T> implements IMobel<T>,Serializable
{

    private List<T> reposts;
    private int total_number;
    private String next_cursor;

    public List<T> getReposts() {
        return reposts;
    }

    public void setReposts(List<T> reposts) {
        this.reposts = reposts;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public String getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(String next_cursor) {
        this.next_cursor = next_cursor;
    }

    @Override
    public List<T> getList() {
        return this.reposts;
    }


}