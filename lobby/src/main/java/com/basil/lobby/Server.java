package com.basil.lobby;

import net.minestom.server.MinecraftServer;

// Config
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// Players
import net.minestom.server.entity.Player;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.coordinate.Pos;

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
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.instance.block.BlockHandler;
import net.minestom.server.utils.NamespaceID;
import net.minestom.server.message.Messenger;

public class Server {
    public static void main(String[] args) 
	throws IOException
    {
        MinecraftServer minecraftServer = MinecraftServer.init();

        InstanceManager instanceManager = MinecraftServer.getInstanceManager();
        InstanceContainer instanceContainer = instanceManager.createInstanceContainer();

	Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader reader = new FileReader("config.json");
        Config configroot = gson.fromJson(reader, Config.class);
	ServerConf config = configroot.getServerConf();

	File vsecretfile = new File("forwarding.secret");
	if(vsecretfile.exists() && !vsecretfile.isDirectory()) { 
		String vsecret = new String(Files.readAllBytes(Path.of("forwarding.secret")));
		if(vsecret != null && !vsecret.trim().isEmpty()) {
			VelocityProxy.enable(vsecret);
		}
	}
	instanceContainer.setChunkLoader(new AnvilLoader(config.getWorld()));
	Spawn spawn = config.getSpawn();

        GlobalEventHandler globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
	    final Player player = event.getPlayer();
            event.setSpawningInstance(instanceContainer);
            System.out.println(player.getUsername() + " joined the game");
            player.setRespawnPoint(new Pos(spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getRoll()));
	    //player.setGameMode(GameMode.ADVENTURE);
	    player.setGameMode(GameMode.CREATIVE);
	
        });

	globalEventHandler.addListener(PlayerMoveEvent.class, event -> {
	    final Player player = event.getPlayer();
	    NetworkBuffer networkBuffer = new NetworkBuffer();
	    
	    // Send people to their target server
	    //String connectTo = "creative"; // TODO make this determined automatically
	    //System.out.println(player.getUsername() + " transferred to " + connectTo);
	    //ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    //out.writeUTF("Connect");
	    //out.writeUTF(connectTo);
	    //player.sendPacket(new PluginMessagePacket("BungeeCord", out.toByteArray()));

            //System.out.println(event.getNewPosition());
            //Portal creativePortal = config.getPortals().get("creative");
            //System.out.println(creativePortal.getX() + creativePortal.getY() + creativePortal.getZ());
	});
	
        globalEventHandler.addListener(PlayerBlockInteractEvent.class, event -> {
		if (event.getHand() == Player.Hand.MAIN) {
			var block = event.getBlock();
			//event.getBlockPosition().withBlockX();
		}
        });

        System.out.println("Server starting...");
        minecraftServer.start("0.0.0.0", config.getPort());
    }
}

// Config root, nothing belongs up here other than the categories toolchain and server
class Config {
    private ServerConf server;
    
    public ServerConf getServerConf() {
        return server;
    }
}

// This is where all the actual server configs should be
class ServerConf {
    private Integer port;
    private String world;
    private Spawn spawn;
    private Map<String, Portal> portals;

    public Integer getPort() {
        return port;
    }

    public String getWorld() {
        return world;
    }
    
    public Spawn getSpawn() {
        return spawn;
    }

    public void setSpawn(Spawn spawn) {
        this.spawn = spawn;
    }

    public Map<String, Portal> getPortals() {
        return portals;
    }

    public void setPortals(Map<String, Portal> portals) {
        this.portals = portals;
    }
}

class Spawn {
    private Double x;
    private Double y;
    private Double z;
    private Float yaw;
    private Float roll;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

    public Float getYaw() {
        return yaw;
    }

    public void setYaw(Float yaw) {
        this.yaw = yaw;
    }

    public Float getRoll() {
        return roll;
    }

    public void setRoll(Float roll) {
        this.roll = roll;
    }
}

class Portal {
    private Double x;
    private Double y;
    private Double z;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }
}

