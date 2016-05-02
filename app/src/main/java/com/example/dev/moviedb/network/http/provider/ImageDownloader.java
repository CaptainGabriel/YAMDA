package com.example.dev.moviedb.network.http.provider;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.dev.moviedb.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This helper class download images from the Internet and binds those with the provided ImageView.
 *
 * Example: "new ImageDownload().download(imageUrl, imageView);"
 *
 * @version 0.0.1
 */
public class ImageDownloader {


    private final String TAG = Utils.makeLogTag(ImageDownloader.class);


    private AsyncTask task;

    private Bitmap placeholder;

    public ImageDownloader(Bitmap placeholder){
        this.placeholder = placeholder;
    }


    /**
     * Download the specified image from the Internet and binds it to the provided ImageView.
     *
     * NOTA: este método poderia não existir mas no futuro pretendemos implementar
     * "cache" de imagens obtidas, pelo que aqui seria um bom lugar para verificar se há
     * em cache ou se temos de fazer download.
     *
     * @param url The URL of the image to download.
     * @param imageView The ImageView to bind the downloaded image to.
     */
    public void download(String url, ImageView imageView) {

        if (url == null || url.isEmpty()) {
            imageView.setImageDrawable(null);
            return;
        }
        task = new BitmapDownloaderTask(imageView).execute(url);
    }

    public void cancelTask(){
        if(!task.isCancelled())
            task.cancel(true);
    }

    /**
     * The download routine.
     * Might block so execute async.
     *
     * @see HttpURLConnection
     *
     * @param imageUrl the url where the image resides
     * @return the bitmap downloaded
     */
    private Bitmap downloadBitmap(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Log.d(TAG, "Error occurred trying to fetch the bitmap from the url " + imageUrl);
            return placeholder;
        }
    }


    /**
     * The actual class that will asynchronously download the image.
     */
    class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {

        private final String TAG = Utils.makeLogTag(BitmapDownloaderTask.class);

        private String url;

        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<>(imageView);
        }

        /**{@inheritDoc}*/
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            return downloadBitmap(url);
        }

        /** {@inheritDoc} */
        @Override
        protected void onCancelled() {
            Log.d(TAG, "Canceled!");
        }

        /**{@inheritDoc}*/
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            //if not GC'ed insert picture.
            if (imageViewReference.get() != null) {
                imageViewReference.get().setImageBitmap(bitmap);
            }
        }
    }





}
