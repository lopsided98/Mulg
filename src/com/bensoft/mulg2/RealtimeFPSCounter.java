package com.bensoft.mulg2;

import org.andengine.entity.util.FPSCounter;

public class RealtimeFPSCounter extends FPSCounter {
    private float fps;
    @Override
    public void onUpdate(float pSecondsElapsed) {
        fps = 1 / pSecondsElapsed;
    }

    @Override
    public void reset() {
        fps = 0;
    }

    @Override
    public float getFPS() {
        return fps;
    }
}
