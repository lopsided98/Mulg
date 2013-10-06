/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bensoft.mulg2.screen;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

import android.content.Context;

import com.bensoft.mulg2.textures.TextureBundle;

/**
 * 
 * @author Ben Wolsieffer
 * @param <T>
 *            The texture bundle the Screen uses
 */
public abstract class Screen<T extends TextureBundle> {

    protected Scene scene;
    protected T textureBundle;

    public Screen(T tex) {
        textureBundle = tex;
        tex.load();
        scene = createScene();
    }

    public Scene getScene() {
        return scene;
    }

    protected Scene createScene() {
        return new Scene();
    }

    public T getTextureBundle() {
        return textureBundle;
    }

    public Context getContext() {
        return ScreenManager.getContext();
    }

    public Engine getEngine() {
        return ScreenManager.getEngine();
    }

    public void runOnUiThread(Runnable r) {
        ScreenManager.runOnUiThread(r);
    }

    public void runOnUpdateThread(Runnable r) {
        ScreenManager.runOnUpdateThread(r);
    }

    public void runOnUpdateThread(Runnable r, boolean onlyWhenEngineRunning) {
        ScreenManager.runOnUpdateThread(r, onlyWhenEngineRunning);
    }

    public abstract void onDestroy();
}
