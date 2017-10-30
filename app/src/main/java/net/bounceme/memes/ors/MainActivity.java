package net.bounceme.memes.ors;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {

    SensorManager sensorManager;
    Sensor accelerometerSensor;
    boolean accelerometerPresent;
    TextView face, face2;
    int z,c = 0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        face = (TextView)findViewById(R.id.face);
        face2 = (TextView) findViewById(R.id.face2);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensorList.size() > 0){
            accelerometerPresent = true;
            accelerometerSensor = sensorList.get(0);
        }
        else{
            accelerometerPresent = false;
            face.setText("Naprava ne podpira te aplikacije! ");
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        if(accelerometerPresent){
            sensorManager.registerListener(accelerometerListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
        if(accelerometerPresent){
            sensorManager.unregisterListener(accelerometerListener);
        }
    }

    private SensorEventListener accelerometerListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1) {}
        @Override
        public void onSensorChanged(SensorEvent arg0) {
            float z_value = arg0.values[2];
            if (z_value >= 0) {
                z++;
                face.setText("UP");
                if (c > 3){face2.setText("Mogoce deluje");}}
            else if (z_value < 0){face.setText("DOWN");
                c++;
                if (z > c)face2.setText("Jap");}
            if (z > 3){face2.setText("DELUJEEEEEEE");}
        }};



}
