package com.liujinli.accountms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;

import com.liujinli.accountms.R;
import com.liujinli.accountms.dao.OutaccountDAO;
import com.liujinli.accountms.model.Tb_outaccount;

import java.util.List;

public class Outaccountinfo extends Activity {

    public static final String FLAG = "id";
    ListView lvinfo;
    String strType = "btnoutinfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.outaccountinfo);

        lvinfo = findViewById(R.id.lvoutaccountinfo);

        ShowInfo();

        lvinfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String strInfo = String.valueOf(((TextView) view).getText());
                String strid = strInfo.substring(0, strInfo.indexOf('|'));
                Intent intent = new Intent(Outaccountinfo.this, InfoMange.class);
                intent.putExtra(FLAG, new String[]{strid, strType});
                startActivity(intent);
            }
        });
    }

    private void ShowInfo() {
        String[] strInfos = null;
        ArrayAdapter<String> arrayAdapter = null;
        OutaccountDAO outaccountinfo = new OutaccountDAO(Outaccountinfo.this);
        List<Tb_outaccount> listoutinfos = outaccountinfo.
                getScrollData(0, (int) outaccountinfo.getCount());
        strInfos = new String[listoutinfos.size()];
        int i = 0;
        for (Tb_outaccount tb_outaccount : listoutinfos) {
            strInfos[i] = tb_outaccount.getid() + "|" + tb_outaccount.getType()
                    + " " + String.valueOf(tb_outaccount.getMoney()) + "元     "
                    + tb_outaccount.getTime();
            i++;
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
