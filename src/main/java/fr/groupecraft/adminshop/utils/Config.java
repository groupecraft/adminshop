package fr.groupecraft.adminshop.utils;

import fr.groupecraft.adminshop.Adminshop;

public enum Config {
    INVENTORY_NAME("inv-name"), AUTO_SAVE("auto-save"), MESSAGE_TIME("message-time"),;
    private String path;

    Config(String path) {
        this.path = path;
    }
    public String getString(){
        Adminshop main= Adminshop.getInstance();
        return main.getConfig().getString(path);
    }
    public boolean getBool(){
        Adminshop main= Adminshop.getInstance();
        return main.getConfig().getBoolean(path);
    }
    public int getInt(){
        Adminshop main= Adminshop.getInstance();
        return main.getConfig().getInt(path);
    }
}
