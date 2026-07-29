package net.minecraft.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemCoal extends Item {

   private Icon field_111220_a;

   public ItemCoal(int par1) {
      super(par1);
      this.setHasSubtypes(true);
      this.setMaxDamage(0);
      this.setCreativeTab(CreativeTabs.tabMaterials);
   }

   public String getUnlocalizedName(ItemStack par1ItemStack) {
      return par1ItemStack.getItemDamage() == 1?"item.charcoal":"item.coal";
   }

   public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
      par3List.add(new ItemStack(par1, 1, 0));
      par3List.add(new ItemStack(par1, 1, 1));
   }

   public Icon getIconFromDamage(int par1) {
      return par1 == 1?this.field_111220_a:super.getIconFromDamage(par1);
   }

   public void registerIcons(IconRegister par1IconRegister) {
      super.registerIcons(par1IconRegister);
      this.field_111220_a = par1IconRegister.registerIcon("charcoal");
   }
}
