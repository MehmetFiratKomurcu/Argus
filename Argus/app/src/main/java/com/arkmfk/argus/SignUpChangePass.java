package com.arkmfk.argus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class SignUpChangePass {
    Context ctx;
    Boolean isUser = false;
    String isUserString = "false";

    public SignUpChangePass(Context c,String username, String password, String page){
        String serverURL = "http://3.95.242.51/" + page + ".php?username="+
                username + "&password=" + password;
        ctx = c;
        new SignUpChangePass.LongOperation().execute(serverURL);
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
            /*
            try{
                // Set Request parameter
                data +="&" + URLEncoder.encode("data", "UTF-8") + "="+serverText.getText();

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            */
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
                            Toast.makeText(ctx, "Operation successful.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(isUser == false){
                Toast.makeText(ctx, "Error.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
