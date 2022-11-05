package br.edu.unisep.devmob.exemploapplogin.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.edu.unisep.devmob.exemploapplogin.model.User;

public class RepositoryUser extends Repository {

    private final SQLiteDatabase banco;
    private final Context ctx;
    private Cursor c;

    public RepositoryUser(Context ctx) {
        super(ctx);
        banco = super.banco;
        this.ctx = ctx;
    }

    public void inserir(User user) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("name", user.getName());
            cv.put("login", user.getLogin());
            cv.put("password", user.getPassword());
            cv.put("email", user.getEmail());
            banco.insert("usuario", null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editar(User user) {
        try {
            String where = "_id=?";
            String[] args = {user.getId().toString()};
            ContentValues cv = new ContentValues();
            cv.put("name", user.getName());
            cv.put("login", user.getLogin());
            cv.put("password", user.getPassword());
            cv.put("email", user.getEmail());
            banco.update("usuario", cv, where, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remover(User user) {
        try {
            String where = "_id=?";
            String[] args = {user.getId().toString()};
            banco.delete("usuario", where, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor listar() {
        try {
            String sql = "select * from usuario";
            return banco.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cursor buscarWhere(String field, String filter) {
        try {
            String sql = "select * from usuario ";
            if (!field.equalsIgnoreCase("")) {
                sql += "where upper(" + field + ") like upper('%" + filter + "%')";
            }
            return banco.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User buscarUnico(Long id) {
        try {
            User user = new User();
            String sql = "select * from usuario where _id = ?";
            String[] args = {id.toString()};
            c = banco.rawQuery(sql, args);
            while (c.moveToNext()) {
                user.setId(c.getLong(c.getColumnIndexOrThrow("_id")));
                user.setName(c.getString(c.getColumnIndexOrThrow("name")));
                user.setLogin(c.getString(c.getColumnIndexOrThrow("login")));
                user.setPassword(c.getString(c.getColumnIndexOrThrow("password")));
                user.setEmail(c.getString(c.getColumnIndexOrThrow("email")));
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkLogin(User user) {
        try {
            String sql = "select * " +
                    "from usuario " +
                    "where login = ? " +
                    "  and password = ?";
            String[] args = {user.getLogin(), user.getPassword()};
            c = banco.rawQuery(sql, args);
            boolean retorno = (c.getCount() > 0);
            if (c != null && !c.isClosed()) {
                c.close();
            }
            return retorno;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
