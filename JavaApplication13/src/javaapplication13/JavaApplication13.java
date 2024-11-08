
package javaapplication13;

import java.util.LinkedList;
import java.util.Stack;
import javax.swing.JOptionPane;

public class JavaApplication13 {

    private LinkedList<String> todoList;
    private LinkedList<String> completedTasks;
    private Stack<String> undoStack;

    public JavaApplication13() {
        todoList = new LinkedList<>();
        completedTasks = new LinkedList<>();
        undoStack = new Stack<>();
    }


    public static void main(String[] args) {
        JavaApplication13 manager = new JavaApplication13();
        int choice;

        do {
            String menu = """
                Menu:
                1. Add Task
                2. Mark Task as Done
                3. Undo
                4. View To-Do List
                5. View Completed Tasks
                6. Exit
                """;

            String input = JOptionPane.showInputDialog(menu + "\nEnter your choice:");
            if (input == null) break;  // Cancel button was pressed, exit the loop
            
            try {
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:
                        String task = JOptionPane.showInputDialog("Enter task:");
                        if (task != null && !task.isEmpty()) {
                            manager.addTask(task);
                        } else {
                            JOptionPane.showMessageDialog(null, "Task cannot be empty.");
                        }
                        break;
                    case 2:
                        manager.viewToDoList();
                        String indexStr = JOptionPane.showInputDialog("Enter numbeer to mark as done:");
                        if (indexStr != null) {
                            try {
                                int index = Integer.parseInt(indexStr);
                                manager.markTaskAsDone(index);
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Please enter a valid number for index.");
                            }
                        }
                        break;
                    case 3:
                        manager.undo();
                        break;
                    case 4:
                        manager.viewToDoList();
                        break;
                    case 5:
                        manager.viewCompletedTasks();
                        break;
                    case 6:
                        JOptionPane.showMessageDialog(null, "Exiting...");
                        System.exit(0);
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for your choice.");
            }
        } while (true);
    }

    public void addTask(String task) {
        todoList.add(task);
        undoStack.push("ADD:" + task);
        JOptionPane.showMessageDialog(null, "Task added: " + task);
    }

    public void markTaskAsDone(int index) {
        if (index >= 1 && index < todoList.size()) {
            String task = todoList.remove(index);
            completedTasks.add(task);
            undoStack.push("DONE:" + index + ":" + task);
            JOptionPane.showMessageDialog(null, "Task marked as done: " + task);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid task index.");
        }
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            String action = undoStack.pop();
            String[] parts = action.split(":", 3);  // Split into maximum 3 parts
            String command = parts[0];

            if (command.equals("ADD") && parts.length > 1) {
                String task = parts[1];
                todoList.remove(task);
                JOptionPane.showMessageDialog(null, "Undid adding task: " + task);
            } else if (command.equals("DONE") && parts.length > 2) {
                int index = Integer.parseInt(parts[1]);
                String task = parts[2];
                completedTasks.remove(task);
                todoList.add(index, task);  // Restore task to its original index
                JOptionPane.showMessageDialog(null, "Undid marking task as done: " + task);
            } else {
                JOptionPane.showMessageDialog(null, "Error: Unable to undo action.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "No actions to undo.");
        }
    }

    public void viewToDoList() {
        StringBuilder list = new StringBuilder("To-Do List:\n");
        if (todoList.isEmpty()) {
            list.append("No tasks in the to-do list.");
        } else {
            for (int i = 0; i < todoList.size(); i++) {
                list.append(i+1).append(": ").append(todoList.get(i)).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, list.toString());
    }

    public void viewCompletedTasks() {
        StringBuilder list = new StringBuilder("Completed Tasks:\n");
        if (completedTasks.isEmpty()) {
            list.append("No completed tasks.");
        } else {
            for (String task : completedTasks) {
                list.append(task).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, list.toString());
    }
}