package com.example.myapp.movieapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ImageCacheManager {
    public static String saveImageToInternalStorage(Bitmap bitmapImage, Context context, int movieId) {
        String fileSavingImages = String.valueOf(movieId);
        File imageFile = new File(context.getFilesDir(), fileSavingImages);
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(imageFile);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imageFile.getAbsolutePath();
    }

    public static Bitmap getBitMapFromStorage(String imagePath) throws FileNotFoundException {
        File f = null;
        try {
            f = new File(imagePath);
        } catch (Exception e) {
            Log.e("Logan", "getBitMapFromStorage: " + e.getMessage());
        }
        FileInputStream fileInputStream = new FileInputStream(f);
        return BitmapFactory.decodeStream(fileInputStream);
    }

    public static Bitmap downloadBitMapImage(String urlPath) {
        InputStream inputStream = null;
        try {
            inputStream = (InputStream) new URL(urlPath).getContent();
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            Log.e("Logan", e.getMessage());
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
