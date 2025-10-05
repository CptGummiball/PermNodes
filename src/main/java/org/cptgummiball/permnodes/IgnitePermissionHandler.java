package org.cptgummiball.permnodes;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

public final class IgnitePermissionHandler {

    @SubscribeEvent
    public void onTntPrimed(EntityJoinLevelEvent e) {
        // Wir interessieren uns nur für frisch gespawnte PrimedTnt
        if (!(e.getEntity() instanceof PrimedTnt tnt)) return;

        // Wer hat gezündet? (kann auch null sein – z.B. Redstone/Explosion)
        LivingEntity owner = tnt.getOwner();
        if (!(owner instanceof ServerPlayer sp)) {
            // Optional: TNT ohne Spieler als Verursacher komplett verbieten
            // e.setCanceled(true);
            return;
        }

        // Bypass?
        if (PermissionService.checkNode(sp, "ignite.bypass")) return;

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(sp, "ignite.tnt")
                || PermissionService.checkNode(sp, "ignite.*")
                || PermissionService.checkNode(sp, "ignite.allow_default")
                : PermissionService.checkNode(sp, "ignite.allow_default");

        if (!allowed) {
            // verhindert das Spawnen der PrimedTnt komplett
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_IGNITE_DENY, true);
        }
    }
}