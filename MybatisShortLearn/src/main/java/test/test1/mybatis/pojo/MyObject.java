package test.test1.mybatis.pojo;

public class MyObject {
	private int object_id;
	private String code;
	private String name;

	public MyObject() {
		// TODO Auto-generated constructor stub
	}

	public int getObject_id() {
		return object_id;
	}

	public void setObject_id(int object_id) {
		this.object_id = object_id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.code + "=>" + this.name;
	}

}
