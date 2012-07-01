package com.avery.chess.shared;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
	
	protected Position position;
	protected boolean isWhite;
	
	public Piece(Position p, boolean isWhite) {
		this.position = p;
		this.isWhite = isWhite;
	}
	
	public boolean isWhite() {
		return isWhite;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	public void setPosition(Position p) {
		this.position = p;
	}
	
	public boolean canJump() {
		return false;
	}
	
	public boolean isOppenentPiece(Position pos) {
		return pos.hasPiece() && isOppenentPiece(pos.getPiece());
	}
	
	public boolean isOppenentPiece(Piece piece) {
		return piece.isWhite != this.isWhite;
	}
	
	// TODO: cache this, invalidate based on lastPieceMoved
	public List<Position> getValidPostions(Board b) {
		List<Position> validPositions = new ArrayList<Position>();
		for (Position p : b.getPositions())
			if (canMove(p, b))
				validPositions.add(p);
		
		return validPositions;
	}
	
	/** 
	 * Determine if this piece can legally move to the position.
	 * 
	 * Each piece has different rules for how it can move that requires 
	 * knowledge of the pieces around it and the desired position.
	 * 
	 */
	abstract boolean canMove(Position pos, Board b);
	
	/**
	 * Attempt to move.
	 * 
	 * Return true if the move was successful.
	 */
	abstract boolean move(Position newPos, Board board);

	@Override
	public String toString() {
		return getClass().toString();
	}
	
	public String toStringWithPosition() {
		return this + " " + position.toString();
	}

}
