package com.rsmouki.zed.tp3.log;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class SignupPage extends AppCompatActivity {
    private static final String TAG = "signUpActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText _userNameText ;
    EditText _passwordText ;
    EditText _verpasswordText ;
    Button _signupButton ;
    TextView _signinLink ;

    private String userName ;
    private String password ;
    static User user= new User();
    private static final String DB_NAME = "user_bd";

    MyDatabase INSTANCE;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        _userNameText = (EditText)findViewById(R.id.input_username) ;
        _passwordText = (EditText)findViewById(R.id.input_password);
        _verpasswordText = (EditText) findViewById(R.id.input_password_ver);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _signinLink = (TextView) findViewById(R.id.link_login);

        INSTANCE = Room.databaseBuilder(this,
                MyDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
        _signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                signup(v);
            }
        });

        _signinLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void signup(View v) {
        Log.d(TAG, "signup");

        if (!validate()) {
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupPage.this,
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

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        progressDialog.dismiss();
                        finish();
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
        _signupButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
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

        if ((user.pass.length() < 4 || user.pass.length() > 10)) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        if(_passwordText.equals(_verpasswordText))
        {
            _passwordText.setError("not the same password");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    boolean checkDatabase()
    {
        Log.d(TAG, user.login + user.pass);
        List<User> usr =
                INSTANCE.userDao().getUser(user.login);
        for(User us : usr) {
            if((user.login.equals(us.login))&&(user.pass.equals(us.pass)))
            {
                Toast.makeText(this,"this account is already exist",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        INSTANCE.userDao().adduser(user);
        return true;
    }
}
