package com.example.ramya_5542.mylibrary.Activities;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramya_5542.mylibrary.R;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final Context context = this;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    TextView nav_user, nav_email;
    DataBase.DataBaseHandler dataBaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fragment_default = MyLibraryFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment_default);
        transaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        nav_user = (TextView) findViewById(R.id.nav_draw_name);
        nav_email = (TextView) findViewById(R.id.nav_draw_email);
        String login_user = getUserName();
        dataBaseHandler = new DataBase.DataBaseHandler(this);
        Members member = dataBaseHandler.getMemberData(login_user, this);
        nav_user.setText(member.getFirstName());
        nav_email.setText(member.getEmail());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (drawer.getDrawerLockMode(GravityCompat.START)
                != DrawerLayout.LOCK_MODE_LOCKED_CLOSED
                && toggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment frag = null;
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();


        if (id == R.id.nav_mylibrary) {
            frag = MyLibraryFragment.newInstance();


        } else if (id == R.id.nav_booklist) {
            frag = BookSearchFragment.newInstance();

        } else if (id == R.id.nav_mylist) {
            frag = MyListFragment.newInstance();

        } else if (id == R.id.nav_profile) {
            frag= MyProfileFragment.newInstance();

        } else if (id == R.id.nav_logout) {
            final Dialog dialog = new Dialog(context);
            dialog.setTitle("Alert Dialog");
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.logout_dialog);
            Button dialogOk = (Button) dialog.findViewById(R.id.button_ok);
            Button dialogNotNow = (Button) dialog.findViewById(R.id.button_notnow);
            dialogOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resetLoginStatus();
                    Intent intent = new Intent(HomeActivity.this, LogInActivity.class);
                    startActivity(intent);
                    dialog.cancel();
                    finish();
                }
            });
            dialogNotNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "continue", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    drawer.closeDrawer(GravityCompat.START);


                }
            });
            dialog.show();


        }

        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        if (frag != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, frag);
            transaction.commit();

            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
        return false;
    }

    private void resetLoginStatus() {
        SharedPreferences myPref = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editMyPref = myPref.edit();
        editMyPref.clear();
        editMyPref.apply();
        editMyPref.commit();
    }

    private String getUserName() {
        SharedPreferences myPref = getSharedPreferences("loginStatus", Context.MODE_PRIVATE);
        return myPref.getString("username", "notfound");

    }
    /*
    public void setNavigationDrawerState(boolean isEnabled) {
        if (isEnabled) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            toggle.setDrawerIndicatorEnabled(false);
            // the extra line of code goes here
           toggle.setHomeAsUpIndicator(R.drawable.ic_left_arrow);

            toggle.syncState();
        }

    }
    */


    public void setDrawerState(boolean isEnabled) {
        if (isEnabled) {
            toggle.setDrawerIndicatorEnabled(true);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
            getSupportActionBar().setHomeButtonEnabled(true);
        } else {
            toggle.setDrawerIndicatorEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.START);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
        toggle.syncState();

    }

}
