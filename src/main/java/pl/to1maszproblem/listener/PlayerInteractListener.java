package pl.to1maszproblem.listener;

import com.google.common.collect.ImmutableMultimap;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.to1maszproblem.Main;
import pl.to1maszproblem.util.TextUtil;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Location clicked = event.getClickedBlock().getLocation();
            Main.getInstance().getConfiguration().getLandsKeys().forEach(land -> {
                if (clicked.equals(land.getBlock())) {
                    if (land.getLocation() == null) { TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getLandLocationDoesntSeted()); return; }
                    if (land.getKey() == null) { TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getLandKeyDoesntSeted()); return; }
                    if (hasItem(player, land.getKey())) {
                        player.getInventory().removeItem(land.getKey());
                        player.teleportAsync(land.getLocation());
                        Main.getInstance().getMessageConfiguration().getTeleported().addPlaceholder(ImmutableMultimap.of("[land-name]", land.getName())).send(player);
                    } else TextUtil.sendMessage(player, "&cNie masz klucza do tej krainy");
                }
            });
        }
    }
    private boolean hasItem(Player player, ItemStack itemStack) {
        return player.getInventory().containsAtLeast(itemStack, itemStack.getAmount());
    }
}
