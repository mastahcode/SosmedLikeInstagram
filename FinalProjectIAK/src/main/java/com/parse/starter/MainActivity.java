/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

  public void displayExeptionPesan(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }

  EditText usernameField;
  EditText passwordFiled;
  TextView loginTextView;
  Button registrasiButton;

  Boolean loginAktif;


  //method agar bisa pakai enter
  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {

    if(keyCode == KeyEvent.KEYCODE_ENTER){
      daftar(v);
    }

    return false;
  }

  @Override
  public void onClick(View v) {

    if (v.getId() == R.id.login) {
      if (loginAktif == true) {
        loginAktif = false;
        loginTextView.setText("Daftar");
        registrasiButton.setText("Log In");
      } else {
        loginAktif = true;
        loginTextView.setText("Logi In");
        registrasiButton.setText("Daftar");
      }
    }

  }

  public void daftar(View view) {

        /*Log.i("info", String.valueOf(usernameField.getText()));
        Log.i("info", String.valueOf(passwordFiled.getText()));*/

    if (loginAktif == true) {
      ParseUser user = new ParseUser();
      user.setUsername(String.valueOf(usernameField.getText()));
      user.setPassword(String.valueOf(passwordFiled.getText()));

      user.signUpInBackground(new SignUpCallback() {
        @Override
        public void done(ParseException e) {
          if (e == null) {
            Toast.makeText(getApplicationContext(), "Registrasi sukses", Toast.LENGTH_SHORT).show();
            showUserList();
          } else {
            Toast.makeText(getApplicationContext(), "Registrasi gagal", Toast.LENGTH_SHORT).show();
            displayExeptionPesan(e.getMessage().substring(e.getMessage().indexOf(" ")));
          }
        }
      });
    } else {
      ParseUser.logInInBackground(String.valueOf(usernameField.getText()), String.valueOf(passwordFiled.getText()), new LogInCallback() {
        @Override
        public void done(ParseUser user, ParseException e) {

          if (user != null) {
            Toast.makeText(getApplicationContext(), "Hore Login sukses jon", Toast.LENGTH_SHORT).show();
            showUserList();
          } else {
            displayExeptionPesan(e.getMessage());
          }

        }
      });
    }


  }

  public void showUserList(){
    Intent intent = new Intent(getApplicationContext(), UserList.class);
    startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if (ParseUser.getCurrentUser() != null){
        showUserList();
    }

    loginAktif = true;

    usernameField = (EditText) findViewById(R.id.username);
    passwordFiled = (EditText) findViewById(R.id.password);
    loginTextView = (TextView) findViewById(R.id.login);
    registrasiButton = (Button) findViewById(R.id.daftar);

    //untuk listener textView login/regis
    loginTextView.setOnClickListener(this);

    //biar bisa pakai enter
    usernameField.setOnKeyListener(this);
    passwordFiled.setOnKeyListener(this);


    ParseAnalytics.trackAppOpenedInBackground(getIntent());


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }



}
