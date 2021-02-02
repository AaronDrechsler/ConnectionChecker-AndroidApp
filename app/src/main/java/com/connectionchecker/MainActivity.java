package com.connectionchecker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.Toast;

import com.newconnectionchecker.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler = new Handler();
    private static final String FILE_NAME = "ConnLog.txt";
    EditText mEditText;
    Button startButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mEditText = findViewById(R.id.edit_text) ;
            startButton = findViewById(R.id.button_start);
        };

        public void start(View v){
            Toast.makeText(MainActivity.this, "check started", Toast.LENGTH_SHORT).show();
            mRunnable.run();
        }

        public void stop(View v){
            Toast.makeText(MainActivity.this, "ended", Toast.LENGTH_SHORT).show();
            mHandler.removeCallbacks(mRunnable);
        }

        private Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                connectionExistAndWriteToFile(CheckConnection.isConnectedOrNot(getApplicationContext()));
                mHandler.postDelayed(this, 60000);
            }
        };

        public void connectionExistAndWriteToFile(boolean connectionExist) {
            final String isConnectedText = "connected "+ getTime() + "\n";
            final String isNotConnectedText = "not connected " + getTime() + "\n";
            FileOutputStream fos = null;
            try {
                    fos = openFileOutput(FILE_NAME, MODE_APPEND);
                    if (connectionExist == false) {
                        fos.write(isNotConnectedText.getBytes());
                    } else {
                        //fos.write(isConnectedText.getBytes());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            mEditText.getText().clear();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }

        public void load(View v){
            FileInputStream fis = null;
            try {
                fis = openFileInput(FILE_NAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String text;
                while((text = br.readLine()) != null){
                    sb.append(text).append("\n");
                }
                mEditText.setText(sb.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        public static String getTime() {
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss93333333333 z");
            Date date = Calendar.getInstance().getTime();
            String time = new String (formatter.format(date));
            return time;
    }
}
