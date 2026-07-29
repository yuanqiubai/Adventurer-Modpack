package atwandaddon;

import atwandaddon.entity.EntityFlameBall;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * 火焰法杖武器
 * 右键蓄力后发射火焰弹(EntityFlameBall)，消耗弹药宝珠(ATcraftAddon.ammoOrb)和耐久。
 * 蓄力机制模仿原版弓 —— 蓄力越久伤害越高(5.0 ~ 8.0)，在 1 秒(20 tick)达到满蓄力。
 *
 * 职责边界：
 * - 仅负责蓄力计时、弹药消耗、发射弹射物
 * - 弹射物的飞行路径和碰撞逻辑由 EntityFlameBall 处理
 * - 渲染由 ClientProxy 注册的 RenderSnowball 处理
 */
public class ItemFlameWand extends Item {

    /**
     * 构造函数
     * 设置法杖为单物品堆叠、3D 模型渲染、256 点耐久。
     *
     * @param _id 物品数字 ID，由 ATcraftAddon.preInit() 分配(25001)
     */
    public ItemFlameWand(int _id) {
        super(_id);
        this.setMaxStackSize(1);   // 法杖不可堆叠
        this.setMaxDamage(256);    // 256 次使用后损坏
        this.setFull3D();          // 手持时使用 3D 渲染模式
    }

    /**
     * 右键点击时触发
     * 检查玩家是否有弹药(创造模式免检)，有则进入蓄力状态。
     * 蓄力期间玩家动画为拉弓姿势(由 getItemUseAction() 控制)。
     *
     * @param _stack 当前手持的法杖 ItemStack
     * @param _world 当前世界实例
     * @param _player 使用法杖的玩家
     * @return 操作后的 ItemStack
     */
    @Override
    public ItemStack onItemRightClick(ItemStack _stack, World _world, EntityPlayer _player) {
        // 创造模式免弹药检查，生存模式需要背包中有弹药宝珠
        if (_player.capabilities.isCreativeMode || _player.inventory.hasItem(ATcraftAddon.ammoOrb.itemID)) {
            _player.setItemInUse(_stack, this.getMaxItemUseDuration(_stack));
        }
        return _stack;
    }

    /**
     * 松手(释放右键)时触发
     * 计算蓄力时长 → 伤害 → 在服务端生成火焰弹实体。
     * 蓄力公式仿原版弓：power = (t² + 2t) / 3，其中 t = chargeTicks / 20。
     * 伤害范围：5.0(瞬发)~ 8.0(满蓄力 1 秒)。
     *
     * 仅在服务端执行生成逻辑(!world.isRemote)，客户端只负责动画。
     *
     * @param _stack 当前手持的法杖 ItemStack
     * @param _world 当前世界实例
     * @param _player 使用法杖的玩家
     * @param _timeLeft 剩余使用时间(tick)，用于反算蓄力时长
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack _stack, World _world, EntityPlayer _player, int _timeLeft) {
        // 蓄力 tick 数 → 归一化 power (0.0 ~ 1.0)
        int charge = this.getMaxItemUseDuration(_stack) - _timeLeft;
        float power = (float) charge / 20.0F;
        power = (power * power + power * 2.0F) / 3.0F; // 弓的蓄力曲线：前段快速上升，后段趋缓

        // 最小蓄力阈值：低于 0.1 视为未蓄力，不发射
        if (power < 0.1F) return;
        if (power > 1.0F) power = 1.0F;

        // 仅服务端执行发射逻辑
        if (!_world.isRemote) {
            // 播放弓弦声效，音高随机变化增加变化感
            _world.playSoundAtEntity(_player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));

            // 生成火焰弹并设置伤害
            EntityFlameBall ball = new EntityFlameBall(_world, _player);
            ball.setDamage(5.0F + power * 3.0F); // 基础 5 点 + 蓄力加成 0~3 点
            _world.spawnEntityInWorld(ball);

            // 生存模式消耗弹药 + 耐久
            if (!_player.capabilities.isCreativeMode) {
                _player.inventory.consumeInventoryItem(ATcraftAddon.ammoOrb.itemID);
            }
            _stack.damageItem(1, _player); // 每次发射消耗 1 点耐久
        }
    }

    /**
     * 最大使用持续时间(tick)
     * 72000 tick = 1 小时，远超实际蓄力需求，允许玩家无限蓄力。
     * 实际满蓄力约在 20 tick(1 秒)处。
     *
     * @param _stack 当前 ItemStack(未使用，预留接口兼容)
     * @return 72000 tick
     */
    @Override
    public int getMaxItemUseDuration(ItemStack _stack) {
        return 72000;
    }

    /**
     * 物品使用时的手部动画类型
     * 返回 bow(拉弓动画)，让法杖蓄力时呈现拉弓姿势。
     *
     * @param _stack 当前 ItemStack(未使用，预留接口兼容)
     * @return EnumAction.bow 拉弓动画
     */
    @Override
    public EnumAction getItemUseAction(ItemStack _stack) {
        return EnumAction.bow;
    }
}
