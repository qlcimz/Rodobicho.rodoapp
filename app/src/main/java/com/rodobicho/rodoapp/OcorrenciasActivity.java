package com.rodobicho.rodoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rodobicho.rodoapp.entidade.Ocorrencia;
import com.rodobicho.rodoapp.entidade.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class OcorrenciasActivity extends AppCompatActivity {

    private String WS_URL = "";
    private List<Ocorrencia> ocorrencias;
    private String json = "";
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocorrencias);

        Button btn_voltar = (Button)findViewById(R.id.btn_voltar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OcorrenciasActivity.this, MainActivity.class));
                finish();
            }
        });

        
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if(acct != null){
            WS_URL = "http://192.168.0.10:8080/rodobicho/ocorrencia/listarByEmail?email="+acct.getEmail();
            //dispara chamada assincrona para listagem no WS
            new AsyncWS().execute();
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncWS extends AsyncTask<Void, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {

            //Converão Objecto Ocorrencia para Json
            Gson gson = new Gson();
            String line;
            String json_usuario = gson.toJson(usuario);

            try {
                //Tentativa de conexão e listagem ao WebService
                URL url = new URL(WS_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                InputStream is = conn.getInputStream();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                    while ((line = reader.readLine()) != null) {
                        json += line;
                    }
                }

                conn.disconnect();
                return json;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "ok";
        }

        @Override
        protected void onPostExecute(String json) {

            //Conversão Array de Json para Array de Objetos Ocorrencia
            Type listType = new TypeToken<List<Ocorrencia>>() {
            }.getType();

            List<Ocorrencia> ocorrenciasWS = new Gson().fromJson(json, listType);

            if (ocorrenciasWS != null) {

                RecyclerView rvContacts = findViewById(R.id.rv_ocorrencia);

                final OcorrenciaAdapter adapter = new OcorrenciaAdapter(ocorrenciasWS);

                rvContacts.setAdapter(adapter);

                rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }


        }
    }

}
