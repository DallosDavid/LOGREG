package com.example.logreg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Reg extends AppCompatActivity {

    DBhelper adabazis;
    EditText nev,emial,jelszo,teljesznev;
    Button bvisza,breg;
    TextView text_adak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        init();
        clik();

    }

    private void clik() {
        bvisza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Reg.this,MainActivity.class));
                finish();
            }
        });


        breg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist(); adatokt();
            }
        });

    }



    private TextWatcher InputEllen= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String neve = nev.getText().toString().trim();
            String email = emial.getText().toString().trim();
            String jelszoo = jelszo.getText().toString().trim();
            String tnev = teljesznev.getText().toString().trim();
            breg.setEnabled(!neve.isEmpty() &&!email.isEmpty() && !jelszoo.isEmpty() && !tnev.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void regist() {
        String[] teljesnsplit = teljesznev.getText().toString().split(" ");
        String[] emaillit = emial.getText().toString().split("@");
        boolean nagye=false;
        boolean [] igazak=new boolean[teljesnsplit.length];
        String [] negy={"A", "Á", "B", "C", "Cs","D", "Dz", "Dzs","E", "É", "F", "G", "Gy","H", "I", "Í", "J","K", "L", "Ly", "M","N", "Ny", "O"," Ó", "Ö", "Ő", "P","Q", "R", "S", "Sz","T", "Ty", "U", "Ú", "Ü", "Ű", "V", "W", "X"," Y", "Z", "Zs"};
        if (teljesnsplit.length == 1){
            nagye=false;
        }else {
            for (int j=0;j<teljesnsplit.length;j++)
            {
                for (int i = 0; i < negy.length; i++) {
                    if (teljesnsplit[j].substring(0, 1).equals(negy[i]) ) {
                        igazak[j]=true;
                        nagye = true;
                    }
                }
            }
        }
        int mind=0;
        for (int i= 0;i<igazak.length;i++)
        {
            if (igazak[i])
            {
                mind++;
            }
        }
        System.out.println(mind);
        if (nagye){
            System.out.println("1");
        }else{
            System.out.println("2");
        }
        String neve1 = nev.getText().toString().trim();
        String email1 = emial.getText().toString().trim();
        String jelso1 = jelszo.getText().toString().trim();
        String teljesn1 = teljesznev.getText().toString().trim();
        System.out.println(neve1);
        System.out.println(email1);
        System.out.println(jelso1);
        System.out.println(teljesn1);

        if (teljesnsplit.length >= 2 ) {

        } else
        {
            Toast.makeText(this, "Kérem teljes nevet adjon meg.", Toast.LENGTH_SHORT).show();
        }
        if (emaillit.length != 2)
        {
            Toast.makeText(this, "Kérem rendes email adjon meg.", Toast.LENGTH_SHORT).show();
        }


        if (neve1.isEmpty() || email1.isEmpty() || jelso1.isEmpty() || teljesn1.isEmpty()) {
            Toast.makeText(Reg.this, "Kérem töltse ki az öszes mezöt.", Toast.LENGTH_SHORT).show(); return;
        } else {

            boolean elen=adabazis.NevEllenor(neve1);
            if (elen)
            {
                System.out.println("0");
            }else{
                System.out.println("1");
            }

            if (teljesnsplit.length>=2)
            {
                System.out.println("JO név");
            }else
            {
                System.out.println("Nem jo nev");
            }


            if (emaillit.length==2)
            {
                System.out.println("JO email");
            }else
            {
                System.out.println("Nem jo email");
            }

            if (mind == teljesnsplit.length)
            {
                System.out.println("JO név nagybetus");
            }else
            {
                System.out.println("Nem jo naygbetus");
            }

            if (!elen && teljesnsplit.length>=2 && emaillit.length==2 && mind == teljesnsplit.length) {
                boolean sikeres = adabazis.regiszt(email1, neve1, jelso1, teljesn1);

                if (sikeres) {
                    Toast.makeText(Reg.this, "Sikeres regiszt", Toast.LENGTH_SHORT).show();

                    return;
                }
            }else{
                Toast.makeText(this, "Sikeretlen", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void adatokt() {
        Cursor adatokk=adabazis.adatLekerdezes();
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
            stringBuffer.append("iD:"+adatokk.getString(0)+"\n");
            stringBuffer.append("Email:"+adatokk.getString(1)+"\n");
            stringBuffer.append("Nev:"+adatokk.getString(2)+"\n");
            stringBuffer.append("jleszo:"+adatokk.getString(3)+"\n");
            stringBuffer.append("Tnev:"+adatokk.getString(4)+"\n");
        }
        text_adak.setText(stringBuffer.toString());
        text_adak.setMovementMethod(new ScrollingMovementMethod());
    }

    private void init() {
        nev = (EditText)findViewById(R.id.edit_rog_nev);
        emial = (EditText)findViewById(R.id.edit_rog_email);
        jelszo = (EditText)findViewById(R.id.edit_rog_nevjelsz);
        teljesznev = (EditText)findViewById(R.id.edit_rog_tnev);
        nev.addTextChangedListener(InputEllen);
        emial.addTextChangedListener(InputEllen);
        jelszo.addTextChangedListener(InputEllen);
        teljesznev.addTextChangedListener(InputEllen);
        text_adak =(TextView)findViewById(R.id.text_adatok);
        bvisza = (Button)findViewById(R.id.re_visz);
        breg = (Button)findViewById(R.id.bttn_regiszt);
        adabazis =  new DBhelper(Reg.this);
    }
}