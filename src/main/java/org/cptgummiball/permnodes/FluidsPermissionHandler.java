package org.cptgummiball.permnodes;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class FluidsPermissionHandler {

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "fluids.bypass")) return;

        var item = e.getItemStack().getItem();

        // Platzieren: Lava-/Wassereimer & andere BucketItems (Mod-Fluids)
        if (item instanceof BucketItem && item != Items.BUCKET) {
            boolean allowed = PermissionService.isLuckPermsPresent()
                    ? PermissionService.checkNode(sp, "fluids.place") ||
                    PermissionService.checkNode(sp, "fluids.*") ||
                    PermissionService.checkNode(sp, "fluids.allow_default")
                    : PermissionService.checkNode(sp, "fluids.allow_default");

            if (!allowed) {
                e.setCancellationResult(InteractionResult.FAIL);
                e.setCanceled(true);
                sp.displayClientMessage(PermConfig.MSG_FLUID_DENY, true);
            }
        }
        // Aufnehmen: leerer Eimer
        else if (item == Items.BUCKET) {
            boolean allowed = PermissionService.isLuckPermsPresent()
                    ? PermissionService.checkNode(sp, "fluids.pickup") ||
                    PermissionService.checkNode(sp, "fluids.*") ||
                    PermissionService.checkNode(sp, "fluids.allow_default")
                    : PermissionService.checkNode(sp, "fluids.allow_default");

            if (!allowed) {
                e.setCancellationResult(InteractionResult.FAIL);
                e.setCanceled(true);
                sp.displayClientMessage(PermConfig.MSG_FLUID_DENY, true);
            }
        }
    }
}