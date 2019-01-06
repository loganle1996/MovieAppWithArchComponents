package com.example.myapp.movieapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.myapp.movieapp.dataOffline.Movie;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyBitMapFactory {

    public static Bitmap downloadBitMapImage(String urlPath) {
        InputStream inputStream = null;
        try {
            inputStream = (InputStream) new URL(urlPath).getContent();
            BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
