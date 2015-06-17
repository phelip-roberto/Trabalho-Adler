package unifei.edu.br.techcar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;



public class MainActivity extends ActionBarActivity {

    // Tempo de splash
    private final int SPLASH_DISPLAY_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent login = new Intent();
        login.setClass(MainActivity.this,LoginActivity.class);

        // Verificar se usuário já logou anteriormente
        if(logged()){
            login.setClass(MainActivity.this,ListAutomovelActivity.class);
        }else{
            login.setClass(MainActivity.this,LoginActivity.class);
        }

        // Timer para realizar splash
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
                startActivity(login);
            }
        }, SPLASH_DISPLAY_TIME);
    }

    // Verificar se usuário já fez login
    public boolean logged(){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_autologin),
                Context.MODE_PRIVATE);
        int autologinStatus = sharedPref.getInt(getString(R.string.autologin_status),0);
        if(autologinStatus == 1){
            return true;
        }else{
            return false;
        }
    }
}