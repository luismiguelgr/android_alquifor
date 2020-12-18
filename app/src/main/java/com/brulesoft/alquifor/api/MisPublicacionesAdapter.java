package com.brulesoft.alquifor.api;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brulesoft.alquifor.MisPublicacionesActivity;
import com.brulesoft.alquifor.PublicacionActivity;
import com.brulesoft.alquifor.R;
import com.brulesoft.alquifor.models.Publicacion;
import com.brulesoft.alquifor.utiles.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisPublicacionesAdapter extends RecyclerView.Adapter<MisPublicacionesAdapter.CustomViewHolder> {

    private Context context;
    private List<Publicacion> dataList;
    private Publicacion publicacion;
    private SharedPreferences preferences;

    public MisPublicacionesAdapter(List<Publicacion> dataList, Context context){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {


        public final View myView;

        TextView tituloPublicacion;
        TextView visitasPublicacion;
        ImageView fotoPublicacion;
        ImageButton botonEliminarMiPublicacion;

        CustomViewHolder(View itemView) {
            super(itemView);
            myView = itemView;

            tituloPublicacion = myView.findViewById(R.id.usuarioComentario);
            fotoPublicacion = myView.findViewById(R.id.fotoPublicacion);
            visitasPublicacion = myView.findViewById(R.id.fechaComentarioMiComentario);
            botonEliminarMiPublicacion = myView.findViewById(R.id.botonEliminarMiPublicacion);

        }
    }



    @Override

    public MisPublicacionesAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.mis_publicaciones_card, parent, false);
        return new MisPublicacionesAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MisPublicacionesAdapter.CustomViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PublicacionActivity.class);
                intent.putExtra("id", dataList.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.tituloPublicacion.setText(dataList.get(position).getTitulo());
        Picasso.get().load(dataList.get(position).getFoto()).transform(new CircleTransform()).error(context.getResources().getDrawable(R.drawable.sin_imagen)).into(holder.fotoPublicacion);
        holder.visitasPublicacion.setText(dataList.get(position).getVisitas().toString());
        holder.botonEliminarMiPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Eliminar publicación");
                builder.setMessage("Está seguro de eliminar la publicación");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        preferences = context.getSharedPreferences("ALQUIFOR", Context.MODE_PRIVATE);
                        String token  = preferences.getString("TOKEN",null);

                        Intent intent = new Intent(v.getContext(), MisPublicacionesActivity.class);
                        MethodPublicaciones service = RetrofitClient.getRetrofitInstance().create(MethodPublicaciones.class);
                        Call<Publicacion> call = service.deletePublicacion("Bearer "+token, dataList.get(position).getId());

                        call.enqueue(new Callback<Publicacion>() {

                            @Override
                            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(context, "Publicación eliminada con éxito", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Publicacion> call, Throwable t) {
                                Toast.makeText(context, "No se ha podido eliminar la publiación", Toast.LENGTH_SHORT).show();
                            }

                        });
                        context.startActivity(intent);
                    }
                });
                builder.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

}