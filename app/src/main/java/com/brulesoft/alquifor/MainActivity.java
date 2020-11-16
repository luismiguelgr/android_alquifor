package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.brulesoft.alquifor.api.GetPublicaciones;
import com.brulesoft.alquifor.api.MyAdapter;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Publicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetPublicaciones service = RetrofitClient.getRetrofitInstance().create(GetPublicaciones.class);
        Call<List<Publicacion>> call = service.getAllPublicaciones();
        call.enqueue(new Callback<List<Publicacion>>() {

            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                Toast.makeText(MainActivity.this, "BIEN", Toast.LENGTH_SHORT).show();
                loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "MAL", Toast.LENGTH_SHORT).show();
            }

        });


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
//        myRecyclerView = findViewById(R.id.myRecyclerView);
//        myAdapter = new MyAdapter(usersList);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
//        myRecyclerView.setLayoutManager(layoutManager);
//        myRecyclerView.setAdapter(myAdapter);
    }




}