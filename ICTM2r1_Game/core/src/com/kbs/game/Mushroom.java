package com.kbs.game;

import com.badlogic.gdx.graphics.Texture;

public class Mushroom extends Item{
    public Mushroom(float x, float y, float width, float height, int level){
        super(x,y,width,height, level);
        setImage(new Texture("Item.png"));
    }

    public static void mushroom(float x, float y, float width, float height, int level){
        new Mushroom(x,y,width,height,level);
    }

    public void hit(){
        list.remove(this);
        Main.player.setSize(2);
        //System.out.println("Nom nom nom. Ik groei.");
    }
}
