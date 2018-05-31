package org.esiea.alahyane_barres.youmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;

public class Movies extends AppCompatActivity {


    GetMoviesService.startActionMovie(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        IntentFilter intentFilter = new IntentFilter(MOVIES_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new MoviesUpdate(),intentFilter);
    }

    public static final String MOVIES_UPDATE = "com.esiea.alahyane_barres.youmovies.MOVIES_UPDATE";
    public class MoviesUpdate extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,getIntent().getAction());


    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.id., menu);
        return true;
    }

    public class NewMovies extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Intent : " + getIntent().getAction());
            JSONArray array = getMoviesFromFile();
            WeatherAdapter wa = (WeatherAdapter) recyclerView.getAdapter();
            wa.setNewWeather(array);
            Log.d("cont ", Integer.toString(array.length()));
        }
    }

    public JSONArray getMoviesFromFile(){
        try {

            InputStream is = new FileInputStream(getCacheDir() + "/" + "weather.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONObject(new String(buffer,"UTF-8")).getJSONArray("list");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}
