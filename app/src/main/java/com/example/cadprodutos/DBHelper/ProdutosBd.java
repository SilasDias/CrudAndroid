package com.example.cadprodutos.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cadprodutos.model.Produtos;

import java.util.ArrayList;

public class ProdutosBd extends SQLiteOpenHelper {

    private static final String DATABASE = "bdprodutos";
    private static final int VERSION = 1;

    public ProdutosBd(Context context){
        super(context, DATABASE,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String produto = "CREATE TABLE produtos(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "nomeproduto TEXT NOT NULL, descricao TEXT NOT NULL, quantidade INTEGER);";
        sqLiteDatabase.execSQL(produto); //cria no bd produto

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String produto = "DROP TABLE IF EXISTS produtos";    //caso tenha produto substitua
        sqLiteDatabase.execSQL(produto);

    }

    // Salvar
    public void salvarProdutos(Produtos produto){
        ContentValues values = new ContentValues(); //

        values.put("nomeproduto", produto.getNomeProduto()); //
        values.put("descricao", produto.getDescricao());
        values.put("quantidade", produto.getQuantidade());

        getWritableDatabase().insert("produtos",null, values);
    }

    // Alterar
    public void alterarProduto(Produtos produto){
        ContentValues values = new ContentValues(); //

        values.put("nomeproduto", produto.getNomeProduto()); //
        values.put("descricao", produto.getDescricao());
        values.put("quantidade", produto.getQuantidade());

        String [] args = {produto.getId().toString()};   // procura argumento pelo id
        getWritableDatabase().update("produtos", values, "id:?",args);
    }

    //deletar
    public void deletarProduto(Produtos produto) {
        String [] args = {produto.getId().toString()};   // procura argumento pelo id
        getWritableDatabase().delete("produtos",  "id:?",args);
    }

    // Listar - mostrar
    public ArrayList<Produtos> getLista(){
        String [] columns = {"id", "nomeproduto", "descricao", "quantidade"}; // campos para fazer select salvar a lista
        Cursor cursor = getWritableDatabase().query("produtos", columns,null,
                null,null,null,null,null); // não precisa fazer ware
        ArrayList<Produtos> produtos = new ArrayList<Produtos>();

        // para acrescentar mais registros
        while (cursor.moveToNext()){
            Produtos produto = new Produtos();
            produto.setId(cursor.getLong(0));
            produto.setNomeProduto(cursor.getString(1));
            produto.setDescricao(cursor.getString(2));
            produto.setQuantidade(cursor.getInt(3));

            produtos.add(produto); // inseri dentro do array
        }
        return produtos;
    }
}
