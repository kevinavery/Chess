package com.avery.chess.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Position {
	
	private final int x;
	private final int y;
	private final int index;
	private Map<Piece, PositionUpdateListener> listeners;
	
	private Piece piece;
	
	public Position() {
		this(0,0);
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		this.index = calcIndex(x, y);
		listeners = new HashMap<Piece, PositionUpdateListener>();
	}
	
	public static int calcIndex(int x, int y) {
		return 8*x + y;
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean hasPiece() {
		return piece != null;
	}
	
	public void removePiece() {
		setPiece(null);
	}
	
	public void setPiece(Piece p) {
		piece = p;
		notifyListeners();
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void addListener(Piece p) {
		listeners.put(p, new PositionUpdateListener(p));
	}
	
	public void removeListener(Piece p) {
		listeners.remove(p);
	}
	
	private void notifyListeners() {
		for (PositionUpdateListener listener : listeners.values())
			listener.onPositionUpdate(this);
	}
	
	/**
	 * This could be used to highlight the nearest valid position while 
	 * the user is dragging a piece around the board.
	 * 
	 * Probably should become a static method in Board.
	 */
	public Position getNearestPosition(List<Position> positions) {
		Position nearest = this;
		double minDistance = Double.MAX_VALUE;
		
		for (Position pos : positions) {
			double dx = nearest.x - pos.x;
			double dy = nearest.y - pos.y;
			
			// not getting any closer than this
			if (dx + dy == 0) {
				return pos;
			}
			
			double distance = Math.sqrt((dx*dx) + (dy*dy));
			if (distance < minDistance) {
				nearest = pos;
			}		
		}
		
		return nearest;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	public String toStringWithPiece() {
		return this + (hasPiece() ? " holds a " + piece.toString() : "");
				
	}
	
	

}
