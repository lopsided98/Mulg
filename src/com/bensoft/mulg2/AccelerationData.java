package com.bensoft.mulg2;

import static com.bensoft.mulg2.Constants.ACCELERATION_OVERRIDE_DEFAULT;
import static com.bensoft.mulg2.Constants.GRAVITY_MULTIPLIER;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Surface;
import android.view.WindowManager;

/**
 * @author Ben Wolsieffer
 */
public class AccelerationData {

    private static SensorManager mSensorManager;
    private static Sensor sens;
    private static float[] sensorData = new float[3];
    private static boolean xReversed = false;
    private static boolean yReversed = false;
    private static boolean zReversed = false;
    public static boolean reversed = false;
    private static boolean override = ACCELERATION_OVERRIDE_DEFAULT;
    private static int xIndex;
    private static int yIndex;
    private static SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            sensorData = sensorEvent.values;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    public static void init(Context ctx) {
        mSensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        sens = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        WindowManager windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);

        Configuration config = ctx.getResources().getConfiguration();

        int rotation = windowManager.getDefaultDisplay().getRotation();

        if (((rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) &&
                config.orientation == Configuration.ORIENTATION_LANDSCAPE)
                || ((rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) &&
                        config.orientation == Configuration.ORIENTATION_PORTRAIT)) {
            // Landscape
            xReversed = true;
            xIndex = 1;
            yIndex = 0;
            // Debug.i("Landscape");
        } else {
            // Portrait
            xReversed = true;
            yReversed = true;
            xIndex = 0;
            yIndex = 1;
            // Debug.i("Portrait");
        }
    }

    /**
     * Starts getting data.
     */
    public static void start() {
        if (!override) {
            if (mSensorManager != null) {
                mSensorManager.registerListener(listener, sens, SensorManager.SENSOR_DELAY_GAME);
            }
        }
    }

    /**
     * Stops the data from updating. Usually called when the app is paused to
     * save power.
     */
    public static void stop() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(listener);
        }
    }

    public static float getX() {
        return sensorData[xIndex] * GRAVITY_MULTIPLIER * (xReversed ? -1 : 1) * (reversed ? -1 : 1);
    }

    public static float getY() {
        return sensorData[yIndex] * GRAVITY_MULTIPLIER * (yReversed ? -1 : 1) * (reversed ? -1 : 1);
    }

    public static float getZ() {
        return sensorData[2] * GRAVITY_MULTIPLIER * (zReversed ? -1 : 1) * (reversed ? -1 : 1);
    }

    public static void reverse() {
        reversed = true;
    }

    public static void unReverse() {
        reversed = false;
    }

    public static void setOverrideEnabled(boolean override) {
        AccelerationData.override = override;
        if (override) {
            stop();
        } else {
            start();
        }
    }

    public static boolean isOverrideEnabled() {
        return override;
    }

    public static void setAcceleration(float x, float y, float z) {
        if (override) {
            sensorData[0] = x;
            sensorData[1] = y;
            sensorData[2] = z;
        }
    }

}
