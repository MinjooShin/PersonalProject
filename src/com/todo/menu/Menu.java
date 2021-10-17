package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<ToDoList How to use command instructions>");
        System.out.println("1. Add a new item ( add )");
        System.out.println("2. Delete an existing item ( del )");
        System.out.println("3. Delete an existing multiple items ( multi del <keyword> )");
        System.out.println("4. Update an item  ( edit )");
        System.out.println("5. List all items ( ls )");
        System.out.println("6. find item ( find <keyword> )");
        System.out.println("7. List all item's category ( is_cate )");
        System.out.println("8. sort the list by name ( ls_name )");
        System.out.println("9. sort the list by name ( ls_name_desc )");
        System.out.println("10. check the completed item by id ( comp <keyword> )");
        System.out.println("11. check the completed multiple items by id ( multi comp <keyword> )");
        System.out.println("12. List completed items ( ls_comp )");
        System.out.println("13. mark the urgent item by id ( urgent <keyword> )");
        System.out.println("14. List urgent items ( ls_urgent )");
        System.out.println("15. mark the important list by id ( important <keyword> )");
        System.out.println("16. List important list ( ls_important )");
        System.out.println("17. sort the list by date ( ls_date )");
        System.out.println("18. sort the list by date ( ls_date_desc )");
        System.out.println("19. find item by category ( find_cate <keyword> )");
        System.out.println("20. exit (Or press escape key to exit)");
    }
    public static void prompt() {
    	System.out.print("\ncommand > ");
    }
}
