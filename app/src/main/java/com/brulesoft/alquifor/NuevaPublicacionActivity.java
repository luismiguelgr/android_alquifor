package com.brulesoft.alquifor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.brulesoft.alquifor.api.MethodPublicaciones;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Publicacion;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuevaPublicacionActivity extends AppCompatActivity {

    Button botonCrearNuevaPublicacion,anadirProListaNuevaPulicacion, anadirContraListaNuevaPulicacion;
    TextView listaProsNuevaPublicacion, listaContraNuevaPublicacion;
    EditText tituloAnadirNuevaPublicacion, descripcionAnadirNuevaPublicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_publicacion);

        botonCrearNuevaPublicacion = (Button) findViewById(R.id.botonCrearNuevaPublicacion);
        tituloAnadirNuevaPublicacion = (EditText) findViewById(R.id.tituloAnadirNuevaPublicacion);
        descripcionAnadirNuevaPublicacion = (EditText) findViewById(R.id.descripcionAnadirNuevaPublicacion);
        anadirProListaNuevaPulicacion = (Button) findViewById(R.id.anadirProListaNuevaPulicacion);
        listaProsNuevaPublicacion = (TextView) findViewById(R.id.listaProsNuevaPublicacion);
        anadirContraListaNuevaPulicacion = (Button) findViewById(R.id.anadirContraListaNuevaPulicacion);
        listaContraNuevaPublicacion = (TextView) findViewById(R.id.listaContraNuevaPublicacion);

        Publicacion nuevaPublicacion = new Publicacion();
        anadirProListaNuevaPulicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(NuevaPublicacionActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(NuevaPublicacionActivity.this);
                builder.setTitle("Añadir pro");
                builder.setView(input);
                builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nuevaPublicacion.addPro(input.getText().toString().trim());
                        listaProsNuevaPublicacion.setText(input.getText().toString().trim());
                    }
                });
                builder.show();
            }
        });

        anadirContraListaNuevaPulicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText input = new EditText(NuevaPublicacionActivity.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(NuevaPublicacionActivity.this);
                builder.setTitle("Añadir contra");
                builder.setView(input);;
                builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nuevaPublicacion.addContra(input.getText().toString().trim());
                        listaContraNuevaPublicacion.setText(input.getText().toString().trim());
                    }
                });
                builder.show();
            }
        });

        botonCrearNuevaPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nuevaPublicacion.setTitulo(tituloAnadirNuevaPublicacion.getText().toString().trim());
                nuevaPublicacion.setDescripcion(descripcionAnadirNuevaPublicacion.getText().toString().trim());
                nuevaPublicacion.setId_usuario(1);

                MethodPublicaciones service = RetrofitClient.getRetrofitInstance().create(MethodPublicaciones.class);
                Call<Publicacion> call = service.addPublicacion(nuevaPublicacion);
                call.enqueue(new Callback<Publicacion>() {

                    @Override
                    public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                        if(response.isSuccessful()){
                            Log.e("PUBLICACION", ""+nuevaPublicacion.toString());
//                            Toast.makeText(NuevaPublicacionActivity.this, "Publicación creada con éxito", Toast.LENGTH_SHORT).show();
                            Toast.makeText(NuevaPublicacionActivity.this, "P"+nuevaPublicacion.toString(), Toast.LENGTH_LONG).show();

                        }else{
                            Log.e("ERRRORR_ENTIDAD", ""+call.request().body());
                            Log.e("ERRRORR_ENTIDAD", ""+nuevaPublicacion.toString());
                            Log.e("ERRRORR1", ""+response.errorBody());
                            Log.e("ERRRORR2", ""+response.raw());
                            Log.e("ERRRORR3", ""+response.message().toString());
                            Log.e("ERRRORR4", ""+response.body());
                        }
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Publicacion> call, Throwable t) {
                        Toast.makeText(NuevaPublicacionActivity.this, "MAL", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }




}