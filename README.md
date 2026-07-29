# ATcraft-Addon-164

冒险者传说整合包（MC 1.6.4）的武器扩展 Mod，基于 Forge 9.11.1.916。

## 环境要求

| 依赖 | 路径 / 版本 |
|------|-------------|
| JDK（编译用） | `F:\java 11` — OpenJDK 21.0.9 |
| JRE（运行用） | `F:\java` — JRE 1.8.0_411 |
| Forge MDK | `F:\ATcraft-Addon-164\forge\`（未上传到 Git，需自行配置） |
| MCP | Forge MDK 内置 — MCP 8.11 |

> ⚠️ `F:\java` 是 JRE 不能编译。编译、反编译等工具操作一律用 `F:\java 11`。

## 快速开始

### 1. 安装 Forge MDK

下载 Forge 9.11.1.916 MDK，解压到 `forge\` 目录：

```
F:\ATcraft-Addon-164\
├── forge\
│   ├── gradlew.bat
│   ├── forge-src\
│   └── ...
├── atwandaddon\        ← Mod 源码
├── libs\               ← 编译依赖 JAR
└── build\              ← 构建输出
```

### 2. 编译项目

```powershell
cd F:\ATcraft-Addon-164\forge
.\gradlew.bat build
```

构建产物在 `F:\ATcraft-Addon-164\build\ATcraft-Addon-1.6.4.jar`。

### 3. 测试

将 `ATcraft-Addon-1.6.4.jar` 放入整合包的 `.minecraft\mods\` 目录启动游戏。

目标整合包路径：
```
C:\Users\Yuanqiubai\Desktop\☆冒险者传说V2.0【制作中】\☆冒险者传说V1.91_测试调试\
```

## 项目结构

```
atwandaddon/
├── ATcraftAddon.java      # Mod 主入口，物品/实体注册
├── ItemFlameWand.java     # 火焰法杖（ID 25001）
├── CommonProxy.java       # 服务端代理
├── entity/
│   └── EntityFlameBall.java  # 火焰弹实体
└── client/
    └── ClientProxy.java   # 客户端渲染注册

adventurer_engine_src/     # adventurer_engine 反编译参考源码
libs/                      # 编译依赖（未上传 Maven，直接放 JAR）
```

## 物品 ID 分配

| ID | 物品 | 类型 |
|----|------|------|
| 25000 | ammoOrb（弹药宝珠） | 消耗品 |
| 25001 | flameWand（火焰法杖） | 蓄力武器 |

> 注意：adventurer_engine 占用 ID 区间 12001–12142，分配新 ID 时避免冲突。

## 常用工具

| 工具 | 命令 |
|------|------|
| 反编译 JAR | `& "F:\java 11\bin\java.exe" -jar F:\ATcraft-Addon-164\tool\cfr-0.152.jar <input.jar> --outputdir <out/>` |
| 编译 Mod | `cd forge && .\gradlew.bat build` |
| 构建后自动复制到整合包 | 手动：`copy build\ATcraft-Addon-1.6.4.jar "C:\Users\Yuanqiubai\Desktop\☆冒险者传说V2.0【制作中】\☆冒险者传说V1.91_测试调试\.minecraft\mods\"` |

## 参考资源

- `adventurer_engine_src/` — adventurer_engine 反编译源码（武器加载机制、NBT 结构参考）
- `C:\Users\Yuanqiubai\Desktop\1.9071\` — 原始 JAR 文件
- `C:\Users\Yuanqiubai\.copilot\skills\atcraft-modding\SKILL.md` — AI 辅助开发技能文档
