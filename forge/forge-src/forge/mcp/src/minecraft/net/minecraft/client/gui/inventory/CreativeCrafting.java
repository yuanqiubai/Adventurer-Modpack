package net.minecraft.client.gui.inventory;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

public class CreativeCrafting implements ICrafting {

   private final Minecraft mc;

   public CreativeCrafting(Minecraft par1Minecraft) {
      this.mc = par1Minecraft;
   }

   public void sendContainerAndContentsToPlayer(Container par1Container, List par2List) {}

   public void sendSlotContents(Container par1Container, int par2, ItemStack par3ItemStack) {
      this.mc.playerController.sendSlotPacket(par3ItemStack, par2);
   }

   public void sendProgressBarUpdate(Container par1Container, int par2, int par3) {}
}
