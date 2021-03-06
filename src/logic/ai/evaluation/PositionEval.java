package logic.ai.evaluation;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import core.Board;
import core.Player;

public class PositionEval implements Evaluation{
	@Override
	public int evaluate(final Board board, final Player player) {
		int discsquares = 0;//评估特性6
		Map<Point,Integer> discvalue = getdiscvaluemap();
		Set<Point> mypoint = board.getSquares(player.color());
		int mydiscsvalue = 0;
		Iterator iterator = mypoint.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			mydiscsvalue = mydiscsvalue + discvalue.get(point);
		}
		Set<Point> oppoint = board.getSquares(player.opponent().color());
		int opdiscsvalue = 0;
		iterator = oppoint.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			opdiscsvalue = opdiscsvalue + discvalue.get(point);
		}
		discsquares = mydiscsvalue - opdiscsvalue;
		return discsquares;
	}
	
	
	
	private Map<Point,Integer> getdiscvaluemap(){
		Map<Point,Integer> discvalue = new HashMap<Point,Integer>();
		Set<Point> eleven = new HashSet<Point>();
		eleven.add(new Point(2,0));
		eleven.add(new Point(0,2));
		eleven.add(new Point(5,0));
		eleven.add(new Point(0,5));
		eleven.add(new Point(7,2));
		eleven.add(new Point(2,7));
		eleven.add(new Point(7,5));
		eleven.add(new Point(5,7));
		Set<Point> negfour = new HashSet<Point>();
		eleven.add(new Point(2,1));
		eleven.add(new Point(1,2));
		eleven.add(new Point(5,1));
		eleven.add(new Point(1,5));
		eleven.add(new Point(2,6));
		eleven.add(new Point(6,2));
		eleven.add(new Point(6,5));
		eleven.add(new Point(5,6));
		Set<Point> negthree = new HashSet<Point>();
		eleven.add(new Point(1,0));
		eleven.add(new Point(0,1));
		eleven.add(new Point(6,0));
		eleven.add(new Point(0,6));
		eleven.add(new Point(7,1));
		eleven.add(new Point(1,7));
		eleven.add(new Point(7,6));
		eleven.add(new Point(6,7));
		eleven.add(new Point(3,3));
		eleven.add(new Point(4,3));
		eleven.add(new Point(3,4));
		eleven.add(new Point(4,4));
		Set<Point> negseven = new HashSet<Point>();
		eleven.add(new Point(1,1));
		eleven.add(new Point(6,1));
		eleven.add(new Point(1,6));
		eleven.add(new Point(6,6));
		Set<Point> twenty = new HashSet<Point>();
		eleven.add(new Point(0,0));
		eleven.add(new Point(7,0));
		eleven.add(new Point(0,7));
		eleven.add(new Point(7,7));
		Set<Point> two = new HashSet<Point>();
		eleven.add(new Point(2,2));
		eleven.add(new Point(3,2));
		eleven.add(new Point(4,2));
		eleven.add(new Point(5,2));
		eleven.add(new Point(2,3));
		eleven.add(new Point(2,4));
		eleven.add(new Point(2,5));
		eleven.add(new Point(3,5));
		eleven.add(new Point(4,5));
		eleven.add(new Point(5,5));
		eleven.add(new Point(5,3));
		eleven.add(new Point(5,4));
		Set<Point> one = new HashSet<Point>();
		eleven.add(new Point(3,1));
		eleven.add(new Point(1,3));
		eleven.add(new Point(4,1));
		eleven.add(new Point(1,4));
		eleven.add(new Point(6,3));
		eleven.add(new Point(3,6));
		eleven.add(new Point(6,4));
		eleven.add(new Point(4,6));
		Set<Point> eight = new HashSet<Point>();
		eleven.add(new Point(3,0));
		eleven.add(new Point(4,0));
		eleven.add(new Point(0,3));
		eleven.add(new Point(0,4));
		eleven.add(new Point(7,3));
		eleven.add(new Point(7,4));
		eleven.add(new Point(3,7));
		eleven.add(new Point(4,7));
		Iterator iterator = eleven.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			discvalue.put(point, 11);
		}
		iterator = negfour.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			discvalue.put(point, -4);
		}
		iterator = negthree.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			discvalue.put(point, -3);
		}
		iterator = negseven.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			discvalue.put(point, -7);
		}
		iterator = twenty.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			discvalue.put(point, 20);
		}
		iterator = two.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			discvalue.put(point, 2);
		}
		iterator = one.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			discvalue.put(point, 1);
		}
		iterator = eight.iterator();
		while(iterator.hasNext()){
			Point point = (Point) iterator.next();
			discvalue.put(point, 8);
		}
		return discvalue;
	}

}
