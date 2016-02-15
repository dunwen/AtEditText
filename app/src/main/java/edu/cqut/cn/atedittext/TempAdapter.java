package edu.cqut.cn.atedittext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dun on 2016/2/14.
 */
public class TempAdapter extends BaseAdapter {
    ArrayList<Bean> list;
    Context c;

    public TempAdapter(ArrayList<Bean> list, Context c) {
        this.list = list;
        this.c = c;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(c).inflate(R.layout.item,null);
        TextView mTextView = (TextView) view.findViewById(R.id.textview);
        Bean b = list.get(position);
        mTextView.setText(b.name);
        return view;
    }
}
