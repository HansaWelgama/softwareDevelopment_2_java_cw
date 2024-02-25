/**
 * Task 1 Arrays version.
 * Name: H.H.Welgama
 * IIT Number: 20221109
 * UOW Number: w1998723
 * I confirm that I understand what plagiarism / collusion /
 * contract cheating is and have read and understood
 * the section on Assessment Offences in the Essential Information for Students.
 * The work that I have submitted is entirely my own.
 * Any work from other authors is duly referenced and acknowledged.
 */
import java.util.*;
import java.io.*;
import java.util.Collections;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
public class FoodCenter {
        private static void MainMenu(){//Calling Mainmenu to the main method
            System.out.println("\n");
            System.out.println("*****Foodies Fave Food Center!*****");
            System.out.println();
            System.out.println("Menu Options:");
            System.out.println("100 or VAQ: View all Queues.");
            System.out.println("101 or VEQ: View all Empty Queues.");
            System.out.println("102 or ACQ: Add customer to a Queue.");
            System.out.println("103 or RCQ: Remove a customer from a Queue. (From a specific location)");
            System.out.println("104 or RCQ: Remove a served customer.");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
            System.out.println("106 or SPD: Store Program Data into file.");
            System.out.println("107 or LPD: Load Program Data from file.");
            System.out.println("108 or STK: View Remaining burgers Stock.");
            System.out.println("109 or AFS: Add burgers to Stock.");
            System.out.println("999 or EXT: Exit the Program.");
        }
        public static void main(String[] args){//Main method of this program
            Scanner obj = new Scanner(System.in);
            String option;
            do {MainMenu();
                System.out.print("Please enter a option from the given menu choices: ");
                option = obj.nextLine();
                switch (option) {//Switch case to call menu option methods
                    case"100","VAQ":
                        viewAllQueues();
                        break;
                    case"101","VEQ":
                        viewEmptyQueues();
                        break;
                    case"102","ACQ":
                        addCustomerToQueue(obj);
                        break;
                    case"103","RCQ":
                        RemoveCustomerfromaQueue(obj);
                        break;
                    case"104","PCQ":
                        RemoveServedcustomer();
                        break;
                    case"105","VCS":
                        ViewCustomersSortedinAlphabeticalOrder();
                        break;
                    case"106","SPD":
                        StoreProgramDataIntoFile();
                        break;
                    case"107","LPD":
                        LoadProgramDataFromFile();
                        break;
                    case "108","STK":
                        ViewRemainingBurgersStock();
                        break;
                    case"109","AFS":
                        AddBurgersToStock();
                        break;
                    case"999","EXT":
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }while (!option.equals("999"));// Loop until the user chooses to exit the program
        }
        private static int stock = 50;//initializing stock variable
        private static final int max_Q1 = 2;// Maximum capacity for  queue 1
        private static final int max_Q2 = 3;// Maximum capacity for  queue 2
        private static final int max_Q3 = 5;// Maximum capacity for  queue 3


        private static final List<String> queue01 = new ArrayList<>();// Creating new array for queue 1
        private static final List<String> queue02 = new ArrayList<>();// Creating new array for queue 2
        private static final List<String> queue03 = new ArrayList<>();// Creating new array for queue 3


        private static void viewAllQueues() {// Method to view all queues and their statuses
            System.out.println("*****************");
            System.out.println("*   Cashiers   *");
            System.out.println("*****************");

            printCashierQueue(queue01, max_Q1, 1);
            printCashierQueue(queue02, max_Q2, 2);
            printCashierQueue(queue03, max_Q3, 3);
        }


        private static void printCashierQueue(List<String> queue, int maxQueueSize, int cashierNumber) {
            StringBuilder queueLine = new StringBuilder("Cashier " + cashierNumber + ": ");
            int SelectedSlots = Math.min(queue.size(), maxQueueSize);
            int emptySlots = maxQueueSize - SelectedSlots;

            for (int i = 0; i < SelectedSlots; i++) {
                queueLine.append("O ");
            }

            for (int i = 0; i < emptySlots; i++) {
                queueLine.append("X ");
            }

            queueLine.deleteCharAt(queueLine.length() - 1);

            System.out.println(queueLine.toString());
        }


        private static void viewEmptyQueues() { //method for view empty queues
            System.out.println();
            System.out.println("****viewEmptyQueues****");

            // Checking if each queue is empty
            boolean isEmptyQueue01 = queue01.isEmpty();
            boolean isEmptyQueue02 = queue02.isEmpty();
            boolean isEmptyQueue03 = queue03.isEmpty();

            if (isEmptyQueue01 && isEmptyQueue02 && isEmptyQueue03) {//checking whether queues are available or no
                System.out.println("All queues are empty.");
            } else {
                if (isEmptyQueue01) {
                    System.out.println("1st Queue is empty.");
                }
                if (isEmptyQueue02) {
                    System.out.println("2nd Queue is empty.");
                }
                if (isEmptyQueue03) {
                    System.out.println("3rd Queue is empty.");
                }
            }
        }


        private static void addCustomerToQueue(Scanner obj) {// Method for adding a customer to a queue
            System.out.println();
            System.out.print("Enter the customer's name: ");
            String name = obj.nextLine();

            System.out.print("Enter the queue number (1, 2, or 3): ");
            String queueNum = obj.nextLine();

            List<String> selectedQueue;
            int maxQueueSize;

            switch (queueNum) {//Selecting the appropriate queue
                case "1":
                    selectedQueue = queue01;
                    maxQueueSize = max_Q1;
                    break;
                case "2":
                    selectedQueue = queue02;
                    maxQueueSize = max_Q2;
                    break;
                case "3":
                    selectedQueue = queue03;
                    maxQueueSize = max_Q3;
                    break;
                default:
                    System.out.println("Invalid queue number! Customer not added to any queue.");
                    return;
            }

            if (selectedQueue.size() < maxQueueSize) {   // Adding the customer to the selected queue
                selectedQueue.add(name);
                stock -= 5;
                System.out.println(name + " added to Queue " + queueNum + ".");
            } else {
                System.out.println("Queue " + queueNum + " is full. " + name + " cannot be added to the queue.");
            }

            if (stock <= 10) {// Displaying a warning message if the stock is low
                System.out.println("Warning! Stock is low!");
            }
        }

        private static List<String> getQueue(String Qnum){//This method is to switch a queue for the two methods below
            switch (Qnum){
                case "1":
                    return queue01;
                case "2":
                    return queue02;
                case "3":
                    return queue03;
                default:
                    throw new IllegalArgumentException("Invalid queue number! ");
            }
        }
        private static void RemoveCustomerfromaQueue(Scanner obj) {//Method for remove a customer from a specific location
            System.out.println();
            System.out.println("****Serve and remove from queue****");
            System.out.print("Enter the queue number (1, 2, or 3): ");
            String Qnum = obj.nextLine();
            if(Qnum.equals("1")||Qnum.equals("2")||Qnum.equals("3")) {
                List<String> queue = getQueue(Qnum);
                if (!queue.isEmpty()) {//checking whether queue number is available or no
                    String customerName = queue.remove(0);
                    System.out.println("Customer " + customerName + " served and removed from Queue " + Qnum);
                } else {
                    System.out.println("Queue " + Qnum + " is empty. No customer removed.");
                }
            }else {
                System.out.println("Invaild queue number! Please try again!(1, 2 or 3)");
            }
        }
        private static void RemoveServedcustomer() {//Method for remove served customer to display
            {
                System.out.println("Customer "+queue01.remove(0) +" served and removed from Queue ");
            }
        }
        private static void ViewCustomersSortedinAlphabeticalOrder(){//Method for view customers sorted in alphabetical order to display
            System.out.println();
            List<String> allCustomers = new ArrayList();
            allCustomers.addAll(queue01);
            allCustomers.addAll(queue02);
            allCustomers.addAll(queue03);
            Collections.sort(allCustomers);
            System.out.println("Here is a list of customers in alphabetical order:  ");
            System.out.println();

            for (int i = 0; i < allCustomers.size(); i++){
                String customer = allCustomers.get(i);
                System.out.println(customer);
            }
        }
        private static void StoreProgramDataIntoFile(){//Method for store program data into a file

            try {// Bufferedfile writing method
                BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Himan\\Desktop\\FoodiesFaveQueueManagementSystem\\FoodiesFaveFoodCenter\\Burger Program Data.txt"));
                //writing to the file named as Part1 using file writer method
                bw.write("Cashiers        Names\n");
                for (String name : queue01) {
                    bw.write("cashier_01:     " + name + "\n");//The name of the customers in the queue 1 are printed part 1.txt file.
                }
                for (String name : queue02) {
                    bw.write("cashier_02:     " + name + "\n");//The name of the customers in the queue 2 are printed part 1.txt file.
                }
                for (String name : queue03) {
                    bw.write("cashier_03:     " + name + "\n");//The name of the customers in the queue 3 are printed part 1.txt file.
                }
                bw.write("Available stock: " + stock + "\n");
                bw.close();//closing Bufferd file writer
                System.out.println();
            } catch (IOException e) {
                System.out.println("Invaild");
            }
            try {// Reading data from saved file using Buffered file Reader method
                BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Himan\\Desktop\\FoodiesFaveQueueManagementSystem\\FoodiesFaveFoodCenter\\Burger Program Data.txt"));
                //reading from the Part1.txt file
                String line;
                System.out.println("Store program data in to  file successfully ");
                while ((line = br.readLine()) != null) {
                    System.out.println();
                    System.out.println( line);
                }
                br.close();//closing    Bufferd file reader
            } catch (IOException e) {
                System.out.println("invaild");
            }
        }

    private static void LoadProgramDataFromFile() {// Creating a BufferedReader to read the program data file
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Himan\\Desktop\\FoodiesFaveQueueManagementSystem\\FoodiesFaveFoodCenter\\Burger Program Data.txt"));
            String line;

            // Clearing existing data from queues and stock
            queue01.clear();
            queue02.clear();
            queue03.clear();
            stock = 0;

            // Reading and restoring the program data
            while ((line = br.readLine()) != null) {
                if (line.startsWith("cashier_01:")) {
                    String customerName = line.substring(line.indexOf(":") + 1).trim();
                    queue01.add(customerName);
                } else if (line.startsWith("cashier_02:")) {
                    String customerName = line.substring(line.indexOf(":") + 1).trim();
                    queue02.add(customerName);
                } else if (line.startsWith("cashier_03:")) {
                    String customerName = line.substring(line.indexOf(":") + 1).trim();
                    queue03.add(customerName);
                } else if (line.startsWith("Available stock:")) {
                    stock = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                }
            }

            br.close();  // Closing the BufferedReader
            System.out.println("Program data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while loading program data.");
        }
    }


    private static void ViewRemainingBurgersStock(){  // Method to view the remaining burger stock

            System.out.println();
            System.out.println("***Remaining Burger Stock***");
            System.out.println("Available Burgers Stock: " + stock);
        }
        private static void AddBurgersToStock(){//method for add burgers to stock
            System.out.println();
            System.out.println("****Burgers add to the stock****");
            Scanner obj1 = new Scanner(System.in);
            System.out.print("Enter the number of burgers to add the stock : ");
            int burgersToAdd = obj1.nextInt();
            int NewStock = stock+burgersToAdd;
            System.out.println(burgersToAdd + " Burgers added! New stock: "+NewStock);

        }

    }





