package com.arkmfk.argus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

public class SignInControl {
    Context ctx;
    Boolean isUser = false;
    String isUserString = "false";

    public SignInControl(Context c,String username, String password){
        String serverURL = "http://3.95.242.51/read_users.php?username="+
                username + "&password=" + password;
        ctx = c;
        new LongOperation().execute(serverURL);
    }

    public Boolean getIsUser(){
        return isUser;
    }

    public String getIsUserString(){
        return isUserString;
    }

    private class LongOperation  extends AsyncTask<String, Void, Void> {
        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        private String Error = null;
        private ProgressDialog Dialog = new ProgressDialog(ctx);
        String data ="";
        int sizeData = 0;

        protected void onPreExecute() {
            Dialog.setMessage("Please wait..");
            Dialog.show();
        }

        protected Void doInBackground(String... urls) {
            BufferedReader reader=null;
            try
            {
                URL url = new URL(urls[0]);

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write( data );
                wr.flush();

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = reader.readLine()) != null)
                {
                    sb.append(line + " ");
                }

                Content = sb.toString();
            }
            catch(Exception ex)
            {
                Error = ex.getMessage();
            }
            finally
            {
                try
                {
                    reader.close();
                }

                catch(Exception ex) {}
            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            // Close progress dialog
            Dialog.dismiss();
            if (Error != null) {
            } else {
                String OutputData = "";
                JSONObject jsonResponse;
                try {

                    /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
                    jsonResponse = new JSONObject(Content);

                    /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
                    /*******  Returns null otherwise.  *******/
                    JSONArray jsonMainNode = jsonResponse.optJSONArray("records");

                    /*********** Process each JSON Node ************/

                    int lengthJsonArr = jsonMainNode.length();

                    for(int i=0; i < lengthJsonArr; i++)
                    {
                        /****** Get Object for each JSON node.***********/
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                        /******* Fetch node values **********/
                        String name       = jsonChildNode.optString("Status").toString();
                        if(name.equalsIgnoreCase("true")){
                            isUser = true;
                            Toast.makeText(ctx, "Signed in successfully.", Toast.LENGTH_SHORT).show();
                            Intent itd = new Intent(ctx, GPS.class);
                            ctx.startActivity(itd);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(isUser == false){
                Toast.makeText(ctx, "Username or password incorrect.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
