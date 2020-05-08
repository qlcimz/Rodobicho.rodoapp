package com.rodobicho.rodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class EntrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);
        ImageButton btn_voltar = (ImageButton)findViewById(R.id.btn_voltar);
        ImageButton btn_entrar = (ImageButton)findViewById(R.id.btn_entrar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntrarActivity.this, MainActivity.class));
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edt_email = (EditText) findViewById(R.id.edt_email);
                final EditText edt_password = (EditText) findViewById(R.id.edt_password);
                final String email = edt_email.getText().toString();
                final String password = edt_password.getText().toString();

                if (email.length() == 0) {
                    edt_email.setError("Favor inserir e-mail");
                    return;
                }
                if (password.length() == 0) {
                    edt_password.setError("Favor inserir senha");
                    return;
                }
            }
        });


    }
}
