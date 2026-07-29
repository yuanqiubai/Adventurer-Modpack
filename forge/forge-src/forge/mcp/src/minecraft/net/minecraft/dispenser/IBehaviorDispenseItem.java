package net.minecraft.dispenser;

import net.minecraft.dispenser.BehaviorDispenseItemProvider;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem {

   IBehaviorDispenseItem itemDispenseBehaviorProvider = new BehaviorDispenseItemProvider();

   ItemStack dispense(IBlockSource var1, ItemStack var2);

}
