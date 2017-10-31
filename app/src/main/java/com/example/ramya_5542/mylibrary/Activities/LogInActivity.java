package com.example.ramya_5542.mylibrary.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.ramya_5542.mylibrary.R;


public class LogInActivity extends AppCompatActivity implements OnClickListener{
    DataBase.DataBaseHandler dataBaseHandler;
    EditText editText_user,editText_pwd;
    Button button_login;
    TextView textView_signUp;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        if (isUserAlreadyLoggedIn()) {
            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            editText_user = (EditText) findViewById(R.id.edittext_username);
            editText_pwd = (EditText) findViewById(R.id.edittext_password);
            button_login = (Button) findViewById(R.id.button_login);
            textView_signUp = (TextView) findViewById(R.id.textview_forsignup);
            linearLayout= (LinearLayout)findViewById(R.id.linear_layout_login);
            button_login.setOnClickListener(this);
            textView_signUp.setOnClickListener(this);

        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.button_login:
                dataBaseHandler = new DataBase.DataBaseHandler(this);
                String userEdit = editText_user.getText().toString();
                String passwordEdit = editText_pwd.getText().toString();

                Boolean userNameCheck = dataBaseHandler.searchUserName(userEdit);
                String passwordCheck = dataBaseHandler.searchPassword(userEdit);
                if(userNameCheck)

                {
                    if (passwordEdit.equals(passwordCheck)&& !passwordEdit.isEmpty()) {
                        setLoginStatus(editText_user.getText().toString());
                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else if(passwordEdit.isEmpty()) {
                        Toast wrongPass = Toast.makeText(LogInActivity.this, "Please Enter the password", Toast.LENGTH_SHORT);
                         wrongPass.show();

                    }else {
                            Snackbar snackbar = Snackbar.make(linearLayout,"Wrong Password",Snackbar.LENGTH_SHORT);
                            snackbar.setAction("Try Again!",new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    // Handle user action
                                }

                            });
                        editText_pwd.setText("");
                       // snackbar.setActionTextColor(getResources().getColor(R.color.fabColor));
                        snackbar.setActionTextColor(ResourcesCompat.getColor(getResources(), R.color.fabColor, null));
                            snackbar.show();

                        }
                    }
                else{
                     editText_user.setError("Invalid User Name!");
                     editText_user.setText("");
                     editText_pwd.setText("");
                }
                break;
            case  R.id.textview_forsignup:
                Intent intent1= new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent1);
                break;

        }

    }

    private boolean isUserAlreadyLoggedIn() {
       SharedPreferences myPref = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
      return myPref.getBoolean("status",false);
    }

    private void setLoginStatus(String username) {
        SharedPreferences myPref = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editMyPref = myPref.edit();
        editMyPref.clear();
        editMyPref.putString("username",username);
        editMyPref.putBoolean("status",true);
        editMyPref.apply();
        editMyPref.commit();
    }

}
