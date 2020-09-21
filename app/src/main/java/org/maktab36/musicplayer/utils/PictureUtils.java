package org.maktab36.musicplayer.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

        Bitmap bitmap=BitmapFactory.decodeFile(path, bmOptions);
        return bitmap;
    }

    /*public static Bitmap getScaledBitmap(String path, ImageView imageView) {
        Point size = new Point();
//        activity.getWindowManager().getDefaultDisplay().getSize(size);
        size.x=imageView.getWidth();
        size.y=imageView.getHeight();
        return getScaledBitmap(path, size.x, size.y);
    }*/

    public static Bitmap getScaledBitmap(byte[] byteCover,int destWidth, int destHeight){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteCover,0,byteCover.length,bmOptions);

        int srcWidth = bmOptions.outWidth;
        int srcHeight = bmOptions.outHeight;

        int scaleFactor = Math.max(1, Math.min(srcWidth / destWidth, srcHeight / destHeight));

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        Bitmap bitmap=BitmapFactory.decodeByteArray(byteCover,0,byteCover.length,bmOptions);
        return bitmap;
    }
}
