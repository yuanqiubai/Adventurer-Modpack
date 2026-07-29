package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class CommandServerList extends CommandBase {

   public String getCommandName() {
      return "list";
   }

   public int getRequiredPermissionLevel() {
      return 0;
   }

   public String getCommandUsage(ICommandSender par1ICommandSender) {
      return "commands.players.usage";
   }

   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.players.list", new Object[]{Integer.valueOf(MinecraftServer.getServer().getCurrentPlayerCount()), Integer.valueOf(MinecraftServer.getServer().getMaxPlayers())}));
      par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(MinecraftServer.getServer().getConfigurationManager().getPlayerListAsString()));
   }
}
