package com.kbs.game;

import com.badlogic.gdx.graphics.Texture;

public class BreakableBlock extends Block{
    public BreakableBlock(float x, float y, float width, float height, int level){
        super(x,y,width,height,level);
        setImage(new Texture("BrickBrown.png"));
    }

    public static void breakableBlock(float x, float y, float width, float height, int level){
        new BreakableBlock(x,y,width,height,level);
    }

    public void hit(){
        if(Main.player.getSize() == 2) {
            list.remove(this);
            //System.out.print("Je bent groot genoeg om dit te breken");
        }
        //System.out.println(": "+list.indexOf(this));
    }
}
