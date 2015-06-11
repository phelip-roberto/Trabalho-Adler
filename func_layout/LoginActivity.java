package unifei.edu.br.techcar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_login.getText().toString().equals("") || txt_senha.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Campo login ou senha vazio(s)",Toast.LENGTH_LONG).show();
                }else{
                    switch(db.consultaUsuario(txt_login.getText().toString(),txt_senha.getText().toString())) {
                        case 1:
                            Toast.makeText(getApplicationContext(),"Usuário inválido",Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(),"Senha incorreta",Toast.LENGTH_LONG).show();
                            break;
                        case 3:
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
