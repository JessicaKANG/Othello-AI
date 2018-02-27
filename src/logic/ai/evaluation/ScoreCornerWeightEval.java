package logic.ai.evaluation;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import core.Board;
import core.Player;
import logic.MoveExplorer;

/**
 * Adds weight to the score if the new state has corners owned by the player
 * 
 * @author c00kiemon5ter
 */
public class ScoreCornerWeightEval implements Evaluation {

	private Set<Point> corners;
	private int cornerweight;
	private Set<Point> sides;
	private int sideweight;
	private Set<Point> dangerpoints;
	private int dangerweight;

	public ScoreCornerWeightEval(int cornerweight,int sideweight,int dangerweight) {
		this.cornerweight = cornerweight;
		this.sideweight = sideweight;
		this.dangerweight = dangerweight;
		this.corners = new HashSet<Point>(4);
		this.corners.add(new Point(0, 0));
		this.corners.add(new Point(Board.BOARD_LENGTH, 0));
		this.corners.add(new Point(0, Board.BOARD_WIDTH));
		this.corners.add(new Point(Board.BOARD_LENGTH, Board.BOARD_WIDTH));
		
		this.sides = new HashSet<Point>((Board.BOARD_LENGTH-2)*(Board.BOARD_WIDTH-2));
		for (int i = 2;i< Board.BOARD_LENGTH-1;i++){
			this.sides.add(new Point(i,0));
		}
		for (int i = 2;i< Board.BOARD_WIDTH-1;i++){
			this.sides.add(new Point(0,i));
		}
		this.dangerpoints = new HashSet<Point>(4);
		this.dangerpoints.add(new Point(1, 1));
		this.dangerpoints.add(new Point(1, 0));
		this.dangerpoints.add(new Point(0, 1));
		this.dangerpoints.add(new Point(Board.BOARD_LENGTH-1, 1));
		this.dangerpoints.add(new Point(Board.BOARD_LENGTH-1, 0));
		this.dangerpoints.add(new Point(Board.BOARD_LENGTH, 1));
		this.dangerpoints.add(new Point(1, Board.BOARD_WIDTH-1));
		this.dangerpoints.add(new Point(0, Board.BOARD_WIDTH-1));
		this.dangerpoints.add(new Point(1, Board.BOARD_WIDTH));
		this.dangerpoints.add(new Point(Board.BOARD_LENGTH-1, Board.BOARD_WIDTH-1));	
		this.dangerpoints.add(new Point(Board.BOARD_LENGTH, Board.BOARD_WIDTH-1));
		this.dangerpoints.add(new Point(Board.BOARD_LENGTH-1, Board.BOARD_WIDTH));
	}

/*	public ScoreCornerWeightEval() {
		this(12);
	}*/

	@Override
	public int evaluate(Board board, Player player) {
		//int score = board.count(player.color());
		int score = 0;
		int number = board.count(player.color())+board.count(player.opponent().color());//当前棋盘子数
		Set<Point> possibleMoves = MoveExplorer.explore(board, player.color());//当前持方所有可落子位置
		Set<Point> possibleMovesop = MoveExplorer.explore(board, player.opponent().color());//敌方所有可落子位置
		int scorediff = board.count(player.opponent().color())-board.count(player.color());//敌我子数差
		int mobility = possibleMoves.size()-possibleMovesop.size();//当前持方移动性
		int dangerpoint = avoiddanger(board,player);
		int goodpoint = goodpoint(board,player);
		return mobility+dangerpoint + goodpoint;
/*		if(number<20){
			score = (int) (mobility*70+(dangerpoint*0.2+goodpoint*0.8)*20+ scorediff*10);
			return score;
		}else if(number<40){
			score = (int) (mobility*30+(dangerpoint*0.2 + goodpoint*0.8)*60 + scorediff*10);
			return score;
		}else if(number<60){
			score = mobility*0 + (dangerpoint*0 + goodpoint*1)*60- scorediff*40;
			return score;
		}else{
			score = mobility*0 + (dangerpoint*0 + goodpoint*1)*0- scorediff*100;
			return score;
		}*/
		
	}
	private int avoiddanger(Board board, Player player){	
			int score = 0 ;
			for (Point p : dangerpoints) {
				if (board.getSquareState(p) == player.color()) {
					score -= dangerweight;
				}
			}
			return score;
	}
	private int goodpoint(Board board, Player player){	
		int score = 0;
		for (Point p : corners) {
			if (board.getSquareState(p) == player.color()) {
				score += cornerweight;
			}
		}
		for (Point p : sides) {
			if (board.getSquareState(p) == player.color()) {
				score += sideweight;
			}
		}
		return score;
	}
	
}