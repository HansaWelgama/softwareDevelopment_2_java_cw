/**
 * Task 2,3
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
    private static int stock = 50;// Current stock of burgers
    private static final int BURGER_PRICE = 650;// Price of a burger
    private static final int MAX_QUEUE_SIZE_1 = 2;// Maximum size of cashier 1
    private static final int MAX_QUEUE_SIZE_2 = 3;// Maximum size of cashier 2
    private static final int MAX_QUEUE_SIZE_3 = 5;// Maximum size of cashier 3
    private static int totalBurgersServedQueue1 = 0;// Total burgers served in cashier 1
    private static int totalBurgersServedQueue2 = 0;// Total burgers served in cashier 2
    private static int totalBurgersServedQueue3 = 0;// Total burgers served in cashier 3
    private static final FoodQueue queue1 = new FoodQueue(MAX_QUEUE_SIZE_1);
    private static final FoodQueue queue2 = new FoodQueue(MAX_QUEUE_SIZE_2);
    private static final FoodQueue queue3 = new FoodQueue(MAX_QUEUE_SIZE_3);
    private static final List<String> waitingList = new ArrayList<>();// List of customers on the waiting list

    static class Customer {
        private final String firstName;// Customer's first name
        private final String lastName;// Customer's last name
        private final int burgersRequired;// Number of burgers required by the customer

        public Customer(String firstName, String lastName, int burgersRequired) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.burgersRequired = burgersRequired;
        }
        public String getFullName() {
            return firstName + " " + lastName;
        }
    }
    static class FoodQueue {
        private final List<Customer> customers;// List of customers in the queue
        private final int maxQueueSize;// Maximum size of the queue
        public FoodQueue(int maxQueueSize) {
            this.customers = new ArrayList<>();
            this.maxQueueSize = maxQueueSize;
        }
        public boolean canAddCustomer() {
            return customers.size() < maxQueueSize;
        }
        public void addCustomer(Customer customer) {
            customers.add(customer);
        }
        public boolean removeCustomer(int position) {
            if (position >= 1 && position <= customers.size()) {
                customers.remove(position - 1);
                return true;
            }
            return false;
        }
        public boolean isEmpty() {
            return customers.isEmpty();
        }
        public int getCustomerCount() {
            return customers.size();
        }
        public List<String> getCustomers() {
            List<String> customerNames = new ArrayList<>();
            for (Customer customer : customers) {
                customerNames.add(customer.getFullName());
            }
            return customerNames;
        }
    }
    private static void mainMenu() {
        System.out.println("\n");
        System.out.println("Foodies Fave Food Center!");
        System.out.println();
        System.out.println("""
                Menu Options:
                100 or VAQ: View all Queues.
                101 or VEQ: View all Empty Queues.
                102 or ACQ: Add customer to a Queue.
                103 or RCQ: Remove a customer from a Queue. (From a specific location)
                104 or PCQ: Remove a served customer.
                105 or VCS: View Customers Sorted in alphabetical order.
                106 or SPD: Store Program Data into file.
                107 or LPD: Load Program Data from file.
                108 or STK: View Remaining burgers Stock.
                109 or AFS: Add burgers to Stock.
                110 or IFQ: Print the income of each queue.
                999 or EXT: Exit the Program.
                """);
    }

    public static void main(String[] args) {// Displays the main menu with available options
        Scanner scanner = new Scanner(System.in);
        String option;
        do {
            mainMenu();
            System.out.print("Please enter an option from the given menu choices: ");
            option = scanner.nextLine();
            switch (option.toUpperCase(Locale.ROOT)) {
                case "100", "VAQ":
                    viewAllQueues();
                    break;
                case "101", "VEQ":
                    viewEmptyQueues();
                    break;
                case "102", "ACQ":
                    addCustomerToQueue(scanner);
                    break;
                case "103", "RCQ":
                    removeCustomerFromQueue(scanner);
                    break;
                case "104", "PCQ":
                    removeServedCustomer();
                    break;
                case "105", "VCS":
                    viewCustomersSortedAlphabetically();
                    break;
                case "106", "SPD":
                    storeProgramDataIntoFile();
                    break;
                case "107", "LPD":
                    loadProgramDataFromFile();
                    break;
                case "108", "STK":
                    viewRemainingBurgersStock();
                    break;
                case "109", "AFS":
                    addBurgersToStock(scanner);
                    break;
                case "110", "IFQ":
                    printQueueIncome();
                    break;
                case "999", "EXT":
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!option.equals("999"));
    }
    private static void viewAllQueues() {// Displays all queues and their current status
        System.out.println("****************");
        System.out.println("*   Cashiers   *");
        System.out.println("****************");

        printCashierQueue(queue1.getCustomers(), MAX_QUEUE_SIZE_1, 1);
        printCashierQueue(queue2.getCustomers(), MAX_QUEUE_SIZE_2, 2);
        printCashierQueue(queue3.getCustomers(), MAX_QUEUE_SIZE_3, 3);
    }
    private static void printCashierQueue(List<String> queue, int maxQueueSize, int cashierNumber) {// Prints the status of a cashier queue
        StringBuilder queueLine = new StringBuilder("Cashier " + cashierNumber + ": ");
        int selectedSlots = Math.min(queue.size(), maxQueueSize);
        int emptySlots = maxQueueSize - selectedSlots;

        for (int i = 0; i < selectedSlots; i++) {
            queueLine.append("O ");
        }

        for (int i = 0; i < emptySlots; i++) {
            queueLine.append("X ");
        }

        queueLine.deleteCharAt(queueLine.length() - 1);

        System.out.println(queueLine.toString());
    }
    private static void viewEmptyQueues() {// Displays the status of empty queues
        System.out.println("****************");
        System.out.println("*   Cashiers   *");
        System.out.println("****************");

        if (MAX_QUEUE_SIZE_1 > queue1.getCustomerCount()) {
            System.out.println("Customers can be added to the 'Cashier number 1'");
        }else
            System.out.println("The 'Cashier number 1' is full!");
        if (MAX_QUEUE_SIZE_2 > queue2.getCustomerCount()) {
            System.out.println("Customers can be added to the 'Cashier number 2'");
        }else
            System.out.println("The 'Cashier number 2' is full!");
        if (MAX_QUEUE_SIZE_3 > queue3.getCustomerCount()) {
            System.out.println("Customers can be added to the 'Cashier number 3'");
        }else
            System.out.println("The 'Cashier number 3' is full!");
    }
    private static void addCustomerToQueue(Scanner scanner) {// Adds a customer to a queue
        System.out.println();
        System.out.print("Enter the customer's first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter the customer's last name: ");
        String lastName = scanner.nextLine();

        int burgersRequired = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Enter the number of burgers required: ");
                burgersRequired = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect data. Please enter an integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        if (stock >= burgersRequired) {
            if (queue1.canAddCustomer()) {
                queue1.addCustomer(new Customer(firstName, lastName, burgersRequired));
                stock -= burgersRequired;
                totalBurgersServedQueue1 += burgersRequired;
                System.out.println(firstName + " " + lastName + " added to Cashier 1.");
            } else if (queue2.canAddCustomer()) {
                queue2.addCustomer(new Customer(firstName, lastName, burgersRequired));
                stock -= burgersRequired;
                totalBurgersServedQueue2 += burgersRequired;
                System.out.println(firstName + " " + lastName + " added to Cashier 2.");
            } else if (queue3.canAddCustomer()) {
                queue3.addCustomer(new Customer(firstName, lastName, burgersRequired));
                stock -= burgersRequired;
                totalBurgersServedQueue3 += burgersRequired;
                System.out.println(firstName + " " + lastName + " added to Cashier 3.");
            } else {
                System.out.println("All queues are full!");
                waitingList.add(firstName + " " + lastName);
                System.out.println(firstName + " " + lastName + " added to the waiting list.");
            }
        } else {
            System.out.println("Insufficient stock. " + firstName + " " + lastName + " cannot be added to the queue.");
        }

        if (stock <= 10) {
            System.out.println("Warning! Stock is low!");
        }
        updateQueueFromWaitingList();
    }
    private static void updateQueueFromWaitingList() {// Updates the queue from the waiting list
        if (!waitingList.isEmpty()) {
            String customer = waitingList.get(0);
            Customer createdCustomer = createCustomerFromFullName(customer);
            if (createdCustomer != null) {
                if (queue1.canAddCustomer()) {
                    queue1.addCustomer(createdCustomer);
                    waitingList.remove(0);
                    System.out.println(customer + " added to Cashier 1 from the waiting list.");
                } else if (queue2.canAddCustomer()) {
                    queue2.addCustomer(createdCustomer);
                    waitingList.remove(0);
                    System.out.println(customer + " added to Cashier 2 from the waiting list.");
                } else if (queue3.canAddCustomer()) {
                    queue3.addCustomer(createdCustomer);
                    waitingList.remove(0);
                    System.out.println(customer + " added to Cashier 3 from the waiting list.");
                }
            }
        }
    }
    private static Customer createCustomerFromFullName(String fullName) {// Creates a customer object from a full name string
        String[] names = fullName.split(" ");
        if (names.length == 2) {
            String firstName = names[0];
            String lastName = names[1];
            return new Customer(firstName, lastName, 0);
        }
        return null;
    }
    private static void removeCustomerFromQueue(Scanner scanner) {// Removes a customer from a queue
        int queueNumber = 0;
        int position = 0;
        boolean validQueueNumber = false;
        boolean validPosition = false;

        while (!validQueueNumber) {
            try {
                System.out.print("Enter the Cashier number: ");
                queueNumber = scanner.nextInt();
                scanner.nextLine();
                validQueueNumber = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect data. For the Cashier number, please provide a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        FoodQueue queue;
        switch (queueNumber) {
            case 1:
                queue = queue1;
                break;
            case 2:
                queue = queue2;
                break;
            case 3:
                queue = queue3;
                break;
            default:
                System.out.println("The Cashier number is incorrect.");
                return;
        }
        while (!validPosition) {
            try {
                System.out.print("Enter the position of the customer to be removed: ");
                position = scanner.nextInt();
                scanner.nextLine();
                validPosition = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect data. For the position, please provide a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        if (position >= 1 && position <= queue.getCustomerCount()) {
            String removedCustomer = queue.getCustomers().get(position - 1);
            if (queue.removeCustomer(position)) {
                System.out.println("Customer " + removedCustomer + " removed from Cashier " + queueNumber + ".");
            } else {
                System.out.println("Invalid position.");
            }
        } else {
            System.out.println("Invalid position.");
        }
    }

    private static void removeServedCustomer() {// Removes the served customer from the queue
        System.out.println("Removing the served customer...");

        String removedCustomer = null;

        if (!queue1.isEmpty()) {
            removedCustomer = queue1.getCustomers().get(0);
            queue1.removeCustomer(1);
            System.out.println(removedCustomer+" removed from Cashier 1.");
            updateQueueFromWaitingList();
        } else if (!queue2.isEmpty()) {
            removedCustomer = queue2.getCustomers().get(0);
            queue2.removeCustomer(1);
            System.out.println(removedCustomer+" removed from Cashier 2.");
            updateQueueFromWaitingList();
        } else if (!queue3.isEmpty()) {
            removedCustomer = queue3.getCustomers().get(0);
            queue3.removeCustomer(1);
            System.out.println(removedCustomer+" removed from Cashier 3.");
            updateQueueFromWaitingList();
        } else {
            System.out.println("No customers in any Cashier.");
        }

    }

    private static void viewCustomersSortedAlphabetically() {// Displays the customers sorted in alphabetical order
        System.out.println("****************");
        System.out.println("*   Cashiers   *");
        System.out.println("****************");

        List<String> allCustomers = new ArrayList<>();
        allCustomers.addAll(queue1.getCustomers());
        allCustomers.addAll(queue2.getCustomers());
        allCustomers.addAll(queue3.getCustomers());

        Collections.sort(allCustomers);
        System.out.println("Customers Sorted in Alphabetical Order: " + allCustomers);
    }
    private static void storeProgramDataIntoFile() {// Stores program data into a file
        int queue1Income = totalBurgersServedQueue1 * BURGER_PRICE;
        int queue2Income = totalBurgersServedQueue2 * BURGER_PRICE;
        int queue3Income = totalBurgersServedQueue3 * BURGER_PRICE;
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("BurgerProgramData.txt"));
            bw.write("Cashier 1\n");

            for (String name : queue1.getCustomers()) {
                bw.write(name + "\n");
            }
            bw.write("Cashier 2\n");
            for (String name : queue2.getCustomers()) {
                bw.write(name + "\n");
            }
            bw.write("Cashier 3\n");
            for (String name : queue3.getCustomers()) {
                bw.write(name + "\n");
            }
            bw.write("Waiting List\n");
            for (String name : waitingList) {
                bw.write(name + "\n");
            }
            bw.write("Available Stock: " + stock + "\n");
            bw.write("Number of Burgers Served Cashier 1: " + totalBurgersServedQueue1 + "\n");
            bw.write("Number of Burgers Served Cashier 2: " + totalBurgersServedQueue2 + "\n");
            bw.write("Number of Burgers Served Cashier 3: " + totalBurgersServedQueue3 + "\n");
            bw.write("Queue Income\n");
            bw.write("Queue 1 Income: LKR" + queue1Income + "\n");
            bw.write("Queue 2 Income: LKR" + queue2Income + "\n");
            bw.write("Queue 3 Income: LKR" + queue3Income + "\n");
            bw.close();
            System.out.println("Queue income and program data stored in file successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while storing queue income and program data.");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader("BurgerProgramData.txt"));
            String line;
            System.out.println("Stored program data from file:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error occurred while reading stored program data from file.");
        }
    }
    private static void loadProgramDataFromFile() {  // Loads program data from a file
        try (BufferedReader reader = new BufferedReader(new FileReader("BurgerProgramData.txt"))) {
            String line;
            FoodQueue currentQueue = null;
            waitingList.clear();
            stock = 0;
            totalBurgersServedQueue1 = 0;
            totalBurgersServedQueue2 = 0;
            totalBurgersServedQueue3 = 0;

            while ((line = reader.readLine()) != null) {
                if (line.equals("Cashier 1")) {
                    currentQueue = queue1;
                } else if (line.equals("Cashier 2")) {
                    currentQueue = queue2;
                } else if (line.equals("Cashier 3")) {
                    currentQueue = queue3;
                } else if (line.equals("Waiting List")) {
                    currentQueue = null;
                } else if (line.startsWith("Available Stock:")) {
                    stock = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                } else if (line.startsWith("Number of Burgers Served Cashier 1:")) {
                    totalBurgersServedQueue1 = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                } else if (line.startsWith("Number of Burgers Served Cashier 2:")) {
                    totalBurgersServedQueue2 = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                } else if (line.startsWith("Number of Burgers Served Cashier 3:")) {
                    totalBurgersServedQueue3 = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                } else {
                    if (currentQueue != null) {
                        currentQueue.addCustomer(createCustomerFromFullName(line));
                    } else {
                        waitingList.add(line);
                    }
                }
            }
            System.out.println("Program data loaded from file successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while loading program data from file.");
        }
    }
    private static void viewRemainingBurgersStock() {    // Displays the remaining burgers stock
        System.out.println("Remaining Burgers Stock: " + stock);
    }

    private static void addBurgersToStock(Scanner scanner) { // Adds burgers to the stock
        boolean validInput = false;
        int burgersToAdd = 0;

        while (!validInput) {
            try {
                System.out.print("Enter the number of burgers to be added to stock: ");
                burgersToAdd = scanner.nextInt();
                scanner.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the number of burgers.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        stock += burgersToAdd;
        System.out.println(burgersToAdd + " burgers added to stock. Remaining Stock: " + stock);
    }
    private static void printQueueIncome() {  // Prints the income of each queue
        int queue1Income = totalBurgersServedQueue1 * BURGER_PRICE;
        int queue2Income = totalBurgersServedQueue2 * BURGER_PRICE;
        int queue3Income = totalBurgersServedQueue3 * BURGER_PRICE;

        System.out.println("Queue Income:");
        System.out.println("Queue 1 Income: LKR" + queue1Income);
        System.out.println("Queue 2 Income: LKR" + queue2Income);
        System.out.println("Queue 3 Income: LKR" + queue3Income);
    }
}