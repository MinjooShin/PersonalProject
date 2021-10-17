package com.todo.dao;

import java.io.BufferedReader;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.db.DbConnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;
	Connection conn;
	
	public TodoList() {
		this.list = new ArrayList<TodoItem>();
		this.conn=DbConnect.getConnection();
	}
	 
	public int addItem(TodoItem t) {
		String sql="insert into list(title, memo, category, current_date, due_date, ls_completed, ls_urgent, ls_important)"+"values(?,?,?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, t.getLs_completed());
			pstmt.setInt(7, t.getLs_urgent());
			pstmt.setString(8, t.getLs_important());
			count=pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql="delete from list where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count=pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	

	public int updateItem(TodoItem t) {
		String sql="update list set title=?, memo=?, category=?, current_date=?, due_date=?, ls_completed=?, ls_urgent=?, ls_important=?"+"where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,t.getTitle());
			pstmt.setString(2,t.getDesc());
			pstmt.setString(3,t.getCategory());
			pstmt.setString(4,t.getCurrent_date());
			pstmt.setString(5,t.getDue_date());
			pstmt.setInt(6, t.getLs_completed());
			pstmt.setInt(7, t.getLs_urgent());
			pstmt.setString(8, t.getLs_important());
			pstmt.setInt(9, t.getId());
			count=pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="select * from list";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date=rs.getString("current_date");
				int ls_completed=rs.getInt("ls_completed");
				int ls_urgent=rs.getInt("ls_urgent");
				String ls_important=rs.getString("ls_important");
				TodoItem t = new TodoItem(title, category, description, due_date, ls_completed, ls_urgent, ls_important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public ArrayList<TodoItem> getListcomp() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="select * from list where ls_completed=1;";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date=rs.getString("current_date");
				int ls_completed=rs.getInt("ls_completed");
				int ls_urgent=rs.getInt("ls_urgent");
				String ls_important=rs.getString("ls_important");
				TodoItem t = new TodoItem(title, category, description, due_date, ls_completed, ls_urgent, ls_important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int checkComp(int com_idx) {
		String sql="update list set ls_completed=?"+"where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, com_idx);
			count=pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getListforfind(String keyword) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword="%"+keyword+"%";
		try {
			String sql="select * from list where title like ? or memo like ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date=rs.getString("current_date");
				int ls_completed=rs.getInt("ls_completed");
				int ls_urgent=rs.getInt("ls_urgent");
				String ls_important=rs.getString("ls_important");
				TodoItem t = new TodoItem(title, category, description, due_date, ls_completed, ls_urgent, ls_important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt=conn.createStatement();
			String sql="select count(id) from list;";
			ResultSet rs=stmt.executeQuery(sql);
			rs.next();
			count=rs.getInt("Count(id)");
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}
	/*
	public void listAll() {
		System.out.println("\n"
				+ "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.toString());
		}
	}	
	*/
	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		String sql="select * from list where title=?;";
		PreparedStatement pstmt;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				if(rs.getString("title").equals(title)) return true;
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//초기 데이터 이전
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql="insert into list (title, memo, category, current_date, due_date, ls_completed, ls_urgent, ls_important)"+"values(?,?,?,?,?,?,?,?);";
			int records=0;
			while((line=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(line, "-");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String current_date = st.nextToken();
				String due_date = st.nextToken();
				int ls_completed=Integer.parseInt(st.nextToken());
				int ls_urgent=Integer.parseInt(st.nextToken());
				String ls_important=st.nextToken();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				pstmt.setInt(6, ls_completed);
				pstmt.setInt(7, ls_urgent);
				pstmt.setString(8, ls_important);
				int count=pstmt.executeUpdate();
				if(count>0) records++;
				pstmt.close();
			}
			System.out.println(records+" records read!!");
			br.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<String> getCategories() {
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="select distinct category from list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category=rs.getString("category");
				list.add(category);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql="select * from list where category = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date=rs.getString("current_date");
				int ls_completed=rs.getInt("ls_completed");
				int ls_urgent=rs.getInt("ls_urgent");
				String ls_important=rs.getString("ls_important");
				TodoItem t = new TodoItem(title, category, description, due_date, ls_completed, ls_urgent, ls_important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="select * from list order by "+orderby;
			if(ordering==0) sql+=" desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date=rs.getString("current_date");
				int ls_completed=rs.getInt("ls_completed");
				int ls_urgent=rs.getInt("ls_urgent");
				String ls_important=rs.getString("ls_important");
				TodoItem t = new TodoItem(title, category, description, due_date, ls_completed, ls_urgent, ls_important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getListurgent() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="select * from list where ls_urgent=1;";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date=rs.getString("current_date");
				int ls_completed=rs.getInt("ls_completed");
				int ls_urgent=rs.getInt("ls_urgent");
				String ls_important=rs.getString("ls_important");
				TodoItem t = new TodoItem(title, category, description, due_date, ls_completed, ls_urgent, ls_important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int markUrgent(int urg_idx) {
		String sql="update list set ls_urgent=?"+"where id=?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, urg_idx);
			count=pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int markImportance(int imp_idx) {
		String sql="update list set ls_important=?"+"where id=?;";
		PreparedStatement pstmt;
		String imp="important";
		int count=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, imp);
			pstmt.setInt(2, imp_idx);
			count=pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public ArrayList<TodoItem> getListimportance() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt=conn.createStatement();
			String sql="select * from list where ls_important='important';";
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				int id=rs.getInt("id");
				String category=rs.getString("category");
				String title=rs.getString("title");
				String description=rs.getString("memo");
				String due_date=rs.getString("due_date");
				String current_date=rs.getString("current_date");
				int ls_completed=rs.getInt("ls_completed");
				int ls_urgent=rs.getInt("ls_urgent");
				String ls_important=rs.getString("ls_important");
				TodoItem t = new TodoItem(title, category, description, due_date, ls_completed, ls_urgent, ls_important);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
