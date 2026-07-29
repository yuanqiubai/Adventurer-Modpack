# ATcraft Addon — MC 1.6.4 Dev Environment Log

> 最后更新: 2026-07-28 08:30
> 工程根目录: `F:\ATcraft-Addon-164\`
> 1.7.10 环境(保留): `F:\ATcraft-Addon\`
> 当前状态: ⚠️ 编译中 — 客户端 38 错误, 服务端 2 错误

---

## 环境概要

| 项目 | 值 |
|------|-----|
| Minecraft 版本 | 1.6.4 |
| Forge 版本 | 9.11.1.1345 (最新) |
| 整合包 Forge | 9.11.1.916 |
| MCP 版本 | 8.11 (mcp811.zip, 手动下载) |
| JDK | `F:\jdk8\jdk8u492-b09\` (JDK 8) |
| 目标整合包 | `☆冒险者传说V1.91` |

## 目录结构

```
F:\ATcraft-Addon-164\
  forge\
    build.gradle              # 手动编写的构建脚本 (未启用)
    forge-universal-1.6.4-9.11.1.1345.jar
    forge-installer.jar
    forge-src.zip             # Forge 源码包
    minecraft_server.1.6.4.jar
    libraries\                # Scala, launchwrapper 等 (来自 installer)
    forge-src\
      forge\
        fml\
          fml.py              # ⚠️ 修改过 (修复 3 个 Bug)
          mc_versions.cfg     # ⚠️ 修改过 (添加 1.6.4 Mojang CDN URL)
          install.py
          mcp811.zip          # 用户手动下载的 MCP 8.11
        mcp\                  # MCP 解压目录
          runtime\            # MCP 运行时 (Python 2.7 工具链)
          jars\
            bin\
              minecraft.jar   # 1.6.4 客户端 (已下载, 4.7MB)
              lwjgl.jar       # LWJGL 2.9.0
              lwjgl_util.jar
              jinput.jar
              windows_natives.jar
            minecraft_server.jar  # 1.6.4 服务端
            versions\1.6.4\1.6.4.json  # ⚠️ 手动创建
          src\                # ⚠️ 部分反编译产物 (211 Java 文件)
    src\main\java\atwandaddon\   # 我们的 addon 源码 (1.6.4 API)
```

## fml.py 修复记录

修复了 3 个 Bug 才能让 Forge 1.6.4 的 `install.py` 在现代环境下运行:

### Bug 1 — `mc_info['libraries']` 未初始化 (KeyError)
- **位置**: `fml.py` 第 102 行 (原)
- **原因**: `read_mc_versions()` 中，当 `client_url` 在 `mc_versions.cfg` 中存在时，走 `else` 分支（旧启动器路径），但该分支未初始化 `mc_info['libraries']` 列表就直接 `.append()`
- **修复**: 在第 93 行后添加 `mc_info['libraries'] = []`

### Bug 2 — `download_list()` 参数数量不匹配 (TypeError)
- **位置**: `fml.py` 第 1145 行
- **原因**: `download_list(list, natives_dir)` 需要 2 个参数，但调用时只传了 1 个 (`download_list(mc_info['downloads'])`)
- **修复**: 改为 `download_list(mc_info['downloads'], mc_info['natives_dir'])`

### Bug 3 — `download_list()` 空列表返回 False 导致 `failed = True` (逻辑错误)
- **位置**: `fml.py` 第 1145 行
- **原因**: `download_list()` 在列表为空时返回 `False`（表示无失败），但原代码用 `not download_list(...)` 反转了逻辑
- **修复**: 去掉 `not`，改为 `failed = download_list(...) or failed`

## mc_versions.cfg 修改

```ini
[1.6.4]
client_url = https://launcher.mojang.com/v1/objects/1703704407101cf72bd88e68579e3696ce733ecd/client.jar
server_url = https://launcher.mojang.com/v1/objects/050f93c1f3fe9e2052398f7bd6aca10c63d64a87/server.jar
client_md5 = 2e5044f5359e82245551167a237f3167
server_md5 = abcf286a14f7aee82e8bf89270433509
mcp_ver    = 8.11
mcp_url    = 
mcp_md5    = 
libraries  = lwjgl.jar lwjgl_util.jar jinput.jar
natives    = windows_natives.jar
```

- `client_url`/`server_url` 指向新 Mojang CDN（旧 s3.amazonaws.com 已挂）
- `mcp_url`/`mcp_md5` 留空（MCP 已手动解压）
- MD5 已填入实际值（否则 `file_backup` 会删除 jar）
- `libraries` 和 `natives` 必须声明（否则 `read_mc_versions` 中 KeyError）

## MCP 构建流程

```
install.py → fml_main()
  ├─ download_mcp()       → 检查 MCP 是否已就绪
  ├─ setup_mcp()          → 打补丁到 commands.py, 复制 FML conf, 重新打包 SRG/EXC
  └─ decompile_minecraft()
       ├─ pre_decompile() → download_minecraft() → 下载/验证 jars
       ├─ cleanup()       → 清除旧的 src/bin/reobf (如果 src 存在)
       ├─ MCP decompile   → 反编译 jar → src/minecraft/
       ├─ apply_fml_patches() → 给反编译代码打 Forge 补丁
       └─ finish_setup_fml()  → updatenames, updatemd5
```

## ✅ 已完成步骤

### 1. MCP 8.11 解压与配置
- 用户手动下载 mcp811.zip
- 手动解压到 `forge\fml\` 目录
- 创建完整 `1.6.4.json`（16 个 library，BOM-stripped）
- 修复 `fml.py` 5 个 Bug（见上方 Bug 1-3 + Bug 4-5）
- 修复 `commands.py` jar 路径 + `normalStart = True`
- 修复 `cleanup.py` 移除交互式提示
- 添加 Mojang CDN URLs 到 `mc_versions.cfg`

### 2. 反编译成功
- MC 1.6.4 客户端反编译: 20.84s ✅
- MC 1.6.4 服务端反编译: 14.19s ✅
- 14 个 Mojang library jar 下载完成
- 4 个 Forge library jar 复制到 `lib/`

### 3. Forge 补丁部分应用
- Forge fml patches 应用到反编译源码
- **问题**: ~50 个补丁块失败 — Forge 1.6.4 补丁是为 JAD 反编译器写的，我们用的是 Fernflower
- 失败导致许多 vanilla 文件缺少 import 和部分 Forge 代码

### 4. 已修复的编译错误（✅ 已解决）
| 文件 | 修复内容 |
|------|---------|
| WorldInfo.java | 添加 `import java.util.Map;` 和 `import net.minecraft.nbt.NBTBase;` |
| WorldType.java | 添加 `Random`, `BiomeGenBase`, `WorldChunkManager`, `IChunkProvider` 导入 |
| CallableMinecraftVersion.java | 类 + 构造器改为 `public` |
| Item.java | 构造器 `Item(int)` 和 `setTextureName()` 改为 `public`；添加 `GameData`, `Side`, `SideOnly` 导入 |
| CryptManager.java | 添加 `import cpw.mods.fml.common.asm.ReobfuscationMarker;` |
| ATcraftAddon.java | `CreativeTabs(12, "ATcraftAddon")` (1.6.4 需要显式 ID) |
| ItemFlameWand.java | `hasItem(itemID)`, `consumeInventoryItem(itemID)` (1.6.4 用 int) |
| Loader.java | 重新添加 `CallableMinecraftVersion` 导入 |

### 5. 编译状态（当前）
- **客户端**: 38 错误 / 100 警告 (多为 deprecation)
- **服务端**: 2 错误 / 1 警告
- 完整日志: `F:\ATcraft-Addon-164\recompile_log.txt`

---

## ⚠️ 当前阻塞 — 编译错误分析

### 根本原因
Forge 1.6.4 的补丁文件（`fml/patches/minecraft/` 下 55+ 个 `.java.patch`）是针对 **JAD** 反编译器输出编写的。MCP 8.11 使用 **Fernflower** 反编译器，输出的局部变量名、方法名与 JAD 不同，导致 `patch` 命令无法匹配上下文，大量补丁块失败。

### 剩余错误分类（客户端 38 个）

| 类别 | 文件 | 错误数 | 典型问题 |
|------|------|--------|---------|
| 缺少 Forge import | EntityPlayer.java | 3 | `FMLCommonHandler`, `FMLNetworkHandler` 未导入 |
| 缺少 Forge import | CrashReport.java | 1 | `FMLCommonHandler` 未导入 |
| 补丁失败 - 字段缺失 | WorldType.java | ~10 | `base11Biomes`, `base12Biomes`, `FlatGeneratorInfo` 等 |
| 补丁失败 - 方法缺失 | Minecraft.java | ~14 | 多个 Forge 新增方法/字段引用失败 |
| 补丁失败 - 方法缺失 | MinecraftServer.java | ~7 | FML 钩子方法不存在 |
| Forge 内部 API | EntityRegistry.java | ~12 | EntityList 字段访问权限 |
| Forge 内部 | FMLNetworkHandler.java | ~9 | 网络相关 API |
| Forge 内部 | FMLClientHandler.java | 1 | entityId 相关 |
| Forge GUI | GuiModList.java, GuiScrollingList.java | 3 | renderEngine 相关 |
| 网络层 | MemoryConnection.java, TcpConnection.java | 3 | 网络管道 API |
| 其他 | ChatMessageComponent, GameSettings, LoadingScreen, IntegratedServer, RenderBlocks | ~6 | 各类型错误 |

### 服务端错误（2 个）
| 文件 | 问题 |
|------|------|
| ChatMessageComponent.java:256 | varargs 类型不匹配 |
| MapGenScatteredFeature.java:107,109 | `field_75075_a` protected 访问 |

---

## 🤔 决策点 — 两条路线

### 路线 A: 继续逐个修复
- 优势: 不改变现有流程，每修复一个文件就少几个错误
- 劣势: WorldType / Minecraft / MinecraftServer 等大文件每个有 7-14 个错误，完全手动修复风险高
- 预估: 还需修复 ~20 个文件的 ~38 个错误

### 路线 B: 从 Forge 通用 jar 反编译
- 优势: Forge 通用 jar 已包含所有正确应用的补丁，反编译后直接就是完整源码
- 劣势: 需要重置部分 MCP 流程，反编译后的方法名可能仍是 SRG 名
- 关键文件: `forge\minecraftforge-universal-1.6.4-9.11.1.1345.jar`（已有）

### ⚡ 路线 C: 只编译 Addon，不重编译 Minecraft
- 优势: 跳过所有 vanilla + Forge 错误，用 pre-built Forge jar 作为依赖
- 劣势: 需要 deobfuscation 步骤让 addon 能调用 Forge API
- 这是最省事的方案

> **待用户决定下一步方向**

---

## 重要文件索引

| 文件 | 用途 | 状态 |
|------|------|------|
| `forge\forge-src\forge\fml\fml.py` | Forge 安装脚本 | ⚠️ 修改过 (6 处修复) |
| `forge\forge-src\forge\fml\mc_versions.cfg` | MC 版本配置 | ⚠️ 修改过 |
| `forge\forge-src\forge\mcp\runtime\commands.py` | MCP 命令入口 | ⚠️ 修改过 (2 处) |
| `forge\forge-src\forge\mcp\runtime\cleanup.py` | MCP 清理脚本 | ⚠️ 修改过 |
| `forge\forge-src\forge\mcp\jars\versions\1.6.4\1.6.4.json` | MC 1.6.4 库列表 | ⚠️ 手动替换 (BOM-free) |
| `forge\forge-src\forge\mcp\lib\` | 4 个 Forge 库 jar | ✅ 已就绪 |
| `forge\forge-src\forge\mcp\src\minecraft\atwandaddon\` | 我们的 Addon 源码 | ✅ API 已适配 1.6.4 |
| `forge\forge-src\forge\mcp\src\minecraft\cpw\` | Forge/FML 源码 | ✅ 编译通过 |
| `forge\forge-src\forge\mcp\src\minecraft\net\minecraft\` | MC 源码 (部分补丁) | ❌ 38 编译错误 |
| `forge\minecraftforge-universal-1.6.4-9.11.1.1345.jar` | Forge 通用 jar | 📦 备用 |

## 运行命令

```powershell
# 设置 JDK
$env:JAVA_HOME = "F:\jdk8\jdk8u492-b09"
$env:Path = "$env:JAVA_HOME\bin;$env:Path"

# 从 forge 目录运行 (因为 install.py 使用 os.path.abspath('mcp'))
cd F:\ATcraft-Addon-164\forge\forge-src\forge

# 运行 (需要处理 cleanup 交互)
.\mcp\runtime\bin\python\python_mcp.exe .\fml\install.py --no-assets
```
