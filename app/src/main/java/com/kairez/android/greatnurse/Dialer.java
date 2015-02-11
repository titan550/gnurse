package com.kairez.android.greatnurse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Dialer
{
    private static final String BASE_URL = "http://192.168.9.50/gnurse/v1";
    private ProgressDialog mProgressDialog;
    private Activity mActivity;
    private CallerInterface caller;

    Dialer(CallerInterface caller)
    {
        this.caller = caller;
    }

    private void startProgressDialog(String message)
    {
        mProgressDialog = ProgressDialog.show(mActivity, "Loading...", message);
    }

    private void stopProgressDialog()
    {

    }

    private void showAlert(String message)
    {
        Context context = mActivity.getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    public void login(String email, String password)
    {
        boolean result;

        LoginApi loginApi = new LoginApi();
        loginApi.execute(email, password);
    }

    private class LoginApi extends AsyncTask<String, Void, CallerInterface.DialerResponse>
    {
        public final String LOG_TAG = LoginApi.class.getName();

        @Override
        protected CallerInterface.DialerResponse doInBackground(String... params)
        {
            //Sleep only to fake real world delay
            try
            {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }

            if (params.length == 0)
            {
                return CallerInterface.DialerResponse.INTERNAL_ERROR;
            }

            String email = params[0];
            String password = params[1];
            String responseJson = new String();

            final String URL = BASE_URL + "/login";
            final String EMAIL_PARAM = "email";
            final String PASSWORD_PARAM = "password";

            List postParams = new ArrayList<NameValuePair>(2);
            postParams.add(new BasicNameValuePair(EMAIL_PARAM, email));
            postParams.add(new BasicNameValuePair(PASSWORD_PARAM, password));

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL);

            try
            {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
                httpPost.setEntity(new UrlEncodedFormEntity(postParams));
            } catch (UnsupportedEncodingException e)
            {
                e.printStackTrace();
                return CallerInterface.DialerResponse.INTERNAL_ERROR;
            }


            try
            {
                HttpResponse response = httpClient.execute(httpPost);
                if(response.getStatusLine().getStatusCode() == 200)
                {
                    responseJson = EntityUtils.toString(response.getEntity());
                    Log.i(LOG_TAG, responseJson);
                    return CallerInterface.DialerResponse.LOGIN_SUCCESSFUL;
                }
                else
                {
                    Log.e(LOG_TAG, "HTTP Status Coder Error: " + response.getStatusLine().getStatusCode());
                    return CallerInterface.DialerResponse.CONNECTION_ERROR;
                }
            } catch (ClientProtocolException e)
            {
                e.printStackTrace();
                return CallerInterface.DialerResponse.CONNECTION_ERROR;
            } catch (IOException e)
            {
                e.printStackTrace();
                return CallerInterface.DialerResponse.CONNECTION_ERROR;
            }
        }

        @Override
        protected void onPostExecute(CallerInterface.DialerResponse dialerResponse)
        {
            super.onPostExecute(dialerResponse);
            caller.callBack(dialerResponse);
        }
    }

}