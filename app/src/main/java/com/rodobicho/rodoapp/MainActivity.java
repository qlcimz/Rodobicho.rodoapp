package com.rodobicho.rodoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btn_novaocorrencia = (ImageButton)findViewById(R.id.btn_novaocorrencia);
        ImageButton btn_entrar = (ImageButton)findViewById(R.id.btn_entrar);
        ImageButton btn_visualizarocorrencias = (ImageButton)findViewById(R.id.btn_visualizarocorrencias);
        ImageButton btn_sobre = (ImageButton)findViewById(R.id.btn_sobre);

        btn_novaocorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EnviarActivity.class));
            }
        });

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EntrarActivity.class));
            }
        });

        btn_visualizarocorrencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OcorrenciasActivity.class));
            }
        });

        btn_sobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SobreActivity.class));
            }
        });

    }
}
