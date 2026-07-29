package net.minecraft.network.packet;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet0KeepAlive;
import net.minecraft.network.packet.Packet100OpenWindow;
import net.minecraft.network.packet.Packet101CloseWindow;
import net.minecraft.network.packet.Packet102WindowClick;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.network.packet.Packet104WindowItems;
import net.minecraft.network.packet.Packet105UpdateProgressbar;
import net.minecraft.network.packet.Packet106Transaction;
import net.minecraft.network.packet.Packet107CreativeSetSlot;
import net.minecraft.network.packet.Packet108EnchantItem;
import net.minecraft.network.packet.Packet10Flying;
import net.minecraft.network.packet.Packet130UpdateSign;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet133TileEditorOpen;
import net.minecraft.network.packet.Packet14BlockDig;
import net.minecraft.network.packet.Packet15Place;
import net.minecraft.network.packet.Packet16BlockItemSwitch;
import net.minecraft.network.packet.Packet17Sleep;
import net.minecraft.network.packet.Packet18Animation;
import net.minecraft.network.packet.Packet19EntityAction;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet200Statistic;
import net.minecraft.network.packet.Packet201PlayerInfo;
import net.minecraft.network.packet.Packet202PlayerAbilities;
import net.minecraft.network.packet.Packet203AutoComplete;
import net.minecraft.network.packet.Packet204ClientInfo;
import net.minecraft.network.packet.Packet205ClientCommand;
import net.minecraft.network.packet.Packet206SetObjective;
import net.minecraft.network.packet.Packet207SetScore;
import net.minecraft.network.packet.Packet208SetDisplayObjective;
import net.minecraft.network.packet.Packet209SetPlayerTeam;
import net.minecraft.network.packet.Packet20NamedEntitySpawn;
import net.minecraft.network.packet.Packet22Collect;
import net.minecraft.network.packet.Packet23VehicleSpawn;
import net.minecraft.network.packet.Packet24MobSpawn;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.network.packet.Packet252SharedKey;
import net.minecraft.network.packet.Packet253ServerAuthData;
import net.minecraft.network.packet.Packet254ServerPing;
import net.minecraft.network.packet.Packet255KickDisconnect;
import net.minecraft.network.packet.Packet25EntityPainting;
import net.minecraft.network.packet.Packet26EntityExpOrb;
import net.minecraft.network.packet.Packet27PlayerInput;
import net.minecraft.network.packet.Packet28EntityVelocity;
import net.minecraft.network.packet.Packet29DestroyEntity;
import net.minecraft.network.packet.Packet2ClientProtocol;
import net.minecraft.network.packet.Packet30Entity;
import net.minecraft.network.packet.Packet34EntityTeleport;
import net.minecraft.network.packet.Packet35EntityHeadRotation;
import net.minecraft.network.packet.Packet38EntityStatus;
import net.minecraft.network.packet.Packet39AttachEntity;
import net.minecraft.network.packet.Packet3Chat;
import net.minecraft.network.packet.Packet40EntityMetadata;
import net.minecraft.network.packet.Packet41EntityEffect;
import net.minecraft.network.packet.Packet42RemoveEntityEffect;
import net.minecraft.network.packet.Packet43Experience;
import net.minecraft.network.packet.Packet44UpdateAttributes;
import net.minecraft.network.packet.Packet4UpdateTime;
import net.minecraft.network.packet.Packet51MapChunk;
import net.minecraft.network.packet.Packet52MultiBlockChange;
import net.minecraft.network.packet.Packet53BlockChange;
import net.minecraft.network.packet.Packet54PlayNoteBlock;
import net.minecraft.network.packet.Packet55BlockDestroy;
import net.minecraft.network.packet.Packet56MapChunks;
import net.minecraft.network.packet.Packet5PlayerInventory;
import net.minecraft.network.packet.Packet60Explosion;
import net.minecraft.network.packet.Packet61DoorChange;
import net.minecraft.network.packet.Packet62LevelSound;
import net.minecraft.network.packet.Packet63WorldParticles;
import net.minecraft.network.packet.Packet6SpawnPosition;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.network.packet.Packet71Weather;
import net.minecraft.network.packet.Packet7UseEntity;
import net.minecraft.network.packet.Packet8UpdateHealth;
import net.minecraft.network.packet.Packet9Respawn;

public abstract class NetHandler {

   public abstract boolean isServerHandler();

   public void handleMapChunk(Packet51MapChunk par1Packet51MapChunk) {}

   public void unexpectedPacket(Packet par1Packet) {}

   public void handleErrorMessage(String par1Str, Object[] par2ArrayOfObj) {}

   public void handleKickDisconnect(Packet255KickDisconnect par1Packet255KickDisconnect) {
      this.unexpectedPacket(par1Packet255KickDisconnect);
   }

   public void handleLogin(Packet1Login par1Packet1Login) {
      this.unexpectedPacket(par1Packet1Login);
   }

   public void handleFlying(Packet10Flying par1Packet10Flying) {
      this.unexpectedPacket(par1Packet10Flying);
   }

   public void handleMultiBlockChange(Packet52MultiBlockChange par1Packet52MultiBlockChange) {
      this.unexpectedPacket(par1Packet52MultiBlockChange);
   }

   public void handleBlockDig(Packet14BlockDig par1Packet14BlockDig) {
      this.unexpectedPacket(par1Packet14BlockDig);
   }

   public void handleBlockChange(Packet53BlockChange par1Packet53BlockChange) {
      this.unexpectedPacket(par1Packet53BlockChange);
   }

   public void handleNamedEntitySpawn(Packet20NamedEntitySpawn par1Packet20NamedEntitySpawn) {
      this.unexpectedPacket(par1Packet20NamedEntitySpawn);
   }

   public void handleEntity(Packet30Entity par1Packet30Entity) {
      this.unexpectedPacket(par1Packet30Entity);
   }

   public void handleEntityTeleport(Packet34EntityTeleport par1Packet34EntityTeleport) {
      this.unexpectedPacket(par1Packet34EntityTeleport);
   }

   public void handlePlace(Packet15Place par1Packet15Place) {
      this.unexpectedPacket(par1Packet15Place);
   }

   public void handleBlockItemSwitch(Packet16BlockItemSwitch par1Packet16BlockItemSwitch) {
      this.unexpectedPacket(par1Packet16BlockItemSwitch);
   }

   public void handleDestroyEntity(Packet29DestroyEntity par1Packet29DestroyEntity) {
      this.unexpectedPacket(par1Packet29DestroyEntity);
   }

   public void handleCollect(Packet22Collect par1Packet22Collect) {
      this.unexpectedPacket(par1Packet22Collect);
   }

   public void handleChat(Packet3Chat par1Packet3Chat) {
      this.unexpectedPacket(par1Packet3Chat);
   }

   public void handleVehicleSpawn(Packet23VehicleSpawn par1Packet23VehicleSpawn) {
      this.unexpectedPacket(par1Packet23VehicleSpawn);
   }

   public void handleAnimation(Packet18Animation par1Packet18Animation) {
      this.unexpectedPacket(par1Packet18Animation);
   }

   public void handleEntityAction(Packet19EntityAction par1Packet19EntityAction) {
      this.unexpectedPacket(par1Packet19EntityAction);
   }

   public void handleClientProtocol(Packet2ClientProtocol par1Packet2ClientProtocol) {
      this.unexpectedPacket(par1Packet2ClientProtocol);
   }

   public void handleServerAuthData(Packet253ServerAuthData par1Packet253ServerAuthData) {
      this.unexpectedPacket(par1Packet253ServerAuthData);
   }

   public void handleSharedKey(Packet252SharedKey par1Packet252SharedKey) {
      this.unexpectedPacket(par1Packet252SharedKey);
   }

   public void handleMobSpawn(Packet24MobSpawn par1Packet24MobSpawn) {
      this.unexpectedPacket(par1Packet24MobSpawn);
   }

   public void handleUpdateTime(Packet4UpdateTime par1Packet4UpdateTime) {
      this.unexpectedPacket(par1Packet4UpdateTime);
   }

   public void handleSpawnPosition(Packet6SpawnPosition par1Packet6SpawnPosition) {
      this.unexpectedPacket(par1Packet6SpawnPosition);
   }

   public void handleEntityVelocity(Packet28EntityVelocity par1Packet28EntityVelocity) {
      this.unexpectedPacket(par1Packet28EntityVelocity);
   }

   public void handleEntityMetadata(Packet40EntityMetadata par1Packet40EntityMetadata) {
      this.unexpectedPacket(par1Packet40EntityMetadata);
   }

   public void handleAttachEntity(Packet39AttachEntity par1Packet39AttachEntity) {
      this.unexpectedPacket(par1Packet39AttachEntity);
   }

   public void handleUseEntity(Packet7UseEntity par1Packet7UseEntity) {
      this.unexpectedPacket(par1Packet7UseEntity);
   }

   public void handleEntityStatus(Packet38EntityStatus par1Packet38EntityStatus) {
      this.unexpectedPacket(par1Packet38EntityStatus);
   }

   public void handleUpdateHealth(Packet8UpdateHealth par1Packet8UpdateHealth) {
      this.unexpectedPacket(par1Packet8UpdateHealth);
   }

   public void handleRespawn(Packet9Respawn par1Packet9Respawn) {
      this.unexpectedPacket(par1Packet9Respawn);
   }

   public void handleExplosion(Packet60Explosion par1Packet60Explosion) {
      this.unexpectedPacket(par1Packet60Explosion);
   }

   public void handleOpenWindow(Packet100OpenWindow par1Packet100OpenWindow) {
      this.unexpectedPacket(par1Packet100OpenWindow);
   }

   public void handleCloseWindow(Packet101CloseWindow par1Packet101CloseWindow) {
      this.unexpectedPacket(par1Packet101CloseWindow);
   }

   public void handleWindowClick(Packet102WindowClick par1Packet102WindowClick) {
      this.unexpectedPacket(par1Packet102WindowClick);
   }

   public void handleSetSlot(Packet103SetSlot par1Packet103SetSlot) {
      this.unexpectedPacket(par1Packet103SetSlot);
   }

   public void handleWindowItems(Packet104WindowItems par1Packet104WindowItems) {
      this.unexpectedPacket(par1Packet104WindowItems);
   }

   public void handleUpdateSign(Packet130UpdateSign par1Packet130UpdateSign) {
      this.unexpectedPacket(par1Packet130UpdateSign);
   }

   public void handleUpdateProgressbar(Packet105UpdateProgressbar par1Packet105UpdateProgressbar) {
      this.unexpectedPacket(par1Packet105UpdateProgressbar);
   }

   public void handlePlayerInventory(Packet5PlayerInventory par1Packet5PlayerInventory) {
      this.unexpectedPacket(par1Packet5PlayerInventory);
   }

   public void handleTransaction(Packet106Transaction par1Packet106Transaction) {
      this.unexpectedPacket(par1Packet106Transaction);
   }

   public void handleEntityPainting(Packet25EntityPainting par1Packet25EntityPainting) {
      this.unexpectedPacket(par1Packet25EntityPainting);
   }

   public void handleBlockEvent(Packet54PlayNoteBlock par1Packet54PlayNoteBlock) {
      this.unexpectedPacket(par1Packet54PlayNoteBlock);
   }

   public void handleStatistic(Packet200Statistic par1Packet200Statistic) {
      this.unexpectedPacket(par1Packet200Statistic);
   }

   public void handleSleep(Packet17Sleep par1Packet17Sleep) {
      this.unexpectedPacket(par1Packet17Sleep);
   }

   public void func_110774_a(Packet27PlayerInput par1Packet27PlayerInput) {
      this.unexpectedPacket(par1Packet27PlayerInput);
   }

   public void handleGameEvent(Packet70GameEvent par1Packet70GameEvent) {
      this.unexpectedPacket(par1Packet70GameEvent);
   }

   public void handleWeather(Packet71Weather par1Packet71Weather) {
      this.unexpectedPacket(par1Packet71Weather);
   }

   public void handleMapData(Packet131MapData par1Packet131MapData) {
      this.unexpectedPacket(par1Packet131MapData);
   }

   public void handleDoorChange(Packet61DoorChange par1Packet61DoorChange) {
      this.unexpectedPacket(par1Packet61DoorChange);
   }

   public void handleServerPing(Packet254ServerPing par1Packet254ServerPing) {
      this.unexpectedPacket(par1Packet254ServerPing);
   }

   public void handleEntityEffect(Packet41EntityEffect par1Packet41EntityEffect) {
      this.unexpectedPacket(par1Packet41EntityEffect);
   }

   public void handleRemoveEntityEffect(Packet42RemoveEntityEffect par1Packet42RemoveEntityEffect) {
      this.unexpectedPacket(par1Packet42RemoveEntityEffect);
   }

   public void handlePlayerInfo(Packet201PlayerInfo par1Packet201PlayerInfo) {
      this.unexpectedPacket(par1Packet201PlayerInfo);
   }

   public void handleKeepAlive(Packet0KeepAlive par1Packet0KeepAlive) {
      this.unexpectedPacket(par1Packet0KeepAlive);
   }

   public void handleExperience(Packet43Experience par1Packet43Experience) {
      this.unexpectedPacket(par1Packet43Experience);
   }

   public void handleCreativeSetSlot(Packet107CreativeSetSlot par1Packet107CreativeSetSlot) {
      this.unexpectedPacket(par1Packet107CreativeSetSlot);
   }

   public void handleEntityExpOrb(Packet26EntityExpOrb par1Packet26EntityExpOrb) {
      this.unexpectedPacket(par1Packet26EntityExpOrb);
   }

   public void handleEnchantItem(Packet108EnchantItem par1Packet108EnchantItem) {}

   public void handleCustomPayload(Packet250CustomPayload par1Packet250CustomPayload) {}

   public void handleEntityHeadRotation(Packet35EntityHeadRotation par1Packet35EntityHeadRotation) {
      this.unexpectedPacket(par1Packet35EntityHeadRotation);
   }

   public void handleTileEntityData(Packet132TileEntityData par1Packet132TileEntityData) {
      this.unexpectedPacket(par1Packet132TileEntityData);
   }

   public void handlePlayerAbilities(Packet202PlayerAbilities par1Packet202PlayerAbilities) {
      this.unexpectedPacket(par1Packet202PlayerAbilities);
   }

   public void handleAutoComplete(Packet203AutoComplete par1Packet203AutoComplete) {
      this.unexpectedPacket(par1Packet203AutoComplete);
   }

   public void handleClientInfo(Packet204ClientInfo par1Packet204ClientInfo) {
      this.unexpectedPacket(par1Packet204ClientInfo);
   }

   public void handleLevelSound(Packet62LevelSound par1Packet62LevelSound) {
      this.unexpectedPacket(par1Packet62LevelSound);
   }

   public void handleBlockDestroy(Packet55BlockDestroy par1Packet55BlockDestroy) {
      this.unexpectedPacket(par1Packet55BlockDestroy);
   }

   public void handleClientCommand(Packet205ClientCommand par1Packet205ClientCommand) {}

   public void handleMapChunks(Packet56MapChunks par1Packet56MapChunks) {
      this.unexpectedPacket(par1Packet56MapChunks);
   }

   public boolean canProcessPacketsAsync() {
      return false;
   }

   public void handleSetObjective(Packet206SetObjective par1Packet206SetObjective) {
      this.unexpectedPacket(par1Packet206SetObjective);
   }

   public void handleSetScore(Packet207SetScore par1Packet207SetScore) {
      this.unexpectedPacket(par1Packet207SetScore);
   }

   public void handleSetDisplayObjective(Packet208SetDisplayObjective par1Packet208SetDisplayObjective) {
      this.unexpectedPacket(par1Packet208SetDisplayObjective);
   }

   public void handleSetPlayerTeam(Packet209SetPlayerTeam par1Packet209SetPlayerTeam) {
      this.unexpectedPacket(par1Packet209SetPlayerTeam);
   }

   public void handleWorldParticles(Packet63WorldParticles par1Packet63WorldParticles) {
      this.unexpectedPacket(par1Packet63WorldParticles);
   }

   public void func_110773_a(Packet44UpdateAttributes par1Packet44UpdateAttributes) {
      this.unexpectedPacket(par1Packet44UpdateAttributes);
   }

   public void func_142031_a(Packet133TileEditorOpen par1Packet133TileEditorOpen) {}

   public boolean isConnectionClosed() {
      return false;
   }
}
