package unifei.edu.br.techcar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Cadastro de usuário
public class CadastroActivity extends ActionBarActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        db = new Database(this);

        // EditText
        final EditText txt_nome = (EditText) findViewById(R.id.txt_nome_cad);
        final EditText txt_login = (EditText) findViewById(R.id.txt_login_cad);
        final EditText txt_senha = (EditText) findViewById(R.id.txt_senha_cad);
        final EditText txt_nascimento = (EditText) findViewById(R.id.txt_nascimento_cad);
        final EditText txt_cpf = (EditText) findViewById(R.id.txt_cpf_cad);
        final EditText txt_email = (EditText) findViewById(R.id.txt_email_cad);
        final EditText txt_telefone = (EditText) findViewById(R.id.txt_tel_cad);

        // Button
        Button bt_cadastrar = (Button) findViewById(R.id.bt_cadastrar_cad);
        Button bt_limpar = (Button) findViewById(R.id.bt_limpar_cad);

        // Botão de cadastro
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

                // Verificar se existem campos vazios
                if(nome.equals("") || login.equals("") || senha.equals("") || nascimento.equals("") || cpf.equals("") || email.equals("") || telefone.equals("")){
                    Toast.makeText(getApplicationContext(),R.string.mensagem_campos_vazios,Toast.LENGTH_SHORT).show();
                }else{
                    if(db.adicionarUsuario(nome, login, senha, nascimento, cpf, email, telefone)){
                        Toast.makeText(getApplicationContext(),R.string.mensagem_usuario_cadastro,Toast.LENGTH_SHORT).show();
                        preferencesChange(1,login);
                        Intent lista = new Intent();
                        lista.setClass(CadastroActivity.this,ListAutomovelActivity.class);
                        startActivity(lista);
                    }else{
                        Toast.makeText(getApplicationContext(),R.string.mensagem_usuario_existente,Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Botão limpar todos os campos
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
