package br.edu.unisep.devmob.exemploapplogin.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.edu.unisep.devmob.exemploapplogin.util.ConnectionHttp;

public class ForecastAsync extends AsyncTask<String,String,String> {

    private ProgressDialog progress;
    private Context ctx;


    public ForecastAsync(Context ctx){
        this.ctx = ctx;
        progress = new ProgressDialog(this.ctx);
        progress.setMessage("buscando dados no servidor");
        progress.setIndeterminate(true);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();

    }


    @Override
    protected void onPreExecute() {
        progress.setMessage("aguarde...");
        progress.show();
    }


    @Override
    protected String doInBackground(String... strings) {
        publishProgress("iniciando os trabalhos");
        try{
            String sUrl = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/"+strings[0];
            sUrl += "?apikey=f53QrF4be5XeZGNBeONqJAejxhaO4H1g";
            sUrl += "&language=pt-br&details=true";
            String retorno = ConnectionHttp.getData(sUrl);
            publishProgress("dados consultados com sucesso");
            return retorno;

        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        progress.setMessage(values[0]);
        progress.setCancelable(false);
        progress.show();
    }

    @Override
    protected void onPostExecute(String s) {
        progress.dismiss();
    }
}
