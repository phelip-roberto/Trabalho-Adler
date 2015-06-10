package unifei.edu.br.techcar;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;


public class InfoActivity extends ActionBarActivity {

    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        db = new Database(this);

        Bundle extras = getIntent().getExtras();

        int pos = -1;

        if(extras != null){
            pos = extras.getInt("POS");
        }

        ArrayList<String> info = new ArrayList<>();

        info = db.automovelPos(pos);

        TextView text_tipo = (TextView) findViewById(R.id.text_tipo_info);
        TextView text_marca = (TextView) findViewById(R.id.text_marca_info);
        TextView text_modelo = (TextView) findViewById(R.id.text_modelo_info);
        TextView text_ano = (TextView) findViewById(R.id.text_ano_info);
        TextView text_cor = (TextView) findViewById(R.id.text_cor_info);
        TextView text_potencia = (TextView) findViewById(R.id.text_potencia_info);
        TextView text_consumo = (TextView) findViewById(R.id.text_consumo_info);
        TextView text_capacidade = (TextView) findViewById(R.id.text_capacidade_info);
        TextView text_harware = (TextView) findViewById(R.id.text_hardware_info);

        text_tipo.setText(info.get(0));
        text_marca.setText(info.get(1));
        text_modelo.setText(info.get(2));
        text_ano.setText(info.get(3));
        text_cor.setText(info.get(4));
        text_potencia.setText(info.get(5));
        text_consumo.setText(info.get(6));
        text_capacidade.setText(info.get(7));
        if(info.get(8).equals("1")){
            text_harware.setText("Ativo");
            text_harware.setTextColor(Color.GREEN);
        }else{
            text_harware.setText("Inativo");
            text_harware.setTextColor(Color.RED);
        }



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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
