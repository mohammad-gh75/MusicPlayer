package org.maktab36.musicplayer.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class PictureUtils {

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);

        int srcWidth = bmOptions.outWidth;
        int srcHeight = bmOptions.outHeight;

        int scaleFactor = Math.max(1, Math.min(srcWidth / destWidth, srcHeight / destHeight));

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(path, bmOptions);
    }

    public static Bitmap getScaledBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaledBitmap(path, size.x, size.y);
    }

    public static Bitmap getScaledBitmap(byte[] byteCover, int destWidth, int destHeight) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteCover, 0, byteCover.length, bmOptions);

        int srcWidth = bmOptions.outWidth;
        int srcHeight = bmOptions.outHeight;

        int scaleFactor = Math.max(1, Math.min(srcWidth / destWidth, srcHeight / destHeight));

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        return BitmapFactory.decodeByteArray(byteCover, 0, byteCover.length, bmOptions);
    }

    public static Bitmap getScaledBitmap(byte[] byteCover, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int x = metrics.widthPixels;
        int y = metrics.heightPixels;
        return getScaledBitmap(byteCover, x, y);
    }
}
