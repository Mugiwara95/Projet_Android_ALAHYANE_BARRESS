package org.esiea.alahyane_barres.youmovies;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.support.v4.content.LocalBroadcastManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;

public class GetMoviesService extends IntentService {

    private static final String get_movies = "org.esiea.alahyane.youmovies.action.GetMoviesService";
    private static final String TAG = "GetMoviesServices";
    public GetMoviesService() {
        super("GetMoviesService");
    }

    public static void startActionMovie(Context context) {
        Intent intent = new Intent(context, GetMoviesService.class);
        intent.setAction(get_movies);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Movies.MOVIES_UPDATE.equals(action)) {
                handleActionMovies();

            }
        }
    }

    private void handleActionMovies() {
        Log.i(TAG, "Thread service name : " + Thread.currentThread().getName());
        URL url = null;

        try {
            url = new URL("https://api.themoviedb.org/4/list/1?api_key=5c78dc220e42e724f2e7b5571e172147");
            Log.i(TAG, "URL : " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            if (HttpsURLConnection.HTTP_OK == conn.getResponseCode()) {
                copyInputStreamToFile(conn.getInputStream(), new File(getCacheDir(), "movies.json"));
                Log.d(TAG, " Le fichier JSON a été téléchargé !");
                LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Movies.MOVIES_UPDATE));
            }else {
                Log.e(TAG, "CONNECTION ERROR" + conn.getResponseCode());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    private void copyInputStreamToFile(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
