package br.edu.unisep.devmob.exemploapplogin.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import br.edu.unisep.devmob.exemploapplogin.R;

public class ClientAdapter  extends SimpleCursorAdapter {

    public ClientAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvClientListId = view.findViewById(R.id.tvClientId);
        TextView tvClientListName = view.findViewById(R.id.tvClientName);
        TextView tvClientListCity = view.findViewById(R.id.tvClientCity);

        tvClientListId.setText(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        tvClientListName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        tvClientListCity.setText(cursor.getString(cursor.getColumnIndexOrThrow("city")));

    }
    
}
