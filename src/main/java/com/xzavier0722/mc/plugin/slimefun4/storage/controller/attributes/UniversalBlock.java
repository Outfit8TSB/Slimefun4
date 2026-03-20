package com.xzavier0722.mc.plugin.slimefun4.storage.controller.attributes;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunUniversalBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunUniversalData;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;

/**
 * Marker interface indicating that a {@link SlimefunItem} uses {@link SlimefunUniversalData}.
 * <p>When the item is a machine, its block material must support storing data via Persistent Data
 * Containers (PDC) to track UUIDs. Otherwise, the item or machine cannot be linked to universal
 * data.</p>
 *
 * <p>See the Paper documentation for supported PDC materials:
 * <a href="https://jd.papermc.io/paper/1.21/org/bukkit/block/TileState.html">Paper Doc</a></p>
 *
 * @author NoRainCity
 *
 * @see SlimefunUniversalData
 * @see SlimefunUniversalBlockData
 */
public interface UniversalBlock {}
