package xyz.juliomeru.tuturu;

import android.app.Activity;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;
import android.view.View;
import android.view.animation.AnimationUtils;

public class MainActivity extends Activity {

	Toast toast_msg;
	MediaPlayer mp;
	TextView txt_nb_tuturu;
	TextView title;
	ImageButton img_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img_btn = (ImageButton)findViewById(R.id.img_btn);
		img_btn.setOnClickListener(v -> {play();});
		title = (TextView)findViewById(R.id.title);
		txt_nb_tuturu = (TextView)findViewById(R.id.txt_nb_tuturu);
		SharedPreferences myPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		txt_nb_tuturu.setText(myPrefs.getString("KeyScore", "0"));
    }

	public void play() {
		int nb_random = (int)(Math.random() * 1000);
		String sound;
		if (nb_random > 925) {
			sound = "supratuturu";
		} else if (nb_random > 750) {
			sound = "tuturubis";
		} else {
			sound = "tuturu";
		}
		count_tuturu();
		play_this_animation(sound);
	}
	
	public void count_tuturu() {
		String nbTuturu = String.valueOf(Integer.parseInt(txt_nb_tuturu.getText().toString()) + 1);
		txt_nb_tuturu.setText(nbTuturu);
		SharedPreferences myPrefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPrefs.edit();
		editor.putString("KeyScore", nbTuturu);
		editor.commit();
	}

	public void play_this_song(String sound) {
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
		int r = getResources().getIdentifier(sound, "raw", getPackageName());
		mp = MediaPlayer.create(getApplicationContext(), r);
		mp.start();
	}
	public void play_this_animation(String name) {
		if (toast_msg != null ) toast_msg.cancel();
		toast_msg = Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT);
		toast_msg.show();

		play_this_song(name);

		title.clearAnimation();
		txt_nb_tuturu.clearAnimation();
		img_btn.clearAnimation();

		if (name == "supratuturu") {
			img_btn.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.animator.supratuturu));
			title.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.animator.rotate));
			txt_nb_tuturu.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.animator.rotate));
		} else if (name == "tuturubis") {
			img_btn.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.animator.tuturubis));
		} else {
			img_btn.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.animator.tuturu));
		}
	}
}
