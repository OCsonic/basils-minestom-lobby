package com.basil.lobby;

import java.util.Map;

public class Config {
    private ServerConf server;
    
    public ServerConf getServerConf() {
        return server;
    }
}

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
