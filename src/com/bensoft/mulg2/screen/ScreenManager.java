package com.bensoft.mulg2.screen;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;

import android.content.Context;

public final class ScreenManager {

    private static BaseGameActivity core;
    private static Screen<?> currScreen;

    public static void init(BaseGameActivity base) {
        core = base;
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
    }

    public static void runOnUiThread(Runnable r) {
        core.runOnUiThread(r);
    }

    public static void runOnUpdateThread(Runnable r) {
        core.runOnUpdateThread(r);
    }

    public static void runOnUpdateThread(Runnable r, boolean onlyWhenEngineRunning) {
        core.runOnUpdateThread(r, onlyWhenEngineRunning);
    }

    public static Screen<?> setScreen(Class<? extends Screen<?>> screen) {
        if (currScreen != null) {
            currScreen.onDestroy();
        }
        try {
            currScreen = screen.newInstance();
        } catch (InstantiationException ex) {
            if (ex.getCause() != null) {
                if (ex.getCause() instanceof RuntimeException) {
                    throw (RuntimeException) ex.getCause();
                } else {
                    ex.getCause().printStackTrace();
                }
            } else {
                ex.printStackTrace();
            }
        } catch (IllegalAccessException ex) {
        }
        core.getEngine().setScene(currScreen.getScene());
        return currScreen;
    }

    public static Screen<?> getScreen() {
        return currScreen;
    }

    public static Context getContext() {
        return core;
    }

    public static Engine getEngine() {
        return core.getEngine();
    }

    public static GameScreen getGameScreen() throws IllegalStateException {
        if (currScreen instanceof GameScreen) {
            return (GameScreen) currScreen;
        } else {
            throw new IllegalStateException("Game screen currently not showing.");
        }
    }

    public static MainMenuScreen getMainMenuScreen() throws IllegalStateException {
        if (currScreen instanceof MainMenuScreen) {
            return (MainMenuScreen) currScreen;
        } else {
            throw new IllegalStateException("Main menu screen currently not showing.");
        }
    }
}