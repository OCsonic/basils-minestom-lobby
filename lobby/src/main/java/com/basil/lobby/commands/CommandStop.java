package commands;

// Stuff required for all commands
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.ServerSender;
import net.minestom.server.command.ConsoleSender;

public class CommandStop extends Command {
	public CommandStop() {
		// Command info
		super("stop");

		// Require to be run as Console or with OP status
		setCondition((sender, commandString) -> (sender instanceof ServerSender)
			|| sender.hasPermission(CmdCore.OPERATOR) || (sender instanceof ConsoleSender));

		// Functionality
		setDefaultExecutor((sender, context) -> {
			// Provide feedback to the user(s)
			String cmdResponse = "Server is shutting down...";
			System.out.println(cmdResponse);
			MinecraftServer.getConnectionManager().getOnlinePlayers().forEach(player -> {
				player.kick(cmdResponse);
			});

			// Stop the server
			MinecraftServer.stopCleanly();
			System.exit(0);
		});
	}
}

