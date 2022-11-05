package br.edu.unisep.devmob.exemploapplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import br.edu.unisep.devmob.exemploapplogin.repository.RepositoryUser;

public class ClientList extends AppCompatActivity {

    private Cursor cursor;
    private RepositoryUser repositoryUser;
    private ListView lvUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        this.lvUserList = findViewById(R.id.lvUserList);
    }
}