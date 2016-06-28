package hu.ait.android.moneyexchangerates.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import hu.ait.android.moneyexchangerates.MainActivity;

public class HttpGetTask extends AsyncTask<String, Void, String> {
    public static final String KEY_RESULT = "KEY_RESULT";
    private Context context;

    public HttpGetTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();

            conn.setConnectTimeout(100000);
            conn.setReadTimeout(100000);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                int ch;
                StringBuffer sb = new StringBuffer();
                while ((ch = is.read()) != -1) {
                    sb.append((char) ch);
                }
                result = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = e.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Intent i = new Intent(MainActivity.ACTION_RATES_RESULT);
        i.putExtra(KEY_RESULT, result);

        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }
}