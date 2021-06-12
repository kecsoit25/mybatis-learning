package test.test1.mybatis.pojo;

public class Question {
	private Integer id;
	private String value;
	private Integer credit;
	private Integer player_id;
	private String isOpen;
	private Player player;

	public Question() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(Integer playId) {
		this.player_id = playId;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String toString() {
		String ret = "";
		ret += "[Q:" + this.id + "," + this.value + "," + this.isOpen + "," + this.credit ;
		if(this.player != null) {
			ret += "," + this.player.toString();
		}
		ret += "]";
		return ret;
	}
}
