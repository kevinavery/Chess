package com.avery.chess.shared;

public class Pawn extends Piece {
	
	private enum MoveType {
		Illegal,
		ForwardOne,
		ForwardTwo,
		Diagonal,
		EnPassant
	}
	
	private final int direction;
	private boolean hasMoved;
	private boolean justMovedTwoSpaces;
	private boolean moving = false;
	
	public Pawn(Board b, Position p, boolean isWhite) {
		super(b, p, isWhite);
		
		direction = isWhite ? -1 : 1;
		hasMoved = false;
		justMovedTwoSpaces = false;
	}
	
	@Override
	public void updateValidPositions() {
		if (moving) return;
		
		validPositions.clear();
		for (Position pos : board.getPositions()) {
			if (!getMoveType(pos).equals(MoveType.Illegal)) {
				validPositions.add(pos);
			}
		}
		
		// add update listeners
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = 0; dy <= 2; dy++) {
				if ((dx != 0 && dy == 2) || (dx == 0 && dy == 0))
					continue;
				int x = position.getX() + dx;
				int y = position.getY() + (dy * direction);
				if (board.fits(x, y)) {
					Position pos = board.getPosition(x, y);
					boolean isAttacking = dy == 1 && dx != 0;
					pos.addListener(this, isAttacking);
				}
			}
		}
	}

	/**
	 * Takes a position on the board and determines what MoveType
	 * that would be for this pawn.
	 */
	private MoveType getMoveType(Position pos) {
		if (!board.fits(pos))
			return MoveType.Illegal;
		
		if (isMoveForwardOne(pos))
			return MoveType.ForwardOne;
		
		if (isMoveForwardTwo(pos))
			return MoveType.ForwardTwo;
		
		if (isDiagonalCapture(pos))
			return MoveType.Diagonal;		
		
		if (isEnPassant(pos))
			return MoveType.EnPassant;
		
		return MoveType.Illegal;
	}
	
	private boolean isMoveForwardOne(Position pos) {
		if (pos.getX() == position.getX() && pos.getY() == position.getY() + direction)
			if (!pos.hasPiece())
				return true;
		
		return false;
	}
	
	private boolean isMoveForwardTwo(Position pos) {
		if (!hasMoved)  {
			int fx = pos.getX();
			int fy = pos.getY()-direction;
			if (board.fits(fx, fy) && isMoveForwardOne(board.getPosition(fx, fy)))
				if (pos.getX() == position.getX() && pos.getY() == position.getY() + (2*direction))
					if (!pos.hasPiece())
						return true;
		}
		return false;
	}
	
	private boolean isDiagonalCapture(Position pos) {
		if (pos.getY() == position.getY() + direction && Math.abs(pos.getX() - position.getX()) == 1) 
			if (isOppenentPiece(pos))
				return true;

		return false;
	}
	
	private boolean isEnPassant(Position pos) {
		if (pos.getY() == position.getY() + direction && Math.abs(pos.getX() - position.getX()) == 1) {
			Position opponentPosition = board.getPosition(pos.getX(), pos.getY() - direction);
			if (opponentPosition != null && isOppenentPiece(opponentPosition)) {
				Piece opponentPiece = opponentPosition.getPiece();
				if (opponentPiece instanceof Pawn && opponentPiece.equals(board.getLastPieceMoved())) 
					if (((Pawn)opponentPiece).justMovedTwoSpaces)
						return true;
			}
		}
		return false;
	}

	@Override
	public boolean move(Position newPos) {
		moving = true; // this is needed to avoid attempting to update current valid positions during enpassant
		
		MoveType type = getMoveType(newPos);
		
		switch (type) {
			case Illegal: 		return false;
			case ForwardTwo: 	justMovedTwoSpaces = true;
								break;
			case EnPassant: 	board.clearPosition(newPos.getX(), newPos.getY() - direction);
			default: 			justMovedTwoSpaces = false;
		}

		hasMoved = true;
		
		moving = false;
		
		return super.move(newPos);
	}

}
