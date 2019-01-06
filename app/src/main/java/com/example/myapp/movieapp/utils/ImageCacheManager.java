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
import java.util.List;

public class ImageCacheManager {
    public String saveImageToInternalStorage(Bitmap bitmapImage, Context context, int movieId) {
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
        File f = new File(imagePath);
        FileInputStream fileInputStream = new FileInputStream(f);
        return BitmapFactory.decodeStream(fileInputStream);
    }
}
