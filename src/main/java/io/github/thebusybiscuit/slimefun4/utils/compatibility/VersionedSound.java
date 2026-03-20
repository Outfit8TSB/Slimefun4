package io.github.thebusybiscuit.slimefun4.utils.compatibility;

import com.google.common.base.Preconditions;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.lang.reflect.Method;
import java.util.Locale;
import javax.annotation.Nonnull;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;

/**
 * Utility for interacting with {@link Sound} across different server versions.
 *
 * @author ybw0014
 */
public final class VersionedSound {

    private VersionedSound() {
        // utility class
    }

    private static final boolean IS_ENUM;
    private static final Method GET_KEY_METHOD;
    private static final Method VALUE_OF_METHOD;

    static {
        boolean isEnum = false;
        Method getKeyMethod = null;
        Method valueOfMethod = null;

        try {
            if (Sound.class.isEnum()) {
                isEnum = true;
            } else {
                getKeyMethod = Sound.class.getMethod("getKey");
            }
            valueOfMethod = Sound.class.getMethod("valueOf", String.class);
        } catch (Exception e) {
            Slimefun.logger().severe("Unable to determine Sound type: " + e.getMessage());
        }

        IS_ENUM = isEnum;
        GET_KEY_METHOD = getKeyMethod;
        VALUE_OF_METHOD = valueOfMethod;
    }

    public static boolean isEnum() {
        return IS_ENUM;
    }

    /**
     * Retrieves the name of the provided {@link Sound}.
     *
     * @param sound the sound instance
     * @return the sound name
     */
    @Nonnull
    public static String getSoundName(@Nonnull Sound sound) {
        try {
            if (isEnum()) {
                return ((Enum<?>) sound).name();
            } else if (GET_KEY_METHOD != null) {
                Object key = GET_KEY_METHOD.invoke(sound);
                if (!(key instanceof NamespacedKey nsKey)) {
                    throw new IllegalStateException("Sound.getKey() returned a non-NamespacedKey value");
                }
                return nsKey.getKey();
            }
        } catch (Exception e) {
            Slimefun.logger().severe("Failed to obtain sound name: " + e.getMessage());
        }

        // fallback
        return sound.toString();
    }

    /**
     * Compatibility wrapper for {@link Sound#valueOf(String)} that normalises the provided name.
     *
     * @param name the sound name
     * @return the matching sound
     * @throws IllegalArgumentException if the name cannot be resolved
     */
    @Nonnull
    public static Sound valueOf(@Nonnull String name) throws IllegalArgumentException {
        Preconditions.checkArgument(name != null, "Sound name cannot be null");

        String enumName = name.toUpperCase(Locale.ROOT).replace('.', '_').replace('-', '_');
        try {
            if (VALUE_OF_METHOD != null) {
                return (Sound) VALUE_OF_METHOD.invoke(null, enumName);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unable to invoke Sound.valueOf for name: " + name, e);
        }

        throw new IllegalArgumentException("Sound.valueOf method unavailable");
    }
}
