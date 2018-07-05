package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.*;

import com.tanuj.imdb.R;

import java.util.List;

import Model.MovieDetails;



public class MovieDetailsAdapter extends RecyclerView.Adapter<MovieDetailsAdapter.ViewHolder>  {

    private Context context;
    private List<MovieDetails> movieDetailsList;
    public MovieDetailsAdapter.OnItemClickListener itemClickListener;

    public MovieDetailsAdapter(Context context, List movieDetailsItem){
        this.context = context;
        this.movieDetailsList = movieDetailsItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_row, parent, false);

        return new MovieDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieDetailsAdapter.ViewHolder holder, int position) {

        holder.movieTitle.setText(movieDetailsList.get(position).getMovieTitle());
        holder.movieDescription.setText(movieDetailsList.get(position).getMovieDescription());
        holder.movieReleaseDate.setText(movieDetailsList.get(position).getReleaseDate());
        holder.movieRevenue.setText("Revenue : " + String.valueOf(movieDetailsList.get(position).getRevenue()));
        holder.movieBudget.setText("Budget : " + String.valueOf(movieDetailsList.get(position).getBudget()));
        holder.movieRatingBar.setRating(Float.parseFloat(movieDetailsList.get(position).getMovieRating()));

        ImageView imageView = holder.moviePoster;
        Glide.with(context).load("http://image.tmdb.org/t/p/w500" + movieDetailsList.get(position).getPosterPath()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return movieDetailsList.size();
    }

    public void setOnClickListener(MovieDetailsAdapter.OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView movieTitle;
        public TextView movieDescription;
        public RatingBar movieRatingBar;
        public ImageView moviePoster;
        public TextView movieReleaseDate;
        public TextView movieBudget;
        public TextView movieRevenue;


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            movieTitle = (TextView)itemView.findViewById(R.id.detailsTitleTextView);
            movieDescription = (TextView)itemView.findViewById(R.id.detailsDescrTextView);
            movieRatingBar = (RatingBar)itemView.findViewById(R.id.detailsRatingBar);
            moviePoster = (ImageView)itemView.findViewById(R.id.detailsPosterImageView);
            movieReleaseDate = (TextView)itemView.findViewById(R.id.detailsReleaseDateTextView);
            movieBudget = (TextView)itemView.findViewById(R.id.detailsBudgetTextView);
            movieRevenue = (TextView)itemView.findViewById(R.id.detailsRevenueTextView);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
