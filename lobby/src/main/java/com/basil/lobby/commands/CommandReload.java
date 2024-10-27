package commands;

// Stuff required for all commands
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.ServerSender;
import net.minestom.server.command.ConsoleSender;

public class CommandReload extends Command {
	public CommandReload() {
		// Command info
		super("reload");
		
		// Require to be run as Console or with OP status
		setCondition((sender, commandString) -> (sender instanceof ServerSender)
			|| sender.hasPermission(CmdCore.OPERATOR) || (sender instanceof ConsoleSender));

		// Functionality
		setDefaultExecutor((sender, context) -> {
			// Provide feedback to the user(s)
			String cmdResponse = "In-place reloading the server...";
			System.out.println(cmdResponse);
			sender.sendMessage(cmdResponse);

			// Trigger a reload of the config file

			// Reload the map file
		});
	}
}

