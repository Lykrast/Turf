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
import net.minecraft.item.DyeColor;
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
		IForgeRegistry<Block> reg = event.getRegistry();
		reg.registerAll(
				makeBlock("turf", new Block(grassProperties()))
				);
		for (DyeColor color : DyeColor.values()) {
			reg.register(makeBlock(color.getTranslationKey() + "_turf", new Block(grassProperties())));
		}
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
		
		//That's the part where I should make a loop
		DyeObjectColor white = new DyeObjectColor(DyeColor.WHITE);
		bcolors.register(white, Holders.WHITE_TURF);
		icolors.register(white, Holders.WHITE_TURF.asItem());
		DyeObjectColor orange = new DyeObjectColor(DyeColor.ORANGE);
		bcolors.register(orange, Holders.ORANGE_TURF);
		icolors.register(orange, Holders.ORANGE_TURF.asItem());
		DyeObjectColor magenta = new DyeObjectColor(DyeColor.MAGENTA);
		bcolors.register(magenta, Holders.MAGENTA_TURF);
		icolors.register(magenta, Holders.MAGENTA_TURF.asItem());
		DyeObjectColor lightBlue = new DyeObjectColor(DyeColor.LIGHT_BLUE);
		bcolors.register(lightBlue, Holders.LIGHT_BLUE_TURF);
		icolors.register(lightBlue, Holders.LIGHT_BLUE_TURF.asItem());
		DyeObjectColor yellow = new DyeObjectColor(DyeColor.YELLOW);
		bcolors.register(yellow, Holders.YELLOW_TURF);
		icolors.register(yellow, Holders.YELLOW_TURF.asItem());
		DyeObjectColor lime = new DyeObjectColor(DyeColor.LIME);
		bcolors.register(lime, Holders.LIME_TURF);
		icolors.register(lime, Holders.LIME_TURF.asItem());
		DyeObjectColor pink = new DyeObjectColor(DyeColor.PINK);
		bcolors.register(pink, Holders.PINK_TURF);
		icolors.register(pink, Holders.PINK_TURF.asItem());
		DyeObjectColor gray = new DyeObjectColor(DyeColor.GRAY);
		bcolors.register(gray, Holders.GRAY_TURF);
		icolors.register(gray, Holders.GRAY_TURF.asItem());
		DyeObjectColor lightGray = new DyeObjectColor(DyeColor.LIGHT_GRAY);
		bcolors.register(lightGray, Holders.LIGHT_GRAY_TURF);
		icolors.register(lightGray, Holders.LIGHT_GRAY_TURF.asItem());
		DyeObjectColor cyan = new DyeObjectColor(DyeColor.CYAN);
		bcolors.register(cyan, Holders.CYAN_TURF);
		icolors.register(cyan, Holders.CYAN_TURF.asItem());
		DyeObjectColor purple = new DyeObjectColor(DyeColor.PURPLE);
		bcolors.register(purple, Holders.PURPLE_TURF);
		icolors.register(purple, Holders.PURPLE_TURF.asItem());
		DyeObjectColor blue = new DyeObjectColor(DyeColor.BLUE);
		bcolors.register(blue, Holders.BLUE_TURF);
		icolors.register(blue, Holders.BLUE_TURF.asItem());
		DyeObjectColor brown = new DyeObjectColor(DyeColor.BROWN);
		bcolors.register(brown, Holders.BROWN_TURF);
		icolors.register(brown, Holders.BROWN_TURF.asItem());
		DyeObjectColor green = new DyeObjectColor(DyeColor.GREEN);
		bcolors.register(green, Holders.GREEN_TURF);
		icolors.register(green, Holders.GREEN_TURF.asItem());
		DyeObjectColor red = new DyeObjectColor(DyeColor.RED);
		bcolors.register(red, Holders.RED_TURF);
		icolors.register(red, Holders.RED_TURF.asItem());
		DyeObjectColor black = new DyeObjectColor(DyeColor.BLACK);
		bcolors.register(black, Holders.BLACK_TURF);
		icolors.register(black, Holders.BLACK_TURF.asItem());
	}
	
	@ObjectHolder(Turf.MODID)
	public static class Holders {
		public static final Block TURF = null;
		public static final Block WHITE_TURF = null;
		public static final Block ORANGE_TURF = null;
		public static final Block MAGENTA_TURF = null;
		public static final Block LIGHT_BLUE_TURF = null;
		public static final Block YELLOW_TURF = null;
		public static final Block LIME_TURF = null;
		public static final Block PINK_TURF = null;
		public static final Block GRAY_TURF = null;
		public static final Block LIGHT_GRAY_TURF = null;
		public static final Block CYAN_TURF = null;
		public static final Block PURPLE_TURF = null;
		public static final Block BLUE_TURF = null;
		public static final Block BROWN_TURF = null;
		public static final Block GREEN_TURF = null;
		public static final Block RED_TURF = null;
		public static final Block BLACK_TURF = null;
	}
}
