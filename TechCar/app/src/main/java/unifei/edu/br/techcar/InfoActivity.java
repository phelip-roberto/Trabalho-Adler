package unifei.edu.br.techcar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;

// Informações do automóvel
public class InfoActivity extends ActionBarActivity {

    Database db;
    public int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        db = new Database(this);

        // Formatador
        final DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        // Obter linha do registro
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            pos = extras.getInt("POS");
        }

        // Obter informações do automóvel
        ArrayList<String> info = new ArrayList<>();
        info = db.automovelPos(pos,getLogin());

        // TextView
        TextView text_tipo = (TextView) findViewById(R.id.text_tipo_info);
        TextView text_marca = (TextView) findViewById(R.id.text_marca_info);
        TextView text_modelo = (TextView) findViewById(R.id.text_modelo_info);
        TextView text_ano = (TextView) findViewById(R.id.text_ano_info);
        TextView text_cor = (TextView) findViewById(R.id.text_cor_info);
        TextView text_potencia = (TextView) findViewById(R.id.text_potencia_info);
        final TextView text_consumo = (TextView) findViewById(R.id.text_consumo_info);
        TextView text_capacidade = (TextView) findViewById(R.id.text_capacidade_info);
        TextView text_harware = (TextView) findViewById(R.id.text_hardware_info);

        // Buttons
        Button bt_niveltanque = (Button) findViewById(R.id.bt_niveltanque);
        Button bt_consumo = (Button) findViewById(R.id.bt_consumo);
        Button bt_gastos = (Button) findViewById(R.id.bt_gastos);

        text_modelo.setText(info.get(0));
        text_tipo.setText(info.get(1));
        text_marca.setText(info.get(2));
        text_ano.setText(info.get(3));
        text_cor.setText(info.get(4));
        text_potencia.setText(info.get(5));
        text_consumo.setText(info.get(6)+" Km/L");
        text_capacidade.setText(info.get(7)+" L");

        if(info.get(8).equals("1")){
            text_harware.setText(R.string.text_hardware_on);
            text_harware.setTextColor(Color.GREEN);
        }else{
            text_harware.setText(R.string.text_hardware_on);
            text_harware.setTextColor(Color.RED);
        }

        // Botão verificar nível do tanque
        bt_niveltanque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                Float nivel = Formulas.nivelTanque();
                String status;
                if(nivel >= 70){
                    status = "CHEIO";
                }else{
                    if(nivel >= 30 && nivel < 70){
                        status = "RAZOÁVEL";
                    }else{
                        status = "BAIXO";
                    }
                }

                // Exibir nível do tanque
                builder.setMessage("Nível do tanque: "+df.format(nivel)+" L"+"\nStatus: "+status)
                        .setTitle(R.string.mensagem_titulo_informacoes)
                        .setPositiveButton(R.string.mensagem_botao_confirmar, null)
                        .show();
            }
        });

        // Botão calcular consumo médio
        bt_consumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                LayoutInflater inflater = InfoActivity.this.getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_consumomedio_custom, null);

                // Exibir formulário
                builder.setView(view)
                        .setTitle(R.string.mensagem_titulo_informacoes)
                        .setPositiveButton(R.string.mensagem_botao_calcular, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String [] consumoS = text_consumo.getText().toString().split(" ");
                                Float consumo = Float.parseFloat(consumoS[0]);

                                TextView txt_distancia = (TextView) view.findViewById(R.id.txt_distancia_consumomedio);

                                // Verificar se a distância é vazia
                                if (txt_distancia.getText().toString().equals("")) {
                                    Toast.makeText(getApplicationContext(), R.string.mensagem_distancia_vazia, Toast.LENGTH_SHORT).show();
                                } else {
                                    float distancia = Float.parseFloat(txt_distancia.getText().toString());
                                    float consumo_medio = Formulas.consumoMedio(distancia, consumo);

                                    // Exibir resposta
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(InfoActivity.this);
                                    builder2.setMessage("Consumo médio: " + consumo_medio+" L")
                                            .setTitle(R.string.mensagem_titulo_resultado)
                                            .setPositiveButton(R.string.mensagem_botao_confirmar, null)
                                            .show();
                                }

                            }
                        })
                        .setNegativeButton(R.string.mensagem_botao_confirmar, null)
                        .show();

            }
        });

        // Botão calcular gastos
        bt_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                LayoutInflater inflater = InfoActivity.this.getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_gasto_custom, null);

                // Exibir formulário
                builder.setView(view)
                        .setTitle(R.string.mensagem_titulo_informacoes)
                        .setPositiveButton(R.string.mensagem_botao_calcular, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String [] consumoS = text_consumo.getText().toString().split(" ");
                                Float consumo = Float.parseFloat(consumoS[0]);

                                TextView txt_distancia = (TextView) view.findViewById(R.id.txt_distancia_gasto);
                                TextView txt_preco = (TextView) view.findViewById(R.id.txt_preco_gasto);

                                // Verificar se existem capos vazios
                                if (txt_distancia.getText().toString().equals("") || txt_preco.getText().toString().equals("")) {
                                    Toast.makeText(getApplicationContext(), R.string.mensagem_campos_vazios, Toast.LENGTH_SHORT).show();
                                } else {
                                    float distancia = Float.parseFloat(txt_distancia.getText().toString());
                                    float preco = Float.parseFloat(txt_preco.getText().toString());
                                    float gasto = Formulas.gastos(distancia, consumo, preco);

                                    // Exibir respostas
                                    AlertDialog.Builder builder2 = new AlertDialog.Builder(InfoActivity.this);
                                    builder2.setMessage("Gasto: R$:" + df.format(gasto))
                                            .setTitle(R.string.mensagem_titulo_resultado)
                                            .setPositiveButton(R.string.mensagem_botao_confirmar, null)
                                            .show();
                                }
                            }
                        })
                        .setNegativeButton(R.string.mensagem_botao_calcular, null)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // TextView
        final TextView text_consumo = (TextView) findViewById(R.id.text_consumo_info);

        AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);

        // Botão do menu editar automóvel
        if(id == R.id.action_editar_automovel){
            LayoutInflater inflater = InfoActivity.this.getLayoutInflater();
            final View view = inflater.inflate(R.layout.dialog_alterar_automovel_custom, null);

            // Exibir formulário
            builder.setView(view)
                    .setTitle(R.string.mensagem_titulo_configuracoes)
                    .setPositiveButton(R.string.mensagem_botao_confirmar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // TextView
                            TextView txt_cor_alterar = (TextView) view.findViewById(R.id.txt_cor_alterar_automovel);
                            TextView txt_potencia_alterar = (TextView) view.findViewById(R.id.txt_potencia_alterar_automovel);
                            TextView txt_consumo_alterar = (TextView) view.findViewById(R.id.txt_consumo_alterar_automovel);

                            String cor = txt_cor_alterar.getText().toString();
                            String potencia = txt_potencia_alterar.getText().toString();
                            String consumo = txt_consumo_alterar.getText().toString();

                            // Verificar consumo
                            if (!cor.isEmpty() || !potencia.isEmpty() || !consumo.isEmpty()) {
                                db.editarAutomovel(cor, potencia, consumo, getLogin(), text_consumo.getText().toString());
                                Toast.makeText(getApplicationContext(), R.string.mensagem_automovel_editar, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),R.string.mensagem_campos_1, Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.mensagem_botao_cancelar, null)
                    .show();
        }

        // Botão do menu remover automóvel
        if (id == R.id.action_remover_automovel) {

            builder.setMessage(R.string.mensagem_automovel_confirmar)
                    .setTitle(R.string.mensagem_titulo_configuracoes)
                    .setPositiveButton(R.string.mensagem_botao_confirmar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.deletarAutomovel(getLogin(), text_consumo.getText().toString());
                            Toast.makeText(getApplicationContext(), R.string.mensagem_automovel_remover, Toast.LENGTH_SHORT).show();
                            Intent lista = new Intent();
                            lista.setClass(InfoActivity.this, ListAutomovelActivity.class);
                            startActivity(lista);
                        }
                    })
                    .setNegativeButton(R.string.mensagem_botao_cancelar, null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Verificar se usuário já fez login
    public String getLogin(){
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_autologin),
                Context.MODE_PRIVATE);
        String autologinStatus = sharedPref.getString(getString(R.string.autologin_login), "");
        return autologinStatus;
    }
}
