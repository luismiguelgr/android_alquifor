package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.brulesoft.alquifor.api.MethodComentarios;
import com.brulesoft.alquifor.api.MethodPublicaciones;
import com.brulesoft.alquifor.api.MisComentariosAdapter;
import com.brulesoft.alquifor.api.MisPublicacionesAdapter;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.Publicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisComentariosActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    private MisComentariosAdapter myAdapter;
    private RecyclerView.LayoutManager lManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_comentarios);
    }

    @Override
    protected void onStart() {
        super.onStart();

        MethodComentarios service = RetrofitClient.getRetrofitInstance().create(MethodComentarios.class);
        Call<List<Comentario>> call = service.getComentariosUsuario(2);
        call.enqueue(new Callback<List<Comentario>>() {

            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {
                loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                Toast.makeText(MisComentariosActivity.this, "MAL", Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void loadDataList(List<Comentario> comentariosList) {

        // Obtener el Recycler
        myRecyclerView = (RecyclerView) findViewById(R.id.recicladorMisComentarios);
        myRecyclerView.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(MisComentariosActivity.this);
        myRecyclerView.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        myAdapter = new MisComentariosAdapter(comentariosList, MisComentariosActivity.this);
        myRecyclerView.setAdapter(myAdapter);
    }
}