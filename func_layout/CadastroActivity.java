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


public class CadastroActivity extends ActionBarActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        db = new Database(this);

        final EditText txt_nome = (EditText) findViewById(R.id.txt_nome_cad);
        final EditText txt_login = (EditText) findViewById(R.id.txt_login_cad);
        final EditText txt_senha = (EditText) findViewById(R.id.txt_senha_cad);
        final EditText txt_nascimento = (EditText) findViewById(R.id.txt_nascimento_cad);
        final EditText txt_cpf = (EditText) findViewById(R.id.txt_cpf_cad);
        final EditText txt_email = (EditText) findViewById(R.id.txt_email_cad);
        final EditText txt_telefone = (EditText) findViewById(R.id.txt_tel_cad);

        Button bt_cadastrar = (Button) findViewById(R.id.bt1_cadastrar_cad);
        Button bt_limpar = (Button) findViewById(R.id.bt2_limpar_cad);

        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txt_nome.getText().toString();
                String login = txt_login.getText().toString();
                String senha = txt_senha.getText().toString();
                String nascimento = txt_nascimento.getText().toString();
                String cpf = txt_cpf.getText().toString();
                String email = txt_email.getText().toString();
                String telefone = txt_telefone.getText().toString();

                if(nome.equals("") || login.equals("") || senha.equals("") || nascimento.equals("") || cpf.equals("") || email.equals("") || telefone.equals("")){
                    Toast.makeText(getApplicationContext(),"Campo(s) vazio(s)",Toast.LENGTH_SHORT).show();
                }else{
                    if(db.addUsuario(nome,login,senha,nascimento,cpf,email,telefone)){
                        Toast.makeText(getApplicationContext(),"Usuário cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                        Intent lista = new Intent();
                        lista.setClass(CadastroActivity.this,ListAutomovelActivity.class);
                        startActivity(lista);
                    }else{
                        Toast.makeText(getApplicationContext(),"Usuário já existente",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        bt_limpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_nome.setText("");
                txt_login.setText("");
                txt_senha.setText("");
                txt_nascimento.setText("");
                txt_cpf.setText("");
                txt_email.setText("");
                txt_telefone.setText("");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
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
