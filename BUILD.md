# ATcraft-Addon-164 编译与部署指南

> **适用于**: AI 工具（GPT / Copilot / Claude 等）自动化编译此项目

---

## 环境信息

| 项目 | 路径 |
|------|------|
| 项目根目录 | `F:\ATcraft-Addon-164` |
| JDK 8（编译用） | `F:\jdk8\jdk8u492-b09` |
| Gradle 项目 | `F:\ATcraft-Addon-164\forge` |
| 测试 modpack | `C:\Users\Yuanqiubai\Desktop\☆冒险者传说V2.0【制作中】\冒险者传说1.91_测试调试\.minecraft` |

---

## 编译与部署（3 步，缺一不可）

### Step 1: Gradle 编译

```powershell
$env:JAVA_HOME='F:\jdk8\jdk8u492-b09'
cd F:\ATcraft-Addon-164\forge
.\gradlew.bat build
```

产物: `F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0.jar`（MCP 命名，**不可部署**）

### Step 2: SpecialSource 重映射（MCP → SRG）

```powershell
& 'F:\jdk8\jdk8u492-b09\bin\java.exe' `
  -jar 'F:\ATcraft-Addon-164\forge\forge-src\forge\mcp\runtime\bin\specialsource.jar' `
  -i 'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0.jar' `
  -o 'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0-srg.jar' `
  -m 'F:\ATcraft-Addon-164\forge\forge-src\forge\mcp\conf\mcp2srg.srg' `
  -r
```

产物: `F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0-srg.jar`（SRG 命名，**可部署**）

### Step 3: 部署到测试 modpack

```powershell
Copy-Item `
  'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0-srg.jar' `
  'C:\Users\Yuanqiubai\Desktop\☆冒险者传说V2.0【制作中】\冒险者传说1.91_测试调试\.minecraft\mods\ATcraft-Addon-164-1.0-srg.jar' `
  -Force
```

---

## 验证

部署后校验 MD5 一致：

```powershell
(Get-FileHash 'C:\Users\Yuanqiubai\Desktop\☆冒险者传说V2.0【制作中】\冒险者传说1.91_测试调试\.minecraft\mods\ATcraft-Addon-164-1.0-srg.jar' -Algorithm MD5).Hash `
  -eq `
(Get-FileHash 'F:\ATcraft-Addon-164\forge\build\libs\ATcraft-Addon-164-1.0-srg.jar' -Algorithm MD5).Hash
# 必须返回 True
```

---

## 关键规则

1. **必须执行全部 3 步** — 只编译不重映射会导致游戏闪退（`NoSuchFieldError` / `NoSuchMethodError`）
2. **必须用 JDK 8 编译** — 高版本 JDK 产出的 class 文件 MC 1.6.4 的 ASM 4.1 无法加载
3. **部署文件名必须匹配** — mods 目录里已有的文件名是什么就覆盖什么，不要改名字
4. **部署的是 `-srg.jar`，不是普通 `.jar`** — 普通 jar 是 MCP 命名的，部署必闪退

---

## 源码位置

```
F:\ATcraft-Addon-164\forge\src\main\java\atwandaddon\
├── ATcraftAddon.java          # @Mod 主类，物品注册
├── CommonProxy.java
├── client\ClientProxy.java
├── entity\                    # 自定义实体（弹丸等）
├── weapon\
│   ├── gun\                   # 枪械类（extend BasicRangedWeapon）
│   ├── ammo\                  # 弹药类（extend BasicAmmo）
│   ├── sword\                 # 近战武器（extend BasicItemSword）
│   └── nbt\                   # NBT Builder 工具类
└── wand\                      # 法杖类
```

纹理资源: `F:\ATcraft-Addon-164\forge\src\main\resources\assets\adventurer_engine\textures\items\`
