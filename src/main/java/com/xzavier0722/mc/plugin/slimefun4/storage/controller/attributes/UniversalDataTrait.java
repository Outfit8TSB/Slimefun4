package com.xzavier0722.mc.plugin.slimefun4.storage.controller.attributes;

import city.norain.slimefun4.api.menu.UniversalMenu;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunUniversalBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunUniversalData;
import lombok.Getter;

/**
 * Declares the traits available for {@link SlimefunUniversalData}.
 * A universal data entry can have one or multiple traits.
 * <p>By default, every universal data entry behaves as a key-value container.</p>
 *
 * @see SlimefunUniversalData
 * @see SlimefunUniversalBlockData
 */
@Getter
public enum UniversalDataTrait {
    /**
     * Indicates that the universal data belongs to a {@link SlimefunUniversalBlockData}.
     */
    BLOCK("location"),

    /**
     * Indicates that the universal data owns a {@link UniversalMenu}.
     */
    INVENTORY("");

    private final String reservedKey;

    UniversalDataTrait(String reservedKey) {
        this.reservedKey = reservedKey;
    }

    public static boolean isReservedKey(String key) {
        for (UniversalDataTrait trait : UniversalDataTrait.values()) {
            if (trait.getReservedKey().equals(key)) {
                return true;
            }
        }

        return false;
    }
}
