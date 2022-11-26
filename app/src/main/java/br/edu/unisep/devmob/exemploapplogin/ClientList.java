package br.edu.unisep.devmob.exemploapplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import br.edu.unisep.devmob.exemploapplogin.adapter.ClientAdapter;
import br.edu.unisep.devmob.exemploapplogin.adapter.UserAdapter;
import br.edu.unisep.devmob.exemploapplogin.repository.ClientRepository;
import br.edu.unisep.devmob.exemploapplogin.repository.RepositoryUser;

public class ClientList extends AppCompatActivity {

    private Cursor cursor;
    private ClientRepository repositoryClient;
    private ListView lvClientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        this.lvClientList = findViewById(R.id.lvClientList);

        repositoryClient = new ClientRepository(ClientList.this);

        loadList();
    }

    private void loadList() {
        try {
            cursor = repositoryClient.listar();
            if (cursor.getCount() > 0) {
                String[] columns = {"_id", "name", "city"};
                int[] fields = {R.id.tvClientId, R.id.tvClientName, R.id.tvClientCity};

                ClientAdapter ClientAdapter = new ClientAdapter(ClientList.this, R.layout.activity_client_list_line, cursor, columns, fields, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

                lvClientList.setAdapter(ClientAdapter);

                lvClientList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ClientList.this, ClientRegister.class);
                        intent.putExtra("clientId", id);
                        startActivity(intent);
                        return false;
                    }
                });
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repositoryClient.fechar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_client, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_client_add){
            startActivity(new Intent(ClientList.this, ClientRegister.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        loadList();
        super.onResume();
    }
}