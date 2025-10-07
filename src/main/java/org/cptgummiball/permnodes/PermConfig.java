package org.cptgummiball.permnodes;

import net.minecraft.network.chat.Component;

/**
 * Configuration for permission denial messages.
 */

public final class PermConfig {
    public static final Component MSG_PLACE_DENY   = Component.literal("§cYou are not allowed to place this block!");
    public static final Component MSG_BREAK_DENY   = Component.literal("§cYou are not allowed to break this block!");
    public static final Component MSG_RECIPE_DENY  = Component.literal("§cYou are not allowed to craft this recipe!");

    public static final Component MSG_USE_DENY     = Component.literal("§cYou are not allowed to use this item!");
    public static final Component MSG_INTERACT_DENY= Component.literal("§cYou are not allowed to interact with this!");
    public static final Component MSG_PORTAL_DENY  = Component.literal("§cYou are not allowed to use this portal!");
    public static final Component MSG_IGNITE_DENY  = Component.literal("§cYou are not allowed to ignite this!");
    public static final Component MSG_PVP_DENY     = Component.literal("§cPvP is disabled for you!");
    public static final Component MSG_ANIMAL_DENY  = Component.literal("§cYou are not allowed to interact with this animal!");
    public static final Component MSG_ANIMAL_ATTACK_DENY  = Component.literal("§cYou are not allowed to animals!");
    public static final Component MSG_CROP_DENY    = Component.literal("§cYou are not allowed to interact with this crop!");
    public static final Component MSG_FLUID_DENY   = Component.literal("§cYou are not allowed to use this fluid!");
    public static final Component MSG_MONSTER_DENY = Component.literal("§cYou are not allowed to attack monsters!");
    public static final Component MSG_VILLAGER_INTERACT_DENY = Component.literal("§cYou are not allowed to interact with this villager!");
    public static final Component MSG_VILLAGER_ATTACK_DENY = Component.literal("§cYou are not allowed to attack this villager!");
}
