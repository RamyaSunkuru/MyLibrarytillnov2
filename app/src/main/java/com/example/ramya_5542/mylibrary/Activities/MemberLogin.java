package com.example.ramya_5542.mylibrary.Activities;

public class MemberLogin {
    int member_id;
    String userName, password;
    public MemberLogin(){}
    public MemberLogin(int member_id,String userName,String password){
        this.member_id= member_id;
        this.userName=userName;
        this.password=password;
    }

    public MemberLogin(String userName,String password){

        this.userName=userName;
        this.password=password;
    }
    public int getMember_id(){return this.member_id;}
    public void setMember_id(int member_id){this.member_id=member_id;}

    public String getUserName(){return  this.userName;}
    public  void setUserName(String userName){this.userName=userName;}

    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password= password;}
}
