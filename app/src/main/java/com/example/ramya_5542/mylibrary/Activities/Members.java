package com.example.ramya_5542.mylibrary.Activities;
public class Members {

    int member_id , mobile ;
    String firstName , email, userName , secondName , password;
    public Members(){

    }

    public Members(int mobile , String firstName ,String secondName, String email,String userName ){

        this.mobile=mobile;
        this.firstName=firstName;
        this.secondName=secondName;
        this.email=email;
        this.userName=userName;
    }


    public int getMember_id(){return this.member_id;}
    public void setMember_id(int member_id){this.member_id=member_id;}
    public int getMobile(){return this.mobile;}
    public void setMobile(int mobile){this.mobile=mobile;}
    public String getFirstName(){return this.firstName;}
    public void setFirstName(String firstName){this.firstName=firstName;}
    public String getSecondName(){return this.secondName;}
    public void setSecondName(String secondName){this.secondName= secondName;}
    public String getEmail(){return this.email;}
    public void setEmail(String email){this.email = email;}
    public String getUserName(){return  this.userName;}
    public  void setUserName(String userName){this.userName=userName;}

    public String getPassword(){return this.password;}
    public void setPassword(String password){this.password= password;}
}
