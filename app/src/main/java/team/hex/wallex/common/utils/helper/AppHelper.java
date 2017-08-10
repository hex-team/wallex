package team.hex.wallex.common.utils.helper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import team.hex.wallex.api.model.Photo;
import team.hex.wallex.application.AppController;
import team.hex.wallex.common.utils.ModernAsyncTask;

/**
 * Created by alireza on 7/4/17.
 */

public class AppHelper {

    public static void setAsWallpaper(final ImageView imageView, final Photo photo, final AppHelperInterface appHelperInterface) {
        ImageHelper.saveBitmap(imageView, photo, new ImageHelper.BitmapInterface() {
            @Override
            public void onSaveBitmap(final File file) {
                new ModernAsyncTask<Void,Void,Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... params) {
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(AppController.getContext());
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), options);
                            myWallpaperManager.setBitmap(bitmap);
                            return true;
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            return false;
                        }
                    }

                    @Override
                    protected void onPostExecute(Boolean bool) {
                        super.onPostExecute(bool);
                        if(bool) {
                            appHelperInterface.onSetAsWallpaper();
                        } else
                            appHelperInterface.onSetAsWallpaperFailed();
                    }
                }.execute();
            }
            @Override
            public void onErrorSaveBitmap(String name) {
                appHelperInterface.onSetAsWallpaperFailed();
            }
        });

    }


    public static void shareLink(Context context, String link) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                link);
        sendIntent.setType("text/plain");
        context.startActivity(sendIntent);
    }


    public interface AppHelperInterface {
        void onSetAsWallpaper();
        void onSetAsWallpaperFailed();
    }



}
