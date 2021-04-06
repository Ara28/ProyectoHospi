package com.example.hospitales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hospitales.interfaces.usuarios;
import com.example.hospitales.models.Usuarios;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public Button btnIngresar;
    public Button btnRegistrar;
    public EditText txtCorreo;
    public EditText txtContrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngresar = (Button) findViewById(R.id.btnIngresarIn);

        txtCorreo = (EditText) findViewById(R.id.txtCorreoIn);
        txtContrasenia = (EditText) findViewById(R.id.txtContraseniaIn);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceder(txtCorreo.getText().toString(), txtContrasenia.getText().toString(), view);
            }
        });
    }

    private void acceder(String Correo, String Contrasenia, View view)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.66/APIhospital/").addConverterFactory(GsonConverterFactory.create())
                .build();
        usuarios users = retrofit.create(usuarios.class);
        Call<Usuarios> call = users.acceder(Correo,Contrasenia);
        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                try
                {
                    if(response.isSuccessful())
                    {
                        Usuarios us = response.body();
                        String  respuesta = us.getNombre();
                        Toast.makeText(MainActivity.this,"Hola "+respuesta,Toast.LENGTH_LONG).show();

                        Intent inicio = new Intent(view.getContext(), Inicio.class);
                        Intent hospi = new Intent(view.getContext(),Hospital.class);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", us);
                        hospi.putExtras(bundle);
                        startActivity(inicio);
                    }

                }catch (Exception ex)
                {
                    Toast.makeText(MainActivity.this, "Session fallida", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}