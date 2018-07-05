package com.tanuj.imdb;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.MovieListAdapter;
import Model.MoviesList;
import Network.CallWebService;
import Network.NetworkStatus;
import Network.OnWebServiceResult;
import Util.CommonUtilities;

public class MainActivity extends AppCompatActivity implements OnWebServiceResult {

    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private List<MoviesList> moviesListList;
    private static String url = "http://api.themoviedb.org/3/movie/upcoming?api_key=8496be0b2149805afa458ab8ec27560c";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.movieListRecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getTitle().equals("Options")) return true;

        if (item.getTitle().equals(CommonUtilities.UpcomingMovies))
            url = "http://api.themoviedb.org/3/movie/upcoming?api_key=8496be0b2149805afa458ab8ec27560c";

        else if (item.getTitle().equals(CommonUtilities.LatestMovies))
            url = "http://api.themoviedb.org/3/movie/latest?api_key=8496be0b2149805afa458ab8ec27560c";

        else if (item.getTitle().equals(CommonUtilities.NowPlaying))
            url = "http://api.themoviedb.org/3/movie/now_playing?api_key=8496be0b2149805afa458ab8ec27560c";

        else if (item.getTitle().equals(CommonUtilities.MostPopular))
            url = "http://api.themoviedb.org/3/movie/popular?api_key=8496be0b2149805afa458ab8ec27560c";

        else if (item.getTitle().equals(CommonUtilities.TopRated))
            url = "http://api.themoviedb.org/3/movie/top_rated?api_key=8496be0b2149805afa458ab8ec27560c";


        getMovies();

        return true;
    }

    private void getMovies(){
        moviesListList = new ArrayList<>();

        // Create Object of FormEncodingBuilder.
        FormEncodingBuilder parameters= new FormEncodingBuilder();

        // Set Parameters
        //parameters.add("id", "123456");
        //parameters.add( "action", "get_contacts");

        // Check Whether Device Connected to Internet
        if(NetworkStatus.getInstance(this).isOnline(this)){
            CallWebService call = new CallWebService(this,url,parameters, CommonUtilities.SERVICE_TYPE.GET_DATA,this);
            // Show Progress Bar
            CommonUtilities.showLoading(this, "Please wait...", false);
            call.execute();
        }else{
            // Show Error Message if no network connection available
            Toast.makeText(this, "Please check your internet connection!!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getWebResponse(String result, CommonUtilities.SERVICE_TYPE type) {
        CommonUtilities.hideLoading();
        try {
            //Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            // Create JSon OBject from result object.
            JSONObject obj= new JSONObject(result);

            // Read Json Object from Json Array of index 0
            JSONArray arr= obj.getJSONArray("results");

            // Loop Through Result Array
            for(int i=0; i<arr.length(); i++){
                // Get Json OBject of the Array
                JSONObject jobj= arr.getJSONObject(i);

                String releaseDate = (jobj.get("release_date") == null ? "" : jobj.getString("release_date"));
                String posterPath = (jobj.get("poster_path") == null ? "" : jobj.getString("poster_path"));
                String backdropPath = (jobj.get("backdrop_path")== null ? "" : jobj.getString("backdrop_path"));

                // Create Object of Movie Details
                MoviesList handler= new MoviesList(jobj.getString("title"),jobj.getString("overview"),
                                         "Released On : " + releaseDate,
                                                    jobj.getInt("vote_count"),
                                                    jobj.getInt("id"), jobj.getBoolean("video"),
                                                    jobj.getDouble("vote_average"), jobj.getDouble("popularity"),
                                                    posterPath, jobj.getString("original_language"),
                                                    jobj.getString("original_title"), backdropPath,
                                                    jobj.getBoolean("adult"));

                moviesListList.add(handler);
            }

            // Crate CustomList Adapter Object and set to List.
            adapter= new MovieListAdapter(this, moviesListList);
            recyclerView.setAdapter(adapter);
            adapter.setOnClickListener(new MovieListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Toast.makeText(MainActivity.this, "Selected Position : " + String.valueOf(moviesListList.get(position).getID())  , Toast.LENGTH_SHORT).show();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                    }
                    Intent movieDetailsIntent = new Intent(MainActivity.this, MovieDetails.class);
                    Bundle movieInfoBundle = new Bundle();
                    movieInfoBundle.putString(String.valueOf(moviesListList.get(position).getID()),"selectedMovieID");
                    movieDetailsIntent.putExtra("selectedMovieID",String.valueOf(moviesListList.get(position).getID()));
                    startActivity(movieDetailsIntent);

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
