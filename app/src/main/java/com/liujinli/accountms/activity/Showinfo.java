package com.liujinli.accountms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.liujinli.accountms.R;
import com.liujinli.accountms.dao.FlagDAO;
import com.liujinli.accountms.model.Tb_flag;
import android.app.Activity;


import java.util.List;

public class Showinfo extends Activity {
    public static final String FLAG = "id";
    Button btnoutinfo, btnininfo, btnflaginfo;
    ListView lvinfo;
    String strType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showinfo);

        lvinfo = findViewById(R.id.lvinfo);
        btnoutinfo = findViewById(R.id.btnoutinfo);
        btnininfo = findViewById(R.id.btnininfo);
        btnflaginfo = findViewById(R.id.btnflaginfo);

        btnoutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowInfo(R.id.btnoutinfo);
            }
        });
        btnininfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowInfo(R.id.btnininfo);
            }
        });
        btnflaginfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowInfo(R.id.btnflaginfo);
            }
        });

        lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String strInfo = String.valueOf(((TextView) view).getText());
                String strid = strInfo.substring(0, strInfo.indexOf('|'));
                Intent intent = null;
                if (strType == "btnflaginfo") {
                    intent = new Intent(Showinfo.this, FlagManage.class);
                    intent.putExtra(FLAG, strid);
                    startActivity(intent);
                }
            }
        });

    }

    private void ShowInfo(int intType) {
        String[] strInfos = null;
        ArrayAdapter<String> arrayAdapter = null;
        Intent intent = null;
        switch (intType) {
            case R.id.btnoutinfo:
                strType = "outinfo";
                intent = new Intent(Showinfo.this, TotalChart.class);// 使用TotalChart窗口初始化Intent对象
                intent.putExtra("passType", strType);// 设置要传递的数据
                startActivity(intent);
                break;
            case R.id.btnininfo:
                strType = "ininfo";
                intent = new Intent(Showinfo.this, TotalChart.class);// 使用TotalChart窗口初始化Intent对象
                intent.putExtra("passType", strType);// 设置要传递的数据
                startActivity(intent);
                break;
            case R.id.btnflaginfo:
                strType = "btnflaginfo";
                FlagDAO flaginfo = new FlagDAO(Showinfo.this);
                List<Tb_flag> listFlags = flaginfo.getScrollData(0,
                        (int) flaginfo.getCount());
                strInfos = new String[listFlags.size()];
                int n = 0;
                for (Tb_flag tb_flag : listFlags) {
                    strInfos[n] = tb_flag.getid() + "|" +tb_flag.getTime()+"|"+ tb_flag.getFlag();
                    if (strInfos[n].length() > 30)
                        strInfos[n] = strInfos[n].substring(0, 30) + "……";
                    n++;
                }

                arrayAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, strInfos);
                lvinfo.setAdapter(arrayAdapter);
                break;
        }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        ShowInfo(R.id.btnflaginfo);    }
}
