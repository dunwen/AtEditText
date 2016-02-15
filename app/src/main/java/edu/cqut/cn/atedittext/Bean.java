package edu.cqut.cn.atedittext;

/**
 * Created by dun on 2016/2/14.
 */
public class Bean implements At.atBean{
    String name;

    public Bean(String name) {
        this.name = name;
    }

    @Override
    public String showOnEditText() {
        return name;
    }
}
