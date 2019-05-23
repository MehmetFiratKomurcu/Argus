package com.arkmfk.argus;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GetAdvertisement {
    Context ctx;
    Double latitude, longitude;

    public GetAdvertisement(Context c,String category, String company, Double lat, Double lon){
        String serverURL = "http://3.95.242.51/read_advertisements.php?Category="+
                category + "&CompanyName=" + company;
        ctx = c;
        latitude = lat;
        longitude = lon;
        new GetAdvertisement.LongOperation().execute(serverURL);
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
                    TextView thresholdStr = ((Activity) ctx).findViewById(R.id.threshold);
                    Double threshold = Double.parseDouble(thresholdStr.getText().toString());
                    List<ItemRow> itemList;
                    RecyclerView recyclerView;
                    recyclerView = (RecyclerView) ((Activity)ctx).findViewById(R.id.recylerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ctx));
                    itemList = new ArrayList<>();
                    int lengthJsonArr = jsonMainNode.length();
                    for(int i=0; i < lengthJsonArr; i++)
                    {
                        /****** Get Object for each JSON node.***********/
                        JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                        /******* Fetch node values **********/
                        String companyID = jsonChildNode.optString("CompanyID").toString();
                        String companyName = jsonChildNode.optString("CompanyName").toString();
                        String companyLocation = jsonChildNode.optString("CompanyLocation").toString();
                        String campaingContext = jsonChildNode.optString("CampaignContext").toString();
                        String campaignDeadline = jsonChildNode.optString("CampaignDeadline").toString();


                        String[] tokens = companyLocation.split(",");
                        Double parsedLatitude, parsedLongitude;
                        try{
                            parsedLatitude = Double.parseDouble(tokens[0]);
                            parsedLongitude = Double.parseDouble(tokens[1]);
                        }catch (Exception e){
                            e.printStackTrace();
                            parsedLatitude = 29.92;
                            parsedLongitude = 40.00;
                        }


                        Double lat2 = parsedLatitude;
                        Double long2 = parsedLongitude;
                        Double R = 6371e3;
                        Double f1 = Math.toRadians(latitude);
                        Double f2 = Math.toRadians(lat2);
                        Double deltafi = Math.toRadians(lat2 - latitude);
                        Double deltalambda = Math.toRadians(long2 - longitude);
                        Double a = Math.sin(deltafi/2) * Math.sin(deltafi/2) +
                                Math.cos(f1) * Math.cos(f2) *
                                        Math.sin(deltalambda/2) * Math.sin(deltalambda/2);
                        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                        Double d = R * c;

                        String formatted = new DecimalFormat("#########").format(d);
                        if(d > 999999999.0){
                            formatted = ">999999999";
                        }
                        if(d <= threshold) {
                            itemList.add(
                                    new ItemRow(
                                            Integer.parseInt(companyID),
                                            campaingContext,
                                            "Company: " + companyName,
                                            "Deadline: " + campaignDeadline,
                                            formatted));
                        }
                    }
                    ItemAdapter adapter = new ItemAdapter(ctx, itemList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
