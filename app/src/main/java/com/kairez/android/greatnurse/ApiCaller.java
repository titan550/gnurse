package com.kairez.android.greatnurse;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

public class ApiCaller extends AsyncTask<String, String, String>
{
    public final String LOG_TAG = ApiCaller.class.getName();

    @Override
    protected String doInBackground(String... params)
    {
        if (params.length == 0)
        {
            return null;
        }

        String email = params[0];
        String password = params[1];
        String responseJson = new String();

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("10.10.126.198")
                .appendPath("gnurse")
                .appendPath("v1")
                .appendPath("login");

        final String BASE_URL = "http://10.10.126.198/gnurse/v1/login";
        final String EMAIL_PARAM = "email";
        final String PASSWORD_PARAM = "password";

        List postParams = new ArrayList<NameValuePair>(2);
        postParams.add(new BasicNameValuePair(EMAIL_PARAM, email));
        postParams.add(new BasicNameValuePair(PASSWORD_PARAM, password));

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("http://10.10.126.198/gnurse/v1/login");

        try
        {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
            httpPost.setEntity(new UrlEncodedFormEntity(postParams));
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }


        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == 200)
            {
                responseJson = EntityUtils.toString(response.getEntity());
                Log.i(LOG_TAG, responseJson);
            }
            else
            {
                Log.e(LOG_TAG, "HTTP Status Coder Error: " + response.getStatusLine().getStatusCode());
            }
        } catch (ClientProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result)
    {
        super.onPostExecute(result);
    }
}
