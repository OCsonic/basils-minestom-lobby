package console;

// Console and command stuff
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.ConsoleSender;

// Standard input
import java.util.Scanner;

public class Console {
    private final CommandManager commandManager;
    public Console(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
	System.out.println("Done! Run /list for commands!");
        while (true) {
            String command = scanner.nextLine();
            executeConsoleCommand(command);
        }
    }

    private void executeConsoleCommand(String command) {
        ConsoleSender consoleSender = new ConsoleSender();
        commandManager.execute(consoleSender, command);
    }
}
