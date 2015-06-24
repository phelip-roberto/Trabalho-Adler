package unifei.edu.br.techcar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity {

    static final String LOG_TAG = "TechCar";
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new Database(this);

        // EditText
        final EditText txt_login = (EditText) findViewById(R.id.txt_usuario);
        final EditText txt_senha = (EditText) findViewById(R.id.txt_senha);

        // Buttons
        final Button bt_login = (Button) findViewById(R.id.bt_entrar);
        final Button bt_cadastrar= (Button) findViewById(R.id.bt_cadastrar);

        // Botão login
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String login = txt_login.getText().toString();
                String senha = txt_senha.getText().toString();

                // verificar se existem campos vazios
                if(login.equals("") || senha.equals("")){
                    Toast.makeText(getApplicationContext(),R.string.mensagem_campos_vazios,Toast.LENGTH_LONG).show();
                }else{
                    switch(db.consultarUsuario(txt_login.getText().toString(), txt_senha.getText().toString())) {
                        case 1:
                            Toast.makeText(getApplicationContext(),R.string.mensagem_usuario_invalido,Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(),R.string.mensagem_usuario_senha_invalida,Toast.LENGTH_LONG).show();
                            break;
                        case 3:
                            preferencesChange(1,login);
                            Intent lista = new Intent();
                            lista.setClass(LoginActivity.this,ListAutomovelActivity.class);
                            startActivity(lista);
                            break;

                    }
                }
            }
        });

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent();
                cadastro.setClass(LoginActivity.this,CadastroActivity.class);
                startActivity(cadastro);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Equipe
        if(id == R.id.action_team){
            Intent team = new Intent();
            team.setClass(LoginActivity.this,EquipeActivity.class);
            startActivity(team);
        }

        return super.onOptionsItemSelected(item);
    }



    // Alterar status do login
    void preferencesChange(int status, String login){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_autologin),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.autologin_status),status);
        editor.putString(getString(R.string.autologin_login),login);
        editor.commit();
    }
}