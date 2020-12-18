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
        import android.widget.TextView;
        import android.widget.Toast;

        import com.brulesoft.alquifor.MisComentariosActivity;
        import com.brulesoft.alquifor.PublicacionActivity;
        import com.brulesoft.alquifor.R;
        import com.brulesoft.alquifor.models.Comentario;

        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class MisComentariosAdapter extends RecyclerView.Adapter<MisComentariosAdapter.CustomViewHolder> {

    private Context context;
    private List<Comentario> dataList;
    private Comentario comentario;
    SharedPreferences preferences;

    public MisComentariosAdapter(List<Comentario> dataList, Context context){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {


        public final View myView;

        TextView textoComentarioCardMiComentario;
        TextView fechaComentarioMiComentario;
        ImageButton botonEliminarMiComentario;

        CustomViewHolder(View itemView) {
            super(itemView);
            myView = itemView;

            textoComentarioCardMiComentario = myView.findViewById(R.id.textoComentarioCardMiComentario);
            fechaComentarioMiComentario = myView.findViewById(R.id.fechaComentarioMiComentario);
            botonEliminarMiComentario = myView.findViewById(R.id.botonEliminarMiComentario);

        }
    }



    @Override

    public MisComentariosAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.mis_comentarios_card, parent, false);
        return new MisComentariosAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MisComentariosAdapter.CustomViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PublicacionActivity.class);
                intent.putExtra("id", dataList.get(position).getIdPublicacion());
                context.startActivity(intent);
            }
        });
        holder.textoComentarioCardMiComentario.setText(dataList.get(position).getTexto());
        holder.fechaComentarioMiComentario.setText(dataList.get(position).getFechaCreacion());
        holder.botonEliminarMiComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Eliminar comentario");
                builder.setMessage("Está seguro de eliminar comentario");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        preferences = context.getSharedPreferences("ALQUIFOR", Context.MODE_PRIVATE);
                        String token  = preferences.getString("TOKEN",null);

                        Intent intent = new Intent(v.getContext(), MisComentariosActivity.class);
                        MethodComentarios service = RetrofitClient.getRetrofitInstance().create(MethodComentarios.class);
                        Call<Comentario> call = service.deleteComentario("Bearer "+token, dataList.get(position).getId());

                        call.enqueue(new Callback<Comentario>() {

                            @Override
                            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                                if(response.isSuccessful()){
                                    Toast.makeText(context, "Comentario eliminado con éxito", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Comentario> call, Throwable t) {
                                Toast.makeText(context, "No se ha podido eliminar el comentario", Toast.LENGTH_SHORT).show();
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