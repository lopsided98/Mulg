package com.bensoft.mulg2;

import static com.bensoft.mulg2.Constants.GAME_SCREEN_HEIGHT_PIXELS;
import static com.bensoft.mulg2.Constants.GAME_SCREEN_INVENTORY_HEIGHT;
import static com.bensoft.mulg2.Constants.GAME_SCREEN_WIDTH_PIXELS;
import static com.bensoft.mulg2.Constants.TILE_WIDTH_PIXELS;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.bensoft.mulg2.screen.MainMenuScreen;
import com.bensoft.mulg2.screen.ScreenManager;
import com.bensoft.mulg2.textures.TextureBundleManager;

/**
 * @author Ben Wolsieffer
 */
public class GameActivity extends SimpleBaseGameActivity {

    private Camera camera;

    @Override
    public Engine onCreateEngine(final EngineOptions eo) {
        return new LimitedFPSEngine(eo, Constants.MAX_FPS);
        // return new Engine(eo);
    }

    @Override
    public EngineOptions onCreateEngineOptions() {

        camera = new Camera(-TILE_WIDTH_PIXELS / 2, -TILE_WIDTH_PIXELS / 2 - GAME_SCREEN_INVENTORY_HEIGHT, GAME_SCREEN_WIDTH_PIXELS, GAME_SCREEN_HEIGHT_PIXELS);
        EngineOptions eo = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(GAME_SCREEN_WIDTH_PIXELS, GAME_SCREEN_HEIGHT_PIXELS), camera);
        eo.getTouchOptions().setNeedsMultiTouch(true);
        return eo;
    }

    @Override
    public void onCreateResources() {
    }

    @Override
    public Scene onCreateScene() {
        TextureBundleManager.init(getFontManager(), getTextureManager(), this,
                "font/", "img_new/");
        ScreenManager.init(this);
        return ScreenManager.setScreen(MainMenuScreen.class).getScene();
    }

    @Override
    public void onPause() {
        if (isGameLoaded()) {
            ScreenManager.getScreen().getScene().setIgnoreUpdate(true);
        }
        AccelerationData.stop();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (isGameLoaded()) {
            ScreenManager.getScreen().getScene().setIgnoreUpdate(false);
        }
        AccelerationData.start();
        super.onResume();
    }
}
