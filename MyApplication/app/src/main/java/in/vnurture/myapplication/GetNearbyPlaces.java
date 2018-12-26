package in.vnurture.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetNearbyPlaces extends AsyncTask<Object,String,String> {

    GoogleMap googleMap;
    InputStream inputStream;
    StringBuilder stringBuilder;
    BufferedReader bufferedReader;
    String url,data;
    Context context;

    public GetNearbyPlaces(View.OnClickListener onClickListener) {
    }


    @Override
    protected String doInBackground(Object... objects) {
        googleMap =(GoogleMap)objects[0];
        url=(String)objects[1];

        try {
            URL myurl =new URL(url);
            HttpURLConnection httpURLConnection=(HttpURLConnection)myurl.openConnection();
            httpURLConnection.connect();
            inputStream=httpURLConnection.getInputStream();
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

            String line ="";
            stringBuilder=new StringBuilder();

            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }

            data=stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {

        Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();

        try {
            JSONObject parentobject = new JSONObject(s);
            JSONArray jsonArray =parentobject.getJSONArray("results");

            for (int i =0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                JSONObject locationobject =jsonObject.getJSONObject("geometry").getJSONObject("location");

                String latitut =locationobject.getString("lat");
                String longitud =locationobject.getString("lng");

                JSONObject nameobject =jsonArray.getJSONObject(i);
                String rs_name = nameobject.getString("name");
                String vicinity =nameobject.getString("vicinity");

                LatLng latLng= new LatLng(Double.parseDouble(latitut),Double.parseDouble(longitud));

                MarkerOptions options=new MarkerOptions();
                options.title(vicinity);
                options.position(latLng);

                googleMap.addMarker(options);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }
}
