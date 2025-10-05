package org.cptgummiball.permnodes;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.living.BabyEntitySpawnEvent;

public final class AnimalsPermissionHandler {

    // Breed (Baby entsteht)
    @SubscribeEvent
    public void onBreed(BabyEntitySpawnEvent e) {
        if (!(e.getCausedByPlayer() instanceof ServerPlayer sp)) return;
        if (PermissionService.checkNode(sp, "animals.bypass")) return;

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(sp, "animals.breed") ||
                PermissionService.checkNode(sp, "animals.*") ||
                PermissionService.checkNode(sp, "animals.allow_default")
                : PermissionService.checkNode(sp, "animals.allow_default");

        if (!allowed) {
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_ANIMAL_DENY, true);
        }
    }

    // Shear / Milk / Lead / NameTag per Rechtsklick auf Entity
    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (!(e.getTarget() instanceof Animal)) return;
        if (PermissionService.checkNode(sp, "animals.bypass")) return;

        var item = e.getItemStack().getItem();

        String needed = null;
        if (item == Items.SHEARS) needed = "animals.shear";
        else if (item == Items.BUCKET && e.getTarget() instanceof Cow) needed = "animals.milk";
        else if (item == Items.LEAD) needed = "animals.lead";
        else if (item == Items.NAME_TAG) needed = "animals.name_tag";

        if (needed != null) {
            boolean allowed = PermissionService.isLuckPermsPresent()
                    ? PermissionService.checkNode(sp, needed) ||
                    PermissionService.checkNode(sp, "animals.*") ||
                    PermissionService.checkNode(sp, "animals.allow_default")
                    : PermissionService.checkNode(sp, "animals.allow_default");

            if (!allowed) {
                e.setCancellationResult(InteractionResult.FAIL);
                e.setCanceled(true);
                sp.displayClientMessage(PermConfig.MSG_ANIMAL_DENY, true);
            }
        }
    }
}