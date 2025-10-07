package org.cptgummiball.permnodes;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

public final class PvPPermissionHandler {

    /* Attacking players */
    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer attacker)) return;
        if (!(e.getTarget() instanceof ServerPlayer)) return; // nur PvP
        if (PermissionService.checkNode(attacker, "pvp.bypass")) return;

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(attacker, "pvp.attack_players")
                || PermissionService.checkNode(attacker, "pvp.*")
                || PermissionService.checkNode(attacker, "pvp.allow_default")
                : PermissionService.checkNode(attacker, "pvp.allow_default");

        if (!allowed) {
            e.setCanceled(true);
            attacker.displayClientMessage(PermConfig.MSG_PVP_DENY, true);
        }
    }

    @SubscribeEvent
    public void onIncomingDamage(LivingIncomingDamageEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer victim)) return;

        var src = e.getSource();
        var attackerEntity = src.getEntity();
        if (!(attackerEntity instanceof ServerPlayer attacker)) return; // nur PvP prüfen
        if (PermissionService.checkNode(attacker, "pvp.bypass")) return;

        boolean allowed = PermissionService.isLuckPermsPresent()
                ? PermissionService.checkNode(attacker, "pvp.attack_players")
                || PermissionService.checkNode(attacker, "pvp.*")
                || PermissionService.checkNode(attacker, "pvp.allow_default")
                : PermissionService.checkNode(attacker, "pvp.allow_default");

        if (!allowed) {
            e.setCanceled(true);
            attacker.displayClientMessage(PermConfig.MSG_PVP_DENY, true);
        }
    }
}