package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.world.World;

public class ItemMapBase extends Item {

   protected ItemMapBase(int par1) {
      super(par1);
   }

   public boolean isMap() {
      return true;
   }

   public Packet createMapDataPacket(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      return null;
   }
}
