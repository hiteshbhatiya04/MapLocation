package in.vnurture.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ListviewActivity extends AppCompatActivity {

    ListView listView;
    GridView gridView;
    Spinner spinner;
    String[] name ={"one","two","three","four","five","six","seven"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        //listView =(ListView)findViewById(R.id.listview);
        //gridView =(GridView) findViewById(R.id.gridview);
        spinner=(Spinner)findViewById(R.id.spinner);

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(ListviewActivity.this,android.R.layout.simple_dropdown_item_1line,name);
        spinner.setAdapter(arrayAdapter);

       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(ListviewActivity.this, ""+name[position], Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
    }
}
