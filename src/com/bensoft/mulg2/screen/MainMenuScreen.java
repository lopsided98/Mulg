package com.bensoft.mulg2.screen;

import static com.bensoft.mulg2.Constants.GAME_SCREEN_HEIGHT_PIXELS;
import static com.bensoft.mulg2.Constants.GAME_SCREEN_WIDTH_PIXELS;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.adt.color.Color;

import com.bensoft.mulg2.GameManager;
import com.bensoft.mulg2.textures.MainMenuTextureBundle;
import com.bensoft.mulg2.textures.TextureBundleManager;

/**
 * 
 * @author Ben Wolsieffer
 */
public class MainMenuScreen extends Screen<MainMenuTextureBundle> {

    private static final int PLAY_BUTTON_ID = 1;
    private MenuScene menuScene;

    public MainMenuScreen() {
        super(TextureBundleManager.getMainMenuTextureBundle());

        GameManager.loadGames(getContext());

        Camera camera = getEngine().getCamera();

        menuScene = new MenuScene(camera, new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
                if (pMenuItem.getID() == PLAY_BUTTON_ID) {
                    ScreenManager.setScreen(GameScreen.class);
                }
                return false;
            }
        });
        scene.setBackground(new Background(Color.WHITE));

        TextureRegion titleTexture = getTextureBundle().getTitleTexture();
        Sprite titleSprite = new Sprite(0, 0, titleTexture, getEngine().getVertexBufferObjectManager()) {
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };

        camera.set(0, 0, titleSprite.getWidth(), GAME_SCREEN_WIDTH_PIXELS / GAME_SCREEN_HEIGHT_PIXELS * titleSprite.getWidth());
        titleSprite.setPosition(titleSprite.getWidth() / 2, (camera.getHeight() - titleSprite.getHeight() / 2) - 10);
        scene.attachChild(titleSprite);
        TextureRegion buttonTexture = getTextureBundle().getPlayButtonTexture();
        IMenuItem playButton = new ScaleMenuItemDecorator(new SpriteMenuItem(PLAY_BUTTON_ID, buttonTexture, getEngine().getVertexBufferObjectManager()), 1.1f, 1.0f);
        menuScene.addMenuItem(playButton);
        menuScene.buildAnimations();
        menuScene.setBackgroundEnabled(false);

        scene.setChildScene(menuScene);
    }

    @Override
    public void onDestroy() {
        textureBundle.unload();
    }
}
