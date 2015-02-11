package com.kairez.android.greatnurse;

public interface CallerInterface
{
    public void callBack(DialerResponse response);

    public enum DialerResponse
    {
        LOGIN_SUCCESSFUL, WRONG_CREDENTIALS, CONNECTION_ERROR, API_KEY_ERROR, INTERNAL_ERROR
    }
}
