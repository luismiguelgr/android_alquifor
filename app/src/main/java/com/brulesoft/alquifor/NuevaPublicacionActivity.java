package com.brulesoft.alquifor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Part;

public class NuevaPublicacionActivity extends AppCompatActivity {

    Button botonCrearNuevaPublicacion,anadirProListaNuevaPulicacion, anadirContraListaNuevaPulicacion, botonAnadirFotoNuevaPublicacion;
    TextView listaProsNuevaPublicacion, listaContraNuevaPublicacion;
    EditText tituloAnadirNuevaPublicacion, descripcionAnadirNuevaPublicacion;
    ImageView muestraImagenAnadirNuevaPublicacion;
    String rutaAbsoluta = "";
    final int FOTO_CONST = 1;
    String nombreImagen ;
    File foto = null;

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
        botonAnadirFotoNuevaPublicacion = (Button) findViewById(R.id.botonAnadirFotoNuevaPublicacion);
        muestraImagenAnadirNuevaPublicacion = (ImageView) findViewById(R.id.muestraImagenAnadirNuevaPublicacion);

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

        botonAnadirFotoNuevaPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sacarFoto(nuevaPublicacion.getId());
            }
        });


        botonCrearNuevaPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nuevaPublicacion.setTitulo(tituloAnadirNuevaPublicacion.getText().toString().trim());
                nuevaPublicacion.setDescripcion(descripcionAnadirNuevaPublicacion.getText().toString().trim());
                nuevaPublicacion.setId_usuario(1);
                nuevaPublicacion.setFoto(nombreImagen);
                RequestBody titulo = RequestBody.create(MultipartBody.FORM, tituloAnadirNuevaPublicacion.getText().toString().trim());
                RequestBody descripcion = RequestBody.create(MultipartBody.FORM, descripcionAnadirNuevaPublicacion.getText().toString().trim());
                RequestBody nombreFoto = RequestBody.create(MultipartBody.FORM, nombreImagen);
                RequestBody pros = RequestBody.create(MultipartBody.FORM, "");
                RequestBody contras = RequestBody.create(MultipartBody.FORM, "");
                RequestBody idUsuario = RequestBody.create(MultipartBody.FORM, "1");
                RequestBody imageBody = RequestBody.create(MediaType.parse("image/*"), foto);


                MethodPublicaciones service = RetrofitClient.getRetrofitInstance().create(MethodPublicaciones.class);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("imagen", nuevaPublicacion.getFoto(), imageBody);
                Call<Publicacion> call = service.addPublicacion(imagePart, titulo, descripcion, idUsuario, nombreFoto, pros, contras);
                call.enqueue(new Callback<Publicacion>() {

                    @Override
                    public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                        if(response.isSuccessful()){
//                          Toast.makeText(NuevaPublicacionActivity.this, "Publicación creada con éxito", Toast.LENGTH_SHORT).show();
                        }else{
                            Log.e("ERRRORR4", ""+response.body());
                        }
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Publicacion> call, Throwable t) {
                        Log.e("ERRRORR_ENTIDAD", ""+call.request().body());
                        Log.e("ERRRORR_ENTIDAD", ""+call.request().method());
                        Log.e("ERRRORR_ENTIDAD", ""+nuevaPublicacion.toString());
                        Log.e("ERRRORR1", ""+t.getMessage());
                        Log.e("ERRRORR2", ""+t.getCause());
                        Log.e("ERRRORR3", ""+t.getLocalizedMessage());
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

    private void sacarFoto(Integer idPublicacion){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){


            try {
                nombreImagen = "Foto_publicacion_"+idPublicacion;
                foto = crearArchivoFoto(nombreImagen);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(foto != null){
                Uri fotoUri = FileProvider.getUriForFile(NuevaPublicacionActivity.this, "com.brulesoft.alquifor", foto);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(intent, FOTO_CONST);
            }
        }
    }

    private File crearArchivoFoto(String nombreImagen){
        File fotoFile = null;
        File rutaArchivo = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        try {
            fotoFile = File.createTempFile(nombreImagen, ".jpg", rutaArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rutaAbsoluta = fotoFile.getAbsolutePath();
        return fotoFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == FOTO_CONST && resultCode == RESULT_OK){
            Uri uriPath = Uri.parse(rutaAbsoluta);
            muestraImagenAnadirNuevaPublicacion.setImageURI(uriPath);
        }
    }


}