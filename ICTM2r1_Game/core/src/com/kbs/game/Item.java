package com.kbs.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Item extends RenderItem {
    public static ArrayList<Item> list = new ArrayList<>();
    private Texture image = new Texture("Item.png");
    private int level;
    private double yspeed = 0;
    private double gravity = 0.3;

    public void setImage(Texture image) {
        this.image = image;
    }
    public int getLevel(){return level;}
    public void setYspeed(double yspeed){this.yspeed = yspeed;}

    public Item(float x, float y, float width, float height, int level) {
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;
        this.level = level;
        list.add(this);
    }

    public static void item(float x, float y, float width, float height, int level) {
        new Item(x, y, width, height, level);
    }

    public void hit() {
        list.remove(this);
        System.out.println("Nom nom nom.");
    }

    public void loop() {
        boolean standing = false;
        for (int i = 0; i < Block.list.size(); i++) {
            if (Player.collision(new Rectangle(x, y - 1, width, height),Main.level, Block.list.get(i),Block.list.get(i).getLevel())) {
                standing = true;
                if(yspeed<0){
                    yspeed = 0;
                }
                //dit is een loop
            }
        }
        if (!standing) {
            yspeed -= gravity;
        }
        if (!Player.checkCollision(x, (int) (y + yspeed), width, height)) {
            y+=yspeed;
        } else {
            if (yspeed != 0) {
                while (!Player.checkCollision(x, (int) (y + Math.signum(yspeed)), width, height)) {
                    y += Math.signum(yspeed);
                }
                yspeed = 0;
            }
        }
    }

    @Override
    public Texture getTexture() {
        return image;
    }
}
