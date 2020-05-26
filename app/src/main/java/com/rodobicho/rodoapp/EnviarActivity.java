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
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;
import com.rodobicho.rodoapp.entidade.Foto;
import com.rodobicho.rodoapp.entidade.Local;
import com.rodobicho.rodoapp.entidade.Ocorrencia;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EnviarActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    private TextView textView;
    private Button btn_tirarfoto;
    private ImageView foto1, foto2, foto3;
    private String json;
    private int i;
    private final String WS_URL = "http://192.168.0.14:8081/rodobicho/ocorrencia/inserir";

    Ocorrencia ocorrencia;
    Local local;
    Foto objFoto1, objFoto2, objFoto3;
    byte[] image1, image2, image3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar);


        Button btn_salvar = (Button) findViewById(R.id.btn_salvar);
        Button btn_apagartudo = (Button) findViewById(R.id.btn_apagartudo);
        Button btn_voltar = (Button) findViewById(R.id.btn_voltar);


        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnviarActivity.this, MainActivity.class));
                finish();
            }
        });

        textView = (TextView) findViewById(R.id.id_textViewLATLONG);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);

        if (acct != null) {
            textView.setText(acct.getEmail());
        }

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

        // Deletando as fotos
        btn_apagartudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll();
                i = 0;
            }
        });

        //Permissão para buscar Localização
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);



        // TODO: Checar se GPS está ativo antes enviar



        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                askLocationPermission();
            }
        });
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 202);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 203);
            }
        } else {
            send();
        }
    }

    public void send() {
        final EditText edt_descricao = (EditText) findViewById(R.id.edt_descricao);
        final String descricao = edt_descricao.getText().toString();

        if (descricao.length() == 0) {
            edt_descricao.setError("É necessária a descrição para a ocorrência");
            return;
        }

        if (hasImage(foto1) == false) {
            mostrarMensagem("É necessário pelo menos uma foto");
            return;
        }

        ocorrencia = new Ocorrencia();
        objFoto1 = new Foto();
        objFoto2 = new Foto();
        objFoto3 = new Foto();
        local = new Local();

        if (image1 != null) {
            objFoto1.setUrl(Base64.encodeToString(image1, Base64.DEFAULT).replace("\n", ""));
        }
        if (image2 != null) {
            objFoto2.setUrl(Base64.encodeToString(image2, Base64.DEFAULT).replace("\n", ""));
        }
        if (image3 != null) {
            objFoto3.setUrl(Base64.encodeToString(image3, Base64.DEFAULT).replace("\n", ""));
        }

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Aqui recebemos a ultima localizacao obtida do usuário pela INTERNET
            Location localizacaoViaInternet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            // Aqui recebemos a ultima localizacao obtida do usuário pelo GPS
            Location localizacaoViaGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //Verificamos se foi obtido a localização via INTERNET
            if (localizacaoViaInternet != null) {

                if (locationManager != null) {
                    local.setLatitude(localizacaoViaInternet.getLatitude());
                    local.setLongitude(localizacaoViaInternet.getLongitude());
                }

            }
            //Verificamos se foi obtido a localização via GPS
            else if (localizacaoViaGPS != null) {
                if (locationManager != null) {
                    local.setLatitude(localizacaoViaGPS.getLatitude());
                    local.setLongitude(localizacaoViaGPS.getLongitude());
                }
            }
        }
        List<Foto> fotos = new ArrayList<Foto>();
        if (objFoto1.getUrl() != null) {
            fotos.add(objFoto1);
        }
        if (objFoto2.getUrl() != null) {
            fotos.add(objFoto2);
        }
        if (objFoto3.getUrl() != null) {
            fotos.add(objFoto3);
        }

        ocorrencia.setDescricao(edt_descricao.getText().toString());
        ocorrencia.setFotos(fotos);
        ocorrencia.setLocal(local);

        //dispara chamada assincrona para inclusão no WS
        new AsyncWS().execute();
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

                mostrarMensagem(String.valueOf(conn.getResponseCode()));

                conn.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "ok";
        }
    }

    private void deleteAll() {
        foto1.setImageResource(0);
        foto2.setImageResource(0);
        foto3.setImageResource(0);
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 104);
            }
        } else {
            if (i < 3) {
                openCamera();
                i++;
            } else if (i >= 3) {
                mostrarMensagem("Limite de 3 fotos atingido");
            }
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
        if (requestCode == 202) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Ative a Permissão para o Uso do Local", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 203) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Ative a Permissão para o Uso do Local", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == 104) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Ative a Permissão para o Uso de Fotos e Mídia", Toast.LENGTH_SHORT).show();
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
            try {
                Bitmap foto = (Bitmap) data.getExtras().get("data");
                if (hasImage(foto1) == false) {
                    foto1.setImageBitmap(foto);
                    Bitmap bitmap = ((BitmapDrawable) foto1.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    image1 = baos.toByteArray();
                } else if (hasImage(foto2) == false) {
                    foto2.setImageBitmap(foto);
                    Bitmap bitmap2 = ((BitmapDrawable) foto2.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    image2 = baos.toByteArray();
                } else if (hasImage(foto3) == false) {
                    foto3.setImageBitmap(foto);
                    Bitmap bitmap3 = ((BitmapDrawable) foto3.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    image3 = baos.toByteArray();
                }
            } catch (Exception e) {
                finish();
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


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            textView.setText("Longitude: " + longitude + "\n" + "Latitude: " + latitude);
        }
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
