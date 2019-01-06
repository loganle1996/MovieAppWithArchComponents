package com.example.myapp.movieapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.myapp.movieapp.dataOffline.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class MovieDownloadTask extends AsyncTask<Movie, Void, Bitmap> {
    private Movie movie;
    private Context context;
    private RecyclerView.ViewHolder mHolder;

    public void setViewHolder(RecyclerView.ViewHolder holder) {
        mHolder = holder;
    }

    public MovieDownloadTask(Context context, Movie movie, RecyclerView.ViewHolder viewHolder) {
        this.context = context;
        this.movie = movie;
        this.mHolder = viewHolder;
    }

    @Override
    protected Bitmap doInBackground(Movie... movies) {
        movie = movies[0];
        String imageUrl = movie.getPosterPath();
        Log.d("Logan", imageUrl);
        InputStream in = null;
        try {
            in = (InputStream) new URL(imageUrl).getContent();
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            Log.e("Logan", "doInBackground: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
