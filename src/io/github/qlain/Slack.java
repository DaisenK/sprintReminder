package io.github.qlain;


import javax.print.attribute.standard.NumberOfInterveningJobs;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * SlackBotのIncoming Webhookのラッパーです。
 * 最低限必要な機能のみ実装しています。
 *
 * @
 */
public class Slack implements SlackInterface {
    private URL url;

    Slack(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.err.println("有効なURLを指定してください。");
        }
    }

    @Override
    public void postMessage(String mes) {
        URL postURL = getUrl();
        HttpURLConnection connection = null;
        InputStream in;
        BufferedReader reader = null;

        try {
            //postURL = new URL(getUrl().toString() + "?payload=\"{\"text\": \"" + mes +"\" }\"");

            connection = (HttpURLConnection) postURL.openConnection();
            connection.addRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.print("{\"text\" : \"" + mes + "\"}");
            ps.close();
            outputStream.close();

            int status = connection.getResponseCode();

            System.out.println("HTTP Status:" + status);

            //if (status == HttpURLConnection.HTTP_OK) {

                in = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line);
                }
                System.out.println(output.toString());
            //}
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (connection != null) {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private URL getUrl() {
        return this.url;
    }
}