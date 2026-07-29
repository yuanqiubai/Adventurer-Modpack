package net.minecraft.command;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

public class CommandServerSay extends CommandBase {

   public String getCommandName() {
      return "say";
   }

   public int getRequiredPermissionLevel() {
      return 1;
   }

   public String getCommandUsage(ICommandSender par1ICommandSender) {
      return "commands.say.usage";
   }

   public void processCommand(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      if(par2ArrayOfStr.length > 0 && par2ArrayOfStr[0].length() > 0) {
         String var3 = func_82361_a(par1ICommandSender, par2ArrayOfStr, 0, true);
         MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromTranslationWithSubstitutions("chat.type.announcement", new Object[]{par1ICommandSender.getCommandSenderName(), var3}));
      } else {
         throw new WrongUsageException("commands.say.usage", new Object[0]);
      }
   }

   public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr) {
      return par2ArrayOfStr.length >= 1?getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames()):null;
   }
}
