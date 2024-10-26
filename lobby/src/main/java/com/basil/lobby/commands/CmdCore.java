package commands;

import net.minestom.server.MinecraftServer;

// Command Registration
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.CommandManager;
import net.minestom.server.permission.Permission;

// Reflection for auto-detection of commands
import java.util.Set;
import org.reflections.Reflections;

public class CmdCore {
	public static final Permission OPERATOR = new Permission("operator");
	public static void registerCommands() {
		CommandManager commandManager = MinecraftServer.getCommandManager();
		commandManager.getConsoleSender().addPermission(CmdCore.OPERATOR);
        
		Reflections reflections = new Reflections("commands");
		Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
		for (Class<? extends Command> commandClass : commandClasses) {
			try {
				Command commandInstance = commandClass.getDeclaredConstructor().newInstance();
				commandManager.register(commandInstance);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
