package net.minecraft.server.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.MinecraftServerGui;

class MinecraftServerGuiINNER2 implements ActionListener {

   // $FF: synthetic field
   final JTextField field_120025_a;
   // $FF: synthetic field
   final MinecraftServerGui field_120024_b;

   MinecraftServerGuiINNER2(MinecraftServerGui par1MinecraftServerGui, JTextField par2JTextField) {
      this.field_120024_b = par1MinecraftServerGui;
      this.field_120025_a = par2JTextField;
   }

   public void actionPerformed(ActionEvent par1ActionEvent) {
      String var2 = this.field_120025_a.getText().trim();
      if(var2.length() > 0) {
         MinecraftServerGui.func_120017_a(this.field_120024_b).addPendingCommand(var2, MinecraftServer.getServer());
      }

      this.field_120025_a.setText("");
   }
}
