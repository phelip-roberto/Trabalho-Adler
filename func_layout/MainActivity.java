package unifei.edu.br.techcar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends ActionBarActivity {

    private final int SPLASH_DISPLAY_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent login = new Intent();
        login.setClass(MainActivity.this,LoginActivity.class);

        // Timer para realizar splash
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(login);
            }
        }, SPLASH_DISPLAY_TIME);
    }
}
