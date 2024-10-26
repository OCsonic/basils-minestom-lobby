package commands;

// Stuff required for all commands
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.CommandManager;
import net.minestom.server.command.ServerSender;

// Gamemode
import net.minestom.server.entity.Player;
import net.minestom.server.entity.GameMode;

public class CommandGamemode extends Command {
	public CommandGamemode() {
		// Command info
		super("gamemode");

		// Require OP status
		setCondition((sender, commandString) -> (sender instanceof ServerSender)
				|| sender.hasPermission(CmdCore.OPERATOR));

		// Functionality
		setDefaultExecutor((sender, context) -> {
			// Provide feedback to the user(s)
			String cmdResponse = "Your gamemode has been set to creative";
			sender.sendMessage(cmdResponse);

			// Set gamemode
			final Player player = (Player) sender;
			player.setGameMode(GameMode.CREATIVE);
		});
	}
}

