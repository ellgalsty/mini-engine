# mini-engine

A modular libGDX-based engine which contains mission page simulation for gear looting, inventory, and stat management.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.
- `android`: Android mobile platform. Needs Android SDK.
- `ios`: iOS mobile platform using RoboVM.

## 🌟 Features

- 🎯 **Mission Page Simulation**  
  Fully functional mission screen UI for gear and stat management.

- 🎖️ **Military Gear Looting**  
  Press **L** to equip new randomized military gear.

- 🐾 **Pet Items**  
  Press **P** to generate random pet gear.

- 🚩 **Flags**  
  Press **F** to generate random flag gear.

- 🧠 **Tactical Items**  
  Press **T** to generate random tactical gear.

- 💾 **Robust Save Data Management**  
  Each gear type (Military, Pet, Flag, Tactical) has a dedicated `GameData` and `SaveData` implementation.

- 📦 **Dialog System**  
  Modular, stylized dialogs for looting, equipping, and inspecting gear.

- 🔔 **Event-Driven Architecture**  
  Uses custom events (e.g., `EquipGearEvent`, `UpdateStatsEvent`) to synchronize UI and logic.

- 📊 **Stat Aggregation Engine**  
  `MissionsManager` calculates player stats using additive and percentage modifiers.

- 🧪 **Data Generator Utility**  
  Easily generates randomized gear and stats for gameplay testing.

- 🧰 **Reusable UI Components**  
  Widget containers and pooled UI components built for performance.

- 🧼 **Clean & Structured Codebase**  
  Modular, readable, and scalable architecture.


## 🛠️ Technologies Used
Java 8+

libGDX

LWJGL3 (for desktop)

Gradle

Pools & JSON serialization (libGDX)

## 🎮 Controls

| Key | Action                                      |
|-----------------|---------------------------------|
| **Loot Button** | Loot new military gear          |
|     **T**       | Add random tactical item        |
|     **P**       | Add random pet item             |
|     **F**       | Add random flag item            | 


## See my UI :)
<img width="544" alt="Screenshot 2025-05-03 at 02 02 08" src="https://github.com/user-attachments/assets/7350848a-7107-4cf9-b0fd-36cd802120d5" />

As you can see, the power logic is not written fully yet, that's why it is 0 now.

## 🔮 Future Plans

Add animation effects for gear looting

Add correct loot drop logic

Add correct power calculation logic

