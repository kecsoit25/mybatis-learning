package test.test1.mybatis.pojo;

public class DbPlayer {
	private Integer id;
	
	private String name;
	private Integer score;
	private String username;
	private String password;
	public DbPlayer() {
		// TODO Auto-generated constructor stub
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String nickname) {
		this.name = nickname;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString() {
		return "[" + this.getClass().getSimpleName() + ":" + this.id + "," + this.name + ","
				+ this.score + "," + this.username + "," + this.password + "]";
	}

}
