package net.minecraft.command;

import net.minecraft.command.ICommandSender;

public interface IAdminCommand {

   void notifyAdmins(ICommandSender var1, int var2, String var3, Object ... var4);
}
