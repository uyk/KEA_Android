package org.androidtown.newgame0501;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;

public class Game extends Activity implements Runnable
{
    private RenderView mainView;
    private Canvas canvas = null;

    @Override
    protected void onCreate(Bundle instanceBundle)
    {
        super.onCreate(instanceBundle);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mainView = new RenderView(this, this);
        setContentView(mainView);

    }
    @Override
    public void onResume()
    {
        super.onResume();
    }
    @Override
    public void onPause()
    {
        super.onPause();
    }
    @Override
    public void run()
    {
    }
    public Bitmap loadBitmap(String fileName)
    {
        InputStream in = null;
        Bitmap bitmap = null;
        try
        {
            in = getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Could not load the file" + fileName);
            return bitmap;
        } catch (IOException e)
        {
            throw new RuntimeException("Cound not load the file " + fileName);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                } catch (IOException e)
                {
                    Log.d("closing imputstream", "Shit");
                }
            }
        }
        //return null;
    }
    public void drawBitmap(Bitmap bitmap, int x, int y)
    {
        Log.d("Game","my_drawBitmap 99");
        if (canvas != null)
        {
            canvas.drawBitmap(bitmap, x, y, null);
            Log.d("Game", "my_drawBitmap 101");
        }
    }
    public Music loadMusic(String fileName)
    {
        try {
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd(fileName);
            return new Music(assetFileDescriptor);
        } catch(IOException e){
            throw new RuntimeException("Could not load music file : " + fileName + " Bad Error");
        }
    }
}
