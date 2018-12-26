package in.vnurture.myapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

import in.vnurture.myapplication.AsyncTasks.AsyncResponse;
import in.vnurture.myapplication.AsyncTasks.WebserviceCall;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    GoogleApiClient client;
    LocationRequest request;
    Button btn_search;
    LatLng latlangcureent;
    Geometry.Location.LocationModel locationModel;
    ArrayList<Geometry.Location.LocationModel.Result> stringArrayList =new ArrayList<Geometry.Location.LocationModel.Result>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btn_search=findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kk = String.valueOf(latlangcureent.latitude);
                String jj =String.valueOf(latlangcureent.longitude);

                //Toast.makeText(MapsActivity.this, ""+kk+"\n"+jj, Toast.LENGTH_SHORT).show();

               // StringBuilder stringBuilder=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
                //stringBuilder.append("location-"+latlangcureent.latitude+","+latlangcureent.longitude);
               // stringBuilder.append("&radius-"+1000);
               // stringBuilder.append("&type-"+"restaurant");
               // stringBuilder.append("&keyword-"+"cruise");
                String url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latlangcureent.latitude+","+latlangcureent.longitude+"&radius=1500&type=restaurant&keyword=cruise&key=AIzaSyCqd-mr3CzJBaKBbUh-8-JYFHI2Ga620Rw";
                //stringBuilder.append("&key-"+getResources().getString(R.string.google_places_key));

                //String url =stringBuilder.toString();
                Log.d("string",url);

                new WebserviceCall(MapsActivity.this, url, "", "Get Detail...!!", true, new AsyncResponse() {

                    @Override
                    public void onCallback(String response) {
                        Log.d("country_data",response);
                        locationModel = new Gson().fromJson(response,Geometry.Location.LocationModel.class);
                        if (locationModel.getStatus().equals("OK"))
                        {

                            Toast.makeText(MapsActivity.this, ""+response, Toast.LENGTH_SHORT).show();


                            for (int i =0; i<locationModel.getResults().size();i++)
                            {
                                //member_list.add(viewMemberModel.getUserDetails().get(i));

                                stringArrayList.add(locationModel.getResults().get(i));



                                String rs_name = stringArrayList.get(i).getName();
                                String vicinity =stringArrayList.get(i).getVicinity();



                                LatLng latLng= new LatLng(stringArrayList.get(i).getGeometry().getLocation().getLat(),stringArrayList.get(i).getGeometry().getLocation().getLng());
                                MarkerOptions markerOptions=new MarkerOptions();
                                markerOptions.title(rs_name);
                                markerOptions.position(latLng);

                                mMap.addMarker(markerOptions);

                            }

                            //LatLng latLng= new LatLng(locationModel.getResults().get(0).getGeometry().getLocation().getLat(),locationModel.getResults().get(0).getGeometry().getLocation().getLng());




                        }else {
                            //Toast.makeText(AddMemberActivity.this, ""+countryModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
                        //Toast.makeText(AddMemberActivity.this, ""+countryModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).execute();

/*
                Object datatransfer[]=new Object[2];
                datatransfer[0] = mMap;
                datatransfer[1]= url;

                GetNearbyPlaces getNearbyPlaces=new GetNearbyPlaces(this);
                getNearbyPlaces.execute(datatransfer);*/
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        client = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        client.connect();

       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        request = new LocationRequest().create();
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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
        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        if (location == null)
        {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
        }
        else
        {
            latlangcureent =new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate update=CameraUpdateFactory.newLatLngZoom(latlangcureent,15);
            mMap.animateCamera(update);

            MarkerOptions options=new MarkerOptions();
            options.position(latlangcureent);
            options.title("Current Location");
            mMap.addMarker(options);
        }

    }
}
