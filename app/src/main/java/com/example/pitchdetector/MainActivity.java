package com.example.pitchdetector;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_RECORD_AUDIO = 1;
    TextView pitchText;
    Thread audioThread;
    Switch sw;
    AudioDispatcher dispatcher;
    AudioProcessor pitchProcessor;
    private Handler uiThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pitchText = findViewById(R.id.pitchText);
/*        sw = findViewById(R.id.pitchSwitch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if(isChecked){
                    recordAudio();
                } else{
                    dispatcher.removeAudioProcessor(pitchProcessor);
                    audioThread.interrupt();
                    pitchText.setText("_");
                }
            }
        });*/

        requestAudioPermissions();
/*        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult res, AudioEvent e) {
                final float pitchHz = res.getPitch();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //processPitch(pitchHz);
                        pitchText.setText(String.valueOf(pitchHz));
                    }
                });
            }
        };

        AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        Thread audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();*/
    }

    public void pSwitch(View v){
        Switch s = (Switch) v;
        if(s.isChecked()){
            recordAudio();
        } else{
            dispatcher.stop();
            pitchText.setText("_");
        }
    }

    private void recordAudio(){
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);

        uiThread = new Handler();

        PitchDetectionHandler pdh = (PitchDetectionResult res, AudioEvent e) -> uiThread.post(() -> {
            final float pitchHz = res.getPitch();
            processPitch(pitchHz);
            //pitchText.setText(String.valueOf(pitchHz));
        });

        pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(pitchProcessor);

        audioThread = new Thread(dispatcher, "Audio Thread");
        audioThread.start();
    }

    private void processPitch(float pitch){
        if(pitch > 26.73 && pitch < 28.32)
            pitchText.setText("A0");
        else if(pitch > 28.32 && pitch < 30.005)
            pitchText.setText("A#/B♭0");
        else if(pitch > 30.005 && pitch < 31.785)
            pitchText.setText("B0");
        else if(pitch > 31.785 && pitch < 33.675)
            pitchText.setText("C1");
        else if(pitch > 33.675 && pitch < 35.68)
            pitchText.setText("C#/D♭1");
        else if(pitch > 35.68 && pitch < 37.8)
            pitchText.setText("D1");
        else if(pitch > 37.8 && pitch < 40.045)
            pitchText.setText("D#/E♭1");
        else if(pitch > 40.045 && pitch < 42.425)
            pitchText.setText("E1");
        else if(pitch > 42.425 && pitch < 44.95)
            pitchText.setText("F1");
        else if(pitch > 44.95 && pitch < 47.625)
            pitchText.setText("F#/G♭1");
        else if(pitch > 47.625 && pitch < 50.455)
            pitchText.setText("G1");
        else if(pitch > 50.455 && pitch < 53.455)
            pitchText.setText("G#/A♭1");
        else if(pitch > 53.455 && pitch < 56.64)
            pitchText.setText("A1");
        else if(pitch > 56.64 && pitch < 60.01)
            pitchText.setText("A#/B♭1");
        else if(pitch > 60.01 && pitch < 63.58)
            pitchText.setText("B1");
        else if(pitch > 63.58 && pitch < 67.36)
            pitchText.setText("C2");
        else if(pitch > 67.36 && pitch < 71.36)
            pitchText.setText("C#/D♭2");
        else if(pitch > 71.36 && pitch < 75.6)
            pitchText.setText("D2");
        else if(pitch > 75.6 && pitch < 80.1)
            pitchText.setText("D#/E♭2");
        else if(pitch > 80.1 && pitch < 84.86)
            pitchText.setText("E2");
        else if(pitch > 84.86 && pitch < 89.91)
            pitchText.setText("F2");
        else if(pitch > 89.91 && pitch < 95.25)
            pitchText.setText("F#/G♭2");
        else if(pitch > 95.25 && pitch < 100.92)
            pitchText.setText("G2");
        else if(pitch > 100.92 && pitch < 106.92)
            pitchText.setText("G#/A♭2");
        else if(pitch > 106.92 && pitch < 113.27)
            pitchText.setText("A2");
        else if(pitch > 113.27 && pitch < 120.0)
            pitchText.setText("A#/B♭2");
        else if(pitch > 120.0 && pitch < 127.14)
            pitchText.setText("B2");
        else if(pitch > 127.14 && pitch < 134.7)
            pitchText.setText("C3");
        else if(pitch > 134.7 && pitch < 142.71)
            pitchText.setText("C#/D♭3");
        else if(pitch > 142.71 && pitch < 151.2)
            pitchText.setText("D3");
        else if(pitch > 151.2 && pitch < 160.19)
            pitchText.setText("D#/E♭3");
        else if(pitch > 160.19 && pitch < 169.71)
            pitchText.setText("E3");
        else if(pitch > 169.71 && pitch < 179.81)
            pitchText.setText("F3");
        else if(pitch > 179.81 && pitch < 190.5)
            pitchText.setText("F#/G♭3");
        else if(pitch > 190.5 && pitch < 201.83)
            pitchText.setText("G3");
        else if(pitch > 201.83 && pitch < 213.83)
            pitchText.setText("G#/A♭3");
        else if(pitch > 213.83 && pitch < 226.54)
            pitchText.setText("A3");
        else if(pitch > 226.54 && pitch < 240.01)
            pitchText.setText("A#/B♭3");
        else if(pitch > 240.01 && pitch < 254.29)
            pitchText.setText("B3");
        else if(pitch > 254.29 && pitch < 269.41)
            pitchText.setText("C4");
        else if(pitch > 269.41 && pitch < 285.42)
            pitchText.setText("C#/D♭4");
        else if(pitch > 285.42 && pitch < 302.4)
            pitchText.setText("D4");
        else if(pitch > 302.4 && pitch < 320.38)
            pitchText.setText("D#/E♭4");
        else if(pitch > 320.38 && pitch < 339.43)
            pitchText.setText("E4");
        else if(pitch > 339.43 && pitch < 359.61)
            pitchText.setText("F4");
        else if(pitch > 359.61 && pitch < 381.0)
            pitchText.setText("F#/G♭4");
        else if(pitch > 381.0 && pitch < 403.65)
            pitchText.setText("G4");
        else if(pitch > 403.65 && pitch < 427.65)
            pitchText.setText("G#/A♭4");
        else if(pitch > 427.65 && pitch < 453.08)
            pitchText.setText("A4");
        else if(pitch > 453.08 && pitch < 480.02)
            pitchText.setText("A#/B♭4");
        else if(pitch > 480.02 && pitch < 508.57)
            pitchText.setText("B4");
        else if(pitch > 508.57 && pitch < 538.81)
            pitchText.setText("C5");
        else if(pitch > 538.81 && pitch < 570.85)
            pitchText.setText("C#/D♭5");
        else if(pitch > 570.85 && pitch < 604.79)
            pitchText.setText("D5");
        else if(pitch > 604.79 && pitch < 640.75)
            pitchText.setText("D#/E♭5");
        else if(pitch > 640.75 && pitch < 678.86)
            pitchText.setText("E5");
        else if(pitch > 678.86 && pitch < 719.23)
            pitchText.setText("F5");
        else if(pitch > 719.23 && pitch < 761.99)
            pitchText.setText("F#/G♭5");
        else if(pitch > 761.99 && pitch < 807.3)
            pitchText.setText("G5");
        else if(pitch > 807.3 && pitch < 855.31)
            pitchText.setText("G#/A♭5");
        else if(pitch > 855.31 && pitch < 906.17)
            pitchText.setText("A5");
        else if(pitch > 906.17 && pitch < 960.05)
            pitchText.setText("A#/B♭5");
        else if(pitch > 960.05 && pitch < 1017.14)
            pitchText.setText("B5");
        else if(pitch > 1017.14 && pitch < 1077.62)
            pitchText.setText("C6");
        else if(pitch > 1077.62 && pitch < 1141.7)
            pitchText.setText("C#/D♭6");
        else if(pitch > 1141.7 && pitch < 1209.59)
            pitchText.setText("D6");
        else if(pitch > 1209.59 && pitch < 1281.51)
            pitchText.setText("D#/E♭6");
        else if(pitch > 1281.51 && pitch < 1357.71)
            pitchText.setText("E6");
        else if(pitch > 1357.71 && pitch < 1438.45)
            pitchText.setText("F6");
        else if(pitch > 1438.45 && pitch < 1523.98)
            pitchText.setText("F#/G♭6");
        else if(pitch > 1523.98 && pitch < 1614.6)
            pitchText.setText("G6");
        else if(pitch > 1614.6 && pitch < 1710.61)
            pitchText.setText("G#/A♭6");
        else if(pitch > 1710.61 && pitch < 1812.33)
            pitchText.setText("A6");
        else if(pitch > 1812.33 && pitch < 1920.1)
            pitchText.setText("A#/B♭6");
        else if(pitch > 1920.1 && pitch < 2034.27)
            pitchText.setText("B6");
        else if(pitch > 2034.27 && pitch < 2155.23)
            pitchText.setText("C7");
        else if(pitch > 2155.23 && pitch < 2283.39)
            pitchText.setText("C#/D♭7");
        else if(pitch > 2283.39 && pitch < 2419.17)
            pitchText.setText("D7");
        else if(pitch > 2419.17 && pitch < 2563.02)
            pitchText.setText("D#/E♭7");
        else if(pitch > 2563.02 && pitch < 2715.43)
            pitchText.setText("E7");
        else if(pitch > 2715.43 && pitch < 2876.9)
            pitchText.setText("F7");
        else if(pitch > 2876.9 && pitch < 3047.96)
            pitchText.setText("F#/G♭7");
        else if(pitch > 3047.96 && pitch < 3229.2)
            pitchText.setText("G7");
        else if(pitch > 3229.2 && pitch < 3421.22)
            pitchText.setText("G#/A♭7");
        else if(pitch > 3421.22 && pitch < 3624.66)
            pitchText.setText("A7");
        else if(pitch > 3624.66 && pitch < 3840.19)
            pitchText.setText("A#/B♭7");
        else if(pitch > 3840.19 && pitch < 4068.54)
            pitchText.setText("B7");
        else if(pitch > 4068.54 && pitch < 4310.47)
            pitchText.setText("C8");
    }

    private void requestAudioPermissions(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECORD_AUDIO)){
                Toast.makeText(this, "Please grant permissions to record audio", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_RECORD_AUDIO);
        }
        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED){
            //recordAudio();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSIONS_RECORD_AUDIO: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //recordAudio();
                } else{
                    Toast.makeText(this, "Permissions Denied to record audio", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}