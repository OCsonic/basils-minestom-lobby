package com.basil.lobby;

import net.minestom.server.MinecraftServer;
import commands.CmdCore;

// Config
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Players
import net.minestom.server.entity.Player;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.permission.Permission;

// Map
import net.minestom.server.instance.*;
import net.minestom.server.instance.block.Block;
import net.minestom.server.instance.anvil.AnvilLoader;

// Velocity
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.common.PluginMessagePacket;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

// Portals
import net.minestom.server.event.player.PlayerMoveEvent;

public class Server {
	public static void main(String[] args) 
		 throws IOException
	{
		MinecraftServer minecraftServer = MinecraftServer.init();

		InstanceManager instanceManager = MinecraftServer.getInstanceManager();
		InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

		// Map config.json to the Config class in Config.java
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileReader reader = new FileReader("config.json");
		Config configroot = gson.fromJson(reader, Config.class);
		ServerConf config = configroot.getServerConf();

		// Load the map file specified in config.json and prepare variables for spawn and portal coords
		instanceContainer.setChunkLoader(new AnvilLoader("worlds/lobby"));
		//instanceContainer.setChunkLoader(new AnvilLoader(config.getWorld()));
		Spawn spawn = config.getSpawn();

		// Attempt to load forwarding.secret
		File vsecretfile = new File("forwarding.secret");
		if(vsecretfile.exists() && !vsecretfile.isDirectory()) { 
			String vsecret = new String(Files.readAllBytes(Path.of("forwarding.secret")));
			if(vsecret != null && !vsecret.trim().isEmpty()) {
				// If forwarding.secret exists then we enable velocity forwarding using that as the secret
				VelocityProxy.enable(vsecret);
			}
		}

		CmdCore.registerCommands();

		GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
		globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
			final Player player = event.getPlayer();
			event.setSpawningInstance(instanceContainer);
			System.out.println(player.getUsername() + " joined the game");
			player.setRespawnPoint(new Pos(spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getRoll()));
			player.setGameMode(GameMode.ADVENTURE);
			
			for (int i = 0; i < config.getOperators().size(); i++) {
				if (player.getUsername().equals(config.getOperators().get(i))) {
					System.out.println(player.getUsername() + " has been made an operator");
					player.addPermission(new Permission("operator"));
				}
			}
				
		});

		globalEventHandler.addListener(PlayerMoveEvent.class, event -> {
			final Player player = event.getPlayer();
			Pos position = player.getPosition();
			NetworkBuffer networkBuffer = new NetworkBuffer();

			// Loop through entries under the config.json "portals" key
			for (Map.Entry<String, Portal> entry : config.getPortals().entrySet()) {
				// For each entry we check if the player is standing in its portal
				String currentPortal = entry.getKey();
				Portal portal = entry.getValue();

				final double targetX = portal.getX();
				final double targetY = portal.getY();
				final double targetZ = portal.getZ();

				if (position.x() >= targetX && position.x() <= targetX + 1.0 &&
				    position.y() >= targetY && position.y() <= targetY + 0.9 &&
				    position.z() >= targetZ && position.z() <= targetZ + 1.0) {
					// If player is standing in portal we send them to the server with a name matching the portal entry
					System.out.println(player.getUsername() + " transferred to " + currentPortal);
					ByteArrayDataOutput out = ByteStreams.newDataOutput();
					out.writeUTF("Connect");
					out.writeUTF(currentPortal);
					player.sendPacket(new PluginMessagePacket("BungeeCord", out.toByteArray()));
				}
			}
			if (position.y() <= spawn.getY() - 10) {
				System.out.println(player.getUsername() + " is out of bounds!");
				event.setNewPosition(new Pos(spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getRoll()));
			}
		});
		System.out.println("Server starting...");
		minecraftServer.start("0.0.0.0", config.getPort());
	}
}
