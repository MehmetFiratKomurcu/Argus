package com.arkmfk.argus;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class GPS extends AppCompatActivity {

    static Double lat = 0.0;
    TextView lat_tb, long_tb, company_tb, threshold;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        spinner = (Spinner) findViewById(R.id.spinner);
        List list = new ArrayList();
        list.add("All Categories");
        list.add("Food");
        list.add("Electronics");
        list.add("Cosmetic");
        list.add("Clothing");

        lat_tb = (TextView) findViewById(R.id.lat_tb);
        long_tb = (TextView) findViewById(R.id.long_tb);
        company_tb = (TextView) findViewById(R.id.company_tb);
        threshold = (TextView) findViewById(R.id.threshold);

        ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        final Button location_btn = (ToggleButton) findViewById(R.id.location_btn);
        location_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    boolean on = ((ToggleButton) v).isChecked();
                    if (on) {
                        Toast.makeText(GPS.this, "ON", Toast.LENGTH_SHORT).show();
                        getLocation();
                        getRealTimeLocation();
                    } else {
                        Toast.makeText(GPS.this, "OFF", Toast.LENGTH_SHORT).show();
                        locationMngr.removeUpdates(locationLstnr);
                    }
            }
        });
        final ImageButton ok_btn = (ImageButton) findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String latStr = lat_tb.getText().toString();
                String longStr = long_tb.getText().toString();
                boolean isNumeric = true;
                Double latNum = null, longNum = null;
                try{
                    latNum = Double.parseDouble(latStr);
                    longNum = Double.parseDouble(longStr);
                }catch (NumberFormatException e){
                    isNumeric = false;
                }
                if(isNumeric){
                    Toast.makeText(GPS.this, " GPS Coordinates OK.", Toast.LENGTH_SHORT).show();
                    if(spinner.getSelectedItem().toString()
                            .equalsIgnoreCase("all categories")){
                        new GetAdvertisement(GPS.this, "",
                                company_tb.getText().toString(), latNum, longNum);
                    }else{
                        new GetAdvertisement(GPS.this, spinner.getSelectedItem().toString(),
                                company_tb.getText().toString(), latNum, longNum);
                    }
                }else{
                    Toast.makeText(GPS.this, "Please enter valid " +
                            "GPS coordinates.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //createCardView();
    }

    List<ItemRow> itemList;
    RecyclerView recyclerView;

    public void createCardView(){
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //new GetAdvertisement(this, "","");
        //initializing the productlist
        itemList = new ArrayList<>();
        //adding some items to our list
        itemList.add(
                new ItemRow(
                        1,
                        "Apple MacBook Air Core i5 5th Gen - (8 GB/128 GB SSD/Mac OS Sierra)",
                        "13.3 inch, Silver, 1.35 kg",
                        "20.05.2019",
                        "aa"));

        //Toast.makeText(GPS.this, " GPS Coordinates OK.", Toast.LENGTH_SHORT).show();
        //creating recyclerview adapter
        ItemAdapter adapter = new ItemAdapter(this, itemList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }

    public void getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double lat,lon;
        try {
            lat = location.getLatitude();
            lon = location.getLongitude();
            final TextView lat_lb = (TextView) findViewById(R.id.lat_tb);
            lat_lb.setText(lat.toString());
            final TextView long_lb = (TextView) findViewById(R.id.long_tb);
            long_lb.setText(lon.toString());
        }catch(Exception e){
            e.printStackTrace();
            getRealTimeLocation();
        }

    }

    LocationManager locationMngr;
    LocationListener locationLstnr;
    public void getRealTimeLocation(){
        Context ctx = this;
        locationMngr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationLstnr = new MyLocationListener((Activity) ctx, spinner, company_tb, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationMngr.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000, 0, locationLstnr);

    }
}


class MyLocationListener implements LocationListener {

    TextView lat_lb = null, company_tb;
    TextView long_lb = null;
    TextView tb_3 = null;
    Double latitude = 0.0;
    Double longitude = 0.0;
    Spinner spinner;
    Context ctx;

    MyLocationListener(Activity a, Spinner spx, TextView ctb, Context c){
        lat_lb = a.findViewById(R.id.lat_tb);
        long_lb = a.findViewById(R.id.long_tb);
        tb_3 = a.findViewById(R.id.textView3);
        spinner = spx;
        company_tb = ctb;
        ctx = c;
    }

    @Override
    public void onLocationChanged(Location loc) {
        longitude = loc.getLongitude();
        latitude = loc.getLatitude();
        lat_lb.setText(latitude.toString());
        long_lb.setText(longitude.toString());

        Double lat2 = 30.0;
        Double long2 = 25.0;
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
        tb_3.setText(d.toString());

        if(spinner.getSelectedItem().toString()
                .equalsIgnoreCase("all categories")){
            new GetAdvertisement(ctx, "",
                    company_tb.getText().toString(), latitude, longitude);
        }else{
            new GetAdvertisement(ctx, spinner.getSelectedItem().toString(),
                    company_tb.getText().toString(), latitude, longitude);
        }

        /*------- To get city name from coordinates -------- */
        /*
        String cityName = null;
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;
        editLocation.setText(s);*/
    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}