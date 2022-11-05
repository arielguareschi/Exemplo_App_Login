package br.edu.unisep.devmob.exemploapplogin.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.File;

public class Repository {

    public SQLiteDatabase banco;
    private final Context ctx;

    public Repository(Context ctx){
        this.ctx = ctx;
        conectar();
        criarTabelas();
    }

    private void conectar(){
        try{
            String dir = ctx.getFilesDir().getAbsolutePath() + File.separator;
            banco = ctx.openOrCreateDatabase(dir + "banco.db", 0, null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void criarTabelas(){
        try{
            String sql = "CREATE TABLE IF NOT EXISTS usuario( " +
                    "  _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "  name TEXT, " +
                    "  login TEXT, " +
                    "  password TEXT, " +
                    "  email TEXT " +
                    ");";
            banco.execSQL(sql);
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(ctx, "Erro ao criar tabelas " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void fechar(){
        if (banco != null && banco.isOpen()){
            banco.close();
        }
    }

}
