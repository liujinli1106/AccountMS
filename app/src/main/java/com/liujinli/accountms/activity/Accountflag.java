package com.liujinli.accountms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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
import android.app.Activity;

import java.util.Calendar;

public class Accountflag extends Activity {

    EditText txtFlag,txtflagTime;
    Button btnflagSaveButton;
    Button btnflagCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountflag);

        txtFlag = findViewById(R.id.txtFlag);
        txtflagTime = findViewById(R.id.txtFlagTime);
        btnflagSaveButton = findViewById(R.id.btnflagSave);
        btnflagCancelButton = findViewById(R.id.btnflagCancel);

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

        btnflagSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strFlag = txtFlag.getText().toString();

                if (!strFlag.isEmpty()) {
                    FlagDAO flagDAO = new FlagDAO(Accountflag.this);
                    Tb_flag tb_flag = new Tb_flag(flagDAO.getMaxId() + 1, txtflagTime.getText().toString(),strFlag);
                    flagDAO.add(tb_flag);
                    Toast.makeText(Accountflag.this, "〖新增便签〗数据添加成功！",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Accountflag.this, "请输入便签！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnflagCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFlag.setText("");
            }
        });
    }
    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(Accountflag.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Accountflag.this.txtflagTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
