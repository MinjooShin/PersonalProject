package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private int id;
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int ls_completed;
    private int ls_urgent;
    private String ls_important;

    public TodoItem(String title, String category, String desc, String due_date,  int ls_completed, int ls_urgent, String ls_important){
    	this.title=title;
        this.desc=desc;
        Date nowdate = new Date();//Date 객체 생성
        SimpleDateFormat spf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//Date포맷 객체 생성
        this.current_date=spf.format(nowdate);// 현재 시간을 지정한 포맷의 문자열로 저장.
        this.category=category;
        this.due_date=due_date;
        this.ls_completed=ls_completed;
        this.ls_urgent=ls_urgent;
        this.ls_important=ls_important;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    public String toSaveString() { //문자열로 저장하기 위함.
    	return getId()+"-"+category+"-"+title+"-"+desc+"-"+current_date+"-"+due_date+"-"+ls_completed+"-"+ls_urgent+"-"+ls_important+"\n";
    }

	@Override
	public String toString() {
		return getId()+". "+" ["+ category +"] " + title + " - " + desc + " - " + current_date + " - " + due_date+ " - " + ls_completed + " - " + ls_urgent + " - " + ls_important;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public void setId(int id) {
		this.id=id;
	}

	public int getId() {
		return id;
	}

	public int getLs_completed() {
		return this.ls_completed;
	}

	public void setLs_completed(int ls_completed) {
		this.ls_completed = ls_completed;
	}

	public int getLs_urgent() {
		return ls_urgent;
	}

	public void setLs_urgent(int ls_urgent) {
		this.ls_urgent = ls_urgent;
	}

	public String getLs_important() {
		return ls_important;
	}

	public void setLs_important(String ls_important) {
		this.ls_important = ls_important;
	}
}
