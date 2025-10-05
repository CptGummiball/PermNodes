# 🧩 PermNodes

**A modular permission management mod for NeoForge 1.21.8+**  
Author: **cptgummiball** • License: **MIT**

---

## 📖 Overview

**PermNodes** is a lightweight, server-side permission control mod for **NeoForge 21.8.46+**.  
It integrates with **LuckPerms** (optional) to allow granular control over player actions such as block placement, breaking, crafting, interactions, PvP, animals, crops, and more.

> 💡 Ideal for modded servers, roleplay setups, or controlled community environments.

---

## ⚙️ Features

- 🔒 **LuckPerms Integration** (optional – graceful fallback if not present)  
- 🧱 **Blocks:** fine-grained place / break permissions with tag support  
- 🧰 **Recipes:** restrict crafting outputs  
- 🎒 **Items:** `use.*` permissions for item usage  
- 🚪 **Interactions:** `interact.*` for chests, doors, buttons, levers, anvils, etc.  
- 🔥 **Ignition:** `ignite.*` for TNT and fire  
- 🌀 **Portals:** `portal.*` for dimension access  
- ⚔️ **PvP:** `pvp.attack_players` and global toggles  
- 🐄 **Animals:** breed, milk, shear, lead, name_tag  
- 🌾 **Crops:** harvest, trample, seed  
- 🌊 **Fluids:** pickup and place control  
- 👾 **Monsters:** restrict attacking hostile mobs  
- 🧩 **Mod-wide bypass:** `mod.allow.<modid>`  
- 💬 Custom in-game denial messages  

---

## 🔧 Installation

1. **Requirements**
   - Minecraft **1.21.8+**
   - **NeoForge 21.8.46+**
   - (optional) **LuckPerms** mod

2. **Install**
   - Download the file `permmod-x.x.x.jar`
   - Place it in your server’s `mods/` directory

3. **Run**
   - Start the server and verify the log:
     ```
     [PermNodes] Mod loaded successfully.
     ```

---

## 🔑 Permission Nodes

| Category | Examples |
|-----------|-----------|
| **Blocks** | `blocks.place.minecraft.dirt`, `blocks.break.tag.minecraft.logs` |
| **Items** | `use.minecraft.bucket`, `use.*` |
| **Interactions** | `interact.minecraft.chest`, `interact.*` |
| **Recipes** | `recipes.craft.minecraft.torch`, `recipes.craft.*` |
| **Portals** | `portal.minecraft.the_nether` |
| **PvP** | `pvp.attack_players` |
| **Animals** | `animals.breed`, `animals.milk` |
| **Crops** | `crops.harvest`, `crops.trample` |
| **Fluids** | `fluids.place`, `fluids.pickup` |
| **Monsters** | `monsters.attack.minecraft.creeper` |
| **Mods (global)** | `mod.allow.minecraft`, `mod.allow.create` |

> 🟢 LuckPerms handles wildcards automatically.  
> 🟡 Without LuckPerms, `.allow_default` nodes act as fallbacks.

---

## 🧩 Development Setup

**Gradle Example:**
```groovy
repositories {
    maven { url = "https://maven.neoforged.net/releases" }
    maven { url = "https://repo.lucko.me/" }
}

dependencies {
    implementation fg.deobf("net.neoforged:neoforge:21.8.46")
    compileOnly fg.deobf("net.luckperms:api:5.4")
}
```

---

## 📜 License

This project is licensed under the **MIT License**.  
See [LICENSE](./LICENSE) for details.

---

## 🤝 Contributing

Issues, suggestions, and pull requests are welcome!  
Please open them on the GitHub repository.

---

### ❤️ Credits

- **cptgummiball** – Development, design & documentation  
- **Lucko & Contributors** – LuckPerms API  
- **NeoForge Team** – Modding framework  

---

> _"Simple structure, deep control."_ – **PermNodes**
