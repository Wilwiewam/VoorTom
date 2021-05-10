package com.kbs.game;

import com.badlogic.gdx.graphics.Texture;

public class MysteryBlock extends Block{
    public MysteryBlock(float x, float y, float width, float height, int level){
        super(x,y,width,height,level);
        setImage(new Texture("BrickYellow.png"));
    }

    public static void mysteryBlock(float x, float y, float width, float height, int level){
        new MysteryBlock(x,y,width,height,level);
    }

    public void hit(){
            list.remove(this);
            new Mushroom(x,y+32,32,32,getLevel()).setYspeed(6);
            block(x,y,width,height,getLevel());
            //System.out.println("Wat zou je krijgen?: "+list.indexOf(this));
    }
}
