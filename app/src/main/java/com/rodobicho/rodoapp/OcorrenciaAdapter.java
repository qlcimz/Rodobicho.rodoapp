package com.rodobicho.rodoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//import androidx.recyclerview.widget.RecyclerView;

import com.rodobicho.rodoapp.entidade.Ocorrencia;

import java.util.List;

/**
 * Classe que é responsavel pelas operações do Recycler View
 */
//public class OcorrenciaAdapter extends
//        //RecyclerView.Adapter<OcorrenciaAdapter.ViewHolder> {
//
//    private List<Ocorrencia> ocorrenciaList;
//
//    public OcorrenciaAdapter(List<Ocorrencia> ocorrencias) {
//        ocorrenciaList = ocorrencias;
//    }
//
//    @Override
//   // public OcorrenciaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//
//        View contactView = inflater.inflate(R.layout.item_ocorrencia, parent, false);
//
//
//        ViewHolder viewHolder = new ViewHolder(contactView);
//        return viewHolder;
//    }
//
//
//    @Override
//    public void onBindViewHolder(OcorrenciaAdapter.ViewHolder viewHolder, int position) {
//        final Ocorrencia ocorrencia = ocorrenciaList.get(position);
//        TextView item_descricao = viewHolder.item_descricao;
//        item_descricao.setText(ocorrencia.getDescricao());
//    }
//
//    // Retorna a quantidade total de itens
//    @Override
//    public int getItemCount() {
//        return ocorrenciaList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView item_descricao;
//
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            item_descricao = itemView.findViewById(R.id.item_descricao);
//        }
//    }
//}
