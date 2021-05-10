package com.kbs.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
	public static int level = 1;
	public static Player player;
	BitmapFont font;
	ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(50,100,16,32);
		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();

		int lvl = 1;
		Block.block(0,50, 800,32,lvl);
		Block.block(100,150,32,32,lvl);
		BreakableBlock.breakableBlock(132,150,32,32,lvl);
		MysteryBlock.mysteryBlock(164,150,32,32,lvl);
		Block.block(196,150,32,32,lvl);
		Block.block(350,82,32,32,lvl);
		new LevelEnd(700,50,100,100,lvl);
		lvl++;
		Block.block(0,50, 800,32,lvl);
		Block.block(200,150,32,32,lvl);
		BreakableBlock.breakableBlock(232,150,32,32,lvl);
		MysteryBlock.mysteryBlock(264,150,32,32,lvl);
		Block.block(296,150,32,32,lvl);
		Block.block(450,82,32,32,lvl);
		//new LevelEnd(700,50,100,100,lvl);

		Gdx.gl.glClearColor(0, 50, 200, 1);
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		player.loop();
		for(int i = 0;i<Block.list.size();i++){
			Block block = Block.list.get(i);
			if(block.getLevel()==level) {
				batch.draw(block.getImage(), block.getX(), block.getY(), block.getWidth(), block.getHeight());
			}
		}
		for(int i = 0;i<Item.list.size();i++){
			Item item = Item.list.get(i);
			if(item.getLevel()==level) {
				batch.draw(item.getTexture(), item.getX(), item.getY(), item.getWidth(), item.getHeight());
			}
			item.loop();
		}
		font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(),25,370);
		font.draw(batch, "Level: "+level,25,350);
		batch.draw(player.getTexture(), player.getX()-8,player.getY(),player.getTexture().getWidth(),player.getTexture().getHeight()*player.getSize());
		/*
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line); //I'm using the Filled ShapeType, but remember you have three of them
		shapeRenderer.rect(player.x,player.y,player.width,player.height); //assuming you have created those x, y, width and height variables
		shapeRenderer.end();
		*/
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
	}
}
