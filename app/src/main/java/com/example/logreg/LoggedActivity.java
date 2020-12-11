package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLInput;

public class LoggedActivity extends AppCompatActivity {

    TextView textView;
    Button butan_ki;
    DBhelper adatbazis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);



        inti();
        adat();

        butan_ki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoggedActivity.this,MainActivity.class));
            finish();
            }
        });

    }

    private void adat() {
  /*      Cursor adatok= adatbazis.adatLekerdezes();
        StringBuffer stringBuffer = new StringBuffer();
        while (adatok.moveToNext())
        {
            Bundle bundle = getIntent().getExtras();
            String szoveg=bundle.getString("data1");
            textView =  (TextView)findViewById(R.id.teljesnev);
            textView.setText(""+adatbazis.EgyLeKerdezes(szoveg));
        }
*/      Bundle bundle = getIntent().getExtras();
        String szoveg=bundle.getString("data1");
        Cursor adatokk=adatbazis.EgyLeKerdezes(szoveg);
        if (adatokk == null)
        {
            Toast.makeText(this, "NSikertelen le kercdezs", Toast.LENGTH_SHORT).show(); return;
        }
        if (adatokk.getCount() == 0)
        {
            Toast.makeText(this, "Nicns felvet adat", Toast.LENGTH_SHORT).show(); return;
        }
        StringBuffer stringBuffer= new StringBuffer();
        while (adatokk.moveToNext())
        {
            stringBuffer.append("Teljes név:"+adatokk.getString(0));
        }
        textView.setText(stringBuffer.toString());




    }


    private void inti() {
        textView = (TextView)findViewById(R.id.teljesnev);
        butan_ki = (Button) findViewById(R.id.btn_main);
        adatbazis = new DBhelper(LoggedActivity.this);
    }
}