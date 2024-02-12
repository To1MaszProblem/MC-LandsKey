package pl.to1maszproblem.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.to1maszproblem.Main;
import pl.to1maszproblem.module.LandsKey;
import pl.to1maszproblem.util.TextUtil;

import java.util.List;
import java.util.Optional;

public class KrainaCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) { TextUtil.sendMessage(player, "&7Poprawne użycie: /kraina [create/delete/setitem/setlocation/setblock/removing/list/reload]"); return false; }
            if (!player.hasPermission("land.list")) {
                TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4land.info&8)");
                return false;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("list")) {
                    TextUtil.sendMessage(player, "&fAktualne krainy na serwerze:");
                    Main.getInstance().getConfiguration().getLandsKeys().forEach(land -> TextUtil.sendMessage(player, "&7- &f" + land.getName()));
                }
                if (!player.hasPermission("land.reload")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4land.reload&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("reload")) {
                    TextUtil.sendMessage(player, "&aPrzeładowano config!");
                    Main.getInstance().getConfiguration().load();
                    Main.getInstance().getMessageConfiguration().load();
                }
            } else if (args.length == 2) {
                if (!player.hasPermission("land.create")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4land.create&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("create")) {
                    String name = args[1];
                    if (Main.getInstance().getConfiguration().getLandsKeys().stream().anyMatch(warp -> warp.getName().toLowerCase().equalsIgnoreCase(name))) {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getLandDoesntExist());
                        return false;
                    }
                    Main.getInstance().getConfiguration().getLandsKeys().add(new LandsKey(name, null, null, null));
                    TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getCreatedLand().replace("[land-name]", name));
                    Main.getInstance().getConfiguration().save();
                }
                if (!player.hasPermission("land.delete")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4land.delete&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("delete")) {
                    String name = args[1];
                    List<LandsKey> lands = Main.getInstance().getConfiguration().getLandsKeys();

                    Optional<LandsKey> landsToRemove = lands.stream()
                            .filter(collection -> collection.getName().equalsIgnoreCase(name))
                            .findFirst();
                    if (landsToRemove.isPresent()) {
                        lands.remove(landsToRemove.get());
                        Main.getInstance().getConfiguration().save();
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getDeletedLand().replace("[land-name]", name));
                    } else {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getLandDoesntExist());
                    }
                }
                if (!player.hasPermission("land.setkey")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4land.setkey&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("setkey")) {
                    String name = args[1];
                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item.getType() == Material.AIR) {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getHoldItemToCreate());
                        return false;
                    }
                    if (Main.getInstance().getConfiguration().getLandsKeys().stream().noneMatch(land -> land.getName().toLowerCase().equalsIgnoreCase(args[1].toLowerCase()))) {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getLandDoesntExist());
                        return false;
                    }

                    Optional<LandsKey> foundLand = Main.getInstance().getConfiguration().getLandsKeys().stream()
                            .filter(land -> land.getName().equalsIgnoreCase(name))
                            .findFirst();

                    if (foundLand.isPresent()) {
                        LandsKey land = foundLand.get();
                        land.setKey(item);
                        Main.getInstance().getConfiguration().save();
                        TextUtil.sendMessage(player, "&7Ustawiono klucz dla krainy &f" + land.getName());
                    }
                }
                if (!player.hasPermission("land.setblock")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4land.setblock&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("setblock")) {
                    String name = args[1];
                    Block targetBlock = player.getTargetBlockExact(5);

                    if (targetBlock == null) {
                        TextUtil.sendMessage(player, "&cNie patrzysz na żaden blok.");
                        return false;
                    }
                    Location location = targetBlock.getLocation();

                    if (Main.getInstance().getConfiguration().getLandsKeys().stream().noneMatch(land -> land.getName().toLowerCase().equalsIgnoreCase(args[1].toLowerCase()))) {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getLandDoesntExist());
                        return false;
                    }

                    Optional<LandsKey> foundLand = Main.getInstance().getConfiguration().getLandsKeys().stream()
                            .filter(land -> land.getName().equalsIgnoreCase(name))
                            .findFirst();

                    if (foundLand.isPresent()) {
                        LandsKey land = foundLand.get();
                        land.setBlock(location);
                        Main.getInstance().getConfiguration().save();
                        TextUtil.sendMessage(player, "&7Ustawiono lokacje dla krainy &f" + land.getName());
                    }
                }
                if (!player.hasPermission("land.setlocation")) {
                    TextUtil.sendMessage(player, "&8>> &cNie posiadasz uprawnień do tej komendy! &8(&4land.setlocation&8)");
                    return false;
                }
                if (args[0].equalsIgnoreCase("setlocation")) {
                    String name = args[1];
                    Location location = player.getLocation();


                    if (Main.getInstance().getConfiguration().getLandsKeys().stream().noneMatch(land -> land.getName().toLowerCase().equalsIgnoreCase(args[1].toLowerCase()))) {
                        TextUtil.sendMessage(player, Main.getInstance().getMessageConfiguration().getLandDoesntExist());
                        return false;
                    }

                    Optional<LandsKey> foundLand = Main.getInstance().getConfiguration().getLandsKeys().stream()
                            .filter(land -> land.getName().equalsIgnoreCase(name))
                            .findFirst();

                    if (foundLand.isPresent()) {
                        LandsKey land = foundLand.get();
                        land.setLocation(location);
                        Main.getInstance().getConfiguration().save();
                        TextUtil.sendMessage(player, "&7Ustawiono lokacje dla krainy &f" + land.getName());
                    }
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) return List.of("create","delete","setkey","setlocation","setblock","removing","list","reload");
        if (args.length == 2 &&
                args[0].equalsIgnoreCase("delete") ||
                args[0].equalsIgnoreCase("setlocation") ||
                args[0].equalsIgnoreCase("setblock") ||
                args[0].equalsIgnoreCase("setkey")) return
                Main.getInstance().getConfiguration().getLandsKeys().stream().map(land -> land.getName().toLowerCase()).toList();
        return null;
    }

}
