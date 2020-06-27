package com.liujinli.accountms.model;

public class Tb_inaccount// 收入信息实体类
{
    private int _id;// 存储收入编号
    private double money;// 存储收入金额
    private String time;// 存储收入时间
    private String type;// 存储收入类别
    private String handler;// 存储收入付款方
    private String mark;// 存储收入备注

    public Tb_inaccount() {// 默认构造函数
        super();
    }

    public Tb_inaccount(int id, double money, String time, String type,
                        String handler, String mark) {
        super();
        this._id = id;
        this.money = money;
        this.time = time;
        this.type = type;
        this.handler = handler;
        this.mark = mark;
    }

    public int getid() {
        return _id;
    }

    public void setid(int id) {
        this._id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
