package logic;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import core.Board;
import core.SquareState;

/**
 * Explores moves.
 * 探索移动
 */
public class MoveExplorer {

	/**
	 * Given a starting position - seed - checks if there's possibility of
	 * finding a desired result looking towards some {@code Direction}.
	 * 给出起始位置和方位，判断该方向是否有敌方棋子
	 * @param board - the board to search in
	 * @param seed - where we start searching from
	 * @param direction - which direction should we search
	 * @return if search in this direction is likely to have interesting findings
	 */
	private static boolean shouldSearch(final Board board, final Point seed, final Direction direction) {
		Point nextPoint = direction.next(seed);
		//如果下一个坐标在棋盘范围内，则返回下一坐标棋色与当前位置棋色取反的比较结果;否则返回false
		return pointIsValid(nextPoint) ? board.getSquareState(nextPoint)
						 == board.getSquareState(seed).opposite() : false;
	}

	/**
	 * Check if the given point a valid point on the board.
	 * Valid means within board limits
	 * 判断该坐标是否在棋盘有效范围
	 * @param point - the point to check
	 * @return if the point is valid
	 */
	private static boolean pointIsValid(Point point) {
		return point.x >= 0 && point.x < Board.BOARD_LENGTH
		       && point.y >= 0 && point.y < Board.BOARD_WIDTH;
	}

	/**
	 * Find all possible points where a player with the given state can move next
	 * 探索该方全部可落子位置
	 * @param board - the board to look into
	 * @param state - the player's color
	 * @return the player's possible moves
	 */
	public static Set<Point> explore(final Board board, final SquareState state) {
		Set<Point> possibleMoves = new HashSet<Point>();//全部可落子位置集合
		Set<Point> statePoints = board.getSquares(state);//当前棋盘中一方全部棋子位置
		for (Point seed : statePoints) {//遍历当前棋盘一方全部棋子位置
			for (Direction direction : Direction.values()) {//遍历每个当前棋子位置的八个方位
				if (shouldSearch(board, seed, direction)) {//如果该方位有敌方棋子
					Point nextPoint = direction.next(seed);
					nextPoint = direction.next(nextPoint);//跳过敌方棋子获得坐标
					while (pointIsValid(nextPoint)) {//如果该坐标仍在棋盘上，则判断
						if (board.getSquareState(nextPoint) == state) {//该位置如果有本方棋子就退出当前棋子在此方位的搜索
							break;
						} else if (board.getSquareState(nextPoint) == SquareState.EMPTY) {//该位置如果是空的
							possibleMoves.add(nextPoint);//则把该位置加到可落子位置集合中，退出当前棋子在此方位的搜索
							break;
						}
						nextPoint = direction.next(nextPoint);//该位置如果是敌方棋子，则再次跳过敌方棋子获取位置
					}
				}
			}
		}
		return possibleMoves;
	}

	/**
	 * Given a starting position - seed - find all points on the board
	 * that must be filled or have their color/state changed.
	 * 一次落子后，找到所有待反转的棋子
	 * @param board - the board to look into
	 * @param seed - the starting position
	 * @return the points that need to change state
	 */
	public static Set<Point> squaresToFill(final Board board, final Point seed) {
		Set<Point> filledlist = new HashSet<Point>();
		SquareState seedState = board.getSquareState(seed);//获得落子颜色
		for (Direction direction : Direction.values()) {//遍历八个方向
			if (shouldSearch(board, seed, direction)) {//如果此方向有敌情
				Point nextPoint = direction.next(seed);
				LinkedList<Point> templist = new LinkedList<Point>();
				while (pointIsValid(nextPoint)) {//沿着该方向一路找下去，记录所有敌方棋子，直到遇到空位或本方棋子则停下
					SquareState nextState = board.getSquareState(nextPoint);
					if (nextState == seedState.opposite()) {
						templist.add(nextPoint);
					} else if (nextState == seedState) {
						filledlist.addAll(templist);
						break;
					} else if (nextState == SquareState.EMPTY) {
						break;
					}
					nextPoint = direction.next(nextPoint);
				}
			}
		}
		return filledlist;//记录下的棋子集合就是待反转的棋子
	}
}

