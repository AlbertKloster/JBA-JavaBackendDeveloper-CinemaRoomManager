package cinema;

import cinema.exception.AlreadyPurchasedException;
import cinema.exception.WrongInputException;

import java.util.Arrays;

public class Cinema {

    private final boolean[][] purchasedTickets;
    private final int[][] prices;
    private final Statistics statistics;

    public Cinema(int numberOfRows, int numberOfSeatsInRow) {
        this.purchasedTickets = setPurchasedTicket(numberOfRows, numberOfSeatsInRow);
        this.prices = setPrices(numberOfRows, numberOfSeatsInRow);
        this.statistics = new Statistics();
    }

    private boolean[][] setPurchasedTicket(int numberOfRows, int numberOfSeatsInRow) {
        return new boolean[numberOfRows][numberOfSeatsInRow];
    }

    private int[][] setPrices(int numberOfRows, int numberOfSeatsInRow) {
        int[][] prices = new int[numberOfRows][numberOfSeatsInRow];
        for (int i = 0; i < numberOfRows; i++)
            prices[i] = getRowPrices(numberOfRows, numberOfSeatsInRow, i);
        return prices;
    }

    private int[] getRowPrices(int numberOfRows, int numberOfSeatsInRow, int rowNumber) {
        int[] rowPrices = new int[numberOfSeatsInRow];
        Arrays.fill(rowPrices, isReduced(numberOfRows, numberOfSeatsInRow, rowNumber) ? 8 : 10);
        return rowPrices;
    }

    private boolean isReduced(int numberOfRows, int numberOfSeatsInRow, int rowNumber) {
        int totalSeats = numberOfRows * numberOfSeatsInRow;
        if (totalSeats <= 60)
            return false;
        return rowNumber >= numberOfRows/2;
    }

    public Statistics getStatistics() {
        int numberOfPurchasedTickets = getNumberOfPurchasedTickets();
        statistics.setPurchasedTickets(numberOfPurchasedTickets);
        statistics.setPercentage(getPercentage(numberOfPurchasedTickets));
        statistics.setCurrentIncome(getCurrentIncome());
        statistics.setTotalIncome(getTotalIncome());
        return statistics;
    }

    private int getNumberOfPurchasedTickets() {
        int count = 0;
        for (boolean[] row : purchasedTickets) {
            for (boolean isPurchased : row) {
                if (isPurchased)
                    count++;
            }
        }
        return count;
    }

    private int getCurrentIncome() {
        int currentIncome = 0;
        for (int i = 0; i < purchasedTickets.length; i++) {
            for (int j = 0; j < purchasedTickets[i].length; j++) {
                if (purchasedTickets[i][j]) {
                    currentIncome += prices[i][j];
                }
            }
        }
        return currentIncome;
    }

    private double getPercentage(int numberOfPurchasedTickets) {
        int totalTickets = purchasedTickets.length * purchasedTickets[0].length;
        return 100.0 * numberOfPurchasedTickets/totalTickets;
    }

    private int getTotalIncome() {
        return Arrays.stream(prices).flatMapToInt(Arrays::stream).sum();
    }

    public void showSeats() {
        StringBuilder stringBuilder = new StringBuilder(appendHeader());
        for (int i = 1; i <= purchasedTickets.length; i++)
            appendRow(stringBuilder, i);
        System.out.println(stringBuilder);

    }

    private void appendRow(StringBuilder stringBuilder, int rowNumber) {
        stringBuilder.append(rowNumber);
        appendSits(stringBuilder, rowNumber);
        stringBuilder.append("\n");
    }

    private void appendSits(StringBuilder stringBuilder, int rowNumber) {
        for (boolean sold : purchasedTickets[rowNumber - 1]) {
            stringBuilder.append(" ").append(sold ? "B" : "S");
        }
    }

    private StringBuilder appendHeader() {
        StringBuilder stringBuilder = new StringBuilder("\nCinema:").append("\n").append(" ");
        for (int i = 1; i <= purchasedTickets[0].length; i++)
            stringBuilder.append(" ").append(i);
        return stringBuilder.append("\n");
    }

    public int buyTicket(int rowNumber, int seatNumber) {
        if (isNotInRange(rowNumber, seatNumber))
            throw new WrongInputException();
        if (isAlreadyPurchased(rowNumber, seatNumber))
            throw new AlreadyPurchasedException();
        setSoldSeat(rowNumber, seatNumber);
        return getPrice(rowNumber, seatNumber);
    }

    private boolean isNotInRange(int rowNumber, int seatNumber) {
        return rowNumber < 1 | rowNumber > purchasedTickets.length |
                seatNumber < 1 | seatNumber > purchasedTickets[0].length;
    }

    private boolean isAlreadyPurchased(int rowNumber, int seatNumber) {
        return purchasedTickets[rowNumber - 1][seatNumber - 1];
    }

    private void setSoldSeat(int rowNumber, int seatNumber) {
        purchasedTickets[rowNumber - 1][seatNumber - 1] = true;
    }

    private int getPrice(int rowNumber, int seatNumber) {
        return prices[rowNumber - 1][seatNumber - 1];
    }

}