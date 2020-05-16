package com.ywl5320.audiorecord;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioRecordUitl {

   private AudioRecord audioRecord;
   private int bufferSizeInBytes;
   private boolean start = false;
   private int readSize = 0;

   private OnRecordListner onRecordListner;

    public AudioRecordUitl() {
        bufferSizeInBytes = AudioRecord.getMinBufferSize(
                44100,
                AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                44100,
                AudioFormat.CHANNEL_IN_STEREO,
                AudioFormat.ENCODING_PCM_16BIT,
                bufferSizeInBytes);
    }

    public void setOnRecordListner(OnRecordListner onRecordListner) {
        this.onRecordListner = onRecordListner;
    }

    public void startRecord()
    {
        new Thread(){
            @Override
            public void run() {
                super.run();
                start = true;
                audioRecord.startRecording();
                byte[] audiodata = new byte[bufferSizeInBytes];
                while(start)
                {
                    readSize = audioRecord.read(audiodata, 0, bufferSizeInBytes);
                    if(onRecordListner != null)
                    {
                        onRecordListner.recordByte(audiodata, readSize);
                    }
                }
                if(audioRecord != null)
                {
                    audioRecord.stop();
                    audioRecord.release();
                    audioRecord = null;
                }
            }
        }.start();
    }

    public void stopRecord()
    {
        start = false;
    }

    public interface OnRecordListner
    {
        void recordByte(byte[] audioData, int readSize);
    }

    public boolean isStart()
    {
        return start;
    }
}
