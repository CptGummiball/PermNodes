package org.cptgummiball.permnodes;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public final class IgnitePermissionHandler {

    @SubscribeEvent
    public void onTntPrimed(EntityJoinLevelEvent e) {

        if (!(e.getEntity() instanceof PrimedTnt tnt)) return;

        LivingEntity owner = tnt.getOwner();
        if (!(owner instanceof ServerPlayer sp)) {

            return;
        }

        if (PermissionService.checkNode(sp, "ignite.bypass")) return;

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(sp, "ignite.tnt")
                || PermissionService.checkNode(sp, "ignite.*")
                || PermissionService.checkNode(sp, "ignite.allow_default")
                : PermissionService.checkNode(sp, "ignite.allow_default");

        if (!allowed) {
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_IGNITE_DENY, true);
        }
    }
}