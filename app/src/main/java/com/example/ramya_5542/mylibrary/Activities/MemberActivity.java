package com.example.ramya_5542.mylibrary.Activities;


public class MemberActivity {
    int member_id , book_id;
    Boolean favorite, reading_status;
    String review;
    int time;
    public MemberActivity(int member_id ,int book_id,Boolean favorite,Boolean reading_status, String review , int time){
        this.member_id=member_id;
        this.book_id=book_id;
        this.favorite=favorite;
        this.reading_status=reading_status;
        this.review=review;
        this.time=time;

    }
    public MemberActivity(int member_id,int book_id){
        this.book_id=book_id;
        this.member_id= member_id;
    }
    public int getMember_id(){return this.member_id;}
    public void setMember_id(int member_id){this.member_id=member_id;}

    public int getBook_id(){return this.book_id;}
    public void setBook_id(int book_id){this.book_id=book_id;}

    public Boolean getFavorite() {return this.favorite;}
    public void setFavorite(Boolean favorite){this.favorite=favorite;}

    public Boolean getReading_status(){return  this.reading_status;}
    public void setReading_status(Boolean reading_status){this.reading_status=reading_status;}

    public String getReview(){return  this.review;}
    public void setReview(String review){this.review=review;}

    public int getTime(){return  this.time;}
    public void setTime(int time){this.time=time;}
}
