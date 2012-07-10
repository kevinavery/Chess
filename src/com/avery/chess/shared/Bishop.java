package com.avery.chess.shared;

public class Bishop extends Piece {

	public Bishop(Board b, Position p, boolean isWhite) {
		super(b, p, isWhite);
	}

	@Override
	public void updateValidPositions() {
		validPositions.clear();
		MoveUtil.diagonals(this);
	}

}
