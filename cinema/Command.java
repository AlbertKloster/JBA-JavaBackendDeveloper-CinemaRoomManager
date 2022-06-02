package cinema;

public enum Command {

    EXIT("0. Exit"),
    SHOW("1. Show the seats"),
    BUY("2. Buy a ticket"),
    STATISTICS("3. Statistics");

    String text;

    Command(String text) {
        this.text = text;
    }

    public static Command getCommand(int i) {
        return Command.values()[i];
    }
}
