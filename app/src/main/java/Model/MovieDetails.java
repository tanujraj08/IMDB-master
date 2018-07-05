package Model;



public class MovieDetails {
    private String movieTitle;
    private String movieRating;
    private String movieDescription;
    private String releaseDate;
    private double budget;
    private double revenue;
    private String status;
    private String posterPath;

    public MovieDetails(String movieTitle, String movieRating, String movieDescription, String releaseDate, double budget, double revenue, String status, String posterPath) {
        this.movieTitle = movieTitle;
        this.movieRating = movieRating;
        this.movieDescription = movieDescription;
        this.releaseDate = releaseDate;
        this.budget = budget;
        this.revenue = revenue;
        this.status = status;
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
