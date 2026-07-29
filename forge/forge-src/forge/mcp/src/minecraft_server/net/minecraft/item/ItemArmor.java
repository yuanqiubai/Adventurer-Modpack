package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BehaviorDispenseArmor;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemArmor extends Item {

   private static final int[] maxDamageArray = new int[]{11, 16, 15, 13};
   private static final String[] field_94606_cu = new String[]{"leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay"};
   public static final String[] field_94603_a = new String[]{"empty_armor_slot_helmet", "empty_armor_slot_chestplate", "empty_armor_slot_leggings", "empty_armor_slot_boots"};
   private static final IBehaviorDispenseItem field_96605_cw = new BehaviorDispenseArmor();
   public final int armorType;
   public final int damageReduceAmount;
   public final int renderIndex;
   private final EnumArmorMaterial material;

   public ItemArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4) {
      super(par1);
      this.material = par2EnumArmorMaterial;
      this.armorType = par4;
      this.renderIndex = par3;
      this.damageReduceAmount = par2EnumArmorMaterial.getDamageReductionAmount(par4);
      this.setMaxDamage(par2EnumArmorMaterial.getDurability(par4));
      this.maxStackSize = 1;
      this.setCreativeTab(CreativeTabs.tabCombat);
      BlockDispenser.dispenseBehaviorRegistry.putObject(this, field_96605_cw);
   }

   public int getItemEnchantability() {
      return this.material.getEnchantability();
   }

   public EnumArmorMaterial getArmorMaterial() {
      return this.material;
   }

   public boolean hasColor(ItemStack par1ItemStack) {
      return this.material != EnumArmorMaterial.CLOTH?false:(!par1ItemStack.hasTagCompound()?false:(!par1ItemStack.getTagCompound().hasKey("display")?false:par1ItemStack.getTagCompound().getCompoundTag("display").hasKey("color")));
   }

   public int getColor(ItemStack par1ItemStack) {
      if(this.material != EnumArmorMaterial.CLOTH) {
         return -1;
      } else {
         NBTTagCompound var2 = par1ItemStack.getTagCompound();
         if(var2 == null) {
            return 10511680;
         } else {
            NBTTagCompound var3 = var2.getCompoundTag("display");
            return var3 == null?10511680:(var3.hasKey("color")?var3.getInteger("color"):10511680);
         }
      }
   }

   public void removeColor(ItemStack par1ItemStack) {
      if(this.material == EnumArmorMaterial.CLOTH) {
         NBTTagCompound var2 = par1ItemStack.getTagCompound();
         if(var2 != null) {
            NBTTagCompound var3 = var2.getCompoundTag("display");
            if(var3.hasKey("color")) {
               var3.removeTag("color");
            }

         }
      }
   }

   public void func_82813_b(ItemStack par1ItemStack, int par2) {
      if(this.material != EnumArmorMaterial.CLOTH) {
         throw new UnsupportedOperationException("Can\'t dye non-leather!");
      } else {
         NBTTagCompound var3 = par1ItemStack.getTagCompound();
         if(var3 == null) {
            var3 = new NBTTagCompound();
            par1ItemStack.setTagCompound(var3);
         }

         NBTTagCompound var4 = var3.getCompoundTag("display");
         if(!var3.hasKey("display")) {
            var3.setCompoundTag("display", var4);
         }

         var4.setInteger("color", par2);
      }
   }

   public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
      return this.material.getArmorCraftingMaterial() == par2ItemStack.itemID?true:super.getIsRepairable(par1ItemStack, par2ItemStack);
   }

   public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
      int var4 = EntityLiving.getArmorPosition(par1ItemStack) - 1;
      ItemStack var5 = par3EntityPlayer.getCurrentArmor(var4);
      if(var5 == null) {
         par3EntityPlayer.setCurrentItemOrArmor(var4, par1ItemStack.copy());
         par1ItemStack.stackSize = 0;
      }

      return par1ItemStack;
   }

   // $FF: synthetic method
   static int[] getMaxDamageArray() {
      return maxDamageArray;
   }

}
