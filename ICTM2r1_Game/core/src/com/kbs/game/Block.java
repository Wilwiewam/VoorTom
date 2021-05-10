package com.kbs.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class Block extends RenderItem {
    public static ArrayList<Block> list = new ArrayList<>();
    private Texture image = new Texture("BrickGrey.png");
    private int level;

    public Texture getImage() {
        return image;
    }
    public int getLevel(){ return level;}
    public void setImage(Texture image) {
        this.image = image;
    }

    public Block(float x, float y, float width, float height, int level){
        setX(x);
        setY(y);
        this.width = width;
        this.height = height;
        this.level = level;
        //if(list.size())
        list.add(this);
    }
    public String toString(){
        return "index: "+list.indexOf(this)+" - x: "+x+" - y: "+y+" - width: "+width+" - height: "+height;
    }

    public static void block(float x, float y, float width, float height,int level){
        new Block(x,y,width,height,level);
    }

    public void hit(){
        //System.out.println("dit blok kan niet stuk: "+list.indexOf(this));
    }

    @Override
    public Texture getTexture() {
        return image;
    }
}
