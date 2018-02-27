package logic.ai.evaluation;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import core.Board;
import core.Player;

public class ConnerclosennessEval implements Evaluation {
		Set<Point> dangerpoints;
	@Override
	public int evaluate(final Board board, final Player player) {
		this.dangerpoints = new HashSet<Point>(4);
		this.dangerpoints.add(new Point(1, 1));
		this.dangerpoints.add(new Point(6, 6));	
		this.dangerpoints.add(new Point(6, 1));
		this.dangerpoints.add(new Point(1, 6));
				
		this.dangerpoints.add(new Point(1, 0));
		this.dangerpoints.add(new Point(0, 1));
		
		this.dangerpoints.add(new Point(6, 0));
		this.dangerpoints.add(new Point(7, 1));
		
		this.dangerpoints.add(new Point(1, 7));
		this.dangerpoints.add(new Point(0, 6));
		
		
		this.dangerpoints.add(new Point(7, 6));
		this.dangerpoints.add(new Point(6, 7));
		int cornerclosenness = 0;//评估特性3
		int mydangerpoint = 0;
		int opdangerpoint = 0;
		for (Point p : dangerpoints) {
			if (board.getSquareState(p) == player.color()) {
				mydangerpoint++;
			}else if(board.getSquareState(p) == player.opponent().color()){
				opdangerpoint++;
			}
		}
		cornerclosenness = (int) ((-12.5)*mydangerpoint + 12.5* opdangerpoint);
		return cornerclosenness;
	}
}
