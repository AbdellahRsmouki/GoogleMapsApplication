package com.rsmouki.zed.tp3.log;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rsmouki.zed.tp3.MainActivity;
import com.rsmouki.zed.tp3.R;
import com.rsmouki.zed.tp3.db.MyDatabase;
import com.rsmouki.zed.tp3.db.User;

import java.util.List;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final String DB_NAME = "user_bd";

    EditText _userNameText ;
    EditText _passwordText ;
    Button _loginButton ;
    TextView _signupLink ;
    MyDatabase INSTANCE;

    static User user= new User();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _userNameText = (EditText)findViewById(R.id.input_userName) ;
        _passwordText = (EditText)findViewById(R.id.input_Password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);

        Log.d(TAG, "creating database");
        INSTANCE = Room.databaseBuilder(this,
                MyDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { login(v); }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupPage.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login(View v) {
        Log.d(TAG, "Login");

        if (!validate()) {
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        if(!checkDatabase())
        {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }, 1000);
            return;
        }

        User.mainUser.setLogin(user.login);
        User.mainUser.setPass(user.pass);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        startActivity(intent);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        finish();
                        progressDialog.dismiss();
                    }
                }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                Log.d("starting homePage","starting homepage");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

        public boolean validate() {
            boolean valid = true;

            user.login = _userNameText.getText().toString();
            user.pass = _passwordText.getText().toString();

            if (user.login.length() < 4 || user.login.length() > 15 ) {
                _userNameText.setError("enter a valid User name ");
                valid = false;
            } else {
                _userNameText.setError(null);
            }

            if (user.pass.length() < 4 || user.pass.length() > 10) {
                _passwordText.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                _passwordText.setError(null);
            }

            return valid;
        }

        boolean checkDatabase()
        {
            Log.d(TAG, "checkdatabase");

            List<User> usr =
                    INSTANCE.userDao().getUser(user.login);
            Log.d(TAG, "userlist");
            for(User us : usr) {
                Log.d(TAG, us.login+ us.pass);
                if((user.login.equals(us.login))&&(user.pass.equals(us.pass)))
                {
                    return true;
                }

            }
            return false;
        }


}