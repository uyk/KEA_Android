package org.androidtown.newgame0501;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameOverScreen extends Screen implements View.OnTouchListener
{
    Bitmap main;
    Music music;
    Rect dst = new Rect();
    GameView gameView;
    Paint paint;
    Typeface font;

    public GameOverScreen(Game game, Canvas canvas, GameView gameView, Integer success)
    {
        super(game);
        this.gameView = gameView;
        dst.set(0, 0, canvas.getWidth(), canvas.getHeight());
        main = game.loadBitmap("gameover.jpg");
        music = game.loadMusic("music.ogg");
        canvas.drawBitmap(main, null, dst, null);
        music.play();
        gameView.setOnTouchListener(this);

        paint = new Paint();
        font = Typeface.createFromAsset(game.getAssets(), "font.ttf");

        String text = Integer.toString(success * 100);
        paint.setColor(Color.BLACK);
        paint.setTextSize(110);
        canvas.drawText(text, 880, 380,paint);
    }

    public void update(float deltaTime)
    {
        Log.d("myLog", "GameOverScreen updating");
    }
    public void pause()
    {
        music.pause();
        Log.d("myLog", "my We are pausing");
    }
    public void resume()
    {
        music.play();
        Log.d("myLog", "my We are resuming");
    }
    public void dispose()
    {
        Log.d("myLog", "my We are disposing");
        music.dispose();
    }
    public boolean onTouch(View v, MotionEvent event)
    {
        game.setContentView(new GameView(game, game));      //게임 화면으로 이동
        return true;
    }
}