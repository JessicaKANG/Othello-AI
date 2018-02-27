package logic;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

import core.Board;
import core.SquareState;

public class FrontierExplorer {
	/**
	 * Given a starting position - seed - checks if there's possibility of
	 * finding a desired result looking towards some {@code Direction}.
	 * 给出起始位置和方位，判断该方向是否有空位
	 * @param board - the board to search in
	 * @param seed - where we start searching from
	 * @param direction - which direction should we search
	 * @return if search in this direction is likely to have interesting findings
	 */
	private static boolean shouldSearch(final Board board, final Point seed, final Direction direction) {
		Point nextPoint = direction.next(seed);
		return pointIsValid(nextPoint) ? board.getSquareState(nextPoint)
						 == SquareState.EMPTY : false;
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
	 * Find all frontier discs of a given state
	 * 探索该方全部边界棋子，即周围有空位，易被翻转的棋子
	 * @param board - the board to look into
	 * @param state - the player's color
	 * @return the player's frontier discs number
	 */
	public static int explore(final Board board, final SquareState state) {
		int frontierdiscs = 0;
		Set<Point> statePoints = board.getSquares(state);//当前棋盘中一方全部棋子位置
		for (Point seed : statePoints) {//遍历当前棋盘一方全部棋子位置
			for (Direction direction : Direction.values()) {//遍历每个当前棋子位置的八个方位
				if (shouldSearch(board, seed, direction)) {//如果该方位有空位
					frontierdiscs++;
				}
			}
		}
		return frontierdiscs;
	}

}
