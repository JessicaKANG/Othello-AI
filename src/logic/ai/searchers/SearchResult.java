package logic.ai.searchers;

import java.awt.Point;

/**
 * A search results consists of a mapping of a move to it's result
 * obtained by the evaluation method that was chosen
 *
 * Alternatively a we could maintain a hash table
 * of moves/keys to results/values, but we don't
 * actually need to store more the search result
 * with the best move, at a time. That is the map
 * would always have size 1, as if we were to insert
 * a new mapping we wouldn't need all the previous.
 */
public class SearchResult {

	private Point point;//坐标
	private int score;//评分
	private int nodenum;
	public int getScore() {
		return score;
	}

	public Point getPoint() {		
		return point;
	}

	public int getnodenum(){
		return nodenum;
	}
	public SearchResult negated() {
		return new SearchResult(point, -score,nodenum);
	}

	public SearchResult(Point point, int score,int nodenum) {
		this.point = point;
		this.score = score;
		this.nodenum = nodenum;
	}
}

