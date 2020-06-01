package com.rodobicho.rodoapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.rodobicho.rodoapp.entidade.Ocorrencia;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Classe que é responsavel pelas operações do Recycler View
 */
public class OcorrenciaAdapter extends RecyclerView.Adapter<OcorrenciaAdapter.ViewHolder> {

    private List<Ocorrencia> ocorrenciaList;

    public OcorrenciaAdapter(List<Ocorrencia> ocorrencias) {
        ocorrenciaList = ocorrencias;
    }

    @Override
    public OcorrenciaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        View contactView = inflater.inflate(R.layout.item_ocorrencia, parent, false);


        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(OcorrenciaAdapter.ViewHolder viewHolder, int position) {
        final Ocorrencia ocorrencia = ocorrenciaList.get(position);
        TextView item_descricao = viewHolder.item_descricao;
        TextView item_data = viewHolder.item_data;
        ImageView imageView = viewHolder.imageView3;

        //Decodificando string base 64 para image
        byte[] imageBytes = Base64.decode(ocorrencia.getFotos().get(0).getUrl(), Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);

        item_data.setText(ocorrencia.getCreated_at());
        item_descricao.setText(ocorrencia.getDescricao());
        imageView.setImageBitmap(decodedImage);
    }

    // Retorna a quantidade total de itens
    @Override
    public int getItemCount() {
        return ocorrenciaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_descricao;
        public TextView item_data;
        public ImageView imageView3;


        public ViewHolder(View itemView) {
            super(itemView);

            item_descricao = itemView.findViewById(R.id.item_descricao);
            item_data = itemView.findViewById(R.id.item_data);
            imageView3 = itemView.findViewById(R.id.imageView3);
        }
    }
}
