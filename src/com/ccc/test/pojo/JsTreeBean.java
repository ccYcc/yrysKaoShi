package com.ccc.test.pojo;

import java.io.Serializable;

/**该类用于输出json树对象，字段名字符合jsTree框架
 * {@link http://www.jstree.com/docs/events/}
 * @author Trible Chen
 *d
 */
public class JsTreeBean implements Serializable{

	int id; //树节点id
	String text;//节点名字
	Serializable children;//节点的孩子节点，List<JsTreeBean>类型；如果使用延迟加载，返回true，代表该节点有孩子节点
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Serializable getChildren() {
		return children;
	}
	public void setChildren(Serializable children) {
		this.children = children;
	}
	
}
