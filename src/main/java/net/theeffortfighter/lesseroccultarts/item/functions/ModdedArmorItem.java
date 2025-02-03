package net.theeffortfighter.lesseroccultarts.item.functions;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.UUID;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModdedArmorItem extends ArmorItem {
    private static final UUID[] MODIFIERS = new UUID[]{
            UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
            UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
            UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
            UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")
    };

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    private final int protection;
    private final float toughness;
    private final float healthBonus;

    public ModdedArmorItem(int protection, float toughness, float healthBonus, EquipmentSlot slot, Settings settings) {
        super(ModdedArmorMaterials.ABYSSAL, slot, settings); // ArmorMaterials is required but ignored
        this.protection = protection;
        this.toughness = toughness;
        this.healthBonus = healthBonus;

        DispenserBlock.registerBehavior(this, new ItemDispenserBehavior());

        // Define Attribute Modifiers
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        UUID uuid = MODIFIERS[slot.getEntitySlotId()];
        builder.put(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uuid, "Armor modifier", protection, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, new EntityAttributeModifier(uuid, "Armor toughness", toughness, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(uuid, "Health modifier", healthBonus, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == this.getSlotType() ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public int getEnchantability() {
        return 10; // Adjust as needed
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        EquipmentSlot slot = this.getSlotType();
        ItemStack equipped = user.getEquippedStack(slot);

        if (equipped.isEmpty()) {
            user.equipStack(slot, itemStack.copy());
            if (!world.isClient()) {
                user.incrementStat(Stats.USED.getOrCreateStat(this));
            }
            itemStack.setCount(0);
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false; // Prevents anvil repairs
    }

    @Override
    public boolean isDamageable() {
        return false; // Prevents taking damage
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!stack.hasNbt() || !stack.getNbt().contains("Unbreakable")) {
            makeUnbreakable(stack);
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void makeUnbreakable(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putBoolean("Unbreakable", true);
    }
}
