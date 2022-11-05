package br.edu.unisep.devmob.exemploapplogin.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import br.edu.unisep.devmob.exemploapplogin.R;

public class UserAdapter extends SimpleCursorAdapter {


    public UserAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvUserListId = view.findViewById(R.id.tvUserListId);
        TextView tvUserListLogin = view.findViewById(R.id.tvUserListLogin);
        TextView tvUserListName = view.findViewById(R.id.tvUserListName);

        tvUserListId.setText(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        tvUserListLogin.setText(cursor.getString(cursor.getColumnIndexOrThrow("login")));
        tvUserListName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));

    }
}
