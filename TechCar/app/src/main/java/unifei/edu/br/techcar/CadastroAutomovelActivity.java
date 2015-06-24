package unifei.edu.br.techcar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

// Cadastro de automóveis
public class CadastroAutomovelActivity extends ActionBarActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_automovel);

        db = new Database(this);

        // EditText
        final EditText txt_modelo = (EditText) findViewById(R.id.txt_modelo_cad_auto);
        final EditText txt_ano = (EditText) findViewById(R.id.txt_ano_cad_auto);
        final EditText txt_cor = (EditText) findViewById(R.id.txt_cor_cad_auto);
        final EditText txt_potencia = (EditText) findViewById(R.id.txt_potencia_cad_auto);
        final EditText txt_consumo = (EditText) findViewById(R.id.txt_consumo_cad_auto);
        final EditText txt_capacidade = (EditText) findViewById(R.id.txt_capacidade_cad_auto);

        // Spinner
        final Spinner sp_tipo = (Spinner) findViewById(R.id.sp_tipo_cad_auto);
        final Spinner sp_marca =(Spinner) findViewById(R.id.sp_marca_cad_auto);
        final Spinner sp_hardware =(Spinner) findViewById(R.id.sp_hardware_cad_auto);

        // Button
        Button btcadastrar = (Button) findViewById(R.id.bt_cadastrar_cad_auto);
        Button  btlimpar = (Button) findViewById(R.id.bt_limpar_cad_auto);

        // Configurando spinner tipo
        ArrayAdapter<CharSequence> adapter_tipo = ArrayAdapter.createFromResource(this, R.array.tipos, android.R.layout.simple_spinner_item);
        adapter_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipo.setAdapter(adapter_tipo);

        // Configurando spinner marca
        ArrayAdapter<CharSequence> adapter_marca = ArrayAdapter.createFromResource(this, R.array.marcas, android.R.layout.simple_spinner_item);
        adapter_marca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_marca.setAdapter(adapter_marca);

        // Configurando spinner hardware
        ArrayAdapter<CharSequence> adapter_hardware = ArrayAdapter.createFromResource(this, R.array.hardwares, android.R.layout.simple_spinner_item);
        adapter_hardware.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_hardware.setAdapter(adapter_hardware);

        // Botão cadastrar
        btcadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String modelo = txt_modelo.getText().toString();
                String ano = txt_ano.getText().toString();
                String cor = txt_cor.getText().toString();
                String potencia = txt_potencia.getText().toString();
                String consumo = txt_consumo.getText().toString();
                String capacidade = txt_capacidade.getText().toString();
                String tipo = sp_tipo.getSelectedItem().toString();
                String marca = sp_marca.getSelectedItem().toString();
                int hardware = sp_hardware.getSelectedItem().toString().equals("Ativo") ? 1 : 0;

                // Verificar se existem campos vazios
                if (modelo.equals("") || txt_ano.getText().toString().equals("") || cor.equals("") || potencia.equals("") || consumo.equals("") || capacidade.equals("")) {
                    Toast.makeText(getApplicationContext(),R.string.mensagem_campos_vazios, Toast.LENGTH_SHORT).show();
                } else {
                    db.adicionarAutomovel(modelo, tipo, marca, Integer.parseInt(ano), cor, Float.parseFloat(potencia), Float.parseFloat(consumo), Float.parseFloat(capacidade), hardware, getLogin());
                    Toast.makeText(getApplicationContext(),R.string.mensagem_automovel_cadastro, Toast.LENGTH_SHORT).show();
                    Intent lista = new Intent();
                    lista.setClass(CadastroAutomovelActivity.this, ListAutomovelActivity.class);
                    startActivity(lista);
                }
            }
        });

        // Botão limpar todos os campos
        btlimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_modelo.setText("");
                txt_ano.setText("");
                txt_cor.setText("");
                txt_potencia.setText("");
                txt_consumo.setText("");
                txt_capacidade.setText("");
                sp_tipo.setId(0);
                sp_marca.setId(0);
                sp_hardware.setId(0);
            }
        });
    }

    // Verificar status do login
    public String getLogin(){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_autologin),
                Context.MODE_PRIVATE);
        String autologinStatus = sharedPref.getString(getString(R.string.autologin_login),"");
        return autologinStatus;
    }
}