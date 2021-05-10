package com.kbs.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Player extends RenderItem{
    private int size;
    private int speed = 200;
    private int jumpHeight = 12;
    private double yspeed;
    private float moveX;
    private double gravity = 0.6;
    private Texture image = new Texture("player.png");
    public ExecutorService backRouteUsb = Executors.newFixedThreadPool(1);
    private String arduinoInput = "9000";

    //--------------------getters en setters--------------------

    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int size){ this.size = size; }

    //--------------------constructor/overrides----------------------------

    public Player(float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        size = 1;
    }

    //---------------------Deze functie gebeurt telkens weer----------------------------------

    public void loop(){
        //System.out.println(checkCollision(x,y,width,height));
        float time = Gdx.graphics.getDeltaTime();

        //bepalen hoe je beweegt op de y as
        int left = 0;
        int right = 0;

        backRouteUsb.submit(new Runnable() {
            @Override
            public void run() {
                arduinoInput = usbRead.getSensor();
            }
        });
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || arduinoInput.charAt(1) == '1' && arduinoInput.charAt(0)=='9'){
            left = 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || arduinoInput.charAt(2) == '1' && arduinoInput.charAt(0)=='9'){
            right = 1;
        }
        moveX = speed*time*(right-left);

        //de verticale beweging bepalen
        boolean standing = false;
        for (int i = 0; i < Block.list.size(); i++) {
            if (collision(new Rectangle(x, y - 1, width, height),Main.level, Block.list.get(i),Block.list.get(i).getLevel())) {
                yspeed = 0;
                standing = true;
                if (Gdx.input.isKeyPressed(Input.Keys.UP) || arduinoInput.charAt(3) == '1' && arduinoInput.charAt(0)=='9') {
                    //System.out.println("Jump");
                    yspeed = jumpHeight;
                    standing = false;
                    break;
                }
            }
        }
        if(!standing){
            yspeed -= gravity;
        }

        //----------------------------beweging voor de y as-----------------------
        if(!checkCollision(x, (int) (y+yspeed),width,height)) {
            y += yspeed;
        } else{
            if(yspeed != 0) {
                while (!checkCollision(x, (int) (y + Math.signum(yspeed)), width, height)) {
                    y += Math.signum(yspeed);
                }
                if(yspeed>0){
                    try {
                        //wanneer je je hoofd stoot
                        getCollision(x, (int) (y + Math.signum(yspeed)), width, height).hit();
                        /*
                        for(int i = 0; i < Block.list.size();i++){
                            System.out.println(Block.list.get(i));
                        }
                        */
                    }catch(NullPointerException exception){
                        System.out.println(exception);
                    }
                }
                yspeed = 0;
            }
        }
        //----------------------------beweging voor de x as-----------------------
        if(!checkCollision((int) (x+moveX),y,width,height)) {
            x += moveX;
        } else{
            //if(moveX != 0) {
                while (!checkCollision((int) (x+Math.signum(moveX)), y, width, height)) {
                    x += Math.signum(moveX);
                }
                x-= Math.signum(moveX);
                moveX = 0;
            //}
        }
        //----------------------------pak items op--------------------------------------
            try{
                (getItemCollision(x, y, width, height)).hit();
            }catch(NullPointerException e){
                //geen collision met een item
            }
        //----------------------------ga dood----------------------------------------------
        if(y<0){
            die();
        }
    }


    //------------------------Collision detection----------------------------
    public static boolean collision(Rectangle rectangle,int level1, Rectangle rectangle2, int level2){
        return rectangle.overlaps(rectangle2) && level1 == level2;
    }
    public static boolean checkCollision(float x,float y,float width,float height){
        for(int i = 0;i < Block.list.size();i++) {
            if (collision(new Rectangle(x,y,width,height),Main.level, Block.list.get(i), Block.list.get(i).getLevel())) {
                return true;
            }
        }
        return false;
    }
    public static Block getCollision(float x, float y, float width, float height){
        for(int i = 0;i < Block.list.size();i++) {
            if (collision(new Rectangle(x,y,width,height),Main.level, Block.list.get(i), Block.list.get(i).getLevel())) {
                return Block.list.get(i);
            }
        }
        return null;
    }
    //-----------------------item pickup-----------------------------
    public static Item getItemCollision(float x, float y, float width, float height){
        for(int i = 0;i < Item.list.size();i++) {
            if (collision(new Rectangle(x,y,width,height),Main.level, Item.list.get(i), Item.list.get(i).getLevel())) {
                return Item.list.get(i);
            }
        }
        return null;
    }
    public void die(){
        x = 50;
        y = 100;
        size = 1;
        Main.level = 1;
    }

    @Override
    public Texture getTexture() {
        return image;
    }
}