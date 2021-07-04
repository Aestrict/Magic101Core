package net.dohaw.magic101core.menus;

import net.dohaw.corelib.JPUtils;
import net.dohaw.corelib.menus.Menu;
import net.dohaw.magic101core.profiles.ProfileCreationSession;
import net.dohaw.magic101core.profiles.Schools;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class ClassSelectionMenu extends Menu implements Listener {

    private ProfileCreationSession session;
    private ProfileCreationMenu prevMenu;

    public ClassSelectionMenu(JavaPlugin plugin, ProfileCreationMenu previousMenu, ProfileCreationSession session) {
        super(plugin, previousMenu, "Class Selection", 9);
        this.session = session;
        this.prevMenu = previousMenu;
        JPUtils.registerEvents(this);
    }

    @Override
    public void initializeItems(Player player) {
        int i = 1;
        for(Schools school :Schools.values()){
            inv.setItem(i, createGuiItem(Material.STICK, school.toString(), new ArrayList<>()));
            ++i;
        }
    }

    @Override
    @EventHandler
    protected void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();

        if(e.getClickedInventory() == null) return;
        if(!e.getClickedInventory().equals(inv)) return;
        e.setCancelled(true);
        if(clickedItem == null || clickedItem.getType().equals(Material.AIR)) return;

        Schools school = null;
        switch(clickedItem.getItemMeta().getDisplayName()){
            case "DEATH":
                school = Schools.DEATH;
                break;
            case "FIRE":
                school = Schools.FIRE;
                break;
            case "ICE":
                school = Schools.ICE;
                break;
            case "LIFE":
                school = Schools.LIFE;
                break;
            case "BALANCE":
                school = Schools.BALANCE;
                break;
            case "MYTH":
                school = Schools.MYTH;
                break;
            case "STORM":
                school = Schools.STORM;
                break;
        }
        session.setSchool(school);
        prevMenu.setSession(session);
        prevMenu.initializeItems(player);
        player.closeInventory();
        prevMenu.openInventory(player);

    }
}
