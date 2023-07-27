package fr.groupecraft.adminshop.bazard;

import fr.groupecraft.adminshop.Adminshop;
import fr.groupecraft.adminshop.item.BazardItem;
import fr.groupecraft.adminshop.item.ItemsDB;
import fr.groupecraft.adminshop.utils.Config;
import fr.groupecraft.adminshop.utils.FilesUsing;
import fr.groupecraft.adminshop.utils.ItemSaveManager;
import fr.groupecraft.adminshop.utils.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BazardInv {

    private static BazardInv instance;
    private final Items items;
    private ItemStack previous, next;//the item Stack that corresponds to the next and previous page
    private int previousI, nextI; //the place of the arrow in the inventory
    private Inventory baseInv;

    private BazardInv(){
        items= new Items();
        actualizeBaseInv();
    }
    /**
     * set the previous item to the one in the file and if the file does not exist set it as the default arrow
     * */
    public void actualizePrevious(){
        File file = new File( Adminshop.getInstance().getDataFolder().toPath() +"/bazard/previous.json");
        if( file.exists()) {
            ArrayList<String> content = new FilesUsing(file).read();
            previousI= Integer.parseInt(content.get(0));
            content.remove(0);
            StringBuilder json = new StringBuilder();
            for( String line : content){
                json.append(line);
            }
            previous =ItemsDB.decodeItem(json.toString());
        }else{
            previous= new ItemStack(Material.ARROW);
            ItemMeta prevM= previous.getItemMeta();
            prevM.setDisplayName("§cPrevious");
            prevM.setLore(Arrays.asList("previous Page"));
            previous.setItemMeta(prevM);
            previousI=3;
        }
    }
    /**
     * set the next item to the one in the file if the file does not exist set it as the default arrow
     * */
    public void actualizeNext(){
        File file = new File( Adminshop.getInstance().getDataFolder().toPath() +"/bazard/next.json");
        if( file.exists()) {
            ArrayList<String> content = new FilesUsing(file).read();
            nextI= Integer.parseInt(content.get(0));
            content.remove(0);
            StringBuilder json = new StringBuilder();
            for( String line : content){
                json.append(line);
            }
            next =ItemsDB.decodeItem(json.toString());
        }else{
            next= new ItemStack(Material.ARROW);
            ItemMeta prevM= next.getItemMeta();
            prevM.setDisplayName("§cNext");
            prevM.setLore(Arrays.asList("Next Page"));
            next.setItemMeta(prevM);
            nextI=5;
        }
    }
    public static BazardInv getInstance(){
        if(instance==null) instance = new BazardInv();
        return instance;
    }
    /**
     * call the {@link Items#loadItems()} and load the items saved
     * Warning: it erases the items currently saved in the list
     */
    public void loadItems(){
        items.loadItems();
    }
    /**
     * call the {@link Items#saveItems()}  and save the items stored in the list
     * Warning: it erases the items currently saved in the file
     */
    public void saveItems(){
        items.saveItems();
    }
    /**
     * actualize the base inventory (name+bottom line) and call the {@link BazardInv#actualizeNext()} and {@link BazardInv#actualizePrevious()} methods to update the previous and next items;
     * */
    public void actualizeBaseInv(){
        actualizeNext();
        actualizePrevious();
        Inventory inv= Bukkit.createInventory(null, 54, Config.INVENTORY_NAME.getString());
        File placeH= new File(Adminshop.getInstance().getDataFolder().toPath() +"/bazard/placeholder.json");
        ItemStack placeHolder= new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta placeHM= placeHolder.getItemMeta();
        placeHM.setDisplayName("  ");
        placeHolder.setItemMeta(placeHM);
        if(placeH.exists()) placeHolder= ItemSaveManager.loadItem(placeH);

        for(int i = 0; i <= 8; i++){
            File file = new File( Adminshop.getInstance().getDataFolder().toPath() +"/bazard/"+i+".json");
            if( file.exists()) inv.setItem(45+i, ItemsDB.decodeItem(new FilesUsing(file).readAsString()));
            else inv.setItem(45+1, placeHolder);
        }
        baseInv=inv;
    }
    /**
     * return the number of pages that have the bazard the number of items in the Items list
     * @return 0 if there is no items in the bazard
     */
    public int getPagesNumber(){
        int lenght= items.items.size();
        int a = lenght/45;
        if(a%45!=0) a++;
        return a;
    }
    /**
     * return the page of the bazard, the page start with page 0
     * */
    public Inventory getBazardPage(int page){
        if(page >= getPagesNumber()) page = getPagesNumber()-1;
        Inventory inv = Bukkit.createInventory(null, 54, Config.INVENTORY_NAME.getString());
        inv.setContents(baseInv.getContents());
        if(page>0) inv.setItem(nextI+45, next);
        if(page<getPagesNumber()-1)inv.setItem(previousI+45, previous);
        for(int i=0; i<45; i++){
            inv.setItem(i,items.items.get(i+45*page).getShopItem());
        }
        return inv;
    }

}
