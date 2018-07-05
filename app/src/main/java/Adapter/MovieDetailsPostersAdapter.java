package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.*;

import com.tanuj.imdb.R;

import java.util.List;

import Model.MoviePosters;


public class MovieDetailsPostersAdapter extends RecyclerView.Adapter<MovieDetailsPostersAdapter.ViewHolder> {

    private Context context;
    private List<MoviePosters> moviePostersList;
    public MovieDetailsPostersAdapter.OnItemClickListener itemClickListener;

    public MovieDetailsPostersAdapter(Context context, List moviePostersList){
        this.context = context;
        this.moviePostersList = moviePostersList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_image_row, parent, false);

        return new MovieDetailsPostersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieDetailsPostersAdapter.ViewHolder holder, int position) {
        ImageView imageView = holder.moviePosterImage;
        Glide.with(context).load("http://image.tmdb.org/t/p/w500" + moviePostersList.get(position).getPosterPath()).into(imageView);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        public ImageView moviePosterImage;

        public ViewHolder(View itemView) {
            super(itemView);

            moviePosterImage = (ImageView)itemView.findViewById(R.id.movieDetailsPosterImageView);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return moviePostersList.size();
    }

    public void setOnClickListener(MovieDetailsPostersAdapter.OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
