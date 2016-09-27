package org.androidtown.newgame0501;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameViewThread extends Thread
{
    Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private boolean isRun = false;

    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView)
    {
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }
    public void setRunning(boolean isRun)
    {
        this.isRun = isRun;
    }
    //repeated
    public void run()
    {
        while(isRun)
        {
            canvas = null;
            try
            {
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder)
                {
                    gameView.onDraw(canvas);
                }
            }finally
            {
                if(canvas != null)
                    surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }

    }
}
