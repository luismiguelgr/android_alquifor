package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.brulesoft.alquifor.api.MethodPublicaciones;
import com.brulesoft.alquifor.api.MisPublicacionesAdapter;
import com.brulesoft.alquifor.api.MyAdapter;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Publicacion;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisPublicacionesActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private MisPublicacionesAdapter myAdapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_publicaciones);
    }

    @Override
    protected void onStart() {
        super.onStart();

        MethodPublicaciones service = RetrofitClient.getRetrofitInstance().create(MethodPublicaciones.class);
        Call<List<Publicacion>> call = service.getAllPublicacionesUsuario(1);
        call.enqueue(new Callback<List<Publicacion>>() {

            @Override
            public void onResponse(Call<List<Publicacion>> call, Response<List<Publicacion>> response) {
                loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Publicacion>> call, Throwable t) {
                Toast.makeText(MisPublicacionesActivity.this, "MAL", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void loadDataList(List<Publicacion> usersList) {

        // Obtener el Recycler
        myRecyclerView = (RecyclerView) findViewById(R.id.recicladorMisPublicaciones);
        myRecyclerView.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(MisPublicacionesActivity.this);
        myRecyclerView.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        myAdapter = new MisPublicacionesAdapter(usersList, MisPublicacionesActivity.this);
        myRecyclerView.setAdapter(myAdapter);
    }
}