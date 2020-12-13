package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText_fel,editText_jel;
    Button button_bej,button_reg;
    DBhelper adatbazis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        button_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
            }
        });

        button_bej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Ellen();

            }
        });

    }

    private void Ellen() {
        String nev=editText_fel.getText().toString().trim();
        String jelsz=editText_jel.getText().toString().trim();

        if (nev.isEmpty() || jelsz.isEmpty())
        {
            Toast.makeText(MainActivity.this, "Nem adaot meg nevet vagy jelszót.", Toast.LENGTH_SHORT).show(); return;
        }
        System.out.println(nev+" "+jelsz);
        boolean neve= adatbazis.Nev(nev,jelsz);
        if(neve){

            String datrt = editText_fel.getText().toString().trim();
            Intent passdat_int=new Intent(MainActivity.this, LoggedInActivity.class);
            passdat_int.putExtra("data1",datrt);
            startActivity(passdat_int);
            finish();

        }else{
            Toast.makeText(MainActivity.this, "Hibás adatokat adot meg.", Toast.LENGTH_SHORT).show(); return;
        }
    }

    private void init() {

        editText_fel = (EditText)findViewById(R.id.edit_felh);
        editText_jel = (EditText)findViewById(R.id.edit_jelsz);
       button_bej = (Button)findViewById(R.id.bttn_bejelent);
       button_reg = (Button)findViewById(R.id.bttn_reg);

       adatbazis = new DBhelper(MainActivity.this);

    }
}