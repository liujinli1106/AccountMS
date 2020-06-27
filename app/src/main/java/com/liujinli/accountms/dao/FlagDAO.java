package com.liujinli.accountms.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liujinli.accountms.model.Tb_flag;

import java.util.ArrayList;
import java.util.List;

public class FlagDAO {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public FlagDAO(Context context){
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}

	//添加便签信息
	public void add(Tb_flag tb_flag) {
		db.execSQL("insert into tb_flag (_id,time,flag) values (?,?,?)", new Object[] {
				tb_flag.getid(),tb_flag.getTime(), tb_flag.getFlag() });// 执行添加便签信息操作
	}

	//更新便签信息
	public void update(Tb_flag tb_flag) {
		db.execSQL("update tb_flag set flag = ? ,time=? where _id = ?", new Object[] {
				tb_flag.getFlag(),tb_flag.getTime(), tb_flag.getid() });// 执行修改便签信息操作
	}

	// 查找便签信息
	public Tb_flag find(int id) {
		Cursor cursor = db.rawQuery(
				"select _id,time,flag from tb_flag where _id = ?",
				new String[] { String.valueOf(id) });// 根据编号查找便签信息，并存储到Cursor类中
		if (cursor.moveToNext()){// 遍历查找到的便签信息
			// 将遍历到的便签信息存储到Tb_flag类中
			return new Tb_flag(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("flag")));
		}
		cursor.close();
		return null;
	}

	// 刪除便签信息
	public void detele(Integer... ids) {
		if (ids.length > 0){// 判断是否存在要删除的id
			StringBuffer sb = new StringBuffer();// 创建StringBuffer对象
			for (int i = 0; i < ids.length; i++){// 遍历要删除的id集合
				sb.append('?').append(',');// 将删除条件添加到StringBuffer对象中
			}
			sb.deleteCharAt(sb.length() - 1);// 去掉最后一个“,“字符
			// 执行删除便签信息操作
			db.execSQL("delete from tb_flag where _id in (" + sb + ")",
					(Object[]) ids);
		}
	}

	// 获取便签信息
	public List<Tb_flag> getScrollData(int start, int count) {
		List<Tb_flag> lisTb_flags = new ArrayList<Tb_flag>();// 创建集合对象
		// 获取所有便签信息
		Cursor cursor = db.rawQuery("select * from tb_flag limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()){// 遍历所有的便签信息
			// 将遍历到的便签信息添加到集合中
			lisTb_flags.add(new Tb_flag(cursor.getInt(cursor
					.getColumnIndex("_id")), cursor.getString(cursor
					.getColumnIndex("time")), cursor.getString(cursor
					.getColumnIndex("flag"))));
		}
		cursor.close();// 关闭游标
		return lisTb_flags;// 返回集合
	}


	//获取总记录数
	public long getCount() {
		Cursor cursor = db.rawQuery("select count(_id) from tb_flag", null);// 获取便签信息的记录数
		if (cursor.moveToNext()){// 判断Cursor中是否有数据
			return cursor.getLong(0);// 返回总记录数
		}
		cursor.close();// 关闭游标
		return 0;// 如果没有数据，则返回0
	}


	//获取便签最大编号
	public int getMaxId() {
		Cursor cursor = db.rawQuery("select max(_id) from tb_flag", null);// 获取便签信息表中的最大编号
		while (cursor.moveToLast()) {// 访问Cursor中的最后一条数据
			return cursor.getInt(0);// 获取访问到的数据，即最大编号
		}
		cursor.close();// 关闭游标
		return 0;// 如果没有数据，则返回0
	}
}
