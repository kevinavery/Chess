package com.avery.chess.shared;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	// positions should be an immutable ordered list
	private final List<Position> positions;
	
	private Piece lastPieceMoved;
	
	public Board() {
		positions = new ArrayList<Position>();
		
		// Lay out the board
		for (int x = 0; x < 8; x++) {
			
			for (int y = 0; y < 8 ; y++) {
				
				boolean isWhite = y > 4;
				Position p = new Position(x,y);
				
				if (y == 1 || y == 6) {
					p.setPiece(new Pawn(this, p, isWhite));		
				}
				else if (y == 0 || y == 7) {
					if (x == 0 || x == 7)
						p.setPiece(new Rook(this, p, isWhite));
					else if (x == 1 || x == 6) 
						p.setPiece(new Knight(this, p, isWhite));
					else if (x == 2 || x == 5)
						p.setPiece(new Bishop(this, p, isWhite));
				}
				
				positions.add(p);	
			}
		}
		
		// Initialize all the validPositiosn of all Pieces
		for (Position pos : positions) {
			if (pos.hasPiece())
				pos.getPiece().updateValidPositions();
		}
	}
	
	public boolean move (Position oldPos, Position newPos) {
		return oldPos.getPiece().move(newPos);
	}
	
	public List<Position> getPositions() {
		return positions;
	}
	
	public Position getPosition(int x, int y) {
		int index = Position.calcIndex(x, y);
		
		if (index < 0 || index > 63)
			throw new IllegalArgumentException("Position index out of range.");
		else
			return positions.get(index);
	}
	
	public boolean fits(Position p) {
		return fits(p.getX(), p.getY());
	}
	
	public boolean fits(int x, int y) {
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
