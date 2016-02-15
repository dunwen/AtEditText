package edu.cqut.cn.atedittext;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btn;
    private static final String TAG = "At";
    At at;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                sb.append("notify ");
                ArrayList<At.atBean> list = at.getAllBean();
                for (At.atBean atBean : list) {
                    sb.append(atBean.showOnEditText()+" ");
                }
                Toast.makeText(MainActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        setEditText();
    }

    private void setEditText() {
        final AlertDialog d = getDialog();

        editText = (EditText) findViewById(R.id.edit_text);
        at = new At(editText, new At.AtImlp() {
            @Override
            public void whenEnterAt() {
                d.show();
            }
        });
    }


    ArrayList<Bean> datelist;
    private AlertDialog getDialog() {
        initdate();


        View view = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();

        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(new TempAdapter(datelist, this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bean currentItem = datelist.get(position);
                at.addPosition(currentItem);
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private void initdate() {
        datelist = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datelist.add(new Bean("dun" + i));
        }
    }

}
