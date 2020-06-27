package com.liujinli.accountms.model;

public class Tb_flag// 便签信息实体类
{
    private int _id;// 存储便签编号
    private String time;// 存储便签记录时间
    private String flag;// 存储便签信息

    public Tb_flag() {
        super();
    }

    public Tb_flag(int id, String time, String flag) {
        super();
        this._id = id;
        this.time = time;
        this.flag = flag;
    }

    public int getid() {
        return _id;
    }

    public void setid(int id) {
        this._id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}