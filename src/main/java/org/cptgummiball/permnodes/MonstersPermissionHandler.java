package org.cptgummiball.permnodes;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

public final class MonstersPermissionHandler {

    private static boolean isMonster(Entity e) {
        if (!(e instanceof LivingEntity le)) return false;
        return le.getType().getCategory() == MobCategory.MONSTER; // alle hostilen Mobs
    }

    private static ResourceLocation entityId(Entity e) {
        return e.getType().builtInRegistryHolder()
                .unwrapKey().map(k -> k.location())
                .orElseGet(() -> BuiltInRegistries.ENTITY_TYPE.getKey(e.getType()));
    }

    private static boolean canAttack(ServerPlayer sp, Entity monster) {
        if (PermissionService.checkNode(sp, "monsters.bypass")) return true;

        ResourceLocation id = entityId(monster);
        String ns = id.getNamespace();
        String dot = ns + "." + id.getPath();

        if (!PermissionService.isLuckPermsPresent()) {
            return PermissionService.checkNode(sp, "monsters.allow_default");
        }

        if (PermissionService.checkNode(sp, "mod.allow." + ns)) return true;

        return PermissionService.checkNode(sp, "monsters.attack." + dot)
                || PermissionService.checkNode(sp, "monsters.attack." + ns + ".*")
                || PermissionService.checkNode(sp, "monsters.attack.*")
                || PermissionService.checkNode(sp, "monsters.allow_default");
    }

    /* Attacking */
    @SubscribeEvent
    public void onAttack(AttackEntityEvent e) {
        if (!(e.getEntity() instanceof ServerPlayer attacker)) return;
        if (!isMonster(e.getTarget())) return;

        if (!canAttack(attacker, e.getTarget())) {
            e.setCanceled(true);
            attacker.displayClientMessage(PermConfig.MSG_MONSTER_DENY, true);
        }
    }

    @SubscribeEvent
    public void onIncomingDamage(LivingIncomingDamageEvent e) {
        if (!isMonster(e.getEntity())) return;

        Entity src = e.getSource().getEntity();
        if (!(src instanceof ServerPlayer attacker)) return;

        if (!canAttack(attacker, e.getEntity())) {
            e.setCanceled(true);
            attacker.displayClientMessage(PermConfig.MSG_MONSTER_DENY, true);
        }
    }
}