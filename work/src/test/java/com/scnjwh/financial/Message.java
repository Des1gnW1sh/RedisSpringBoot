package com.scnjwh.intellectreport;

import java.util.Arrays;


public class Message {
	
	private String msg_title;
	private String id;
	private String[] msg_content;
	public Message() {
		// TODO Auto-generated constructor stub
	}
	
	public Message( String id,String msg_title, String[] msg_content) {
		super();
		this.msg_title = msg_title;
		this.id = id;
		this.msg_content = msg_content;
	}
	public String getMsg_title() {
		return msg_title;
	}
	public void setMsg_title(String msg_title) {
		this.msg_title = msg_title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String[] getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String[] msg_content) {
		this.msg_content = msg_content;
	}

	@Override
	public String toString() {
		return "Message [msg_title=" + msg_title + ", id=" + id
				+ ", msg_content=" + Arrays.toString(msg_content) + "]";
	}
	
}
