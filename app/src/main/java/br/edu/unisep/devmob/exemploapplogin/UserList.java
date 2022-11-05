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

import br.edu.unisep.devmob.exemploapplogin.adapter.UserAdapter;
import br.edu.unisep.devmob.exemploapplogin.repository.Repository;
import br.edu.unisep.devmob.exemploapplogin.repository.RepositoryUser;

public class UserList extends AppCompatActivity {

    private Cursor cursor;
    private RepositoryUser repositoryUser;
    private ListView lvUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        this.lvUserList = findViewById(R.id.lvUserList);

        repositoryUser = new RepositoryUser(UserList.this);

        loadList();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        repositoryUser.fechar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_user, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_user_add){
            startActivity(new Intent(UserList.this, UserRegister.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadList() {
        try {
            cursor = repositoryUser.listar();
            if (cursor.getCount() > 0) {
                String[] columns = {"_id", "name", "login", "password", "email"};
                int[] fields = {R.id.tvUserListId, R.id.tvUserListLogin, R.id.tvUserListName};

                UserAdapter userAdapter = new UserAdapter(UserList.this, R.layout.activity_user_list_line, cursor, columns, fields, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

                lvUserList.setAdapter(userAdapter);

                lvUserList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(UserList.this, UserRegister.class);
                        intent.putExtra("userId", id);
                        startActivity(intent);
                        return false;
                    }
                });
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}