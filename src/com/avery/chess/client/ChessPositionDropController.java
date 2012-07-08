package com.avery.chess.client;

import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.avery.chess.shared.Board;
import com.avery.chess.shared.Piece;
import com.avery.chess.shared.Position;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class ChessPositionDropController extends SimpleDropController {

	private static final String style_valid_position = "valid-position";
	
	private final ChessPosition dropTarget;
	
	private ChessBoard chessBoard;
	private List<ChessPosition> positions;
	private Board board;
	private static List<Position> validPositions;

	public ChessPositionDropController(ChessPosition dropTarget, ChessBoard chessBoard) {
		super(dropTarget);
		this.dropTarget = dropTarget;
		this.chessBoard = chessBoard;
		this.positions = chessBoard.getPositions();
		this.board = chessBoard.getBoard();
	}

	@Override
	public void onMove(DragContext context) {
		if (validPositions == null) {
			log("onMove: "+getPieceFromContext(context).toStringWithPosition());
			
			validPositions = getPieceFromContext(context).getValidPostions();
			for (Position pos : validPositions) {
				positions.get(pos.getIndex()).addStyleName(style_valid_position);
			}
		}
		
		super.onMove(context);
	}

	@Override
	public void onDrop(DragContext context) {
		clearValidPositions();
		
		board.move(getPieceFromContext(context).getPosition(), dropTarget.getPosition());	
		
		chessBoard.reDrawPieces();
		
		super.onDrop(context);		
		
		log("onDrop: "+dropTarget.getPosition().toStringWithPiece());
	}
	
	private void clearValidPositions() {
		for (Position pos : validPositions) {
			positions.get(pos.getIndex()).removeStyleName(style_valid_position);
		}
		validPositions = null;
	}

	@Override
	public void onPreviewDrop(DragContext context) throws VetoDragException {
		log("onPreviewDrop: "+dropTarget.getPosition().toStringWithPiece());
		
		Piece p = getPieceFromContext(context);
		if (!validPositions.contains(dropTarget.getPosition())) {
			clearValidPositions();
			throw new VetoDragException();
		}
		
		super.onPreviewDrop(context);
	}

	@Override
	public void onEnter(DragContext context) {
		//Piece p = getPieceFromContext(context);
		//log("Enter: "+p.toStringWithPosition());
		//log("Enter: "+dropTarget.getPosition().toStringWithPiece());
	
		super.onEnter(context);
	}

	@Override
	public void onLeave(DragContext context) {
		//Piece p = getPieceFromContext(context);
		//log("Leave: "+p.toStringWithPosition());
		//log("Leave: "+dropTarget.getPosition().toStringWithPiece());
		
		super.onLeave(context);
	}
	
	private static Piece getPieceFromContext(DragContext context) {
		return ((ChessPiece)context.draggable).getPiece();
	}
	
	private static void log(String s) {
		Label label  = new Label(s);
		RootPanel.get("logs").add(label);
	}
	
}