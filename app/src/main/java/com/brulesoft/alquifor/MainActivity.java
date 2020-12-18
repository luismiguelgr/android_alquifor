package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.brulesoft.alquifor.api.MethodPublicaciones;
import com.brulesoft.alquifor.api.MethodUsuarios;
import com.brulesoft.alquifor.api.MyAdapter;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Publicacion;
import com.brulesoft.alquifor.models.Usuario;
import com.brulesoft.alquifor.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager lManager;
    FloatingActionButton botonAddPublicacion;
    Button botonLogin;
    String token = "";
    String email = "";
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onStart() {
        super.onStart();

        botonAddPublicacion = (FloatingActionButton) findViewById(R.id.botonAddPublicacion);
        botonLogin = (Button) findViewById(R.id.botonLogin);

        preferences = getSharedPreferences("ALQUIFOR", Context.MODE_PRIVATE);
        if(preferences.contains("TOKEN")){
            token  = preferences.getString("TOKEN",null);
            email  = preferences.getString("EMAIL",null);
            botonAddPublicacion.setVisibility(View.VISIBLE);
        }

        MethodUsuarios serviceUsuario = RetrofitClient.getRetrofitInstance().create(MethodUsuarios.class);
        Call<Usuario> callUsuario = serviceUsuario.getUsuarioEmail("Bearer "+token,email);

        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
//                Log.e("UUSUARRIOOO", ""+call.request());
//                Toast.makeText(MainActivity.this, "No"+call.request(), Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()) {
                                            String id = response.body().getId().toString();
                                            preferences.edit().putString("ID_USUARIO", id).apply();
                                            String usuario = response.body().getUsuario();
                                            preferences.edit().putString("USUARIO", usuario).apply();
                                            String nombre = response.body().getNombre();
                                            preferences.edit().putString("NOMBRE", nombre).apply();
                                            String primerApellido = response.body().getPrimer_apellido();
//                                            preferences.edit().putString("PRIMER_APELLIDO", primerApellido).apply();
//                                            String segundoApellido = response.body().getSegundo_apellido();
//                                            preferences.edit().putString("SEGUNDO_APELLIDO", segundoApellido).apply();
//                                            String fechaNacimiento = response.body().getFecha_nacimiento();
//                                            preferences.edit().putString("FECHA_NACIMIENTO", fechaNacimiento).apply();
//                                            String direccion = response.body().getDireccion();
//                                            preferences.edit().putString("DIRECCION", direccion).apply();
//                                            String ciudad = response.body().getCiudad();
//                                            preferences.edit().putString("CIUDAD", ciudad).apply();
//                                            String provincia = response.body().getProvincia();
//                                            preferences.edit().putString("PROVINCIA", provincia).apply();
//                                            Integer codigoPostal = response.body().getCodigo_postal();
//                                            preferences.edit().putInt("CODIGO_POSTAL", codigoPostal).apply();
//                                            Integer telefono = response.body().getTelefono();
//                                            preferences.edit().putInt("TELEFONO", telefono).apply();

                }else{
//                    Toast.makeText(MainActivity.this, "No"+response.errorBody(), Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(LoginActivity.this, "No se ha podido obtener el usuario", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });

        MethodPublicaciones service = RetrofitClient.getRetrofitInstance().create(MethodPublicaciones.class);
        Call<List<Publicacion>> call = service.getAllPublicaciones();
        call.enqueue(new Callback<List<Publicacion>>() {

            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
//                Toast.makeText(MainActivity.this, "Bienvenid@", Toast.LENGTH_SHORT).show();
                if(response.isSuccessful()){
                    loadDataList(response.body());
                }else{
                    Toast.makeText(MainActivity.this, "Debe estar conectado a internet", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hemos tenido problemas", Toast.LENGTH_SHORT).show();
            }

        });

        botonAddPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NuevaPublicacionActivity.class);
                startActivity(intent);
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Prueba  para confirmar que tengo token
                SharedPreferences preferences = MainActivity.this.getSharedPreferences("ALQUIFOR", Context.MODE_PRIVATE);
                String usuario2  = preferences.getString("USUARIO",null);
                String email2  = preferences.getString("EMAIL",null);
                String id2  = preferences.getString("ID_USUARIO",null);
                String nombre2  = preferences.getString("NOMBRE",null);
                String primerAp2  = preferences.getString("PRIMER_APELLIDO",null);
                String segundoAp2  = preferences.getString("SEGUNDO_APELLIDO",null);
//                String direcc2  = preferences.getString("DIRECCION",null);
//                String ciudad2  = preferences.getString("CIUDAD",null);
//                String provin2  = preferences.getString("PROVINCIA",null);
//                String codig2  = preferences.getString("CODIGO_POSTAL",null);
//                String telefo2  = preferences.getString("TELEFONO",null);
                Toast.makeText(MainActivity.this, "USUARIO"+usuario2+"EMAIL"+email2+"ID_USUARIO"+id2, Toast.LENGTH_LONG).show();
//                        "NOMBRE"+nombre2+"PRIMER_APELLIDO"+primerAp2+"SEGUNDO_APELLIDO"+segundoAp2, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(token.isEmpty()){
            getMenuInflater().inflate(R.menu.menuopcione_sin_registro, menu);
        }else{
            getMenuInflater().inflate(R.menu.menuopciones, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.salir) {
            finish();
            System.exit(0);
        }
        if (id == R.id.mis_comentarios) {
            Intent intent = new Intent(this, MisComentariosActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Mis comentarios", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.mis_publicaciones) {
            Intent intent = new Intent(this, MisPublicacionesActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Mis publicaciones", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.opcionMenuiniciarSesion) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Login", Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.opcionMenuDesconectarse) {
            SharedPreferences.Editor borrarToken = preferences.edit();
            borrarToken.clear();
            borrarToken.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Desconectado", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadDataList(List<Publicacion> usersList) {

        // Obtener el Recycler
        myRecyclerView = (RecyclerView) findViewById(R.id.reciclador);
        myRecyclerView.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(MainActivity.this);
        myRecyclerView.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        myAdapter = new MyAdapter(usersList, MainActivity.this);
        myRecyclerView.setAdapter(myAdapter);
    }


}