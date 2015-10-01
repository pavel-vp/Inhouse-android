package com.mobileme.photolocator.api;

import java.io.Serializable;

/**
 * Created by User on 31.08.2015.
 */
public class HSProfileInfo implements Serializable {


    private boolean checked;
    private boolean moderated;
    private String name;
    private String nikname;
    private String phone;
    private String address;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isModerated() {
        return moderated;
    }

    public void setModerated(boolean moderated) {
        this.moderated = moderated;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNikname() {
        return nikname;
    }

    public void setNikname(String nikname) {
        this.nikname = nikname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HSProfileInfo{" +
                "checked=" + checked +
                ", moderated=" + moderated +
                ", name='" + name + '\'' +
                ", nikname='" + nikname + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
