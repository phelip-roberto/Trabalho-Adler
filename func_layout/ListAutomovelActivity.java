package unifei.edu.br.techcar;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListAutomovelActivity extends ActionBarActivity {

    Database db;
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_automovel);

        Button bt_adicionar_auto = (Button) findViewById(R.id.bt_addautomovel);

        db = new Database(this);

        ListView list_auto = (ListView) findViewById(R.id.list_automovel);

        ArrayList<String> lista = new ArrayList<>();

        lista = db.listarAutomoveis();

         adapter = new ArrayAdapter<String>(this,R.layout.list_item_custom, lista);

        list_auto.setAdapter(adapter);

        bt_adicionar_auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cadastro = new Intent().setClass(ListAutomovelActivity.this, CadastroAutomovelActivity.class);
                startActivity(cadastro);
            }
        });

        list_auto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent info = new Intent();
                info.setClass(ListAutomovelActivity.this,InfoActivity.class);
                info.putExtra("POS",position);
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
