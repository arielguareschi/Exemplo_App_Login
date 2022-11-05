package br.edu.unisep.devmob.exemploapplogin.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.unisep.devmob.exemploapplogin.model.Client;
import br.edu.unisep.devmob.exemploapplogin.model.User;

public class ClientRepository extends Repository {

    private final SQLiteDatabase banco;
    private final Context ctx;
    private Cursor c;

    public ClientRepository(Context ctx) {
        super(ctx);
        banco = super.banco;
        this.ctx = ctx;
    }

    public void inserir(Client client) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("name", client.getName());
            cv.put("address", client.getAddress());
            cv.put("city", client.getCity());
            cv.put("phone", client.getPhone());
            cv.put("cellphone", client.getCellphone());
            cv.put("active", client.getActive());
            banco.insert("cliente", null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar(Client client) {
        try {
            String where = "_id=?";
            String[] args = {client.getId().toString()};
            ContentValues cv = new ContentValues();
            cv.put("name", client.getName());
            cv.put("address", client.getAddress());
            cv.put("city", client.getCity());
            cv.put("phone", client.getPhone());
            cv.put("cellphone", client.getCellphone());
            cv.put("active", client.getActive());
            banco.update("cliente", cv, where, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remover(Client client) {
        try {
            String where = "_id=?";
            String[] args = {client.getId().toString()};
            banco.delete("cliente", where, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor listar() {
        try {
            String sql = "select * from cliente";
            return banco.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cursor buscarWhere(String field, String filter) {
        try {
            String sql = "select * from cliente ";
            if (!field.equalsIgnoreCase("")) {
                sql += "where upper(" + field + ") like upper('%" + filter + "%')";
            }
            return banco.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Client buscarUnico(Long id) {
        try {
            Client client = new Client();
            String sql = "select * from cliente where _id = ?";
            String[] args = {id.toString()};
            c = banco.rawQuery(sql, args);
            while (c.moveToNext()) {
                client.setId(c.getLong(c.getColumnIndexOrThrow("_id")));
                client.setName(c.getString(c.getColumnIndexOrThrow("name")));
                client.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                client.setCellphone(c.getString(c.getColumnIndexOrThrow("cellphone")));
                client.setPhone(c.getString(c.getColumnIndexOrThrow("phone")));
                client.setCity(c.getString(c.getColumnIndexOrThrow("city")));
                client.setActive(c.getInt(c.getColumnIndexOrThrow("active")));
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
            return client;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void fechar() {
        super.fechar();
        if (c != null && !c.isClosed()) {
            c.close();
        }
        if (banco != null) {
            banco.close();
        }
    }

}
