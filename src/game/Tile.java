package game;

import java.util.ArrayList;

public class Tile {
	public static Tile[] tiles = new Tile[256];
	
	public static final Tile SAND = new Tile(0, 0).addConnector(1).addConnector(3);
	public static final Tile DIRT = new Tile(1, 2).addConnector(0).addConnector(2).addConnector(3).addConnector(4);
	public static final Tile GRASS = new Tile(2, 1).addConnector(1).addConnector(3).addConnector(4);
	public static final Tile WATER = new Tile(3, 4).addConnector(0).addConnector(1).addConnector(2).addConnector(4);
	public static final Tile SNOW = new Tile(4, 3).addConnector(1).addConnector(2).addConnector(3);
	
	public final int id;
	public int tex;
	
	private ArrayList<Integer> connectors = new ArrayList<Integer>();
	
	public Tile(int tileID, int tileTex){
		id = tileID;
		tex = tileTex;
		tiles[id] = this;
		connectors.add(id);
		//blah
	}
	
	public Tile addConnector(int tileID){
		connectors.add(tileID);
		return this;
	}
	
	public boolean connects(int tileID){
		return connectors.contains(tileID);
	}
	
	public String toString(){
		return "" + id;
	}
}
