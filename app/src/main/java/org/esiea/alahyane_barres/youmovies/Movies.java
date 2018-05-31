package org.esiea.alahyane_barres.youmovies;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Movies extends AppCompatActivity {

    public static final String MOVIES_UPDATE = "org.esiea.alahyane_barres.youmovies.MOVIES_UPDATE";
    private final String TAG = "Movies";
    private RecyclerView recyclerView;
    IntentFilter intentFilter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        recyclerView = findViewById(R.id.movie_item);
        RecyclerView rv_movies = (RecyclerView)findViewById(R.id.movie_item);
        rv_movies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        MoviesAdapter ma = new MoviesAdapter(getMoviesFromFile());

        recyclerView.setAdapter(ma);

        GetMoviesService.startActionMovie(this);
        IntentFilter intentFilter = new IntentFilter(MOVIES_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new MoviesUpdate(), intentFilter);


    }


    public class MoviesUpdate extends BroadcastReceiver {
        MoviesAdapter ma = new MoviesAdapter(getMoviesFromFile());
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, intent.getAction());
            Toast.makeText(getApplicationContext(), R.string.notif_text, Toast.LENGTH_SHORT).show();
            ma.setNewMovies(getMoviesFromFile());
        }
    }


    private class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesHolder> {

        private JSONArray movies;

        public MoviesAdapter(JSONArray movies) {
            this.movies = movies;
        }

        @Override
        public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            MoviesHolder mh = new MoviesHolder(inflater.inflate(R.layout.activity_movies, parent, false));
            return mh;
        }

        @Override
        public void onBindViewHolder(MoviesHolder holder, int position) {
            try {
                holder.name.setText(movies.getJSONObject(position).get("title").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return movies.length();
        }


        public class MoviesHolder extends RecyclerView.ViewHolder {
            TextView name;

            public MoviesHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.movie_item);
            }
        }

        public void setNewMovies(JSONArray movies) {
            this.movies = movies;
            notifyDataSetChanged();
        }
    /*@Override
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
            org.esiea.alahyane_barres.youmovies.MoviesAdapter wa = (org.esiea.alahyane_barres.youmovies.MoviesAdapter) recyclerView.getAdapter();
            wa.setNewMovies(array);
            Log.d("cont ", Integer.toString(array.length()));
        }
    }*/


    }
    public JSONArray getMoviesFromFile() {
        try {
            InputStream in = new FileInputStream(getCacheDir() + "/" + "movies.json");
            byte[] buf = new byte[in.available()];
            in.read(buf);
            in.close();
            return new JSONArray(new String(buf, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }
}

