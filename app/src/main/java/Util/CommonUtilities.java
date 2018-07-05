package Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;



// Create Ennum To define constatns
public class CommonUtilities {
    public static ProgressDialog dialog;

    public static final String MostPopular = "Most Popular";
    public  static final String UpcomingMovies = "Upcoming";
    public static final String LatestMovies = "Latest";
    public static final String NowPlaying = "Now Playing";
    public static final String TopRated = "Top Rated";

    public enum SERVICE_TYPE{
        GET_DATA,
        ERROR_REPORT
    }

    public static ProgressDialog showLoading(Context context, String message, boolean cancealable){
        dialog= new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setCancelable(cancealable);
        return dialog;
    }

    public static void hideLoading(){
        if(dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void showToast(Context context, String message){
        Toast toast= Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}