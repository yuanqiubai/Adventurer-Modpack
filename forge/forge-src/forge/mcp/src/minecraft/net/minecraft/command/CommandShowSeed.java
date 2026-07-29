package net.minecraft.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;

public class CommandShowSeed extends CommandBase {

   public boolean canCommandSenderUseCommand(ICommandSender par1ICommandSender) {
      return MinecraftServer.getServer().isSinglePlayer() || super.canCommandSenderUseCommand(par1ICommandSender);
   }

   public String getCommandName() {
      return "seed";
   }

   public int getRequiredPermissionLevel() {
      return 2;
   }

   public String getCommandUsage(ICommandSender par1ICommandSender) {
      return "commands.seed.usage";
   }

   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      Object var3 = par1ICommandSender instanceof EntityPlayer?((EntityPlayer)par1ICommandSender).worldObj:MinecraftServer.getServer().worldServerForDimension(0);
      par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.seed.success", new Object[]{Long.valueOf(((World)var3).getSeed())}));
   }
}
