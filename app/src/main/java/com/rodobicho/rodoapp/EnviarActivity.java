package com.rodobicho.rodoapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.rodobicho.rodoapp.entidade.Foto;
import com.rodobicho.rodoapp.entidade.Local;
import com.rodobicho.rodoapp.entidade.Ocorrencia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class EnviarActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    private TextView textView;
    private ImageButton btn_tirarfoto;
    private ImageView foto1, foto2, foto3;
    private String json;
    private final String WS_URL = "http://10.0.2.2:8080/incendio/ocorrenciaController/inserir";
    Ocorrencia ocorrencia;
    Local local;
    Foto objFoto1, objFoto2, objFoto3;
    byte[] image1, image2, image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);

        ImageButton btn_salvar = (ImageButton) findViewById(R.id.btn_salvar);
        ImageButton btn_voltar = (ImageButton) findViewById(R.id.btn_voltar);

        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnviarActivity.this, MainActivity.class));
            }
        });

        textView = (TextView) findViewById(R.id.id_textViewLATLONG);

        // Declaração para o uso da câmera
        btn_tirarfoto = findViewById(R.id.btn_tirarfoto);
        foto1 = findViewById(R.id.foto1);
        foto2 = findViewById(R.id.foto2);
        foto3 = findViewById(R.id.foto3);

        // Usando a câmera
        btn_tirarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });

        //Permissão para buscar Localização
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        final Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
//                mostrarMensagem("TESTE");

                if (image1 != null) {
                    mostrarMensagem("OK");
                } else {
                    mostrarMensagem("TESTE");

                }
//                objFoto2.setUrlFoto(image2);
//                objFoto3.setUrlFoto(image3);

//                local.setLatitude(location.getLatitude());
//                local.setLongitude(location.getLongitude());

//                ocorrencia.setDescricao("Teste");
//                List<Foto> fotos = null;
//                fotos.add(objFoto1);
//                fotos.add(objFoto2);
//                fotos.add(objFoto3);
//                ocorrencia.setFotos(fotos);
//                ocorrencia.setLocal(local);
            }
        });
    }

    /**
     * Metodo para mostrar mensagem
     *
     * @param str
     */
    public void mostrarMensagem(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncWS extends AsyncTask<Void, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {

            //Converão Objecto Ocorrencia para Json
            Gson gson = new Gson();
            json = gson.toJson(ocorrencia);


            try {
                //Tentativa de conexão e inclusão ao WebService
                URL url = new URL(WS_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.connect();

                OutputStream os = conn.getOutputStream();
                os.write(json.getBytes());
                os.flush();

                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "ok";
        }
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Ative a Permissão para o Uso da Câmera", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void openCamera() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, 102);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            Bitmap foto = (Bitmap) data.getExtras().get("data");
            if (hasImage(foto1) == false) {
                foto1.setImageBitmap(foto);
                Bitmap bitmap = ((BitmapDrawable) foto1.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                image1 = baos.toByteArray();
            } else if (hasImage(foto2) == false) {
                foto2.setImageBitmap(foto);
//                Bitmap bitmap2 = ((BitmapDrawable) foto1.getDrawable()).getBitmap();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                image2 = baos.toByteArray();
            } else if (hasImage(foto3) == false) {
                foto3.setImageBitmap(foto);
                btn_tirarfoto.setEnabled(false);
//                Bitmap bitmap3 = ((BitmapDrawable) foto1.getDrawable()).getBitmap();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                image3 = baos.toByteArray();
            }
        }
    }

    private boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }

        return hasImage;
    }

    /*
            Autor:Vítor Abramo Nardelli Ferreira
            Métodos para identificação da localização do aparelho (Latitude e Longitude)
             */
    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        textView.setText("Longitude: " + longitude + "\n" + "Latitude: " + latitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
