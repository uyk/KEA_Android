package org.androidtown.newgame0501;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

public class RenderView extends View implements View.OnTouchListener
{
    Game game;
    Bitmap main;
    Rect dst = new Rect();

    public RenderView(Context context, Game game) {
        super(context);
        this.game = game;
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("Main.png");
            main = BitmapFactory.decodeStream(inputStream);
            inputStream.close();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_4444;
            this.setOnTouchListener(this);

        } catch (IOException e) {

        } finally {

        }
    }

    protected void onDraw(Canvas canvas)
    {
        dst.set(0, 0, this.getWidth(), this.getHeight());
        canvas.drawBitmap(main, null, dst, null);
        invalidate();
    }
    public boolean onTouch(View v, MotionEvent event)
    {
        game.setContentView(new GameView(game, game));      //게임 화면으로 이동
        return true;
    }
}