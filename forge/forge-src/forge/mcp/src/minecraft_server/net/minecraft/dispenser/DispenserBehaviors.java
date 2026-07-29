package net.minecraft.dispenser;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.DispenserBehaviorArrow;
import net.minecraft.dispenser.DispenserBehaviorBoat;
import net.minecraft.dispenser.DispenserBehaviorDye;
import net.minecraft.dispenser.DispenserBehaviorEgg;
import net.minecraft.dispenser.DispenserBehaviorEmptyBucket;
import net.minecraft.dispenser.DispenserBehaviorExperience;
import net.minecraft.dispenser.DispenserBehaviorFilledBucket;
import net.minecraft.dispenser.DispenserBehaviorFire;
import net.minecraft.dispenser.DispenserBehaviorFireball;
import net.minecraft.dispenser.DispenserBehaviorFireworks;
import net.minecraft.dispenser.DispenserBehaviorMobEgg;
import net.minecraft.dispenser.DispenserBehaviorPotion;
import net.minecraft.dispenser.DispenserBehaviorSnowball;
import net.minecraft.dispenser.DispenserBehaviorTNT;
import net.minecraft.item.Item;

public class DispenserBehaviors {

   public static void registerDispenserBehaviours() {
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.arrow, new DispenserBehaviorArrow());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.egg, new DispenserBehaviorEgg());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.snowball, new DispenserBehaviorSnowball());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.expBottle, new DispenserBehaviorExperience());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.potion, new DispenserBehaviorPotion());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.monsterPlacer, new DispenserBehaviorMobEgg());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.firework, new DispenserBehaviorFireworks());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.fireballCharge, new DispenserBehaviorFireball());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.boat, new DispenserBehaviorBoat());
      DispenserBehaviorFilledBucket var0 = new DispenserBehaviorFilledBucket();
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.bucketLava, var0);
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.bucketWater, var0);
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.bucketEmpty, new DispenserBehaviorEmptyBucket());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.flintAndSteel, new DispenserBehaviorFire());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.dyePowder, new DispenserBehaviorDye());
      BlockDispenser.dispenseBehaviorRegistry.putObject(Item.itemsList[Block.tnt.blockID], new DispenserBehaviorTNT());
   }
}
