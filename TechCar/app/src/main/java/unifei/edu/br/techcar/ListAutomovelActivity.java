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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

// Lista de automóveis do usuário
public class ListAutomovelActivity extends ActionBarActivity {

    Database db;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_automovel);

        db = new Database(this);

        // Button
        Button bt_adicionar_auto = (Button) findViewById(R.id.bt_addautomovel);

        // ListView
        ListView list_auto = (ListView) findViewById(R.id.list_automovel);

        ArrayList<String> lista = new ArrayList<>();

        lista = db.listarAutomoveis(getLogin());
        adapter = new ArrayAdapter<String>(this,R.layout.list_item_custom, lista);
        list_auto.setAdapter(adapter);

        // Botão adicionar
        bt_adicionar_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent().setClass(ListAutomovelActivity.this, CadastroAutomovelActivity.class);
                startActivity(cadastro);
            }
        });

        // Click da lista
        list_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent info = new Intent();
                info.setClass(ListAutomovelActivity.this, InfoActivity.class);
                info.putExtra("POS", position);
                startActivity(info);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_automovel, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        AlertDialog.Builder builder = new AlertDialog.Builder(ListAutomovelActivity.this);

        // Editar usuário menu
        if (id == R.id.action_editar_usuario) {

            LayoutInflater inflater = ListAutomovelActivity.this.getLayoutInflater();
            final View view = inflater.inflate(R.layout.dialog_alterar_custom, null);

            // Formulário
            builder.setView(view)
                    .setTitle(R.string.mensagem_titulo_configuracoes)
                    .setPositiveButton(R.string.mensagem_botao_confirmar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // TextView
                            TextView txt_senha_alterar = (TextView) view.findViewById(R.id.txt_senha_alterar);
                            TextView txt_email_alterar = (TextView) view.findViewById(R.id.txt_email_alterar);
                            TextView txt_telefone_alterar = (TextView) view.findViewById(R.id.txt_telefone_alterar);

                            String senha = txt_senha_alterar.getText().toString();
                            String email = txt_email_alterar.getText().toString();
                            String telefone = txt_telefone_alterar.getText().toString();

                            // Verificar se existem campos vazios
                            if (!senha.isEmpty() || !email.isEmpty() || !telefone.isEmpty()) {
                                db.editarUsuario(senha, email, telefone, getLogin());
                                Toast.makeText(getApplicationContext(), "Usuário alterado com sucesso", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), R.string.mensagem_campos_1, Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }

        // Remover usuário menu
        if (id == R.id.action_remover_usuario) {

            builder.setMessage(R.string.mensagem_usuario_confirmar)
                    .setTitle(R.string.mensagem_titulo_configuracoes)
                    .setPositiveButton(R.string.mensagem_botao_confirmar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deletarUsuario(getLogin());
                            Toast.makeText(getApplicationContext(), R.string.mensagem_usuario_remover, Toast.LENGTH_SHORT).show();
                            preferencesChange(0, "");
                            Intent log = new Intent();
                            log.setClass(ListAutomovelActivity.this, LoginActivity.class);
                            startActivity(log);
                        }
                    })
                    .setNegativeButton(R.string.mensagem_botao_cancelar, null)
                    .show();
        }

        if(id == R.id.action_logout_usuario){
            builder.setMessage(R.string.mensagem_logout_confirmar)
                    .setTitle(R.string.mensagem_titulo_logout)
                    .setPositiveButton(R.string.mensagem_botao_confirmar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            preferencesChange(0, "");
                            Intent log = new Intent();
                            log.setClass(ListAutomovelActivity.this, LoginActivity.class);
                            startActivity(log);
                        }
                    })
                    .setNegativeButton(R.string.mensagem_botao_cancelar, null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Botão back - não fazer nada
    @Override
    public void onBackPressed() {
    }

    // Verificar se usuário já fez login
    public String getLogin(){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_autologin),
                Context.MODE_PRIVATE);
        String autologinStatus = sharedPref.getString(getString(R.string.autologin_login),"");
        return autologinStatus;
    }

    // Editar login automativo
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
