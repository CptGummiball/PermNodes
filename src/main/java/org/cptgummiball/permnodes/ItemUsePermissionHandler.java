package org.cptgummiball.permnodes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class ItemUsePermissionHandler {

    private static ResourceLocation itemId(ItemStack s) {
        return s.getItemHolder()
                .unwrapKey().map(k -> k.location())
                .orElseGet(() -> BuiltInRegistries.ITEM.getKey(s.getItem()));
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "use.bypass")) return;

        ItemStack stack = e.getItemStack();
        if (stack.isEmpty()) return;

        ResourceLocation id = itemId(stack);
        String ns = id.getNamespace();
        String dot = ns + "." + id.getPath();

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(sp, "mod.allow." + ns) ||
                PermissionService.checkNode(sp, "use." + dot) ||
                PermissionService.checkNode(sp, "use." + ns + ".*") ||
                PermissionService.checkNode(sp, "use.*") ||
                PermissionService.checkNode(sp, "use.allow_default")
                : PermissionService.checkNode(sp, "use.allow_default");

        if (!allowed) {
            e.setCancellationResult(InteractionResult.FAIL);
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_USE_DENY, true);
        }
    }
}
