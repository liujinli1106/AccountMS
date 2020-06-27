package com.liujinli.accountms.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import com.liujinli.accountms.R;
import com.liujinli.accountms.dao.InaccountDAO;
import com.liujinli.accountms.model.Tb_inaccount;

import java.util.Calendar;

public class AddInaccount extends Activity {

    EditText txtInMoney, txtInTime, txtInHandler, txtInMark;
    Spinner spInType;
    Button btnInSaveButton, btnInCancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinaccount);

        txtInMoney = findViewById(R.id.txtInMoney);
        txtInTime = findViewById(R.id.txtInTime);
        txtInHandler = findViewById(R.id.txtInHandler);
        txtInMark = findViewById(R.id.txtInMark);
        spInType = findViewById(R.id.spInType);
        btnInSaveButton = findViewById(R.id.btnInSave);
        btnInCancelButton = findViewById(R.id.btnInCancel);

        txtInTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg();
                    return true;
                }
                return false;
            }
        });

        txtInTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickDlg();
                }
            }
        });

        btnInSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strInMoney = txtInMoney.getText().toString();
                if (!strInMoney.isEmpty()) {
                    InaccountDAO inaccountDAO = new InaccountDAO(AddInaccount.this);
                    Tb_inaccount tb_inaccount = new Tb_inaccount(inaccountDAO.getMaxId() + 1,
                            Double.parseDouble(strInMoney), txtInTime.getText().toString(), spInType.getSelectedItem().toString(),
                            txtInHandler.getText().toString(), txtInMark.getText().toString());
                    inaccountDAO.add(tb_inaccount);
                    Toast.makeText(AddInaccount.this, "增加收入信息成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddInaccount.this, "请输入收入金额！", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnInCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtInMoney.setText("");
                txtInMoney.setHint("0.00");
                txtInTime.setText("");
                txtInTime.setHint("2020-06-01");
                txtInHandler.setText("");
                txtInMark.setText("");
                spInType.setSelection(0);
            }
        });

    }

    protected void showDatePickDlg() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddInaccount.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AddInaccount.this.txtInTime.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
