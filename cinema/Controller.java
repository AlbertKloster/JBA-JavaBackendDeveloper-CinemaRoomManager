package cinema;

import java.util.Scanner;

public class Controller {
    private static final Scanner SC = new Scanner(System.in);

    public static void run() {
        Cinema cinema = init();
        boolean exit = false;
        while (!exit) {
            printMenu();
            switch (Command.getCommand(SC.nextInt())) {
                case SHOW: {
                    showSeats(cinema);
                    break;
                }
                case BUY: {
                    buySeat(cinema);
                    break;
                }
                case STATISTICS: {
                    printStatistics(cinema);
                    break;
                }
                case EXIT: {
                    exit = true;
                }
            }
        }

    }

    private static void printStatistics(Cinema cinema) {
        Statistics statistics = cinema.getStatistics();
        System.out.printf("Number of purchased tickets: %d\n" +
                "Percentage: %.2f%%\n" +
                "Current income: $%d\n" +
                "Total income: $%d\n",
                statistics.getPurchasedTickets(),
                statistics.getPercentage(),
                statistics.getCurrentIncome(),
                statistics.getTotalIncome());
    }

    private static Cinema init() {
        return new Cinema(getNumberOfRows(), getNumberOfSeats());
    }

    private static int getNumberOfRows() {
        System.out.println("\nEnter the number of rows:");
        return SC.nextInt();
    }

    private static int getNumberOfSeats() {
        System.out.println("Enter the number of seats in each row:");
        return SC.nextInt();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println(Command.SHOW.text);
        System.out.println(Command.BUY.text);
        System.out.println(Command.STATISTICS.text);
        System.out.println(Command.EXIT.text);
    }

    private static void showSeats(Cinema cinema) {
        cinema.showSeats();
    }

    private static void buySeat(Cinema cinema) {
        try {
            int price = cinema.buyTicket(getRowNumber(), getSeatNumber());
            System.out.printf("Ticket price: $%d\n", price);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            buySeat(cinema);
        }
    }

    private static int getRowNumber() {
        System.out.println("\nEnter a row number:");
        return SC.nextInt();
    }

    private static int getSeatNumber() {
        System.out.println("Enter a seat number in that row:");
        return SC.nextInt();
    }

}
