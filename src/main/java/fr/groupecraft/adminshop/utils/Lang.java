package fr.groupecraft.adminshop.utils;

import fr.groupecraft.adminshop.Adminshop;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public enum Lang {
    MESSAGE_BEFORE("message.before"),MESSAGE_AFTER("message.after"),PRICE_BEFORE("lore.price.before"),PRICE_AFTER("lore.price.after"),NUMDER_BEFORE("lore.number.before"),
    NUMBER_AFTER("lore.number.after");

    private String path;
    private FileConfiguration lang;
    Lang(String path) {
        this.path=path;
    }
    private void setupFile(){
        if(lang!= null) return;//retuns if the file is already setup
        Adminshop main= Adminshop.getInstance();
        File file = new File(main.getDataFolder(),"lang.yml");
        if(file.exists()){
            main.saveResource("lang.yml",false);
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            lang= new YamlConfiguration();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public String getString(){
        setupFile();
        return lang.getString(path);
    }
}
