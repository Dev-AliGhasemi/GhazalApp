package ir.vira.MediaController;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import ir.vira.R;

public class MediaController {

    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private SeekBar seekBar;
    private TextView textView;
    private Handler handler;
    private int totalMinute , totalSecond , minute , second;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public MediaController(ImageView imageView, SeekBar seekBar , TextView textView) {
        this.imageView = imageView;
        this.seekBar = seekBar;
        this.textView = textView;
        handler = new Handler();
    }

    public void startMusic(String musicLink , final Context context){
        if (mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(context , Uri.parse(musicLink));
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    imageView.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                    syncSeekBar();
                    totalSecond = mediaPlayer.getDuration()/1000;
                    totalMinute = totalSecond / 60;
                    totalSecond -= (totalMinute * 60);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.seekTo(0);
                    stopMusic();
                }
            });
        }else {
            seekBar.setMax(mediaPlayer.getDuration());
            Log.e("MSG",seekBar.getMax()+"");
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
            mediaPlayer.start();
            imageView.setImageResource(R.drawable.ic_pause);
            syncSeekBar();
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    if (progress == mediaPlayer.getDuration()){
                        mediaPlayer.seekTo(0);
                        stopMusic();
                    }else {
                        mediaPlayer.seekTo(progress);
                    }
                } else if (progress == mediaPlayer.getDuration()){
                    mediaPlayer.seekTo(0);
                    stopMusic();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void stopMusic() {
        if (mediaPlayer != null){
            mediaPlayer.pause();
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            imageView.setImageResource(R.drawable.ic_play);
        }
    }
    public boolean isMediaPlayerPlaying(){
        return mediaPlayer.isPlaying();
    }

    public void replay(){
        if (mediaPlayer != null){
            if (isMediaPlayerPlaying()){
                mediaPlayer.seekTo(0);
                startMusic("" , null);
            }
        }
    }

    private void syncSeekBar(){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        second = mediaPlayer.getCurrentPosition()/1000;
        minute = second /60;
        second -= (minute * 60);
        textView.setText( minute+":"+second+" / "+totalMinute+":"+totalSecond);
        if (mediaPlayer.isPlaying()){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    syncSeekBar();
                }
            },1000);
        }
    }
}
