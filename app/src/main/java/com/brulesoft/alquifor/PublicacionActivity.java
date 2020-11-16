package com.brulesoft.alquifor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.brulesoft.alquifor.api.GetPublicaciones;
import com.brulesoft.alquifor.api.MyAdapter;
import com.brulesoft.alquifor.api.RetrofitClient;
import com.brulesoft.alquifor.models.Publicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicacionActivity extends AppCompatActivity {

    TextView idPublicacionSeleccionada;
    TextView descripcionPublicacionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);

        idPublicacionSeleccionada = (TextView) findViewById(R.id.idPublicacionSeleccionada);
        descripcionPublicacionSeleccionada = (TextView) findViewById(R.id.descripcionPublicacionSeleccionada);
        Integer id = getIntent().getIntExtra("id", 0);
        idPublicacionSeleccionada.setText(id.toString());


        GetPublicaciones service = RetrofitClient.getRetrofitInstance().create(GetPublicaciones.class);
        Call<Publicacion> call = service.getPublicacion(id);
        call.enqueue(new Callback<Publicacion>() {

            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                Toast.makeText(PublicacionActivity.this, "BIEN", Toast.LENGTH_SHORT).show();
//               loadDataList(response.body());
                descripcionPublicacionSeleccionada.setText(response.body().getDescripcion());
            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
                Toast.makeText(PublicacionActivity.this, "MAL", Toast.LENGTH_SHORT).show();
            }

        });
    }


}