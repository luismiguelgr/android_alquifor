package com.brulesoft.alquifor.api;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brulesoft.alquifor.PublicacionActivity;
import com.brulesoft.alquifor.R;
import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.Publicacion;

import java.util.List;

public class MyAdapterComentario extends RecyclerView.Adapter<MyAdapterComentario.CustomViewHolder> {

    private Context context;
    private List<Comentario> dataList;
    private Publicacion publicacion;

    public MyAdapterComentario(List<Comentario> dataList, Context context){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {


        public final View myView;

        TextView textoComentario;

        CustomViewHolder(View itemView) {
            super(itemView);
            myView = itemView;

            textoComentario = myView.findViewById(R.id.textoComentario);

        }
    }



    @Override

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comentario_layout, parent, false);
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
        holder.textoComentario.setText(dataList.get(position).getTexto());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }



}

