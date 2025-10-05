# 🧾 Changelog — PermNodes

All notable changes to **PermNodes** will be documented in this file.  
This project follows [Semantic Versioning](https://semver.org/).

---

## [1.0.1] – 2025-10-06
### New Features & Improvements ✨
**Author:** cptgummiball  
**Minecraft:** 1.21.8+  
**NeoForge:** 21.8.46+

#### 🧑‍🌾 Villager Permissions
- Added new **`VillagerPermissionHandler`** for trading and protection:
    - Controls right-click **interactions and trading** with Villagers and Wandering Traders.  
      → Uses new nodes:
        - `interact.villager.<ns>.<path>` (e.g. `interact.villager.minecraft.villager`)
        - `interact.villager.<ns>.*`, `interact.villager.*`
    - Controls **attacking Villagers** and similar entities.  
      → Uses new nodes:
        - `villagers.attack.<ns>.<path>` (e.g. `villagers.attack.minecraft.villager`)
        - `villagers.attack.<ns>.*`, `villagers.attack.*`
    - Supports `villagers.allow_default`, `villagers.bypass`, and `mod.allow.<ns>` for global overrides.
- Trading logic was moved **out of `InteractPermissionHandler`** to the new dedicated class for cleaner separation.

#### 🐮 Animal Permissions
- Extended `AnimalsPermissionHandler`:
    - Added attack protection for all passive mobs.  
      → New permission set `animals.attack.*` (entity-specific handling, supports `<ns>.<path>` resolution).
    - Matches `MonstersPermissionHandler` structure for unified logic and consistency.

#### 🧱 Refactoring & Consistency
- Unified permission check structure across all entity handlers (`Monsters`, `Animals`, `Villagers`).
- Consistent naming convention for all bypass and default permissions:
    - `<category>.allow_default`
    - `<category>.bypass`
- Standardized permission feedback messages in `PermConfig` (multi-language ready).

#### 📘 Documentation
- Updated **Permission Reference** in `README.md` to reflect:
    - Added Villager-related permission nodes.
    - Removed Villager section from Interact category.
    - Improved formatting and grouping by system (Blocks, Entities, Environment, etc.).
- Added example permission strings and notes for LuckPerms behavior.

---

## [1.0.0] – 2025-10-05
### Initial Release 🎉
**Author:** cptgummiball  
**License:** MIT  
**Minecraft:** 1.21.8+  
**NeoForge:** 21.8.46+  
**LuckPerms (optional):** 5.4+

#### ✨ Features
- **Full permission-based gameplay control**:
    - Restrict **block placement**, **block breaking**, and **crafting** recipes.
    - Fine-grained control via `namespace.path` or wildcard-based permissions.
- **Item & interaction management**:
    - Control usage of any item (`use.*`), block interaction (`interact.*`),  
      and interaction with containers, doors, trapdoors, switches, crafting tables, anvils, and more.
- **Entity-specific restrictions**:
    - Animal breeding, milking, shearing, naming, leading (`animals.*`).
    - Hostile mob attack permissions (`monsters.*`).
    - Player vs. Player combat control (`pvp.attack_players`).
- **World and environment protection**:
    - Crop interactions (`crops.*`), fluid usage (`fluids.*`),  
      TNT ignition control (`ignite.*`), and portal access (`portal.*`).
- **Namespace-level overrides**:
    - Global mod permission `mod.allow.<namespace>` for whitelisting entire content mods.
- **LuckPerms integration**:
    - Automatic permission checks using the LuckPerms API.
    - Graceful fallback to internal defaults if LuckPerms is not installed.
- **Server-only design**:
    - Lightweight; no client dependencies required.

#### ⚙️ Configuration
- Added `PermConfig.java` for configurable server messages (multi-language ready).
- Default English messages included.

#### 🔒 Known Limitations
- LuckPerms **does not auto-display** PermNodes permissions in the web editor.  
  To manage them easily, assign nodes manually or create a `permnodes_template` group.

---

**PermNodes** © 2025 by *cptgummiball* — released under the **MIT License**.