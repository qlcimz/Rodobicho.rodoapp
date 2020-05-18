//package com.rodobicho.rodoapp.sqlite;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import com.rodobicho.rodoapp.entidade.Ocorrencia;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class OcorrenciaDB extends SQLiteOpenHelper {
//    public static final String NOME_BANCO = "com.rodobicho.rodoapp.sqlite";
//    private static final int VERSAO_BANCO = 1;
//    private static final String TAG = "OcorrenciaDB";
//    private static final String tb_ocorrencia = "ocorrencia";
//    private static final String col_id = "id";
//    private static final String col_descricao = "descricao";
//
//    public OcorrenciaDB(Context context) {
//        super(context, NOME_BANCO, null, VERSAO_BANCO);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        StringBuffer sql = new StringBuffer();
//        sql.append(" CREATE TABLE IF NOT EXISTS " + tb_ocorrencia + " (" + col_id + " LONG PRIMARY KEY, " + col_descricao + " TEXT)");
//        sqLiteDatabase.execSQL(sql.toString());
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//
//    public List<Ocorrencia> listarTodos() throws Exception {
//        SQLiteDatabase db = this.getReadableDatabase();
//        List<Ocorrencia> lista = new ArrayList<Ocorrencia>();
//        String query = "SELECT * FROM " + tb_ocorrencia;
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                Ocorrencia ocorrencia = new Ocorrencia();
//                ocorrencia.setId(Long.parseLong((cursor.getString(0))));
//                ocorrencia.setDescricao(cursor.getString(1));
//                lista.add(ocorrencia);
//            } while (cursor.moveToNext());
//        }
//        return lista;
//    }
//
//    public void salvar(Ocorrencia ocorrencia) throws Exception {
//        SQLiteDatabase db = getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(col_id, ocorrencia.getId());
//        values.put(col_descricao, ocorrencia.getDescricao());
//        db.insert(tb_ocorrencia,null,values);
//        db.close();
//
////        try {
////            ContentValues values = new ContentValues();
////            values.put(col_id, ocorrencia.getId());
////            values.put(col_descricao, ocorrencia.getDescricao());
////
////
////            if (ocorrencia.getId() != 0) {
////                String[] whereArgs = new String[]{(String.valueOf(ocorrencia.getId()))};
////                if (db.update("ocorrencia", values, "id=?", whereArgs) == 0) {
////                    Log.d(TAG, "Não encontrado registro para alteração!");
////                    throw new Exception("Registro não encontrado!");
////
////                }
////            } else {
////                ocorrencia.setId((long) db.insert("ocorrencia", "", values));
////            }
////        } catch (Exception e) {
////            Log.d(TAG, "Erro ao salvar no banco:");
////            e.printStackTrace();
////            throw e;
////        } finally {
////            db.close();
////        }
//    }
//
//    public void excluir(int id) throws Exception {
//        SQLiteDatabase db = getWritableDatabase();
//
//        try {
//            String[] whereArgs = new String[]{(String.valueOf(id))};
//            db.delete("ocorrencia", "id=?", whereArgs);
//        } catch (Exception e) {
//            Log.d(TAG, "Erro ao excluir no banco:");
//            e.printStackTrace();
//            throw e;
//        } finally {
//            db.close();
//        }
//    }
//}
