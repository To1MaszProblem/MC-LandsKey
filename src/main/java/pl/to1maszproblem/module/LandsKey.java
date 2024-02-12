package pl.to1maszproblem.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

@AllArgsConstructor @Data
public class LandsKey implements Serializable {
    private String name;

    private Location block;

    private Location location;

    private ItemStack key;

}
