package com.rodobicho.rodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        ImageButton btn_cadastrar = (ImageButton)findViewById(R.id.btn_cadastrar);
        ImageButton btn_voltar = (ImageButton)findViewById(R.id.btn_voltar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CadastrarActivity.this, MainActivity.class));
            }
        });

        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText edt_nome = (EditText) findViewById(R.id.edt_nome);
                final EditText edt_email = (EditText) findViewById(R.id.edt_email);
                final EditText edt_senha = (EditText) findViewById(R.id.edt_senha);
                final EditText edt_csenha = (EditText) findViewById(R.id.edt_csenha);
                final String nome = edt_nome.getText().toString();
                final String email = edt_email.getText().toString();
                final String senha = edt_senha.getText().toString();
                final String csenha = edt_csenha.getText().toString();


                if (nome.length() == 0) {
                    edt_nome.setError("Favor inserir nome");
                    return;
                }
                else if (email.length() == 0) {
                    edt_email.setError("Favor inserir e-mail");
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    edt_email.setError("E-mail inválido");
                }
                else if (senha.length() == 0) {
                    edt_senha.setError("Senha inválida");
                }
                else if (!csenha.equals(senha)) {
                    edt_csenha.setError("Senhas não equivalentes");
                }
            }
        });
    }


}
