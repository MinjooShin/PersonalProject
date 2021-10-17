package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title="", desc="", category="", due_date="", ls_important="";
		int ls_completed=0, ls_urgent=0;
		Scanner sc = new Scanner(System.in);

		System.out.println("\n"
				+ "========== Create item Section\n"
				+ "enter the title > ");
		title=sc.next();
		sc.nextLine();//제목 뒤에 enter없애주기 위함.
		if (list.isDuplicate(title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		System.out.println("enter the category > ");
		category = sc.next();
		sc.nextLine();//제목 뒤에 enter없애주기 위함.
		
		System.out.println("enter the description > ");
		desc = sc.nextLine().trim();
		
		System.out.println("enter the due_date > ");
		due_date = sc.nextLine().trim();
		
		System.out.println("enter the ls_completed > ");
		ls_completed = sc.nextInt();
		sc.nextLine(); //엔터키 제거
		
		System.out.println("enter the ls_urgent > ");
		ls_urgent = sc.nextInt();
		sc.nextLine(); //엔터키 제거
		
		System.out.println("enter the ls_important > ");
		ls_important = sc.nextLine().trim();
		
		
		TodoItem t = new TodoItem(title, category, desc, due_date, ls_completed, ls_urgent, ls_important);
		
		if(list.addItem(t)>0) System.out.println("item added!");
		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);

		System.out.println("\n"
				+ "========== Delete Item Section\n"
				+ "enter the number of item to remove\n"
				+ "\n");
		int num = sc.nextInt();
		if(l.deleteItem(num)>0) System.out.println("item deleted!");
		sc.close();
	}
	
	public static void multiDeleteItem(TodoList l, String choice) {
		
		String find_str=choice.substring(10);
		int num=0, count=0;
		StringTokenizer st = new StringTokenizer(find_str, ",");
		
		System.out.println("\n" + "========== Delete Multiple Items Section\n");
		
		while(st.hasMoreTokens()) {
			num=Integer.parseInt(st.nextToken());
			System.out.println(num);
			if(l.deleteItem(num)>0) count++; 
		}
			
		System.out.printf("총 %d개의 항목이 삭제되었습니다.", count);
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "========== Edit Item Section\n"
				+ "enter the number of the item you want to update\n"
				+ "\n");
		int num = sc.nextInt();
		sc.nextLine(); //엔터키 제거
		
		System.out.println("enter the new title ");
		String new_title = sc.nextLine().trim();
		
		System.out.println("enter the new category of the item");
		String new_category = sc.next();
		sc.nextLine(); //엔터키 제거

		System.out.println("enter the new description ");
		String new_desc = sc.nextLine().trim();

		System.out.println("enter the due_date");
		String new_due_date = sc.nextLine().trim();
		
		System.out.println("enter the ls_completed");
		int new_ls_completed = sc.nextInt();
		sc.nextLine(); //엔터키 제거
		
		System.out.println("enter the ls_urgent");
		int new_ls_urgent = sc.nextInt();
		sc.nextLine(); //엔터키 제거
		
		System.out.println("enter the ls_important");
		String new_ls_important = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_desc, new_category, new_due_date, new_ls_completed, new_ls_urgent, new_ls_important);
		t.setId(num);
		if(l.updateItem(t)>0) System.out.println("item updated!");
		
	}
	
	public static void saveList(TodoList l, String filename) {
	//list내용을 filename에 저장.
		try {
			Writer w = new FileWriter(filename, true); 
			for(TodoItem item: l.getList()) {
				w.write(item.toSaveString());
			}
			w.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadList(TodoList l, String filename) {
		//파일 내용 읽기
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String oneline;
			while((oneline=br.readLine())!=null) {
				StringTokenizer st = new StringTokenizer(oneline, "-");
				int id=Integer.parseInt(st.nextToken());
				String category=st.nextToken();
				String title=st.nextToken();
				String desc=st.nextToken();
				String current_date=st.nextToken();
				String due_date=st.nextToken();
				int ls_completed=Integer.parseInt(st.nextToken());
				int ls_urgent=Integer.parseInt(st.nextToken());
				String ls_important=st.nextToken();
				TodoItem item = new TodoItem(title, category, desc, due_date, ls_completed, ls_urgent, ls_important);
				item.setId(id);
				item.setCurrent_date(current_date);
				System.out.println(item.toString());
			}
			br.close();
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void findItem(TodoList l, String choice) {
		String find_str="";
		find_str=choice.substring(5);
		int cnt_sum=0;
		for(TodoItem item: l.getListforfind(find_str)) {
			if(item.getCategory().contains(find_str)||item.getTitle().contains(find_str)||item.getDesc().contains(find_str)) {
				System.out.println(item.toString());
				cnt_sum++;
			}
		}
		System.out.println("총 "+cnt_sum+"개의 항목을 찾았습니다.");
	}

	public static void findCate(TodoList l, String choice) {
		String find_str="";
		find_str=choice.substring(10);
		int count=0;
		for(TodoItem item : l.getListCategory(find_str)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listCate(TodoList l) {
		int count=0;
		for(String item: l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리 등록되어 있습니다.\n", count);
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item:l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void lscompleteItem(TodoList l) {
		int count=0;
			for(TodoItem item : l.getListcomp()) {
				count++;
				System.out.println("[v] "+item.toString());
			}
			System.out.printf("총 %d개의 항목이 완료되었습니다.", count);
	}
	
	public static void completeItem(TodoList l, String choice) {
		String find_str="";
		find_str=choice.substring(5);
		int com_idx =Integer.parseInt(find_str);
		if(l.checkComp(com_idx)>0) System.out.println("check completed!");	
		}
	
	public static void multiCompleteItem(TodoList l, String choice) {
		String find_str=choice.substring(11);
		int num=0, count=0;
		StringTokenizer st = new StringTokenizer(find_str, ",");
		
		while(st.hasMoreTokens()) {
			num=Integer.parseInt(st.nextToken());
			System.out.println(num);
			if(l.checkComp(num)>0) count++; 
		}
			
		System.out.printf("총 %d개의 항목이 완료 체크되었습니다.", count);
		}

	public static void lsurgentItem(TodoList l) {
		int count=0; //급한 항목이 몇 개 있는지 알리기위함.
			for(TodoItem item: l.getListurgent()) {
				count++;
				System.out.println("[!] "+item.toString());
			}
			System.out.printf("총 %d개의 급한 항목이 있습니다.", count);
		
	}
	
	public static void urgentItem(TodoList l, String choice) {
		String find_str="";
		find_str=choice.substring(7);
		int urg_idx=Integer.parseInt(find_str); //표시할 항목을 아이디 값으로 찾기 위해 int형 변수 선언해서 문자열로 가지고 온 파라미터 변수를 integer로 바꿔줌
		if(l.markUrgent(urg_idx)>0) System.out.println("mark completed!");
		}


	public static void ls_importantItem(TodoList l) {
		int count=0; //중요한 항목이 몇 개 있는지 알리기위함.
			for(TodoItem item: l.getListimportance()) {
				count++;
				System.out.println("[★] "+item.toString());
			}
			System.out.printf("총 %d개의 중요한 항목이 있습니다.", count);
		
	}
	public static void importantItem(TodoList l, String choice) {
		String find_str="";
		find_str=choice.substring(10);
		int imp_idx=Integer.parseInt(find_str); //표시할 항목을 아이디 값으로 찾기 위해 int형 변수 선언해서 문자열로 가지고 온 파라미터 변수를 integer로 바꿔줌
		if(l.markImportance(imp_idx)>0) System.out.println("Importance mark completed!");
		}
	}
	