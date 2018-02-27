package core;

/**
 * The state of a square - a point in the board.
 * The state can be:
 * <ul>棋格的四种状态：黑子，白子，空的，可落子
 * <li>Black - the square is colored black
 * <li>White - the square is colored white
 * <li>Pssbl - the square is a next possible move
 * <li>Empty - the square is empty.
 * </ul>
 * Each state is represented with a state-symbol.
 *
 * @author c00kiemon5ter
 */
public enum SquareState {

	BLACK('●'),
	WHITE('○'),
	PSSBL('.'),
	EMPTY(' ');
	private final char symbol;
	/**
	 * 构造器
	 * @param symbol
	 */
	SquareState(char symbol) {
		this.symbol = symbol;
	}

	public char symbol() {
		return this.symbol;
	}
	/**
	 * 当前棋子颜色取反
	 * @return
	 */
	public SquareState opposite() {
		return this == BLACK ? WHITE : BLACK;
	}

	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
}