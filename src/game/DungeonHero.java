package game;

import java.util.ArrayList;
import java.util.Random;

import engine.Engine;
import engine.GameController;
import engine.RenderHelper;
import engine.Texture;

public class DungeonHero implements GameController {

	int[][] background;
	
	@Override
	public String gameTitle() {
		return "Dungeon Hero";
	}

	@Override
	public void init() {
		background = new int[40][24];
		Random seedGen = new Random();
		long seed = seedGen.nextLong();
		Random rand = new Random(seed);
		for(int x = 0; x < background.length; x++){
			for(int y = 0; y < background[x].length; y++){
				Tile genTile = null;
				boolean connects = false;
				System.out.println(x + " " + y);
				while(!connects){
					genTile = Tile.tiles[rand.nextInt(5)];
					if(y - 1 >= 0 && x - 1 >= 0){
						if(Tile.tiles[background[x-1][y - 1]].connects(genTile.id)){
							connects = true;
						}
					}else if(y - 1 >= 0){
						if(Tile.tiles[background[x][y - 1]].connects(genTile.id)){
							connects = true;
						}
					}else if(x - 1 >= 0){
						if(Tile.tiles[background[x - 1][y]].connects(genTile.id)){
							connects = true;
						}
					} else if(x == 0 && y == 0){
						connects = true;
					}
				}
				background[x][y] = genTile.id;
			}
		}
		System.out.println(seed);
	}

	@Override
	public void input() {
		
	}

	@Override
	public void update() {
	}

	@Override
	public void render() {
		RenderHelper.color(1, 1, 1);
		Texture.getTexture("/img/tiles.png").bindTexture();
		RenderHelper.enableTextures();
		for(int x = 0; x < background.length; x++){
			for(int y = 0; y < background[0].length; y++){
				RenderHelper.drawTexturedQuad(x * 32, y * 32, 32, 32, Tile.tiles[background[x][y]].tex * 16, 0, 16, 16);
			}
		}
		RenderHelper.disableTextures();
		Texture.getTexture("/img/tiles.png").releaseTexture();
	}

	@Override
	public void deinit() {

	}

	public static void main(String[] args) {
		Engine.instance.setupGameController(new DungeonHero());
	}

}
