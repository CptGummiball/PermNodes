package org.cptgummiball.permnodes;

import net.minecraft.network.chat.Component;

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
    public static final Component MSG_CROP_DENY    = Component.literal("§cYou are not allowed to interact with this crop!");
    public static final Component MSG_FLUID_DENY   = Component.literal("§cYou are not allowed to use this fluid!");
    public static final Component MSG_MONSTER_DENY = Component.literal("§cYou are not allowed to attack monsters!");
}
