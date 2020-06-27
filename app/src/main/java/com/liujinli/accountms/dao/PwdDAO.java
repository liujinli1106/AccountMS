package com.liujinli.accountms.dao;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liujinli.accountms.model.Tb_pwd;

public class PwdDAO {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public PwdDAO(Context context){
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}


	//添加密码信息
	public void add(Tb_pwd tb_pwd) {
		// 执行添加密码操作
		db.execSQL("insert into tb_pwd (password) values (?)",
				new Object[] { tb_pwd.getPassword() });
	}

	//设置密码信息
	public void update(Tb_pwd tb_pwd) {
		// 执行修改密码操作
		db.execSQL("update tb_pwd set password = ?",
				new Object[] { tb_pwd.getPassword() });
	}

	//查找密码信息
	public Tb_pwd find() {
		// 查找密码并存储到Cursor类中
		Cursor cursor = db.rawQuery("select password from tb_pwd", null);
		if (cursor.moveToNext()){// 遍历查找到的密码信息
			// 将密码存储到Tb_pwd类中
			return new Tb_pwd(cursor.getString(cursor
					.getColumnIndex("password")));
		}
		cursor.close();
		return null;
	}

	//获取总记录数
	public long getCount() {
		Cursor cursor = db.rawQuery
				("select count(password) from tb_pwd", null);// 获取密码信息的记录数
		if (cursor.moveToNext()){// 判断Cursor中是否有数据
			return cursor.getLong(0);// 返回总记录数
		}
		cursor.close();
		return 0;
	}
}
