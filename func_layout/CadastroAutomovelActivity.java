package unifei.edu.br.techcar;

import android.app.AlertDialog;
import android.content.Intent;
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


public class CadastroAutomovelActivity extends ActionBarActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new Database(this);

        setContentView(R.layout.activity_cadastro_automovel);

        final EditText txt_modelo = (EditText) findViewById(R.id.txt_modelo_cad_auto);
        final EditText txt_ano = (EditText) findViewById(R.id.txt_ano_cad_auto);
        final EditText txt_cor = (EditText) findViewById(R.id.txt_cor_cad_auto);
        final EditText txt_potencia = (EditText) findViewById(R.id.txt_potencia_cad_auto);
        final EditText txt_consumo = (EditText) findViewById(R.id.txt_consumo_cad_auto);
        final EditText txt_capacidade = (EditText) findViewById(R.id.txt_capacidade_cad_auto);

        Button btcadastrar = (Button) findViewById(R.id.bt_cadastrar_cad_auto);
        Button  btlimpar = (Button) findViewById(R.id.bt_limpar_cad_auto);

        final Spinner sp_tipo = (Spinner) findViewById(R.id.sp_tipo_cad_auto);
        final Spinner sp_marca =(Spinner) findViewById(R.id.sp_marca_cad_auto);
        final Spinner sp_hardware =(Spinner) findViewById(R.id.sp_hardware_cad_auto);


        ArrayAdapter<CharSequence> adapter_tipo = ArrayAdapter.createFromResource(this, R.array.tipos, android.R.layout.simple_spinner_item);
        adapter_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_tipo.setAdapter(adapter_tipo);

        ArrayAdapter<CharSequence> adapter_marca = ArrayAdapter.createFromResource(this, R.array.marcas, android.R.layout.simple_spinner_item);
        adapter_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_marca.setAdapter(adapter_marca);

        ArrayAdapter<CharSequence> adapter_hardware = ArrayAdapter.createFromResource(this, R.array.hardware, android.R.layout.simple_spinner_item);
        adapter_tipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_hardware.setAdapter(adapter_hardware);

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
                int hardware = sp_hardware.getSelectedItem().toString().equals("Ativo")?1:0;

                if(modelo.equals("") || txt_ano.getText().toString().equals("") || cor.equals("") || potencia.equals("") || consumo.equals("") || capacidade.equals("")){
                    Toast.makeText(getApplicationContext(), "Campo(s) vazio(s)", Toast.LENGTH_SHORT).show();
                }else{
                    db.addAutomovel(modelo,tipo,marca,Integer.parseInt(ano),cor,Float.parseFloat(potencia),Float.parseFloat(consumo),Float.parseFloat(capacidade),hardware);
                    Toast.makeText(getApplicationContext(),"Automóvel cadastrado com sucesso",Toast.LENGTH_SHORT).show();
                    Intent lista = new Intent();
                    lista.setClass(CadastroAutomovelActivity.this,ListAutomovelActivity.class);
                    startActivity(lista);
                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro_automovel, menu);
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
