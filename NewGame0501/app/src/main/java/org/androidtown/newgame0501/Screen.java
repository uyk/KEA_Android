package org.androidtown.newgame0501;

public abstract class Screen
{
    protected final Game game;

    public Screen(Game game) {this.game = game;}
    public abstract void update(float detaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();
}
