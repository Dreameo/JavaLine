package com.yfh.qqzone.pojo;

import java.util.Date;

public class UserDetail {
    private Integer id;
    private String reaName;
    private String tel;
    private String email;
    private Date birth;
    private String star;

    /**
     * 父类：java.util.Date 年月日时分秒毫秒
     * 子类：java.sql.Date 年月日
     * 子类：java.sql.Time 时分秒
     */

    public UserDetail() {
    }

    public UserDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReaName() {
        return reaName;
    }

    public void setReaName(String reaName) {
        this.reaName = reaName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
