package com.liujinli.accountms.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liujinli.accountms.model.Tb_outaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OutaccountDAO {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public OutaccountDAO(Context context){
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}


	//添加支出信息
	public void add(Tb_outaccount tb_outaccount) {
		// 执行添加支出信息操作
		db.execSQL(
				"insert into tb_outaccount (_id,money,time,type,address,mark) values (?,?,?,?,?,?)",
				new Object[] { tb_outaccount.getid(), tb_outaccount.getMoney(),
						tb_outaccount.getTime(), tb_outaccount.getType(),
						tb_outaccount.getAddress(), tb_outaccount.getMark() });
	}


	//更新支出信息
	public void update(Tb_outaccount tb_outaccount) {
		// 执行修改支出信息操作
		db.execSQL(
				"update tb_outaccount set money = ?,time = ?,type = ?,address = ?,mark = ? where _id = ?",
				new Object[] { tb_outaccount.getMoney(),
						tb_outaccount.getTime(), tb_outaccount.getType(),
						tb_outaccount.getAddress(), tb_outaccount.getMark(),
						tb_outaccount.getid() });
	}


	//查找支出信息
	public Tb_outaccount find(int id) {
		Cursor cursor = db
				.rawQuery(
						"select _id,money,time,type,address,mark from tb_outaccount where _id = ?",
						new String[] { String.valueOf(id) });// 根据编号查找支出信息，并存储到Cursor类中
		if (cursor.moveToNext()){// 遍历查找到的支出信息
			// 将遍历到的支出信息存储到Tb_outaccount类中
			return new Tb_outaccount(
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("address")),
					cursor.getString(cursor.getColumnIndex("mark")));
		}
		cursor.close();
		return null;
	}


	//刪除支出信息
	public void detele(Integer... ids) {
		if (ids.length > 0){// 判断是否存在要删除的id
			StringBuffer sb = new StringBuffer();// 创建StringBuffer对象
			for (int i = 0; i < ids.length; i++){// 遍历要删除的id集合
				sb.append('?').append(',');// 将删除条件添加到StringBuffer对象中
			}
			sb.deleteCharAt(sb.length() - 1);// 去掉最后一个“,“字符
			// 执行删除支出信息操作
			db.execSQL("delete from tb_outaccount where _id in (" + sb + ")",
					(Object[]) ids);
		}
	}

	// 支出信息汇总
	public Map<String,Float> getTotal() {
		// 获取所有支出汇总信息
		Cursor cursor = db.rawQuery("select type,sum(money) from tb_outaccount group by type",null);
		int count=0;
		count=cursor.getCount();
		Map<String,Float> map=new HashMap<String,Float>();	//创建一个Map对象
		cursor.moveToFirst();	//移动第一条记录
		for(int i=0;i<count;i++){// 遍历所有的收入汇总信息
			map.put(cursor.getString(0),cursor.getFloat(1));
			System.out.println("支出："+cursor.getString(0)+cursor.getFloat(1));
			cursor.moveToNext();//移到下条记录
		}
		cursor.close();
		return map;
	}

	//获取支出信息
	public List<Tb_outaccount> getScrollData(int start, int count) {
		List<Tb_outaccount> tb_outaccount = new ArrayList<Tb_outaccount>();// 创建集合对象
		// 获取所有支出信息
		Cursor cursor = db.rawQuery("select * from tb_outaccount limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()){// 遍历所有的支出信息
			// 将遍历到的支出信息添加到集合中
			tb_outaccount.add(new Tb_outaccount(cursor.getInt(cursor
					.getColumnIndex("_id")), cursor.getDouble(cursor
					.getColumnIndex("money")), cursor.getString(cursor
					.getColumnIndex("time")), cursor.getString(cursor
					.getColumnIndex("type")), cursor.getString(cursor
					.getColumnIndex("address")), cursor.getString(cursor
					.getColumnIndex("mark"))));
		}
		cursor.close();
		return tb_outaccount;
	}


	//获取总记录数
	public long getCount() {
		Cursor cursor = db.rawQuery("select count(_id) from tb_outaccount",
				null);// 获取支出信息的记录数
		if (cursor.moveToNext()){// 判断Cursor中是否有数据
			return cursor.getLong(0);// 返回总记录数
		}
		cursor.close();// 关闭游标
		return 0;// 如果没有数据，则返回0
	}


	//获取支出最大编号
	public int getMaxId() {
		Cursor cursor = db.rawQuery("select max(_id) from tb_outaccount", null);// 获取支出信息表中的最大编号
		while (cursor.moveToLast()) {// 访问Cursor中的最后一条数据
			return cursor.getInt(0);// 获取访问到的数据，即最大编号
		}
		cursor.close();
		return 0;
	}
}
