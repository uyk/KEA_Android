package org.androidtown.newgame0501;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener
{
    Game game;
    Bitmap person;
    Music music;
    private GameViewThread gameViewThread;
    Paint paint;
    Paint fontPaint;
    Rect personLocation;
    Rect obstacleLocation;
    int personRun = 1;          //Running Person  draw counter
    int personJump = 1;         //Jumping person draw counter
    int skyHeight = 500;     //하늘 높이
    int holeWidth = 150;     //구멍 넓이
    int skyObstacle = 400;   //장애물있을 때 하늘 높이
    int gap = 360;              //장애물 사이의 간격
    int count = 0;
    int runTimeGap = 5;
    int jumpTimeGap = 7;
    Random generator;
    boolean isJumping = false;
    int success = 0;

    Queue<Integer> queue = new LinkedList<Integer>();

    public GameView(Context context, Game game)
    {
        super(context);
        this.game = game;

        paint = new Paint();
        fontPaint = new Paint();
        generator = new Random();
        personLocation = new Rect();
        obstacleLocation = new Rect();
        if(queue.isEmpty())
            queue.offer(generator.nextInt(2));      //큐에 난수 하나 저장

        music = game.loadMusic("summer_s_here.mp3");
        if(!music.isPlaying())
            music.play();
        this.setOnTouchListener(this);
        getHolder().addCallback(this);
        gameViewThread = new GameViewThread(getHolder(), this);
    }

    public void onDraw(Canvas canvas)
    {
        Queue<Integer> tempQueue = new LinkedList<Integer>();
        tempQueue.addAll(queue);    //queue의 내용을 복사한 새로운 임시 큐 생성

        int drawGap = 0;

        //Background
        canvas.drawRGB(255, 255, 255);
        paint.setStyle(Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, skyHeight, this.getWidth(), this.getHeight(), paint); //땅
        fontPaint.setColor(Color.BLACK);
        fontPaint.setTextSize(50);
        canvas.drawText("SCORE : " + success * 100, canvas.getWidth() - 400, 140, fontPaint);

        //Person
        if(!isJumping)  //not jumping
        {
            if (personRun <= runTimeGap*1)
            {
                person = game.loadBitmap("person_01.png");
            }else if(personRun <= runTimeGap*2)
            {
                person = game.loadBitmap("person_02.png");
            }else if(personRun <= runTimeGap*3)
            {
                person = game.loadBitmap("person_03.png");
            }else if(personRun <= runTimeGap*4)
            {
                person = game.loadBitmap("person_04.png");
            }else if(personRun <= runTimeGap*5)
            {
                person = game.loadBitmap("person_05.png");
            }else if(personRun <= runTimeGap*6)
            {
                person = game.loadBitmap("person_06.png");
            }else if(personRun <= runTimeGap*7)
            {
                person = game.loadBitmap("person_07.png");
            }
            personRun++;
            if(personRun > runTimeGap*7 ) personRun = 1;        // goto first motion
            personLocation.left = 100;
            personLocation.top = 400;
            personLocation.right = personLocation.left + person.getWidth();
            personLocation.bottom = personLocation.top + person.getHeight();
        } else
        {
            if (personJump <= jumpTimeGap*1)
            {
                person = game.loadBitmap("jump_01.png");
                personLocation.top = 350;
            }else if(personJump <= jumpTimeGap*2)
            {
                person = game.loadBitmap("jump_02.png");
                personLocation.top = 300;
            }else if(personJump <= jumpTimeGap*3)
            {
                person = game.loadBitmap("jump_03.png");
                personLocation.top = 250;
            }else if(personJump <= jumpTimeGap*4)
            {
                person = game.loadBitmap("jump_04.png");
                personLocation.top = 200;
            }else if(personJump <= jumpTimeGap*5)
            {
                person = game.loadBitmap("jump_05.png");
                personLocation.top = 150;
            }else if(personJump <= jumpTimeGap*6)
            {
                person = game.loadBitmap("jump_06.png");
                personLocation.top = 200;
            }else if(personJump <= jumpTimeGap*7)
            {
                person = game.loadBitmap("jump_07.png");
                personLocation.top = 250;
            }
            else if(personJump <= jumpTimeGap*8)
            {
                person = game.loadBitmap("jump_08.png");
                personLocation.top = 300;
            }
            else if(personJump <= jumpTimeGap*9)
            {
                person = game.loadBitmap("jump_09.png");
                personLocation.top = 350;
            }
            personJump++;
            if(personJump > jumpTimeGap*9)
            {
                personJump = 1;
                isJumping = false;
            }
            personLocation.left = 100;
            personLocation.right = personLocation.left + person.getWidth();
            personLocation.bottom = personLocation.top + person.getHeight();
        }
        canvas.drawBitmap(person,null,personLocation,null);
        //end of Person draw


        while(!tempQueue.isEmpty())
        {
            int polledData = tempQueue.poll();
            switch (polledData)
            {
                case 0 :        //hole obstacle
                    paint.setColor(Color.WHITE);
                    if ((this.getWidth() + holeWidth >= count)) //왼쪽  끝까지
                    {
                        obstacleLocation.left = this.getWidth() - count + drawGap;
                        obstacleLocation.top = skyHeight;
                        obstacleLocation.right = this.getWidth() - count + holeWidth + drawGap;
                        obstacleLocation.bottom = this.getHeight();
                        canvas.drawRect(obstacleLocation, paint);
                        //gameOvercheck
                        if(personLocation.left > obstacleLocation.left && personLocation.right < obstacleLocation.right)
                        {
                            if(personLocation.bottom >= obstacleLocation.top)
                            {
                                gameViewThread.setRunning(false);
                                tempQueue.clear();
                                queue.clear();
                                music.stop();
                                new GameOverScreen(game, canvas, this, success);
                                Log.d("myLog","case1");
                            }
                        }
                    }
                    break;
                case 1 :
                    paint.setColor(Color.BLACK);
                    if ((this.getWidth() + holeWidth > count)) //왼쪽  끝까지
                    {
                        obstacleLocation.left = this.getWidth() - count + drawGap;
                        obstacleLocation.top = skyObstacle;
                        obstacleLocation.right = this.getWidth() - count + holeWidth + drawGap;
                        obstacleLocation.bottom = this.getHeight();
                        canvas.drawRect(obstacleLocation,paint);
                    }
                    //gameover check
                    if(personLocation.left > obstacleLocation.left && personLocation.right < obstacleLocation.right)
                    {
                        if(personLocation.bottom > obstacleLocation.top)
                        {
                            gameViewThread.setRunning(false);
                            tempQueue.clear();
                            queue.clear();
                            music.stop();
                            new GameOverScreen(game, canvas, this, success);
                            Log.d("success","case2"+"success");
                        }
                    }
                    break;
            }// switch
            //게임종료되지않고 장애물 그림, 장애물이 사람보다 뒤
            if(obstacleLocation.right == 100 || obstacleLocation.right == 99 ||
                    obstacleLocation.right == 98 || obstacleLocation.right == 97)
            {
                success++;
                Log.d("success","success" + Integer.toString(success));
            }
            drawGap += gap;
        }   //while 큐에있는 장애물 모두 그림

        if((count % 360) == 0)      //360마다 새 장애물 추가
        {
            queue.offer(generator.nextInt(2));
        }
        if((count >= this.getWidth() + holeWidth))  //장애물 하나가 끝까지 다옴
        {
            queue.poll();   //장애물 하나 제거
            count -= 360;
        }
        count += 4;
    }//onDraw()

    @Override
    //be called when something change
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }
    @Override
    //be called when view is made
    public void surfaceCreated(SurfaceHolder holder)
    {
        gameViewThread.setRunning(true);
        gameViewThread.start();
    }
    @Override
    //be called when view end
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        gameViewThread.setRunning(false);
        music.dispose();
        while(retry)
        {
            try
            {
                gameViewThread.join();//이 쓰레드가 종료될 때까지 다른 스레드 멈춤
                retry = false;
            } catch(InterruptedException e) {}
        }
    }
    public boolean onTouch(View v, MotionEvent event)
    {
        Log.d("myLog", "GameView touch, 169");
        isJumping = true;
        return true;
    }
}
