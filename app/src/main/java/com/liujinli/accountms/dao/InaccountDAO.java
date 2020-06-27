package com.liujinli.accountms.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.liujinli.accountms.model.Tb_inaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InaccountDAO {
    private DBOpenHelper helper;
    private SQLiteDatabase db;

    public InaccountDAO(Context context) {
        helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }


    // 添加收入信息
    public void add(Tb_inaccount tb_inaccount) {
        // 执行添加收入信息操作
        db.execSQL(
                "insert into tb_inaccount (_id,money,time,type,handler,mark) "
                        + "values (?,?,?,?,?,?)",
                new Object[]{tb_inaccount.getid(), tb_inaccount.getMoney(),
                        tb_inaccount.getTime(), tb_inaccount.getType(),
                        tb_inaccount.getHandler(), tb_inaccount.getMark()});
    }


    //更新收入信息
    public void update(Tb_inaccount tb_inaccount) {
        // 执行修改收入信息操作
        db.execSQL(
                "update tb_inaccount set money = ?,time = ?,type = ?,handler = ?,"
                        + "mark = ? where _id = ?",
                new Object[]{tb_inaccount.getMoney(), tb_inaccount.getTime(),
                        tb_inaccount.getType(), tb_inaccount.getHandler(),
                        tb_inaccount.getMark(), tb_inaccount.getid()});
    }


    //查找收入信息
    public Tb_inaccount find(int id) {
        Cursor cursor = db
                .rawQuery(
                        "select _id,money,time,type,handler,mark from tb_inaccount"
                                + " where _id = ?",
                        new String[]{String.valueOf(id)});// 根据编号查找收入信息，并存储到Cursor类中
        if (cursor.moveToNext()) {// 遍历查找到的收入信息
            // 将遍历到的收入信息存储到Tb_inaccount类中
            return new Tb_inaccount(
                    cursor.getInt(cursor.getColumnIndex("_id")),
                    cursor.getDouble(cursor.getColumnIndex("money")),
                    cursor.getString(cursor.getColumnIndex("time")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("handler")),
                    cursor.getString(cursor.getColumnIndex("mark")));
        }
        cursor.close();
        return null;
    }

    //收入信息汇总
    public Map<String, Float> getTotal() {
        // 获取所有收入汇总信息
        Cursor cursor = db.rawQuery("select type,sum(money) "
                + "from tb_inaccount group by type", null);
        int count = 0;
        count = cursor.getCount();

        Map<String, Float> map = new HashMap<String, Float>();    //创建一个Map对象
        cursor.moveToFirst();    //移动第一条记录
        for (int i = 0; i < count; i++) {// 遍历所有的收入汇总信息
            map.put(cursor.getString(0), cursor.getFloat(1));
            System.out.println("收入：" + cursor.getString(0));
            cursor.moveToNext();//移到下条记录
        }
        cursor.close();// 关闭游标
        return map;// 返回Map对象
    }

    //刪除收入信息
	public void detele(Integer... ids) {
        if (ids.length > 0) {// 判断是否存在要删除的id
            StringBuffer sb = new StringBuffer();// 创建StringBuffer对象
            for (int i = 0; i < ids.length; i++) {// 遍历要删除的id集合
                sb.append('?').append(',');// 将删除条件添加到StringBuffer对象中
            }
            sb.deleteCharAt(sb.length() - 1);// 去掉最后一个“,“字符
            // 执行删除收入信息操作
            db.execSQL("delete from tb_inaccount where _id in (" + sb + ")",
                    (Object[]) ids);
        }
    }

    //获取收入信息
    public List<Tb_inaccount> getScrollData(int start, int count) {
        List<Tb_inaccount> tb_inaccount = new ArrayList<Tb_inaccount>();// 创建集合对象
//		db = helper.getWritableDatabase();// 初始化SQLiteDatabase对象
        // 获取所有收入信息
        Cursor cursor = db.rawQuery("select * from tb_inaccount limit ?,?",
                new String[]{String.valueOf(start), String.valueOf(count)});
        while (cursor.moveToNext()) {// 遍历所有的收入信息
            // 将遍历到的收入信息添加到集合中
            tb_inaccount.add(new Tb_inaccount(cursor.getInt(cursor
                    .getColumnIndex("_id")), cursor.getDouble(cursor
                    .getColumnIndex("money")), cursor.getString(cursor
                    .getColumnIndex("time")), cursor.getString(cursor
                    .getColumnIndex("type")), cursor.getString(cursor
                    .getColumnIndex("handler")), cursor.getString(cursor
                    .getColumnIndex("mark"))));
        }
        cursor.close();
        return tb_inaccount;
    }


    //获取总记录数
    public long getCount() {
        Cursor cursor = db
                .rawQuery("select count(_id) from tb_inaccount", null);// 获取收入信息的记录数
        if (cursor.moveToNext()) {// 判断Cursor中是否有数据
            return cursor.getLong(0);// 返回总记录数
        }
        cursor.close();
        return 0;
    }

    //获取收入最大编号
    public int getMaxId() {
        Cursor cursor = db.rawQuery("select max(_id) from tb_inaccount", null);// 获取收入信息表中的最大编号
        while (cursor.moveToLast()) {// 访问Cursor中的最后一条数据
            return cursor.getInt(0);// 获取访问到的数据，即最大编号
        }
        cursor.close();// 关闭游标
        return 0;// 如果没有数据，则返回0
    }
}
