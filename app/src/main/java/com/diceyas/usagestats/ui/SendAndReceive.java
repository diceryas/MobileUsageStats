//version 2.0

package com.diceyas.usagestats.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
//import android.os.StrictMode;

public class SendAndReceive {

    private static String _result = "";
    private CountDownLatch runningThreadNum;
    private String dst = "";
    private String data = "";
    public class MyThread implements Runnable {
        String result = "";
        @Override
        public void run() {
            try {
                //Looper.prepare() ;
//    			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
                URL url = new URL(dst);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);
                conn.setUseCaches(false);

                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(data);
                bw.flush();
                osw.close();
                bw.close();

                int i = conn.getResponseCode();

                if (i == 200) {
                    InputStream inStream = conn.getInputStream();
                    InputStreamReader isr = new InputStreamReader(inStream,"UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    line = br.readLine();
                    if(line == null)return;
                    System.out.println(line);
                    inStream.close();
                    result = line;
                    br.close();
                    isr.close();
                }

                else {
                    result = result + i;
//   				handler.obtainMessage(0,result).sendToTarget();
                }
            } catch (MalformedURLException  e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            _result = result;
            runningThreadNum.countDown();
        }
    }
    public String postAndGetString(String url,String _data)
    {
        dst = url;
        data = _data;
        runningThreadNum = new CountDownLatch(1);
        new Thread(new MyThread()).start();
        try {
            runningThreadNum.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return _result;
    }
    public void service()
    {

    }

}
