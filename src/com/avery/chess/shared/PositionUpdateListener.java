package com.avery.chess.shared;

/**
 * Allows a Piece to register for updates from a Position.
 * 
 * This way we only update a Piece's valid positions when necessary.
 */
public class PositionUpdateListener {
	
	private Piece piece;
	private boolean isAttacking;
	
	public PositionUpdateListener(Piece p, boolean isAttacking) {
		this.piece = p;
		this.isAttacking = isAttacking;
	}
	
	public void onPositionUpdate(Position pos) {
		piece.updateValidPositions();
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public boolean isAttacking() {
		return isAttacking;
	}

}
