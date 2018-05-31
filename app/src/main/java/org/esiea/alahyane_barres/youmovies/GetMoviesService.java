package org.esiea.alahyane_barres.youmovies;


public class GetMoviesService extends IntentService {

    private static final String get_movies = "org.esiea.alahyane.youmovies.action.GetMoviesService";



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
            if (get_movies.equals(action)) {
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
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(MainActivity.MOVIES_UPDATE));


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
