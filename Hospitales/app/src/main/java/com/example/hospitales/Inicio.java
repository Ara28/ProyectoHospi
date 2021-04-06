package com.example.hospitales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.hospitales.interfaces.sedes;
import com.example.hospitales.models.Sedes;
import com.example.hospitales.models.Usuarios;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Inicio extends AppCompatActivity {
    public Button btnAmericas;
    public Button btnAngeles;
    public Button btnVivo;
    public Usuarios user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_session);

        btnAmericas = (Button) findViewById(R.id.btnHosAmericas);
        btnAngeles = (Button) findViewById(R.id.btnHosAngeles);
        btnVivo = (Button) findViewById(R.id.btnHosVivo);

        btnVivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.66/APIhospital/").addConverterFactory(GsonConverterFactory.create()).build();

                sedes sed = retrofit.create(sedes.class);
                Call<Sedes> call = sed.buscarS(1);
                call.enqueue(new Callback<Sedes>() {
                    @Override
                    public void onResponse(Call<Sedes> call, Response<Sedes> response) {
                        Sedes sedes = response.body();
                        Intent hospital = new Intent(view.getContext(), Hospital.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("hospital", sedes);
                        hospital.putExtras(bundle);
                        startActivity(hospital);
                        recibirUsuarios(view);
                    }

                    @Override
                    public void onFailure(Call<Sedes> call, Throwable t) {
                        Toast.makeText(Inicio.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        btnAngeles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.66/APIhospital/").addConverterFactory(GsonConverterFactory.create()).build();

                sedes sed = retrofit.create(sedes.class);
                Call<Sedes> call = sed.buscarS(3);
                call.enqueue(new Callback<Sedes>() {
                    @Override
                    public void onResponse(Call<Sedes> call, Response<Sedes> response) {
                        Sedes sedes = response.body();
                        Intent hospital = new Intent(view.getContext(), Hospital.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("hospital", sedes);
                        hospital.putExtras(bundle);
                        startActivity(hospital);
                        recibirUsuarios(view);
                    }

                    @Override
                    public void onFailure(Call<Sedes> call, Throwable t) {
                        Toast.makeText(Inicio.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

        btnAmericas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.66/APIhospital/").addConverterFactory(GsonConverterFactory.create()).build();

                sedes sed = retrofit.create(sedes.class);
                Call<Sedes> call = sed.buscarS(2);
                call.enqueue(new Callback<Sedes>() {
                    @Override
                    public void onResponse(Call<Sedes> call, Response<Sedes> response) {
                        Sedes sede = response.body();
                        Intent hospital = new Intent(view.getContext(), Hospital.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("hospital", sede);
                        hospital.putExtras(bundle);
                        startActivity(hospital);
                        recibirUsuarios(view);
                    }

                    @Override
                    public void onFailure(Call<Sedes> call, Throwable t) {
                        Toast.makeText(Inicio.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }

    public void recibirUsuarios(View view)
    {
        Bundle userIn = getIntent().getExtras();

        if (userIn != null)
        {
            user = (Usuarios) userIn.getSerializable("usuario");
            enviarUsuario(view, user);
            Toast.makeText(this,"ok", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"No ok", Toast.LENGTH_LONG).show();
        }
    }

    public void enviarUsuario(View view, Usuarios user)
    {
        Intent resultado = new Intent(view.getContext(), Hospital.class);

        Bundle bundleRes = new Bundle();

        bundleRes.putSerializable("usuario", user);

        resultado.putExtras(bundleRes);

    }

}