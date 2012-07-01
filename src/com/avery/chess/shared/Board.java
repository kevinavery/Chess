package com.avery.chess.shared;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	// positions should be an immutable ordered list
	private final List<Position> positions;
	
	private Piece lastPieceMoved;
	
	public Board() {
		positions = new ArrayList<Position>();
		
		for (int x = 0; x < 8; x++) {
			
			for (int y = 0; y < 8 ; y++) {
				
				boolean isWhite = y > 4;
				
				if (y > 1 && y < 6) {
					Position p = new Position(x,y);
					positions.add(p);	
				}
				else if (y == 1 || y == 6) {
					Position p = new Position(x,y);
					p.setPiece(new Pawn(p, isWhite));
					positions.add(p);					
				}
				else {
					Position p = new Position(x,y);
					positions.add(p);	
				}
			}
		}
	}
	
	public List<Position> getPositions() {
		return positions;
	}
	
	public Position getPosition(int x, int y) {
		int index = Position.calcIndex(x, y);
		
		if (index < 0 || index > 63)
			return null;
		else
			return positions.get(index);
	}
	
	public boolean move (Position oldPos, Position newPos) {
		return oldPos.getPiece().move(newPos, this);
	}
	
	public boolean fits(Position p) {
		int x = p.getX();
		int y = p.getY();
		
		return x >= 0 && x <= 7 && y >= 0 && y <= 7;
	}

	public void clearPosition(int x, int y) {
		positions.get(Position.calcIndex(x, y)).removePiece();
	}

	public Piece getLastPieceMoved() {
		return lastPieceMoved;
	}
	
	public void setLastPieceMoved(Piece p) {
		lastPieceMoved = p;
	}

}
