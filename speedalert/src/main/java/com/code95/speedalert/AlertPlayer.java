package com.code95.speedalert;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;


/**
 * Alert player class used to play an alert from local file
 * or Url depending in the mode set.
 */
public class AlertPlayer {

    private static MediaPlayer mMediaPlayer;

    public enum Mode {
        DataFromLocalFile,
        DataFromUrl
    }

    /**
     *
     * @param context used to create the media player.
     * @param url The DataFromUrl of th file to be played.
     * @param resId The res id of the file to be played.
     * @param mode Media player mode (DataFromUrl : Plays file online, DataFromLocalFile: Plays an audio file embedded in the project)
     */
    public static void playAlert(Context context, String url, int resId, Mode mode) {
        if(mode != null) {
            switch (mode) {
                case DataFromLocalFile:  //local file
                    if (mMediaPlayer == null) {
                        mMediaPlayer = MediaPlayer.create(context, resId);
                    }
                    break;
                case DataFromUrl: //url
                    if (mMediaPlayer == null) {
                        mMediaPlayer = new MediaPlayer();
                    }
                    try {
                        mMediaPlayer.setDataSource(url);
                        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    mMediaPlayer = new MediaPlayer();
            }
        } else {
            mMediaPlayer = MediaPlayer.create(context, R.raw.alert);
        }

        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }

    }

}
