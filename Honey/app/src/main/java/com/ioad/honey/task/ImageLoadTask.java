package com.ioad.honey.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.net.URL;
import java.util.HashMap;

public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    private final String TAG = getClass().getSimpleName();

    private String urlStr;
    private ImageView imageView;
    private Bitmap bitmap;

    private static HashMap<String, Bitmap> bitmapHashMap = new HashMap<>();

    public ImageLoadTask() {
    }

    public ImageLoadTask(String urlStr, ImageView imageView) {
        this.urlStr = urlStr;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {

        try {
            if (bitmapHashMap.containsKey(urlStr)) {
                Bitmap oldBitmap = bitmapHashMap.remove(urlStr);
                if (oldBitmap != null && !oldBitmap.isRecycled()) {
                    oldBitmap.recycle();
                    oldBitmap = null;
                }
            }

            URL url = new URL(urlStr);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            bitmapHashMap.put(urlStr, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);
        imageView.invalidate();
    }


}
