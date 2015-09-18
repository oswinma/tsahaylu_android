package net.tsahaylu.www.dto;

import java.io.Serializable;
import java.util.Date;

import net.tsahaylu.www.model.Group;

public class GroupDTO  implements Serializable {

	private Long id;

	private Long fromid;

	private int status = 0;
	
	private String des;	
	
	private String name;
	
	private String type;

	private Date createtime;
	
	private int num=0;
	
	public GroupDTO()
	{
	}
	
	public GroupDTO(Group g, int num)
	{
		super();
		this.createtime=g.getCreatetime();
		this.des=g.getDes();
		this.fromid=g.getFromid();
		this.id=g.getId();
		this.name=g.getName();
		this.num=num;
		this.status=g.getStatus();
		this.type=g.getType();
	}
	
	
	
	public int getNum() {
		return num;
	}



	public void setNum(int num) {
		this.num = num;
	}



	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFromid() {
		return fromid;
	}

	public void setFromid(Long fromid) {
		this.fromid = fromid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
