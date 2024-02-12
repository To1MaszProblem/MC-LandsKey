package pl.to1maszproblem.configuration;

import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import pl.to1maszproblem.module.LandsKey;

import java.util.Arrays;
import java.util.List;

@Getter
public class Configuration extends OkaeriConfig {
    private List<LandsKey> landsKeys = Arrays.asList(
            new LandsKey("test",
                    new Location(Bukkit.getWorld("world"), 0.0, 100.0, 0.0),
                    new Location(Bukkit.getWorld("world"), 10.0, 100.0, 0.0),
                    new ItemStack(Material.DIRT)));
}
