package com.avery.chess.shared;


public class Pawn extends Piece {
	
	private enum MoveType {
		Illegal,
		ForwardOne,
		ForwardTwo,
		Diagonal,
		EnPassant
	}
	
	private int direction;
	private boolean hasMoved;
	private boolean justMovedTwoSpaces;
	
	public Pawn(Position p, boolean isWhite) {
		super(p, isWhite);
		
		direction = isWhite ? -1 : 1;
		hasMoved = false;
		justMovedTwoSpaces = false;
	}
	
	@Override
	boolean canMove(Position pos, Board b) {
		return !getMoveType(pos, b).equals(MoveType.Illegal);
	}

	/**
	 * Take in a position on the board and determine what MoveType
	 * that would be for this pawn.
	 */
	private MoveType getMoveType(Position pos, Board b) {
		if (!b.fits(pos))
			return MoveType.Illegal;
		
		if (isMoveForwardOne(pos, b))
			return MoveType.ForwardOne;
		
		if (isMoveForwardTwo(pos, b))
			return MoveType.ForwardTwo;
		
		if (isDiagonalCapture(pos, b))
			return MoveType.Diagonal;		
		
		if (isEnPassant(pos, b))
			return MoveType.EnPassant;
		
		return MoveType.Illegal;
	}
	
	private boolean isMoveForwardOne(Position pos, Board b) {
		if (pos.getX() == position.getX() && pos.getY() == position.getY() + direction)
			if (!pos.hasPiece())
				return true;
		
		return false;
	}
	
	private boolean isMoveForwardTwo(Position pos, Board b) {
		if (!hasMoved && isMoveForwardOne(new Position(pos.getX(),pos.getY()-direction), b)) 
			if (pos.getX() == position.getX() && pos.getY() == position.getY() + (2*direction))
				if (!pos.hasPiece())
					return true;
		
		return false;
	}
	
	private boolean isDiagonalCapture(Position pos, Board b) {
		if (pos.getY() == position.getY() + direction && Math.abs(pos.getX() - position.getX()) == 1) 
			if (isOppenentPiece(pos))
				return true;

		return false;
	}
	
	private boolean isEnPassant(Position pos, Board b) {
		if (pos.getY() == position.getY() + direction && Math.abs(pos.getX() - position.getX()) == 1) {
			Position opponentPosition = b.getPosition(pos.getX(), pos.getY() - direction);
			if (opponentPosition != null) {
				Piece opponentPiece = opponentPosition.getPiece();
				if (opponentPiece instanceof Pawn && opponentPiece.equals(b.getLastPieceMoved())) 
					if (((Pawn)opponentPiece).justMovedTwoSpaces)
						return true;
			}
		}
		return false;
	}

	@Override
	boolean move(Position newPos, Board board) {
		MoveType type = getMoveType(newPos, board);
		
		switch (type) {
			case Illegal: 		return false;
			case ForwardTwo: 	justMovedTwoSpaces = true;
								break;
			case EnPassant: 	board.clearPosition(newPos.getX(), newPos.getY() - direction);
			default: 			justMovedTwoSpaces = false;
		}

		newPos.setPiece(this);
		position = newPos;
		hasMoved = true;
		
		board.setLastPieceMoved(this);
		
		return true;
	}

}
