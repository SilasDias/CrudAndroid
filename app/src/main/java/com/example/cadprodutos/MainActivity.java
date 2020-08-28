package com.example.cadprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cadprodutos.DBHelper.ProdutosBd;
import com.example.cadprodutos.model.Produtos;


import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lista;
    ProdutosBd bdHelper;
    ArrayList<Produtos> listview_Produtos;
    Produtos produto;
    ArrayAdapter adapter; // pesquisar sobre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Implementação do botao cadastrar
        Button btnCadastrar = (Button) findViewById(R.id.btn_Cadastrar);
        btnCadastrar.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioProdutos.class);
                startActivity(intent);
            }
        });

        lista = (ListView) findViewById(R.id.listview_Produtos);
        registerForContextMenu(lista);
        //click longo
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                Produtos produtoEscolhido = (Produtos) adapter.getItemAtPosition(position);

                Intent i = new Intent(MainActivity.this, FormularioProdutos.class);
                i.putExtra("produto-escolhido", produtoEscolhido);
                startActivity(i);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
                produto = (Produtos) adapter.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuDelete = menu.add("Deletar Este Produto");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                bdHelper = new ProdutosBd(MainActivity.this);
                bdHelper.deletarProduto(produto);
                bdHelper.close();
                carregarProduto();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarProduto();
    }

    public void carregarProduto(){
        bdHelper = new ProdutosBd(MainActivity.this);
        listview_Produtos = bdHelper.getLista();
        bdHelper.close();

        if (listview_Produtos != null){
            adapter = new ArrayAdapter<Produtos>(MainActivity.this, android.R.layout.simple_list_item_1, listview_Produtos);
            lista.setAdapter(adapter);
        }
        //  finish();
    }

}





