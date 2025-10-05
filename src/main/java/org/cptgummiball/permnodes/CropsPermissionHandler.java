package org.cptgummiball.permnodes;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class CropsPermissionHandler {

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent e) {
        if (!(e.getPlayer() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "crops.bypass")) return;

        BlockState s = e.getState();
        if (s.getBlock() instanceof CropBlock crop && crop.isMaxAge(s)) {
            boolean allowed = PermissionService.isLuckPermsPresent()
                    ? PermissionService.checkNode(sp, "crops.harvest") ||
                    PermissionService.checkNode(sp, "crops.*") ||
                    PermissionService.checkNode(sp, "crops.allow_default")
                    : PermissionService.checkNode(sp, "crops.allow_default");
            if (!allowed) {
                e.setCanceled(true);
                e.getPlayer().displayClientMessage(PermConfig.MSG_CROP_DENY, true);
            }
        }
    }

    @SubscribeEvent
    public void onTrample(BlockEvent.FarmlandTrampleEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "crops.bypass")) return;

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(sp, "crops.trample") ||
                PermissionService.checkNode(sp, "crops.*") ||
                PermissionService.checkNode(sp, "crops.allow_default")
                : PermissionService.checkNode(sp, "crops.allow_default");

        if (!allowed) e.setCanceled(true);
    }

    @SubscribeEvent
    public void onSeed(PlayerInteractEvent.RightClickBlock e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "crops.bypass")) return;

        BlockState state = e.getLevel().getBlockState(e.getPos());
        boolean isFarmland = state.getBlock() instanceof FarmBlock;
        String name = e.getItemStack().getItem().toString().toLowerCase(); // robust genug, sonst explizit Tags prüfen
        boolean looksLikeSeed = name.contains("seed");

        if (isFarmland && looksLikeSeed) {
            boolean allowed = PermissionService.isLuckPermsPresent()
                    ? PermissionService.checkNode(sp, "crops.seed") ||
                    PermissionService.checkNode(sp, "crops.*") ||
                    PermissionService.checkNode(sp, "crops.allow_default")
                    : PermissionService.checkNode(sp, "crops.allow_default");

            if (!allowed) {
                e.setCanceled(true);
                sp.displayClientMessage(PermConfig.MSG_CROP_DENY, true);
            }
        }
    }
}