package xyz.risingkingdom.tooltipped;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.tag.ItemTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.risingkingdom.tooltipped.command.RegistryCommand;

import java.util.Collection;
import java.util.List;

public class Tooltipped implements ClientModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("Rongmario/Tooltipped");

    // public final RegistryCommand REGISTRY_COMMMND = new RegistryCommand();

    @Override
    public void onInitializeClient() {
        CommandRegistry.INSTANCE.register(false, dispatcher -> RegistryCommand.register(dispatcher));
        LOGGER.info("Tooltipped Initialized...");
    }

    public static void injectTooltip(Object stackIn, TooltipContext context, List<Text> list) {
        ItemStack stack = (ItemStack) stackIn;
        Item item = stack.getItem();
        if (context.isAdvanced()) {
            //Tags
            LiteralText blank = new LiteralText("");
            if (stack.hasTag() && Screen.hasControlDown()) {
                CompoundTag compoundTag = stack.getTag();
                if (compoundTag == null) return;
                //compoundTag.getKeys().forEach(tag -> {

                //});
                list.add((new LiteralText(compoundTag.toString()).formatted(Formatting.ITALIC, Formatting.DARK_GRAY)));
            }
            Collection<Identifier> identifiers = ItemTags.getContainer().getTagsFor(item);
            if (!identifiers.isEmpty()) {
                list.add(blank);
                list.add(new LiteralText("Tags:").formatted(Formatting.DARK_GRAY));
                for (Identifier identifier : identifiers) {
                    list.add(new LiteralText(identifier.toString()).formatted(Formatting.DARK_GRAY));
                }
            }
            if (stack.isEnchantable()) {
                list.add(blank);
                list.add(new LiteralText("Enchantability: ".concat(String.valueOf(item.getEnchantability()))).formatted(Formatting.DARK_GRAY));
            }
            if (item instanceof BlockItem) {
                Block block = ((BlockItem)item).getBlock();
                BlockState state = block.getDefaultState();
                list.add(blank);
                list.add((new LiteralText("Block Stats: ").formatted(Formatting.DARK_GRAY)));
                list.add((new LiteralText("Blast Resistance: ".concat(String.valueOf(block.getBlastResistance()))).formatted(Formatting.ITALIC, Formatting.DARK_GRAY)));
                list.add((new LiteralText("Piston Behaviour: ".concat(state.getPistonBehavior().name())).formatted(Formatting.ITALIC, Formatting.DARK_GRAY)));
                if (state.getLuminance() > 0) {
                    list.add((new LiteralText("Luminance: ".concat(String.valueOf(state.getLuminance()))).formatted(Formatting.ITALIC, Formatting.DARK_GRAY)));
                }
                if (block.hasRandomTicks(state)) {
                    list.add((new LiteralText("Has Random Ticks").formatted(Formatting.ITALIC, Formatting.DARK_GRAY)));
                }
                /*
                if (block.hasBlockEntity()) {
                    BlockEntityProvider provider = (BlockEntityProvider) block;
                    if (provider.)
                }
                */
            }
            /*
            if (item instanceof BucketItem) {
                BucketItem bucketItem = (BucketItem) item;
            }
            */
        }
    }

}
