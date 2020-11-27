package com.brulesoft.alquifor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.brulesoft.alquifor.api.MethodUsuarios;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Usuario;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    Button crearUsuarioRegistro;
    TextView textoUsuarioRegistro, textoPasswordRegistro, textoEmailRegistro, textoNombreRegistro, textoPrimerApellidoRegistro,
            textoSegundoApellidoRegistro, fechaNacimientoRegistro, textoDireccionRegistro, textoCiudadRegistro,
            textoProvinciaRegistro, codigoPostalRegistro, telefonoRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        crearUsuarioRegistro = findViewById(R.id.crearUsuarioRegistro);
        textoUsuarioRegistro = findViewById(R.id.textoUsuarioRegistro);
          textoPasswordRegistro = findViewById(R.id.textoPasswordRegistro);
          textoEmailRegistro = findViewById(R.id.textoEmailRegistro);
          textoNombreRegistro = findViewById(R.id.textoNombreRegistro);
          textoPrimerApellidoRegistro = findViewById(R.id.textoPrimerApellidoRegistro);
          textoSegundoApellidoRegistro = findViewById(R.id.textoSegundoApellidoRegistro);
          fechaNacimientoRegistro = findViewById(R.id.fechaNacimientoRegistro);
          textoDireccionRegistro = findViewById(R.id.textoDireccionRegistro);
          textoCiudadRegistro = findViewById(R.id.textoCiudadRegistro);
          textoProvinciaRegistro = findViewById(R.id.textoProvinciaRegistro);
          codigoPostalRegistro = findViewById(R.id.codigoPostalRegistro);
          telefonoRegistro = findViewById(R.id.telefonoRegistro);

        textoUsuarioRegistro.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCamposUsuario();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textoPasswordRegistro.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCamposUsuario();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textoEmailRegistro.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCamposUsuario();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textoNombreRegistro.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkCamposUsuario();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        crearUsuarioRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer codigoPostalUsuario = 0;
                Integer telefonoUsuario =  0;
                if(!codigoPostalRegistro.getText().toString().isEmpty()){
//                    codigoPostalRegistro.setText("0", TextView.BufferType.EDITABLE);
                    codigoPostalUsuario = Integer.parseInt(codigoPostalRegistro.getText().toString());
                }

                if(!telefonoRegistro.getText().toString().isEmpty()){
//                    codigoPostalRegistro.setText("0", TextView.BufferType.EDITABLE);
                    telefonoUsuario = Integer.parseInt(telefonoRegistro.getText().toString());
                }

                Usuario nuevoUsuario = new Usuario(
                        textoUsuarioRegistro.getText().toString(), textoPasswordRegistro.getText().toString(),
                        textoEmailRegistro.getText().toString(), textoNombreRegistro.getText().toString(),
                        textoPrimerApellidoRegistro.getText().toString(),textoSegundoApellidoRegistro.getText().toString(),
                        fechaNacimientoRegistro.getText().toString(),textoDireccionRegistro.getText().toString(),
                        textoCiudadRegistro.getText().toString(), textoProvinciaRegistro.getText().toString(),
                        codigoPostalUsuario, telefonoUsuario
                );
                MethodUsuarios service = RetrofitClient.getRetrofitInstance().create(MethodUsuarios.class);
                Call<Usuario> call = service.addUsuario(nuevoUsuario);

                call.enqueue(new Callback<Usuario>() {

                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegistroActivity.this, "Usuario creado con éxito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistroActivity.this, "El usuario no se ha podido crear", Toast.LENGTH_SHORT).show();
                            Log.e("ERRRRORRRR", ""+call.request().toString());
                            Log.e("ERRRRORRRR", ""+nuevoUsuario.toString());
                        }
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(RegistroActivity.this, "El usuario no se ha podido crear", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void checkCamposUsuario() {
        if (!textoUsuarioRegistro.getText().toString().isEmpty() && !textoPasswordRegistro.getText().toString().isEmpty() &&
            !textoEmailRegistro.getText().toString().isEmpty() && !textoNombreRegistro.getText().toString().isEmpty()
        ) {
            crearUsuarioRegistro.setEnabled(true);
        } else {
            crearUsuarioRegistro.setEnabled(false);
        }
    }
}