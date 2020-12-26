package com.kosmo.jsonparser32_02;

public class JSONItem {
    private String name;
    private String age;
    private String hobbys;
    private String loginInfo;

    public JSONItem(String name, String age, String hobbys, String loginInfo) {
        this.name = name;
        this.age = age;
        this.hobbys = hobbys;
        this.loginInfo = loginInfo;
    }//////////생성자

    public String getName() {
        return name;
    }
    public String getAge() {
        return age;
    }
    public String getHobbys() {
        return hobbys;
    }
    public String getLoginInfo() {
        return loginInfo;
    }


}
