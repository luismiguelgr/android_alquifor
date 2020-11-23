package com.brulesoft.alquifor.api;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.brulesoft.alquifor.PublicacionActivity;
import com.brulesoft.alquifor.R;
import com.brulesoft.alquifor.models.Comentario;
import com.brulesoft.alquifor.models.Publicacion;

import java.text.ParseException;
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

        TextView textoComentarioCard1, fechaComentarioCard;

        CustomViewHolder(View itemView) {
            super(itemView);
            myView = itemView;

            textoComentarioCard1 = myView.findViewById(R.id.textoComentarioCard1);
            textoComentarioCard1.setMovementMethod(new ScrollingMovementMethod());
            fechaComentarioCard = myView.findViewById(R.id.fechaComentarioCard);
            fechaComentarioCard.setMovementMethod(new ScrollingMovementMethod());
        }
    }

    @Override

    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comentario_card, parent, false);
        return new CustomViewHolder(view);
    }

    @Override

    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.textoComentarioCard1.setText(dataList.get(position).getTexto());
        holder.fechaComentarioCard.setText(dataList.get(position).getFechaCreacion());
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }



}

