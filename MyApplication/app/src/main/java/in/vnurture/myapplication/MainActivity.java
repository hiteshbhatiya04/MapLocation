package in.vnurture.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText tv_first,tv_second,tv_third,tv_fourth;
    Button btn_submit;
    String first,second,third,fourth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_first=(EditText) findViewById(R.id.tv_first);
        tv_second=findViewById(R.id.tv_second);
        tv_third=findViewById(R.id.tv_third);
        tv_fourth=findViewById(R.id.tv_fourth);
        btn_submit=(Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                first=tv_first.getText().toString();
                second=tv_second.getText().toString();
                third=tv_third.getText().toString();
                fourth=tv_fourth.getText().toString();


                //Toast.makeText(MainActivity.this,"This is testing",Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, ""+first, Toast.LENGTH_SHORT).show();


            }
        });

    }
}
