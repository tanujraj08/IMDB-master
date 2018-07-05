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

import Model.MoviesList;



public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private Context context;
    private List<MoviesList> moviesLists;
    public OnItemClickListener itemClickListener;

    public MovieListAdapter(Context context, List movieListItem){
        this.context = context;
        this.moviesLists = movieListItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {

        holder.movieTitle.setText(moviesLists.get(position).getTitle());
        holder.movieDescription.setText(moviesLists.get(position).getReleasedDate());

        ImageView imageView = holder.moviePoster;
        Glide.with(context).load("http://image.tmdb.org/t/p/w500" + moviesLists.get(position).getPosterPath()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return moviesLists.size();
    }

    public void setOnClickListener(OnItemClickListener itemClickListener){
       this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public TextView movieTitle;
        public TextView movieDescription;
        public RatingBar movieRatingBar;
        public ImageView moviePoster;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            movieTitle = (TextView)itemView.findViewById(R.id.movieTitleTextView);
            movieDescription = (TextView)itemView.findViewById(R.id.detailsDescriptionTextView);
            movieRatingBar = (RatingBar)itemView.findViewById(R.id.movieRatingBar);
            moviePoster = (ImageView)itemView.findViewById(R.id.moviePosterImageView);
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
