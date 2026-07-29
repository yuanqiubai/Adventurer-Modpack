package atwandaddon;

import atwandaddon.entity.EntityFlameBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlameWand extends Item {

    public ItemFlameWand(int _id) {
        super(_id);
        this.setMaxStackSize(1);
        this.setMaxDamage(256);
        this.setFull3D();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack _stack, World _world, EntityPlayer _player) {
        if (_player.capabilities.isCreativeMode || _player.inventory.hasItem(ATcraftAddon.ammoOrb.itemID)) {
            _player.setItemInUse(_stack, this.getMaxItemUseDuration(_stack));
        }
        return _stack;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack _stack, World _world, EntityPlayer _player, int _timeLeft) {
        int charge = this.getMaxItemUseDuration(_stack) - _timeLeft;
        float power = (float) charge / 20.0F;
        power = (power * power + power * 2.0F) / 3.0F;
        if (power < 0.1F) return;
        if (power > 1.0F) power = 1.0F;

        if (!_world.isRemote) {
            _world.playSoundAtEntity(_player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));

            EntityFlameBall ball = new EntityFlameBall(_world, _player);
            ball.setDamage(5.0F + power * 3.0F);
            _world.spawnEntityInWorld(ball);

            if (!_player.capabilities.isCreativeMode) {
                _player.inventory.consumeInventoryItem(ATcraftAddon.ammoOrb.itemID);
            }
            _stack.damageItem(1, _player);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack _stack) {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack _stack) {
        return EnumAction.bow;
    }
}
