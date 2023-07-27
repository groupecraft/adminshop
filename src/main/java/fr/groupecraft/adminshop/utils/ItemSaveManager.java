package fr.groupecraft.adminshop.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSaveManager {

    private static final Gson gson = new GsonBuilder()
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
    /**
     * load the item save in the specified file in the json format, if the file does not exist, return null
     * @param file the file
     * @return the itemstack
     * */
    public static ItemStack loadItem(File file){
        if(!file.exists()) return null;
        FilesUsing fileU= new FilesUsing(file);
        ArrayList<String> content = fileU.read();
        StringBuilder json = new StringBuilder(content.get(0));
        content.remove(0);
        for(String line : content){
            json.append("\n").append(line);
        }
        return gson.fromJson(json.toString(),ItemStack.class);
    }
    /**
     * save the item in the specified file in the json format, if the file does already exist erase it's content
     * @param item the ItemStack to save
     * @param file the file in with the item will me save
     * */
    public static void saveItem(ItemStack item, File file){
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        FilesUsing fileU= new FilesUsing(file);
        String[] json = gson.toJson(item).split("\n");
        fileU.writeWOK((ArrayList<String>) Arrays.asList(json));
    }
}
