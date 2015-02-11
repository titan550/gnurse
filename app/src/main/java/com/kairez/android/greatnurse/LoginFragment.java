package com.kairez.android.greatnurse;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment implements CallerInterface
{
    private ProgressDialog mProgressDialog;
    private Button btnLogin;

    public LoginFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                login();
            }
        });
        return rootView;
    }

    private void login()
    {
        mProgressDialog = ProgressDialog.show(getActivity(), "Please wait...", "Checking email and password");
        btnLogin.setEnabled(false);

        EditText email = (EditText) getActivity().findViewById(R.id.email);
        EditText password = (EditText) getActivity().findViewById(R.id.password);

        Dialer dialer = new Dialer(this);
        dialer.login(email.getText().toString(), password.getText().toString());
    }
    public void callBack(DialerResponse result)
    {
        String message;

        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss(); //stop the progress dialog popup
            btnLogin.setEnabled(true); //re-enable the login button
        }
        if (result == DialerResponse.LOGIN_SUCCESSFUL)
        {
            message = "logged in successfully";
        }
        else if(result == DialerResponse.WRONG_CREDENTIALS)
        {
            message = "Invalid Credentials";
        }
        else
        {
            message = "Unknown Error";
        }

        Toast toast = Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}