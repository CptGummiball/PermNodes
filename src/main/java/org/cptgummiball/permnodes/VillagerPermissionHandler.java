package org.cptgummiball.permnodes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public final class VillagerPermissionHandler {

    private static boolean isVillagerEntity(Entity e) {
        return e instanceof AbstractVillager;
    }

    private static ResourceLocation entityId(Entity e) {
        return e.getType().builtInRegistryHolder()
                .unwrapKey().map(k -> k.location())
                .orElseGet(() -> BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()));
    }

    private static boolean canTrade(ServerPlayer sp, Entity villager) {
        if (PermissionService.checkNode(sp, "villagers.bypass")) return true;

        ResourceLocation id = entityId(villager);
        String ns  = id.getNamespace();
        String dot = ns + "." + id.getPath();

        if (!PermissionService.isLuckPermsPresent()) {
            return PermissionService.checkNode(sp, "villagers.allow_default");
        }

        if (PermissionService.checkNode(sp, "mod.allow." + ns)) return true;

        return PermissionService.checkNode(sp, "villagers.interact." + dot)
                || PermissionService.checkNode(sp, "villagers.interact." + ns + ".*")
                || PermissionService.checkNode(sp, "villagers.interact.*")
                || PermissionService.checkNode(sp, "villagers.*")
                || PermissionService.checkNode(sp, "villagers.allow_default");
    }

    /* Interacting (trading) */
    @SubscribeEvent
    public void onVillagerInteract(PlayerInteractEvent.EntityInteract e) {
        if (!(e.getEntity() instanceof ServerPlayer sp)) return;
        if (!isVillagerEntity(e.getTarget())) return;

        if (!canTrade(sp, e.getTarget())) {
            e.setCancellationResult(InteractionResult.FAIL);
            e.setCanceled(true);
            sp.displayClientMessage(PermConfig.MSG_VILLAGER_INTERACT_DENY, true);
        }
    }

    private static boolean canAttackVillager(ServerPlayer sp, Entity villager) {
        if (PermissionService.checkNode(sp, "villagers.bypass")) return true;

        ResourceLocation id = entityId(villager);
        String ns  = id.getNamespace();
        String dot = ns + "." + id.getPath();

        if (!PermissionService.isLuckPermsPresent()) {
            return PermissionService.checkNode(sp, "villagers.allow_default");
        }

        if (PermissionService.checkNode(sp, "mod.allow." + ns)) return true;

        return PermissionService.checkNode(sp, "villagers.attack." + dot)
                || PermissionService.checkNode(sp, "villagers.attack." + ns + ".*")
                || PermissionService.checkNode(sp, "villagers.attack.*")
                || PermissionService.checkNode(sp, "villagers.allow_default");
    }

    /* Attacking */
    @SubscribeEvent
    public void onAttackVillager(AttackEntityEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer attacker)) return;
        if (!isVillagerEntity(e.getTarget())) return;

        if (!canAttackVillager(attacker, e.getTarget())) {
            e.setCanceled(true);
            attacker.displayClientMessage(PermConfig.MSG_VILLAGER_ATTACK_DENY, true); // oder eigener MSG_VILLAGER_DENY
        }
    }

    @SubscribeEvent
    public void onVillagerIncomingDamage(LivingIncomingDamageEvent e) {
        if (!isVillagerEntity(e.getEntity())) return;

        Entity src = e.getSource().getEntity();
        if (!(src instanceof ServerPlayer attacker)) return;

        if (!canAttackVillager(attacker, e.getEntity())) {
            e.setCanceled(true);
            attacker.displayClientMessage(PermConfig.MSG_VILLAGER_ATTACK_DENY, true); // oder eigener MSG_VILLAGER_DENY
        }
    }
}
