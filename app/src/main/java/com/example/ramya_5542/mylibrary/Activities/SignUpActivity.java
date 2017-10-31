package com.example.ramya_5542.mylibrary.Activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ramya_5542.mylibrary.R;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    public DataBase.DataBaseHandler dataBaseHandler;
    EditText et_username,et_create_pwd,et_reenter_pwd,et_firstname,et_secondname,et_mobile,et_email;
    Button button_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        et_username      =(EditText) findViewById(R.id.editText_username);
        et_firstname     =(EditText) findViewById(R.id.editText_firstname);
        et_secondname    =(EditText) findViewById(R.id.editText_secondname);
        et_email         =(EditText) findViewById(R.id.editText_email);
        et_mobile        =(EditText) findViewById(R.id.editText_mobileno);
        et_create_pwd=(EditText) findViewById(R.id.editText_createpassword);
        et_reenter_pwd=(EditText) findViewById(R.id.editText_reenterpassword);
        button_register =(Button)findViewById(R.id.button_register);
        button_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.button_register:
                String userName = et_username.getText().toString();
                String firstName = et_firstname.getText().toString();
                String secondName ;
                String email = et_email.getText().toString();
                String mobile;
                String pass1str = et_create_pwd.getText().toString();
                String pass2str = et_reenter_pwd.getText().toString();
                if(TextUtils.isEmpty(userName)){
                    et_username.setError("This field cannot be blank");
                    return;
                }

                if(TextUtils.isEmpty(email)){

                    et_email.setError("please enter email");
                    return;
                }
                if(TextUtils.isEmpty(firstName)){

                    et_firstname.setError("please enter your First name");
                    return;
                }
                if(TextUtils.isEmpty(et_secondname.getText().toString())){
                    secondName = " ";
                } else {
                   secondName = et_secondname.getText().toString();
                }
                if(TextUtils.isEmpty(et_mobile.getText().toString())){
                    mobile = " ";
                } else {
                    mobile = et_mobile.getText().toString();
                }
                dataBaseHandler = new DataBase.DataBaseHandler(this);
               boolean duplicate_userName = dataBaseHandler.searchUserName(userName);
                if(!duplicate_userName){
                    if(!pass1str.equals(pass2str))
                    {
                        Toast pass = Toast.makeText(SignUpActivity.this,"Passwords doesnt match",Toast.LENGTH_SHORT);
                        pass.show();
                        break;
                    }
                    else{

                        dataBaseHandler = new DataBase.DataBaseHandler(this);
                       Uri responseUri = dataBaseHandler.addMemberDetailTable(new Members(Integer.parseInt(mobile),firstName,secondName,email,userName),this);
                        if(Integer.parseInt(responseUri.getLastPathSegment()) > 0) {
                            int  memId = dataBaseHandler.getMemberIdWithUri(userName,this);
                           // Toast.makeText(this,"MemId " + memId, Toast.LENGTH_SHORT).show();
                            dataBaseHandler.addLogInDetails(new MemberLogin(memId,userName, pass1str));
                        }
                        // it gets the row number for that particular table

                       /* ContentValues contentValues= new ContentValues();
                        contentValues.put(DataBase.USER_NAME,userName);
                        contentValues.put(DataBase.FIRSTNAME,firstName);
                        contentValues.put(DataBase.SECONDNAME,secondName);
                        contentValues.put(DataBase.EMAIL,email);
                        contentValues.put(DataBase.MOBILE,mobile);
                        getContentResolver().insert(m ,contentValues);
                        //Uri.parse("content://com.example.ramya_5542.mylibrary.com.example.ramya_5542.mylibrary.Activities.DataBase/MyLibraryData/Members_Details_Table")*/
                        Intent intent = new Intent(this,LogInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    et_username.setError("User Name already exists");
                    Toast pass = Toast.makeText(SignUpActivity.this,"User Name already exists!",Toast.LENGTH_SHORT);
                    pass.show();
                    break;
                }




        }

    }

}
