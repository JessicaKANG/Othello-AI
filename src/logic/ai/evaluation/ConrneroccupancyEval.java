package logic.ai.evaluation;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import core.Board;
import core.Player;

public class ConrneroccupancyEval implements Evaluation {
	Set<Point> corners;
	@Override
	public int evaluate(final Board board, final Player player) {
		this.corners = new HashSet<Point>(4);
		this.corners.add(new Point(0, 0));
		this.corners.add(new Point(7, 0));
		this.corners.add(new Point(0, 7));
		this.corners.add(new Point(7, 7));
		int corneroccupancy = 0;//评估特性2
		int mycorner = 0;
		int opcorner = 0;
		for (Point p : corners) {
			if (board.getSquareState(p) == player.color()) {
				mycorner++;
			}else if(board.getSquareState(p) == player.opponent().color()){
				opcorner++;
			}
		}
		corneroccupancy = 25*mycorner - 25*opcorner;
		return corneroccupancy;
	}
}
