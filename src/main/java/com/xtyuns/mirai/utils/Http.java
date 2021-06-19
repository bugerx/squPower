package com.xtyuns.mirai.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
    public static String httpGet(String url) throws IOException {
        HttpURLConnection uc;
        InputStream is;
        BufferedReader br;
        String data;

        uc = (HttpURLConnection) new URL(url).openConnection();
        uc.setRequestMethod("GET");
        uc.setConnectTimeout(1000 * 30);
        uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36");
        uc.connect();

        is = uc.getInputStream();
        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String tmp;
        StringBuilder sbd = new StringBuilder();
        while ((tmp = br.readLine()) != null) {
            sbd.append(tmp);
            sbd.append("\r\n");
        }
        data = sbd.toString();

        br.close();
        is.close();
        uc.disconnect();

        return data;
    }

    public static String httpPost(String url, String param) throws IOException {
        HttpURLConnection uc;
        OutputStream os;
        InputStream is;
        BufferedReader br;
        String data;

        uc = (HttpURLConnection) new URL(url).openConnection();
        uc.setRequestMethod("POST");
        uc.setConnectTimeout(1000 * 30);
        uc.setDoInput(true);
        uc.setDoOutput(true);
        uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.125 Safari/537.36");

        os = uc.getOutputStream();
        os.write(param.getBytes());


        is = uc.getInputStream();
        br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        String tmp;
        StringBuilder sbd = new StringBuilder();
        while ((tmp = br.readLine()) != null) {
            sbd.append(tmp);
            sbd.append("\r\n");
        }
        data = sbd.toString();

        br.close();
        is.close();
        os.close();
        uc.disconnect();

        return data;
    }
}