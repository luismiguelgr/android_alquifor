package com.brulesoft.alquifor.api;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brulesoft.alquifor.PublicacionActivity;
import com.brulesoft.alquifor.R;
import com.brulesoft.alquifor.models.Publicacion;
import com.brulesoft.alquifor.utiles.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder> {

    private Context context;
    private List<Publicacion> dataList;
    private Publicacion publicacion;

    public MyAdapter(List<Publicacion> dataList, Context context){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {


        public final View myView;

        TextView tituloPublicacion;
        TextView visitasPublicacion;
        ImageView fotoPublicacion;

        CustomViewHolder(View itemView) {
            super(itemView);
            myView = itemView;

            tituloPublicacion = myView.findViewById(R.id.usuarioComentario);
            fotoPublicacion = myView.findViewById(R.id.fotoPublicacion);
            visitasPublicacion = myView.findViewById(R.id.fechaComentario);

        }
    }



    @Override

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.publicaciones_card, parent, false);
        return new CustomViewHolder(view);
    }

    @Override

    public void onBindViewHolder(CustomViewHolder holder, int position) {
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

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }



}

