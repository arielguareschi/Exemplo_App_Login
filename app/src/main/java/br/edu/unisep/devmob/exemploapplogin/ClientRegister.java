package br.edu.unisep.devmob.exemploapplogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import br.edu.unisep.devmob.exemploapplogin.model.Client;
import br.edu.unisep.devmob.exemploapplogin.model.User;
import br.edu.unisep.devmob.exemploapplogin.repository.ClientRepository;
import br.edu.unisep.devmob.exemploapplogin.repository.RepositoryUser;

public class ClientRegister extends AppCompatActivity {

    EditText etName;
    EditText etAdress;
    EditText etCity;
    EditText etPhone;
    EditText etCellPhone;
    SwitchMaterial swActive;

    private Client client;

    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        etName = findViewById(R.id.etName);
        etAdress = findViewById(R.id.etAdress);
        etCity = findViewById(R.id.etCity);
        etPhone = findViewById(R.id.etPhone);
        etCellPhone = findViewById(R.id.etCellPhone);
        swActive = findViewById(R.id.swActive);

        client = new Client();

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            Long id = extras.getLong("clientId");
            ClientRepository clientRepository = new ClientRepository(ClientRegister.this);
            try {
                client = clientRepository.buscarUnico(id);

                if (client != null) {
                    etName.setText(client.getName());
                    etAdress.setText(client.getAddress());
                    etCity.setText(client.getCity());
                    etPhone.setText(client.getPhone());
                    etCellPhone.setText(client.getCellphone());

                }

            } finally {
                clientRepository.fechar();
            }
        }

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            ClientRepository rpclient = new ClientRepository(ClientRegister.this);
            try {
                client.setName(etName.getText().toString());
                client.setAddress(etAdress.getText().toString());
                client.setCity(etCity.getText().toString());
                client.setPhone(etPhone.getText().toString());
                client.setCellphone(etCellPhone.getText().toString());

                if (client.getId() != null) {
                    rpclient.editar(client);
                } else {
                    rpclient.inserir(client);
                }

                Toast.makeText(ClientRegister.this, "Cliente salvo com sucesso!", Toast.LENGTH_SHORT).show();
            } finally {
                rpclient.fechar();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_client_register, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_client_delete) {
            if (client.getId() != null) {
                ClientRepository rpclient = new ClientRepository(ClientRegister.this);
                try {
                    rpclient.remover(client);
                    Toast.makeText(ClientRegister.this, "Cliente removido com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                } finally {
                    rpclient.fechar();
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
}