package teste.transaction.com.testtransaction;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.HttpResponseException;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;
import cz.msebera.android.httpclient.entity.FileEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.params.BasicHttpParams;

/**
 * Created by mainente on 30/06/16.
 */
public class Session {

    private static final int POST = 1;
    private static final int GET  = 2;
    private static Session instance = null;

    public static Session getInstance() {
        if (null == instance) {
            instance = new Session();
        }
        return instance;
    }




    public JSONObject httpSynchronousRequest(String URL,RequestParams requestParams) throws  Exception {

        String sLog = URL;


        final CountDownLatch signal = new CountDownLatch(1);
        final JSONObject[] response = new JSONObject[1];
        final Exception[] exception = new Exception[1];
        final boolean[] success = new boolean[1];




        SyncHttpClient client = new SyncHttpClient();

        client.post(URL,requestParams ,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject joResponse) {
                // Root JSON in response is an dictionary i.e { "data : [ ... ] }
                try {
                    String sJSONResponse = joResponse.toString(5);
                    if (sJSONResponse.length() > 1000) {
                        sJSONResponse = sJSONResponse.substring(0, 1000);
                    }
                    response[0] = joResponse;
                    success[0] = true;
                }
                catch (Exception e) {
                }
                signal.countDown();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }
        });
        return (JSONObject) response[0];





    }

}
