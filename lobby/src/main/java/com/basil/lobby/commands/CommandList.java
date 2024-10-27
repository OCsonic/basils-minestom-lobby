package commands;

// Stuff required for all commands
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.ServerSender;
import net.minestom.server.command.ConsoleSender;

// Reflections for generating command list
import java.util.Set;
import org.reflections.Reflections;

public class CommandList extends Command {
	public CommandList() {
		// Command info
		super("list");

		// Functionality
		setDefaultExecutor((sender, context) -> {
			// Provide feedback to the user(s)
			String cmdResponse = "Currently registered commands:";
	
			// Functionality
			Reflections reflections = new Reflections("commands");
			StringBuilder commandList = new StringBuilder( cmdResponse + "\n" );
			// Get list of command classes and loop through them to append each command name to a list.
			Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
			for (Class<? extends Command> commandClass : commandClasses) {
				try {
					Command commandInstance = commandClass.getDeclaredConstructor().newInstance();
					commandList.append(commandInstance.getName()).append(" ");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(commandList);
			sender.sendMessage(commandList.toString());
		});
	}
}

