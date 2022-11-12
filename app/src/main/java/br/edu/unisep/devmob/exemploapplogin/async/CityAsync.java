package br.edu.unisep.devmob.exemploapplogin.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import br.edu.unisep.devmob.exemploapplogin.util.ConnectionHttp;

public class CityAsync extends AsyncTask<String,String,String> {

    private ProgressDialog progress;
    private Context ctx;


    public CityAsync(Context ctx){
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
            String sUrl = "http://dataservice.accuweather.com/locations/v1/cities/search";
            sUrl += "?apikey=f53QrF4be5XeZGNBeONqJAejxhaO4H1g";
            sUrl += "&language=pt-br&details=true";
            sUrl += "&q="+strings[0].replace(" ","%20").replace("ã","%C3%A3");
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
