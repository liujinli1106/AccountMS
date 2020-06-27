package com.liujinli.accountms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.liujinli.accountms.R;
import android.app.Activity;
import com.liujinli.accountms.dao.InaccountDAO;
import com.liujinli.accountms.model.Tb_inaccount;

import java.util.List;

public class Inaccountinfo extends Activity {

    public static final String FLAG = "id";
    ListView lvinfo;
    String strType = "btnininfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inaccountinfo);

        lvinfo = findViewById(R.id.lvinaccountinfo);

        ShowInfo();

        lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String StrInfo = String.valueOf(((TextView) view).getText());
                String Strid = StrInfo.substring(0, StrInfo.indexOf('|'));
                Intent intent = new Intent(Inaccountinfo.this, InfoMange.class);
                intent.putExtra(FLAG, new String[]{Strid, strType});
                startActivity(intent);
            }
        });
    }

    private void ShowInfo() {
        String[] strInfos = null;
        InaccountDAO inaccountinfo = new InaccountDAO(Inaccountinfo.this);
        ArrayAdapter<String> arrayAdapter = null;// 创建ArrayAdapter对象

        List<Tb_inaccount> listinfos = inaccountinfo.getScrollData(0, (int) inaccountinfo.getCount());
        strInfos = new String[listinfos.size()];
        int m = 0;
        for (Tb_inaccount tb_inaccount : listinfos) {
            strInfos[m] = tb_inaccount.getid() + "|" + tb_inaccount.getType()
                    + " " + String.valueOf(tb_inaccount.getMoney()) + "元     "
                    + tb_inaccount.getTime();
            m++;
        }
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strInfos);
        lvinfo.setAdapter(arrayAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ShowInfo();
    }
}
