# PermNodes — Permission Reference (updated)

> **Notes**
> - LuckPerms resolves wildcards (`*`) automatically.
> - `allow_default` acts as a fallback when LuckPerms is absent (and can be combined with config defaults if enabled).
> - `<ns>` = namespace/modid (e.g., `minecraft`, `create`), `<path>` = registry path (e.g., `oak_door`, `dirt`).
> - Tag rules use Minecraft/NeoForge tags (e.g., `minecraft.logs`).

| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Global (per mod)** | `mod.allow.<ns>` | `mod.allow.minecraft`, `mod.allow.create` | Broad allow for content from `<ns>` (acts like a namespace bypass for IDs/tags). |

### Blocks
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Place** | `blocks.place.<ns>.<path>` | `blocks.place.minecraft.dirt` | Allow placing a specific block. |
|  | `blocks.place.<ns>.*` | `blocks.place.minecraft.*` | Allow placing any block from `<ns>`. |
|  | `blocks.place.*` |  | Allow placing any block (global). |
|  | `blocks.place.tag.<ns>.<tag>` | `blocks.place.tag.minecraft.logs` | Allow placing blocks by tag. |
|  | `blocks.place.allow_default` |  | Fallback allow. |
|  | `blocks.place.bypass` |  | Bypass all place checks. |
| **Break** | `blocks.break.<ns>.<path>` | `blocks.break.minecraft.diamond_ore` | Allow breaking a specific block. |
|  | `blocks.break.<ns>.*` | `blocks.break.create.*` | Allow breaking any block from `<ns>`. |
|  | `blocks.break.*` |  | Allow breaking any block (global). |
|  | `blocks.break.tag.<ns>.<tag>` | `blocks.break.tag.minecraft.logs` | Allow breaking blocks by tag. |
|  | `blocks.break.allow_default` |  | Fallback allow. |
|  | `blocks.break.bypass` |  | Bypass all break checks. |

### Recipes (Crafting)
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Craft** | `recipes.craft.<ns>.<path>` | `recipes.craft.minecraft.torch` | Allow crafting a specific result item. |
|  | `recipes.craft.<ns>.*` | `recipes.craft.minecraft.*` | Allow crafting any recipe from `<ns>`. |
|  | `recipes.craft.*` |  | Allow crafting any recipe. |
|  | `recipes.craft.allow_default` |  | Fallback allow. |
|  | `recipes.craft.bypass` |  | Bypass all crafting checks. |

### Items
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Use** | `use.<ns>.<path>` | `use.minecraft.bucket`, `use.minecraft.flint_and_steel` | Allow using a specific item. |
|  | `use.<ns>.*` | `use.create.*` | Allow using any item from `<ns>`. |
|  | `use.*` |  | Allow using any item. |
|  | `use.allow_default` |  | Fallback allow. |
|  | `use.bypass` |  | Bypass all item-use checks. |

### Interactions (Blocks / Containers / etc.)
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Interact** | `interact.<ns>.<path>` | `interact.minecraft.chest`, `interact.minecraft.oak_door` | Allow interacting with a specific **block** (right-click). |
|  | `interact.<ns>.*` | `interact.minecraft.*` | Allow interacting with any block from `<ns>`. |
|  | `interact.*` |  | Allow interacting with any block. |
|  | `interact.tag.<ns>.<tag>` | `interact.tag.minecraft.doors` | Allow interaction by block tag (e.g., doors, trapdoors). |
|  | `interact.allow_default` |  | Fallback allow. |
|  | `interact.bypass` |  | Bypass all interaction checks. |

### Portals
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Portal Use** | `portal.<ns>.<path>` | `portal.minecraft.the_nether`, `portal.minecraft.the_end` | Allow using a specific portal/dimension entry. |
|  | `portal.<ns>.*` | `portal.minecraft.*` | Allow using all portals from `<ns>`. |
|  | `portal.*` |  | Allow using any portal. |
|  | `portal.allow_default` |  | Fallback allow. |
|  | `portal.bypass` |  | Bypass all portal checks. |

### Ignite
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Ignite** | `ignite.tnt` |  | Allow priming TNT (player-caused). |
|  | `ignite.fire` |  | Allow starting fires (if separated from `use.*`). |
|  | `ignite.*` |  | Allow all ignite actions. |
|  | `ignite.allow_default` |  | Fallback allow. |
|  | `ignite.bypass` |  | Bypass all ignite checks. |

### PvP
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Player Combat** | `pvp.attack_players` |  | Allow damaging other **players** (melee/projectiles/etc.). |
|  | `pvp.*` |  | All PvP permissions. |
|  | `pvp.allow_default` |  | Fallback allow. |
|  | `pvp.bypass` |  | Bypass all PvP checks. |

### Animals
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Interact** | `animals.breed` |  | Allow breeding animals. |
|  | `animals.shear` |  | Allow shearing. |
|  | `animals.milk` |  | Allow milking (e.g., cows). |
|  | `animals.lead` |  | Allow attaching leads. |
|  | `animals.name_tag` |  | Allow applying name tags. |
|  | `animals.*` |  | All animal interactions. |
|  | `animals.allow_default` |  | Fallback allow. |
|  | `animals.bypass` |  | Bypass all animal checks. |
| **Attack** | `animals.attack.<ns>.<path>` | `animals.attack.minecraft.cow` | Allow damaging a specific animal entity type. |
|  | `animals.attack.<ns>.*` | `animals.attack.minecraft.*` | Allow damaging any animal from `<ns>`. |
|  | `animals.attack.*` |  | Allow damaging any animal. |

### Monsters
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Attack** | `monsters.attack.<ns>.<path>` | `monsters.attack.minecraft.creeper` | Allow damaging a specific hostile mob. |
|  | `monsters.attack.<ns>.*` | `monsters.attack.minecraft.*` | Allow damaging any hostile mob from `<ns>`. |
|  | `monsters.attack.*` |  | Allow damaging any hostile mob. |
|  | `monsters.allow_default` |  | Fallback allow. |
|  | `monsters.bypass` |  | Bypass all monster checks. |

### Villagers (Trading & Protection)
| Category | Permission Pattern | Example(s) | Effect / Description |
|---|---|---|---|
| **Trading / Interaction** | `interact.villager.<ns>.<path>` | `interact.villager.minecraft.villager`, `interact.villager.minecraft.wandering_trader` | Allow opening/trading with a specific villager-type entity. |
|  | `interact.villager.<ns>.*` | `interact.villager.minecraft.*` | Allow interaction with all villager-type entities from `<ns>`. |
|  | `interact.villager.*` |  | Allow all villager interactions. |
| **Attack** | `villagers.attack.<ns>.<path>` | `villagers.attack.minecraft.villager` | Allow damaging a specific villager-type entity. |
|  | `villagers.attack.<ns>.*` | `villagers.attack.minecraft.*` | Allow damaging any villager-type entity from `<ns>`. |
|  | `villagers.attack.*` |  | Allow damaging any villager-type entity. |
|  | `villagers.allow_default` |  | Fallback allow (villager attack). |
|  | `villagers.bypass` |  | Bypass all villager attack checks. |
