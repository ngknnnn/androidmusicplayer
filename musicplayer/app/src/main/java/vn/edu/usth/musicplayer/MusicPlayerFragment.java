package vn.edu.usth.musicplayer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MusicPlayerFragment extends Fragment {
    public MusicPlayerFragment() {
        //empty constructor
    };
    private View parent_view;
    private AppCompatSeekBar seek_song_progressbar;

    private FloatingActionButton btn_play;

    private TextView tv_song_current_duration, tv_song_total_duration;
    private CircularImageView image;

    private MediaPlayer mp;
    private Handler mHandler = new Handler();

    private MusicUtils utils;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.music_player, container, false);
        parent_view = view.findViewById(R.id.parent_view);
        seek_song_progressbar = view.findViewById(R.id.seek_song_progressbar);
        btn_play = view.findViewById(R.id.btn_play);
        seek_song_progressbar.setProgress(0);
        seek_song_progressbar.setMax(MusicUtils.MAX_PROGRESS);

        tv_song_current_duration =  view.findViewById(R.id.tv_song_current_duration);
        tv_song_total_duration = view.findViewById(R.id.total_duration);
        image =  view.findViewById(R.id.image);

        mp = new MediaPlayer();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                btn_play.setImageResource(R.drawable.ic_play_arrow);
            }
        });

        try {
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = getActivity().getAssets().openFd("999DoaHong.mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (Exception e) {
            Snackbar.make(parent_view, "Cannot load audio file", Snackbar.LENGTH_SHORT).show();
        }

        utils = new MusicUtils();
        seek_song_progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mHandler.removeCallbacks(mUpdateTimeTask);
                int totalDuration = mp.getDuration();
                int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
                mp.seekTo(currentPosition);
                mHandler.post(mUpdateTimeTask);
            }
        });
        buttonPlayerAction();
        updateTimerAndSeekbar();

        return view;
    }

    private void buttonPlayerAction() {
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (mp.isPlaying()) {
                    mp.pause();
                    btn_play.setImageResource(R.drawable.ic_play_arrow);
                } else {
                    mp.start();
                    btn_play.setImageResource(R.drawable.ic_pause);
                    mHandler.post(mUpdateTimeTask);
                }
                rotateTheDisk();
            }
        });
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            updateTimerAndSeekbar();
            if (mp.isPlaying()) {
                mHandler.postDelayed(this, 100);
            }
        }
    };

    private void updateTimerAndSeekbar() {
        long totalDuration = mp.getDuration();
        long currentDuration = mp.getCurrentPosition();

        tv_song_total_duration.setText(utils.milliSecondsToTimer(totalDuration));
        tv_song_current_duration.setText(utils.milliSecondsToTimer(currentDuration));

        int progress = (int) (utils.getProgressSeekBar(currentDuration, totalDuration));
        seek_song_progressbar.setProgress(progress);
    }

    private void rotateTheDisk() {
        if (!mp.isPlaying()) return;
        image.animate().setDuration(100).rotation(image.getRotation() + 2f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rotateTheDisk();
                super.onAnimationEnd(animation);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mUpdateTimeTask);
        mp.release();
    }


}
