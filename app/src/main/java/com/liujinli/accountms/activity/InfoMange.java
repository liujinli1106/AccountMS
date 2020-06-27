package com.liujinli.accountms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.liujinli.accountms.R;
import com.liujinli.accountms.dao.InaccountDAO;
import com.liujinli.accountms.dao.OutaccountDAO;
import com.liujinli.accountms.model.Tb_inaccount;
import com.liujinli.accountms.model.Tb_outaccount;

import java.util.Calendar;
import android.app.Activity;

public class InfoMange extends Activity {

    TextView tvtitle, textView;// 创建两个TextView对象
    EditText txtMoney, txtTime, txtHA, txtMark;// 创建4个EditText对象
    Spinner spType;// 创建Spinner对象
    Button btnEdit, btnDel;// 创建两个Button对象
    String[] strInfos;// 定义字符串数组
    String strid, strType;// 定义两个字符串变量，分别用来记录信息编号和管理类型

    OutaccountDAO outaccountDAO = null;
    InaccountDAO inaccountDAO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomange);

        outaccountDAO = new OutaccountDAO(InfoMange.this);
        inaccountDAO = new InaccountDAO(InfoMange.this);

        tvtitle = findViewById(R.id.inouttitle);
        textView = findViewById(R.id.tvInOut);
        txtMoney = findViewById(R.id.txtInOutMoney);
        txtTime = findViewById(R.id.txtInOutTime);
        txtHA = findViewById(R.id.txtInOut);
        txtMark = findViewById(R.id.txtInOutMark);
        spType = findViewById(R.id.spInOutType);
        btnEdit = findViewById(R.id.btnInOutEdit);
        btnDel = findViewById(R.id.btnInOutDelete);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        strInfos = bundle.getStringArray(Showinfo.FLAG);
        strid = strInfos[0];
        strType = strInfos[1];
        if (strType.equals("btnoutinfo")) {
            tvtitle.setText("支出管理");
            textView.setText("地点");
            Tb_outaccount tb_outaccount = outaccountDAO.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(tb_outaccount.getMoney()));
            txtTime.setText(tb_outaccount.getTime());
            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this, R.array.outtype, android.R.layout.simple_dropdown_item_1line);
            spType.setAdapter(adapter);
            spType.setPrompt(tb_outaccount.getType());
            txtHA.setText(tb_outaccount.getAddress());
            txtMark.setText(tb_outaccount.getMark());
        } else if (strType.equals("btnininfo")) {
            tvtitle.setText("收入管理");
            textView.setText("付款方：");
            Tb_inaccount tb_inaccount = inaccountDAO.find(Integer.parseInt(strid));
            txtMoney.setText(String.valueOf(tb_inaccount.getMoney()));
            txtTime.setText(tb_inaccount.getTime());
            ArrayAdapter adapter = ArrayAdapter.createFromResource(
                    this, R.array.intype, android.R.layout.simple_dropdown_item_1line);
            spType.setAdapter(adapter);
            spType.setPrompt(tb_inaccount.getType());
            txtHA.setText(tb_inaccount.getHandler());
            txtMark.setText(tb_inaccount.getMark());
        }

        txtTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        txtTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickDlg();
                }
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strType.equals("btnoutonfo")) {
                    Tb_outaccount tb_outaccount = new Tb_outaccount();
                    tb_outaccount.setid(Integer.parseInt(strid));// 设置编号
                    tb_outaccount.setMoney(Double.parseDouble(txtMoney
                            .getText().toString()));// 设置金额
                    tb_outaccount.setTime(txtTime.getText().toString());// 设置时间
                    tb_outaccount.setType(spType.getSelectedItem().toString());// 设置类别
                    tb_outaccount.setAddress(txtHA.getText().toString());// 设置地点
                    tb_outaccount.setMark(txtMark.getText().toString());// 设置备注
                    outaccountDAO.update(tb_outaccount);// 更新支出信息
                } else if (strType.equals("btnininfo")) {
                    Tb_inaccount tb_inaccount = new Tb_inaccount();// 创建Tb_inaccount对象
                    tb_inaccount.setid(Integer.parseInt(strid));// 设置编号
                    tb_inaccount.setMoney(Double.parseDouble(txtMoney.getText()
                            .toString()));// 设置金额
                    tb_inaccount.setTime(txtTime.getText().toString());// 设置时间
                    tb_inaccount.setType(spType.getSelectedItem().toString());// 设置类别
                    tb_inaccount.setHandler(txtHA.getText().toString());// 设置付款方
                    tb_inaccount.setMark(txtMark.getText().toString());// 设置备注
                    inaccountDAO.update(tb_inaccount);// 更新收入信息
                }
                Toast.makeText(InfoMange.this, "〖数据〗修改成功！", Toast.LENGTH_SHORT).show();
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (strType.equals("btnoutinfo")) {
                    outaccountDAO.detele(Integer.parseInt(strid));
                }else if (strType.equals("btnininfo")){
                    inaccountDAO.detele(Integer.parseInt(strid));
                }
                Toast.makeText(InfoMange.this, "〖数据〗删除成功！", Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(InfoMange.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                InfoMange.this.txtTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

}
