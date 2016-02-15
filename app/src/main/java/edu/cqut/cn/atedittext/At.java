package edu.cqut.cn.atedittext;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by dun on 2016/2/15.
 */
public class At {
    EditText editText;
    private ArrayList<Position> AtList = new ArrayList<>();
    private static final String AT_CHAR = "@";
    private static final String TAG = "At";
    AtImlp atImlp;
    whenAtEffectivePolicy policy = new whenAtEffectivePolicy() {
        @Override
        public boolean whenAtEffective(CharSequence str, int start, int before, int after) {
            char c = str.charAt(start-1);
            return !(c>='0'&&c<='9')||(c>='a'&&c<='z')||(c>='A'&&c<='Z');
        }
    };

    public At(EditText editText,AtImlp atImlp) {
        this.editText = editText;
        this.atImlp = atImlp;
        setEditText();
    }

    private void setEditText() {
        editText.addTextChangedListener(new TextWatcher() {
            String removeString = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Log.i(TAG, "beforeTextChanged: start>" + start + " count>" + count + " after>" + after);

                if (count - after == 1) {
                    String lastString = getLastString(s, start);
                    removeString = lastString;

                }

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.i(TAG, "onTextChanged: start>"+start+" count>"+count+" before>"+before);
                if (count - before == 1) {
                    if (start == 0||policy.whenAtEffective(s, start, before, count)) {
                        String lastString = getLastString(s, start);
                        if (lastString.equals(AT_CHAR)) {
                            if (atImlp != null) {
                                atImlp.whenEnterAt();
                            }
                        }
                    }

                } else if (before - count == 1 && removeString.equals(" ")) {
                    for (Position position : AtList) {
                        if (start == position.finish) {
                            Editable editable = editText.getText();
                            editable.delete(position.start, position.finish);
                            AtList.remove(position);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 设置什么时候@有效，什么时候@无效
     * */
    public void setPolicy(whenAtEffectivePolicy policy) {
        this.policy = policy;
    }

    /**
     * 获得光标位置输入的最后一个字符
     * */
    private String getLastString(CharSequence text, int index) {
        if (text.length() > 0 && index >= 0) {
            return text.subSequence(index, index + 1).toString();
        } else {
            return "";
        }
    }

    /**
     * 添加一个提到的实例
     * */
    public void addPosition(atBean b){
        int SelectionStart = editText.getSelectionStart();
        Editable editable = editText.getText();
        editable.insert(SelectionStart, b.showOnEditText() + " ");

        Position p = new Position(SelectionStart-1,SelectionStart+b.showOnEditText().length(),b);

        AtList.add(p);
    }

    /**
     * 获得已经提到的所有实体类
     * */
    public ArrayList<atBean> getAllBean(){
        ArrayList<atBean> list = new ArrayList<>();
        for (Position position : AtList) {
            list.add(position.bean);
        }
        return list;
    }




    /**
     * 用于在edittext中输入@符号后的动作
     * */
    public interface AtImlp{
        void whenEnterAt();
    }

    /**
     * 用本类的实体类必须实现本接口，
     * */
    public interface atBean{
        /**
         * 显示在editText中的文本
         * */
        String showOnEditText();
    }

    public interface whenAtEffectivePolicy{
        /**
         * true @有效，false 无效
         * @param str 改变后的字符串；
         * @param start 光标位置
         *
         * */
        boolean whenAtEffective(CharSequence str,int start,int before,int after);
    }

    /**
     * 首字母从0开始计数：
     *                   asdf
     *                   0123
     * 标记了每个提到的人（user）的文本在整个字符串中的位置，比如： 你好@123 hi， 则这个positon的start为2，finish为5，
     * */
    class Position {
        public int start = -1;
        public int finish = -1;
        public atBean bean;

        public Position(int start, int finish, atBean bean) {
            this.start = start;
            this.finish = finish;
            this.bean = bean;
        }

        @Override
        public String toString() {
            return "Position{" +
                    bean.showOnEditText() +
                    ", finish=" + finish +
                    ", start=" + start +
                    '}';
        }
    }



}
