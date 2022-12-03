package br.edu.unisep.devmob.exemploapplogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

import br.edu.unisep.devmob.exemploapplogin.async.CityAsync;
import br.edu.unisep.devmob.exemploapplogin.async.ForecastAsync;

public class MenuPrincipal extends AppCompatActivity {

    private TextView tvName, tvCityReturn;
    private Button btnClient;
    private Button btnUser, btnCity;
    private EditText etCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Bundle extras = getIntent().getExtras();

        tvName = findViewById(R.id.tvName);
        btnClient = findViewById(R.id.btnClient);
        btnUser = findViewById(R.id.btnUser);
        tvCityReturn = findViewById(R.id.tvCityReturn);
        btnCity = findViewById(R.id.btnCity);
        etCity = findViewById(R.id.etCity);


        if (extras != null) {
            String login = extras.getString("name");
            tvName.setText(getString(R.string.welcome).concat(login));
        }

        btnUser.setOnClickListener(view -> {
            Intent intentUserRegister = new Intent(MenuPrincipal.this, UserList.class);
            startActivity(intentUserRegister);
        });

        btnClient.setOnClickListener(view -> {
            Intent intentClientRegister = new Intent(MenuPrincipal.this, ClientList.class);
            startActivity(intentClientRegister);
        });

        btnCity.setOnClickListener(view -> {
            try {
                String city = etCity.getText().toString().toLowerCase(Locale.ROOT);
                CityAsync ca = new CityAsync(MenuPrincipal.this);
                ca.execute(city);
                String retorno = ca.get();
                if (!retorno.equals("")) {
                    JSONArray todos = new JSONArray(retorno);
                    String txt = "";
                    for(int i = 0; i<todos.length();i++){
                        JSONObject c = todos.getJSONObject(i);
                        String key = c.getString("Key");
                        String nome = c.getString("LocalizedName");
                        String estado = c.getJSONObject("AdministrativeArea").getString("ID");
                        String previsao = "indisponivel";
                        String tempMin = "";
                        String tempMax = "";

                        ForecastAsync fa = new ForecastAsync(MenuPrincipal.this);
                        fa.execute(key);
                        String previsaoRetorno = fa.get();
                        if(!previsaoRetorno.equals("")){
                            JSONObject previsaoJson = new JSONObject(previsaoRetorno);
                            JSONArray previsaoArray = previsaoJson.getJSONArray("DailyForecasts");
                            JSONObject previsaoDia = previsaoArray.getJSONObject(0);
                            JSONObject dia = previsaoDia.getJSONObject("Day");
                            previsao = dia.getString("LongPhrase");
                            JSONObject temperatura = previsaoDia.getJSONObject("Temperature");
                            JSONObject minima = temperatura.getJSONObject("Minimum");
                            JSONObject maxima = temperatura.getJSONObject("Maximum");
                            tempMin = minima.getInt("Value")+minima.getString("Unit");
                            tempMax = maxima.getInt("Value")+maxima.getString("Unit");

                        }

                        txt += key+" - "+nome+" - "+estado+" \n" + "Previsão: " + previsao + "\n" +
                                "Temperatura: " + tempMin + " à " + tempMax + "\n\n";
                    }
                    tvCityReturn.setText(txt);
                }
                //tvCityReturn.setText(retorno);
            } catch (Exception e) {
                e.printStackTrace();
                tvCityReturn.setText("erro: " + e.getMessage());
            }
        });
    }

}