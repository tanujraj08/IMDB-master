package com.tanuj.imdb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.MovieDetailsAdapter;
import Network.CallWebService;
import Network.NetworkStatus;
import Network.OnWebServiceResult;
import Util.CommonUtilities;



public class MovieDetails extends AppCompatActivity implements OnWebServiceResult {

    private RecyclerView recyclerView;
    private MovieDetailsAdapter adapter;
    private String movieID = "";
    private List<Model.MovieDetails> movieDetailsList;
    private String url = "http://api.themoviedb.org/3/movie/" + movieID + "?api_key=8496be0b2149805afa458ab8ec27560c";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //Bundle movieInfo = getIntent().getExtras();
        //Toast.makeText(this, String.valueOf(movieInfo), Toast.LENGTH_SHORT).show();
        movieID = getIntent().getStringExtra("selectedMovieID");
        Toast.makeText(this, String.valueOf(movieID), Toast.LENGTH_SHORT).show();

        url = "http://api.themoviedb.org/3/movie/" + movieID + "?api_key=8496be0b2149805afa458ab8ec27560c";

        recyclerView = (RecyclerView)findViewById(R.id.movieDetailsRecyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getMovieDetails();
    }

    private void getMovieDetails(){
        movieDetailsList = new ArrayList<>();

        // Create Object of FormEncodingBuilder.
        FormEncodingBuilder parameters= new FormEncodingBuilder();

        // Set Parameters
        //parameters.add("id", "123456");
        //parameters.add( "action", "get_contacts");

        // Check Whether Device Connected to Internet
        if(NetworkStatus.getInstance(this).isOnline(this)){
            Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
            CallWebService call = new CallWebService(this,url,parameters, CommonUtilities.SERVICE_TYPE.GET_DATA,null,this);
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

            String releaseDate = (obj.get("release_date") == null ? "" : obj.getString("release_date"));
            String posterPath = (obj.get("poster_path") == null ? "" : obj.getString("poster_path"));
            //String backdropPath = (jobj.get("backdrop_path")== null ? "" : jobj.getString("backdrop_path"));
            double budget = (obj.get("budget")== null ? 0.00 : obj.getDouble("budget"));
            String status = (obj.get("status")== null ? "" : obj.getString("status"));
            double revenue = (obj.get("revenue")== null ? 0.00 : obj.getDouble("revenue"));

            // Create Object of Movie Details
            Model.MovieDetails detailsRow = new Model.MovieDetails(obj.getString("title"),
                    String.valueOf(obj.getDouble("vote_average")),
                    obj.getString("overview"), releaseDate, budget,revenue, status, posterPath);

            movieDetailsList.add(detailsRow);
            Toast.makeText(this, detailsRow.getMovieTitle(), Toast.LENGTH_SHORT).show();

            // Crate CustomList Adapter Object and set to List.
            adapter= new MovieDetailsAdapter(this, movieDetailsList);
            recyclerView.setAdapter(adapter);
            //adapter.setOnClickListener(new MovieListAdapter.OnItemClickListener() {
                //@Override
                //public void onItemClick(View view, int position) {
                    //Toast.makeText(this, "Selected Position : " + movieDetailsList.get(position).getMovieDescription(), Toast.LENGTH_SHORT).show();
                    //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                     //   ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
                    //}
                    //startActivity(new Intent(this, MovieDetails.class));

                //}
            //});

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
