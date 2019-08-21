package lykrast.turf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Turf.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Turf.MODID)
public class Turf {
	public static final String MODID = "turf";
	
	public Turf() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
	}
	
	private static List<Item> blockitems;
	public static final ItemGroup ITEM_GROUP = new ItemGroup(ItemGroup.getGroupCountSafe(), MODID) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(Holders.TURF);
		}
	};

	@SubscribeEvent
	public static void registerBlocks(final RegistryEvent.Register<Block> event) {
		blockitems = new ArrayList<>();
		event.getRegistry().registerAll(
				makeBlock("turf", new Block(grassProperties())),
				makeBlock("white_turf", new Block(grassProperties()))
				);
	}

	@SubscribeEvent
	public static void registerItems(final RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> reg = event.getRegistry();
		blockitems.forEach(reg::register);
		blockitems = null;
	}
	
	private static Block makeBlock(String name, Block block) {
		block.setRegistryName(MODID, name);
		blockitems.add(new BlockItem(block, ((new Item.Properties()).group(ITEM_GROUP))).setRegistryName(MODID, name));
		return block;
	}
	
	private static Block.Properties grassProperties() {
		//Grass ticks randomly, I don't want that but there's no method to turn it off, so just copying stuff manually
		return Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.6F).sound(SoundType.PLANT);
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		BlockColors bcolors = Minecraft.getInstance().getBlockColors();
		ItemColors icolors = Minecraft.getInstance().getItemColors();
		
		//Copy pasted and simplified the grass one
		bcolors.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5, 1),
				Holders.TURF);
		icolors.register((stack, tintIndex) -> GrassColors.get(0.5, 1), Holders.TURF.asItem());
		
		//Too lazy to AT the colorValue so just copy pasted (and converted to hex) from DyeColor
		bcolors.register((state, world, pos, tintIndex) -> 0xF9FFFE, Holders.WHITE_TURF);
		icolors.register((stack, tintIndex) -> 0xF9FFFE, Holders.WHITE_TURF.asItem());
	}
	
	@ObjectHolder(Turf.MODID)
	public static class Holders {
		public static final Block TURF = null;
		public static final Block WHITE_TURF = null;
	}
}
