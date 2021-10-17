package com.todo;

import java.util.Scanner;
import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//초기 데이터 이전 목적
		//l.importData("todolist.txt");
		boolean quit = false;
		TodoUtil.loadList(l, "todolist.txt");
		
		Menu.displaymenu(); 
		do {
			Menu.prompt();
			String choice = sc.nextLine();

			if (choice.equals("add")) {
				TodoUtil.createItem(l);
			}
			else if (choice.equals("del")) {
				TodoUtil.deleteItem(l);
			}	
			else if (choice.contains("multi del")) {
				TodoUtil.multiDeleteItem(l, choice);
			}	
			else if (choice.equals("edit")){
				TodoUtil.updateItem(l);
			}
			else if (choice.equals("ls")) {
				TodoUtil.listAll(l, "current_date", 1);
			}
			else if (choice.contains("find_cate")){
				TodoUtil.findCate(l, choice);
			}
			else if (choice.contains("find")){
				TodoUtil.findItem(l, choice);
			}
			else if(choice.equals("ls_comp")) {
				TodoUtil.lscompleteItem(l);
			}
			else if(choice.contains("multi comp")) {
				TodoUtil.multiCompleteItem(l ,choice);
			}
			else if(choice.contains("comp")) {
				TodoUtil.completeItem(l ,choice);
			}
			else if(choice.equals("ls_urgent")) {
				TodoUtil.lsurgentItem(l);
			}
			else if(choice.contains("urgent")) {
				TodoUtil.urgentItem(l ,choice);
			}
			else if(choice.equals("ls_important")) {
				TodoUtil.ls_importantItem(l);
			}
			else if(choice.contains("important")) {
				TodoUtil.importantItem(l ,choice);
			}
			else if (choice.equals("ls_cate")){
				TodoUtil.listCate(l);
			}
			else if (choice.equals("ls_name")) {
				System.out.println("sorted in order of the title.");
				TodoUtil.listAll(l,"title", 1);
			}
			else if (choice.equals("ls_name_desc")) {
				System.out.println("sorted in reverse order of the title.");
				TodoUtil.listAll(l,"title", 0);
			}
			else if (choice.equals("ls_date")) {
				System.out.println("sorted in order of the date.");
				TodoUtil.listAll(l,"due_date", 1);
			}
			else if (choice.equals("ls_date_desc")) {
				System.out.println("sorted in reverse order of the date.");
				TodoUtil.listAll(l,"due_date", 0);
			}
			else if (choice.equals("help")) {
				Menu.displaymenu();
			}	
			else if (choice.equals("exit")) {
				TodoUtil.saveList(l,"todolist.txt");
				quit = true;
			}
			else {
				System.out.println("please enter one of the above mentioned command (if you need a help, you can enter the \"help\" key.)");
			}
			
		} while (!quit);
	}
}
