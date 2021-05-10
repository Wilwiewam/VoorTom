package com.kbs.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class LevelEnd extends Item {
    public LevelEnd(float x, float y, float width, float height, int level) {
        super(x, y, width, height, level);
    }

    public void hit() {
        Main.level++;
        Main.player.setX(50);
        Main.player.setY(100);
    }
}
