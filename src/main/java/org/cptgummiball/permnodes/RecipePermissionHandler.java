package org.cptgummiball.permnodes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public final class RecipePermissionHandler {

    private static String itemToDot(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return "minecraft.air";
        ResourceLocation id = stack.getItemHolder()
                .unwrapKey()
                .map(k -> k.location())
                .orElseGet(() -> BuiltInRegistries.ITEM.getKey(stack.getItem()));
        if (id == null) return "minecraft.air";
        return id.getNamespace() + "." + id.getPath();
    }

    @SubscribeEvent
    public void onCrafted(PlayerEvent.ItemCraftedEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;

        if (PermissionService.checkNode(sp, "recipes.craft.bypass")) return;

        ItemStack result = e.getCrafting();
        if (result == null || result.isEmpty()) return;

        String dot = itemToDot(result);
        String node = "recipes.craft." + dot;

        if (!PermissionService.isLuckPermsPresent()) {
            if (!PermissionService.checkNode(sp, "recipes.craft.allow_default")) {
                result.setCount(0);
                sp.displayClientMessage(PermConfig.MSG_RECIPE_DENY, true);
            }
            return;
        }

        boolean allowed =
                PermissionService.checkNode(sp, node) ||
                        PermissionService.checkNode(sp, "recipes.craft.*") ||
                        PermissionService.checkNode(sp, "recipes.craft." + dot.split("\\.")[0] + ".*") ||
                        PermissionService.checkNode(sp, "recipes.craft.allow_default");

        if (!allowed) {
            result.setCount(0);
            sp.displayClientMessage(PermConfig.MSG_RECIPE_DENY, true);
        }
    }
}