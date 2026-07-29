package net.minecraft.command;

import net.minecraft.command.SyntaxErrorException;

public class WrongUsageException extends SyntaxErrorException {

   public WrongUsageException(String par1Str, Object ... par2ArrayOfObj) {
      super(par1Str, par2ArrayOfObj);
   }
}
