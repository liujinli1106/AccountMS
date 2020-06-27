package com.liujinli.accountms.model;

public class Tb_pwd// 密码数据表实体类
{
    private String password;// 定义字符串，表示用户密码

    public Tb_pwd() {// 默认构造函数
        super();
    }

    public Tb_pwd(String password) {
        super();
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
