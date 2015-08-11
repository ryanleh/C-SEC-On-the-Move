package com.app.csec_otm.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.app.csec_otm.R;

/**
 * Created by Ryan Lehmkuhl on 7/26/15.
 */
public class Login_Activity extends Activity {

    // TODO: remove after connecting to a real authentication system.

    //Hardcoded username and password
    private static final String DUMMY_EMAIL = "test@gmail.com";
    private static final String DUMMY_PASSWORD = "password";

    // TODO

    //Variables for email and password inputs
    private TextView mEmailView;
    private TextView mPasswordView;


    /**
     * Set variable to corresponding UI View and set up Listeners for Sign-In button and losing
     * focus from a TextView when background clicked
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View LinearLayout;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = (TextView) findViewById(R.id.email);
        mPasswordView = (TextView) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        LinearLayout = findViewById(R.id.background);
        LinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEmailView.clearFocus();
                mPasswordView.clearFocus();
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return false;
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        if (!isEmailValid(email) || TextUtils.isEmpty(email)) {
            mEmailView.setError(Html.fromHtml("<font color='red'>" + getString(R.string.email_incorrect) + "</font>"));
            focusView = mEmailView;
            cancel = true;
        }

        if (!isPasswordValid(password) || TextUtils.isEmpty(password)) {
            mPasswordView.setError(Html.fromHtml("<font color='red'>" + getString(R.string.password_incorrect) + "</font>"));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // Put focus onto the item that had an error
            focusView.requestFocus();
        } else {
            //starts Product View Activity
            Intent i = new Intent(Login_Activity.this, SingleFragmentActivity.class);
            Login_Activity.this.startActivity(i);
        }
    }


    // TODO: Redirect to background check or implement server authorizaiton here

    private boolean isEmailValid(String email) {
        return (email.equals(DUMMY_EMAIL) && email.contains("@"));
    }


    private boolean isPasswordValid(String password) {
        return (password.equals(DUMMY_PASSWORD));
    }

    // TODO



}
