package com.brittank88.anything_goes.config;

import com.brittank88.anything_goes.AnythingGoes;
import com.mrcrayfish.configured.api.ConfigType;
import com.mrcrayfish.configured.api.simple.*;

// TODO: Migrate property names to en_us.json.
// See: https://github.com/MrCrayfish/Configured/blob/1.19.X/src/main/resources/assets/configured/lang/en_us.json (the simpleconfig entries)
public final class AnythingGoesConfig {

    @SimpleConfig(
            id   = AnythingGoes.MOD_ID,
            name = "server",
            type = ConfigType.SERVER
    ) public static final ServerConfig SERVER_CONFIG = new ServerConfig();

    public static final class ServerConfig {

        @SimpleProperty(
                name    = "Fixes",
                comment = "Sub-config for crash/logic fixes."
        ) public final Fixes FIXES = new Fixes();
        @SimpleProperty(
                name    = "Tweaks",
                comment = "Sub-config for minor tweak features."
        ) public final Tweaks TWEAKS = new Tweaks();
        @SimpleProperty(
                name    = "Uncaps",
                comment = "Sub-config for cap-removal features."
        ) public final Uncaps UNCAPS = new Uncaps();
        @SimpleProperty(
                name    = "Extras",
                comment = "Sub-config for extras/brand-new features"
        ) public final Extras EXTRAS = new Extras();

        public static final class Fixes {

            @SimpleProperty(
                    name    = "Over-Leveled Enchantments Fix",
                    comment = "Fixes over-leveled enchantments potentially rendering the item useless or causing crashes."
            ) public final BoolProperty overLeveledEnchantmentsFix = BoolProperty.create(true);

            @SimpleProperty(
                    name    = "Enchantment Lang Fix",
                    comment = "Patches the lang to fix enchantment levels above 10 displaying as 'enchantment.level.N'."
                            + "\nRoman numerals over 3999 are achieved via bold numerals representing an overbar (x1000), and circled numerals representing a double-overbar (x1000000)."
            ) public final BoolProperty enchantmentLangFix = BoolProperty.create(true);
        }

        public static final class Tweaks {

            @SimpleProperty(
                    name    = "Infinity++",
                    comment = "Infinity no longer requires an arrow in the player's inventory."
            ) public final BoolProperty infinityPlusPlus = BoolProperty.create(true);

            @SimpleProperty(
                    name    = "Looting++",
                    comment = "Looting applies to dropped experience points."
            ) public final BoolProperty lootingPlusPlus = BoolProperty.create(true);

            @SimpleProperty(
                    name    = "Multishot++",
                    comment = "Multishot shoots one more projectile per extra level."
            ) public final BoolProperty multishotPlusPlus = BoolProperty.create(true);
        }

        public static final class Uncaps {

            @SimpleProperty(
                    name    = "Over-Leveled Enchantments",
                    comment = "Removes the level cap on enchantments when an anvil is used to combine them, or the /enchant command is used."
            ) public final BoolProperty overLeveledEnchantments = BoolProperty.create(true);

            @SimpleProperty(
                    name    = "Over-Leveled Enchantments++",
                    comment = "Increases the maximum possible enchantment level from 255 to 2147483647."
            ) public final BoolProperty overLeveledEnchantmentsPlusPlus = BoolProperty.create(true);
        }

        public static final class Extras {

            @SimpleProperty(
                    name    = "Librarian Bribes",
                    comment = "Librarians will potentially accept emerald blocks as a bribe to sell higher-level enchantments."
                            + "\nCredits to Fuzss' Universal Enchants mod for the original idea."
            ) public final BoolProperty librarianBribes = BoolProperty.create(true);

            @SimpleProperty(
                    name    = "Librarian Bribes Chance",
                    comment = "The chance that a librarian will accept a bribe."
                            + "\nCredits to Fuzss' Universal Enchants mod for the original idea."
            ) public final DoubleProperty librarianBribesChance = DoubleProperty.create(1D, 0.0D, 1.0D);

            @SimpleProperty(
                    name    = "Grindstone Disenchanting",
                    comment = "Grindstones can be used transfer enchantments from items to books."
            ) public final BoolProperty grindstoneDisenchanting = BoolProperty.create(true);

            @SimpleProperty(
                    name    = "Grindstone XP Cost",
                    comment = "The amount of XP per enchantment required to use a grindstone."
            ) public final IntProperty grindstoneXpCost = IntProperty.create(3, 0, Integer.MAX_VALUE);

            @SimpleProperty(
                    name    = "Grindstone XP Cost Level Factor",
                    comment = "The factor applied to the cost per enchantment, based on the enchantment's level."
            ) public final DoubleProperty grindstoneXpCostLevelFactor = DoubleProperty.create(1D, 1.0D, Double.MAX_VALUE);
        }
    }
}
