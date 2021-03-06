package com.avery.chess.shared;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
	
	protected Board board;
	protected Position position;
	protected boolean isWhite;
	protected List<Position> validPositions;
	
	public Piece(Board b, Position p, boolean isWhite) {
		this.board = b;
		this.position = p;
		this.isWhite = isWhite;
		this.validPositions = new ArrayList<Position>();
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
	
	public boolean isOppenentPiece(Position pos) {
		return pos.hasPiece() && isOppenentPiece(pos.getPiece());
	}
	
	public boolean isOppenentPiece(Piece piece) {
		return piece.isWhite != this.isWhite;
	}
	
	public List<Position> getValidPostions() {
		return validPositions;
	}
	
	/**
	 * Rebuild the list of positions that this piece could currently move to
	 * and add all the listeners to all positions that could effect its validPositions
	 */
	public abstract void updateValidPositions();
	
	/** 
	 * Determine if this piece can legally move to the position.
	 */
	public boolean canMove(Position pos) {
		return validPositions.contains(pos);
	}
	
	/**
	 * Attempt to move.
	 * 
	 * Return true if the move was successful.
	 */
	public boolean move(Position newPos) {
		if (!canMove(newPos))
			return false;
		
		// Remove old listeners
		for (Position pos : validPositions)
			pos.removeListener(this);

		// Update old position
		position.removePiece();
		
		board.setLastPieceMoved(this);
		
		// Configure new position
		position = newPos;
		position.setPiece(this);
		
		updateValidPositions();
		
		// sigh, we must update all pawns who may be ready to enpassant
		for (Position p : board.getPositions())
			if (p.getPiece() instanceof Pawn && (p.getY() == 3 || p.getY() == 4))
				p.getPiece().updateValidPositions();
		
		return true;
	}

	@Override
	public String toString() {
		String color = isWhite ? "white" : "black";
		String className = getClass().toString().toLowerCase();
		return className.substring(className.lastIndexOf(".")+1) +"_"+ color ;
	}
	
	public String toStringWithPosition() {
		return this + " " + position.toString();
	}

}
