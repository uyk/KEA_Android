package org.androidtown.newgame0501;

import android.media.SoundPool;

public class Sound
{
    int soundId;
    SoundPool soundPool;

    public Sound(SoundPool s, int sId)
    {
        soundPool = s;
        soundId = sId;
    }
    public void play(float volume)
    {
        soundPool.play(soundId,volume,volume,0,0,volume);
    }
    public void dispose()
    {
        soundPool.unload(soundId);
    }
}
