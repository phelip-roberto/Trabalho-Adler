package unifei.edu.br.techcar;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RelatorioCustosActivity extends ActionBarActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_custos);

        db = new Database(this);

        String modelo = "";
        String login = "";

        // Obter login e modelo
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            modelo = extras.getString("MODELO");
            login = extras.getString("LOGIN");
        }

        // lista de custos
        ArrayList<String> lista = new ArrayList<>();

        lista = db.listarCustos(modelo,login);

        if(lista.isEmpty()){
           Toast.makeText(getApplicationContext(),R.string.mensagem_custo_vazio, Toast.LENGTH_SHORT).show();
        }else {

            TableLayout table = (TableLayout) findViewById(R.id.table_custos_relatorio);

            // Preencher tabela
            TableRow tbrow_header = new TableRow(this);

            TextView text_id = new TextView(this);
            text_id.setText("ID");
            text_id.setTextColor(Color.BLACK);
            text_id.setGravity(Gravity.CENTER);
            tbrow_header.addView(text_id);

            TextView text_data = new TextView(this);
            text_data.setText("Data");
            text_data.setTextColor(Color.BLACK);
            text_data.setGravity(Gravity.CENTER);
            tbrow_header.addView(text_data);

            TextView text_custo = new TextView(this);
            text_custo.setText("Custo");
            text_custo.setTextColor(Color.BLACK);
            text_custo.setGravity(Gravity.CENTER);
            tbrow_header.addView(text_custo);

            table.addView(tbrow_header);

            for (int cont=0;cont < lista.size();cont++){
                String[] info = lista.get(cont).split("@");
                TableRow tbrow = new TableRow(this);

                TextView text_id_info = new TextView(this);
                text_id_info.setText(info[0]);
                text_id_info.setTextColor(Color.BLACK);
                text_id_info.setGravity(Gravity.CENTER);
                tbrow.addView(text_id_info);

                TextView text_data_info = new TextView(this);
                text_data_info.setText(info[1]);
                text_data_info.setTextColor(Color.BLACK);
                text_data_info.setGravity(Gravity.CENTER);
                tbrow.addView(text_data_info);

                TextView text_custo_info = new TextView(this);
                text_custo_info.setText(info[2]);
                text_custo_info.setTextColor(Color.BLACK);
                text_custo_info.setGravity(Gravity.CENTER);
                tbrow.addView(text_custo_info);

                table.addView(tbrow);
            }
        }
    }
}
