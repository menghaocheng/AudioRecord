package com.ywl5320.audiorecord;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private AudioRecordUitl audioRecordUtil = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void recordaudio(View view) {
        if(audioRecordUtil == null)
        {
            audioRecordUtil = new AudioRecordUitl();
            audioRecordUtil.setOnRecordListner(new AudioRecordUitl.OnRecordListner() {
                @Override
                public void recordByte(byte[] audioData, int readSize) {
                    Log.d("ywl5320", "readSize is :" + readSize);
                }
            });
            audioRecordUtil.startRecord();
        }
        else
        {
            audioRecordUtil.stopRecord();
            audioRecordUtil = null;
        }
    }
}
