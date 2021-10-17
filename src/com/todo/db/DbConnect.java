
/*
자바에서 JDBC를 이용해서 Database와 통신함.
JDBC(Java DataBase Connectivity):데이터베이스에 연결하기 위한 API를 자바에 제공하는 기능.
DBMS(DataBase Management System):디비관리시스템. 자신의 드라이버에 의해 동작.
SQLite -> 커넥터(jar파일) 다운=자신의 JDBC 드라이버 등록
즉, JDBC는 드라이버가 등록되면, 미리 정의된 API의 메소드를 호출하여 데이터베이스에 접근함.  
1. 사용할 DBMS(SQLite) 커넥터 자바 버전 다운받고, Add External JARs...
2. sql문을 사용할 클래스에 라이브러리 추가하기. import java.sql.*;
*/
package com.todo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	private static Connection conn=null;
	
	public static void closeConnection() {
		if(conn!=null) {
			try {
				conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if(conn==null) {
			try {
				/*
				!Reflection!
				Class.forName(JDBC_DRIVERS);
				드라이버 경로 전달. 해당 클래스(드라이버)가 없으면 ClassNotFoundException을 Throw함.
				try~catch문으로 핸들링해줘야함.
				성공하면 driverMannager에 해당 드라이버가 등록되어 DB사용 가능.	
				*/
				Class.forName("org.sqlite.JDBC");
				//해당 경로의 DB에 접속해서 DB내의 데이터를 conn에 저장. 실패하면 SQLException발생.
				conn=DriverManager.getConnection("jdbc:sqlite:"+"todolist.db");
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}
	
}
