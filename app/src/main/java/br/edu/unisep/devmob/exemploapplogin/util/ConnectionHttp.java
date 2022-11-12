package br.edu.unisep.devmob.exemploapplogin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ConnectionHttp {

    public static String getData(String sUrl){
        try{
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type","application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            conn.connect();
            InputStream in = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine())!=null){
                buffer.append(line);
            }
            reader.close();
            in.close();
            conn.disconnect();
            return buffer.toString();
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }
}
