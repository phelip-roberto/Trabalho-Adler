package unifei.edu.br.techcar;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ListAutomovelActivity extends ActionBarActivity {

    Database db;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_automovel);

        db = new Database(this);

        TextView mensagem = (TextView) findViewById(R.id.text_mensagem);

        ArrayList<String> list = new ArrayList<>();
        list=db.listarAutomoveis();

        if(!list.isEmpty()){
            ListView list_automoveis = (ListView) findViewById(R.id.list_automovel);
            adapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item_custom, list);
            list_automoveis.setAdapter(adapter);

        }else{
            mensagem.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
