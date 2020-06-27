package com.liujinli.accountms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.liujinli.accountms.R;
import com.liujinli.accountms.dao.FlagDAO;
import com.liujinli.accountms.model.Tb_flag;

import java.util.Calendar;
import android.app.Activity;


public class FlagManage extends Activity {

    EditText txtFlag, txtflagTime;
    Button btnEdit, btnDel;
    String strid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flagmanage);

        txtFlag = findViewById(R.id.txtFlagManage);
        txtflagTime = findViewById(R.id.txtFlagTime);
        btnEdit = findViewById(R.id.btnFlagManageEdit);
        btnDel = findViewById(R.id.btnFlagManageDelete);


        txtflagTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        txtflagTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickDlg();
                }
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        strid = bundle.getString(Showinfo.FLAG);

        final FlagDAO flagDAO = new FlagDAO(FlagManage.this);
        txtFlag.setText(flagDAO.find(Integer.parseInt(strid)).getFlag());
        txtflagTime.setText(flagDAO.find(Integer.parseInt(strid)).getTime());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tb_flag tb_flag = new Tb_flag();
                tb_flag.setid(Integer.parseInt(strid));
                tb_flag.setTime(txtflagTime.getText().toString());
                tb_flag.setFlag(txtFlag.getText().toString());
                flagDAO.update(tb_flag);
                Toast.makeText(FlagManage.this, "〖便签数据〗修改成功！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagDAO.detele(Integer.parseInt(strid));
                Toast.makeText(FlagManage.this, "〖便签数据〗删除成功！",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(FlagManage.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                FlagManage.this.txtflagTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
