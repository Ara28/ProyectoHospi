package com.example.hospitales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospitales.interfaces.comentarios;
import com.example.hospitales.interfaces.sedes;
import com.example.hospitales.interfaces.usuarios;
import com.example.hospitales.models.Comentarios;
import com.example.hospitales.models.Sedes;
import com.example.hospitales.models.Usuarios;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Hospital extends AppCompatActivity {
    public TextView Nombre, Direccion, Comentario, Calificacion;
    public EditText addComentario, addCalificacion;
    public Button btnGuardar, btnLlamar;
    public int Id_usuario;
    public int Id_sede;
    public Usuarios usua = null;
    public Sedes sedesEn=null;
    private final int PHONE_CALL_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        Nombre = (TextView) findViewById(R.id.txtNombre);
        Direccion = (TextView) findViewById(R.id.txtDireccion);


        Bundle sedesIn = getIntent().getExtras();

        if (sedesIn != null)
        {
            btnLlamar = (Button) findViewById(R.id.btnLlamar);
            sedesEn = (Sedes) sedesIn.getSerializable("hospital");
            Nombre.setText(sedesEn.getNombre());
            Direccion.setText(sedesEn.getCalle()+" "+sedesEn.getColonia()+" "+sedesEn.getCp()+" "+sedesEn.getLoteymanzana());
            Id_sede= sedesEn.getId();
            btnLlamar.setText(sedesEn.getNumero());

        }else {
            Toast.makeText(Hospital.this, "Error", Toast.LENGTH_LONG).show();
        }




        cargarComentarios(Id_sede);




        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btnLlamar.getText() != null)
                {
                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
                    {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PHONE_CALL_CODE);

                        //versionNueva();
                    }else
                    {
                        versionAntriores(sedesEn.getNumero());
                    }
                }
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addComentario = (EditText) findViewById(R.id.txtAddComentario);
                 addCalificacion = (EditText) findViewById(R.id.txtComCalificacion);

                Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.66/APIhospital/").addConverterFactory(GsonConverterFactory.create())
                        .build();
                comentarios comen = retrofit.create(comentarios.class);
                Call<Comentarios> call = comen.comentar(addComentario.getText().toString(), Integer.parseInt(addCalificacion.getText().toString()), Id_sede, Id_usuario);
                call.enqueue(new Callback<Comentarios>() {
                    @Override
                    public void onResponse(Call<Comentarios> call, Response<Comentarios> response) {
                        Comentarios comentario = response.body();
                        if(response.isSuccessful()){
                            if (comentario.getComentario()!= null)
                            {
                                Toast.makeText(Hospital.this, "No se logro comentar con exito", Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Comentarios> call, Throwable t) {
                        Toast.makeText(Hospital.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void cargarComentarios(int Id_Sede)
    {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.100.66/APIhospital/").addConverterFactory(GsonConverterFactory.create())
                .build();

        comentarios comentario = retrofit.create(comentarios.class);
        Call<Comentarios> call = comentario.mostrarComentarios(Id_Sede);
        call.enqueue(new Callback<Comentarios>() {
            @Override
            public void onResponse(Call<Comentarios> call, Response<Comentarios> response) {
                if(response.isSuccessful())
                {
                    Comentario = (TextView) findViewById(R.id.txtComentario);
                    Calificacion = (TextView) findViewById(R.id.txtCalificacion);

                    Comentarios c = response.body();

                    Comentario.setText(c.getComentario());
                    Calificacion.setText(c.getCalificacion()+"");;
                }
            }

            @Override
            public void onFailure(Call<Comentarios> call, Throwable t) {
                Toast.makeText(Hospital.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PHONE_CALL_CODE:
                String permission= permissions[0];
                int result = grantResults[0];
                if(permission.equals(Manifest.permission.CALL_PHONE)){

                    if (result== PackageManager.PERMISSION_GRANTED){
                        String phoneNumber=sedesEn.getNumero();
                        Intent llamada= new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                        startActivity(llamada);


                    }
                    else{
                        Toast.makeText(this, "No aceptas el permiso", Toast.LENGTH_LONG).show();
                    }

                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void versionAntriores(String num)
    {
        Intent IntentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+num));
        if (verificarPermisos(Manifest.permission.CALL_PHONE))
        {
            startActivity(IntentLlamada);
        }else
        {
            Toast.makeText(this, "Configura los permisos", Toast.LENGTH_LONG).show();
        }
    }
    private boolean verificarPermisos(String permiso)
    {
        int resultado = this.checkCallingPermission(permiso);
        return resultado == PackageManager.PERMISSION_GRANTED;
    }
}