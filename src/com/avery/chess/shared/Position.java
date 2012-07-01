package com.avery.chess.shared;

import java.util.List;

public class Position {
	
	private int x;
	private int y;
	private int index;
	
	private Piece piece;
	
	public Position() {
		this(0,0);
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
		this.index = 8*x + y;
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
		piece = null;
	}
	
	public void setPiece(Piece p) {
		piece = p;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
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
		return this + " " + (hasPiece() ? piece.toString() : "");
	}
	
	

}
