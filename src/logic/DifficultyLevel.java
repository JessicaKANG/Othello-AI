package logic;

/**
 * Four difficulty levels.
 * Difficulty specifies the depth that the {@code Searchers} will dive
 * to predict score results and thus select the next move
 * 搜索深度决定难度系数
 * @author c00kiemon5ter
 */
public enum DifficultyLevel {

	EASY("Easy", 1),
	NORMAL("Normal", 4),
	HARD("Hard", 5),
	HEROIC("Heroic", 6);
	private String description;
	private int level;

	private DifficultyLevel(String descr, int level) {
		this.description = descr;
		this.level = level;
	}

	public String description() {
		return description;
	}

	public int level() {
		return level;
	}
}
