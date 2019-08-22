package lykrast.turf;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.WallBlock;
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
import net.minecraftforge.common.ToolType;
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
		Block turf = makeBlock("turf", new Block(grassProperties()));
		reg.registerAll(
				turf,
				makeBlock("turf_slab", new SlabBlock(grassProperties())),
				makeBlock("turf_stairs", new UseableStairsBlock(turf.getDefaultState(), grassProperties())),
				makeBlock("turf_wall", new WallBlock(grassProperties()))
				);
		for (DyeColor color : DyeColor.values()) {
			String name = color.getTranslationKey();
			Block dyed = makeBlock(name + "_turf", new Block(grassProperties()));
			reg.registerAll(
					dyed,
						makeBlock(name + "_turf_slab", new SlabBlock(grassProperties())),
						makeBlock(name + "_turf_stairs", new UseableStairsBlock(dyed.getDefaultState(), grassProperties())),
						makeBlock(name + "_turf_wall", new WallBlock(grassProperties()))
						);
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
		//Also harvest tool, that's a forge thing
		return Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.6F).sound(SoundType.PLANT).harvestTool(ToolType.SHOVEL);
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		BlockColors bcolors = Minecraft.getInstance().getBlockColors();
		ItemColors icolors = Minecraft.getInstance().getItemColors();
		
		//Copy pasted and simplified the grass one
		bcolors.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5, 1),
				Holders.TURF, Holders.TURF_SLAB, Holders.TURF_STAIRS, Holders.TURF_WALL);
		icolors.register((stack, tintIndex) -> GrassColors.get(0.5, 1), Holders.TURF.asItem(), Holders.TURF_SLAB.asItem(), Holders.TURF_STAIRS.asItem(), Holders.TURF_WALL.asItem());
		
		//That's the part where I should make a loop
		int white = DyeColor.WHITE.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> white, Holders.WHITE_TURF, Holders.WHITE_TURF_SLAB, Holders.WHITE_TURF_STAIRS, Holders.WHITE_TURF_WALL);
		icolors.register((stack, tintIndex) -> white, Holders.WHITE_TURF.asItem(), Holders.WHITE_TURF_SLAB.asItem(), Holders.WHITE_TURF_STAIRS.asItem(), Holders.WHITE_TURF_WALL.asItem());
		int orange = DyeColor.ORANGE.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> orange, Holders.ORANGE_TURF, Holders.ORANGE_TURF_SLAB, Holders.ORANGE_TURF_STAIRS, Holders.ORANGE_TURF_WALL);
		icolors.register((stack, tintIndex) -> orange, Holders.ORANGE_TURF.asItem(), Holders.ORANGE_TURF_SLAB.asItem(), Holders.ORANGE_TURF_STAIRS.asItem(), Holders.ORANGE_TURF_WALL.asItem());
		int magenta = DyeColor.MAGENTA.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> magenta, Holders.MAGENTA_TURF, Holders.MAGENTA_TURF_SLAB, Holders.MAGENTA_TURF_STAIRS, Holders.MAGENTA_TURF_WALL);
		icolors.register((stack, tintIndex) -> magenta, Holders.MAGENTA_TURF.asItem(), Holders.MAGENTA_TURF_SLAB.asItem(), Holders.MAGENTA_TURF_STAIRS.asItem(), Holders.MAGENTA_TURF_WALL.asItem());
		int lightBlue = DyeColor.LIGHT_BLUE.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> lightBlue, Holders.LIGHT_BLUE_TURF, Holders.LIGHT_BLUE_TURF_SLAB, Holders.LIGHT_BLUE_TURF_STAIRS, Holders.LIGHT_BLUE_TURF_WALL);
		icolors.register((stack, tintIndex) -> lightBlue, Holders.LIGHT_BLUE_TURF.asItem(), Holders.LIGHT_BLUE_TURF_SLAB.asItem(), Holders.LIGHT_BLUE_TURF_STAIRS.asItem(), Holders.LIGHT_BLUE_TURF_WALL.asItem());
		int yellow = DyeColor.YELLOW.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> yellow, Holders.YELLOW_TURF, Holders.YELLOW_TURF_SLAB, Holders.YELLOW_TURF_STAIRS, Holders.YELLOW_TURF_WALL);
		icolors.register((stack, tintIndex) -> yellow, Holders.YELLOW_TURF.asItem(), Holders.YELLOW_TURF_SLAB.asItem(), Holders.YELLOW_TURF_STAIRS.asItem(), Holders.YELLOW_TURF_WALL.asItem());
		int lime = DyeColor.LIME.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> lime, Holders.LIME_TURF, Holders.LIME_TURF_SLAB, Holders.LIME_TURF_STAIRS, Holders.LIME_TURF_WALL);
		icolors.register((stack, tintIndex) -> lime, Holders.LIME_TURF.asItem(), Holders.LIME_TURF_SLAB.asItem(), Holders.LIME_TURF_STAIRS.asItem(), Holders.LIME_TURF_WALL.asItem());
		int pink = DyeColor.PINK.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> pink, Holders.PINK_TURF, Holders.PINK_TURF_SLAB, Holders.PINK_TURF_STAIRS, Holders.PINK_TURF_WALL);
		icolors.register((stack, tintIndex) -> pink, Holders.PINK_TURF.asItem(), Holders.PINK_TURF_SLAB.asItem(), Holders.PINK_TURF_STAIRS.asItem(), Holders.PINK_TURF_WALL.asItem());
		int gray = DyeColor.GRAY.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> gray, Holders.GRAY_TURF, Holders.GRAY_TURF_SLAB, Holders.GRAY_TURF_STAIRS, Holders.GRAY_TURF_WALL);
		icolors.register((stack, tintIndex) -> gray, Holders.GRAY_TURF.asItem(), Holders.GRAY_TURF_SLAB.asItem(), Holders.GRAY_TURF_STAIRS.asItem(), Holders.GRAY_TURF_WALL.asItem());
		int lightGray = DyeColor.LIGHT_GRAY.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> lightGray, Holders.LIGHT_GRAY_TURF, Holders.LIGHT_GRAY_TURF_SLAB, Holders.LIGHT_GRAY_TURF_STAIRS, Holders.LIGHT_GRAY_TURF_WALL);
		icolors.register((stack, tintIndex) -> lightGray, Holders.LIGHT_GRAY_TURF.asItem(), Holders.LIGHT_GRAY_TURF_SLAB.asItem(), Holders.LIGHT_GRAY_TURF_STAIRS.asItem(), Holders.LIGHT_GRAY_TURF_WALL.asItem());
		int cyan = DyeColor.CYAN.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> cyan, Holders.CYAN_TURF, Holders.CYAN_TURF_SLAB, Holders.CYAN_TURF_STAIRS, Holders.CYAN_TURF_WALL);
		icolors.register((stack, tintIndex) -> cyan, Holders.CYAN_TURF.asItem(), Holders.CYAN_TURF_SLAB.asItem(), Holders.CYAN_TURF_STAIRS.asItem(), Holders.CYAN_TURF_WALL.asItem());
		int purple = DyeColor.PURPLE.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> purple, Holders.PURPLE_TURF, Holders.PURPLE_TURF_SLAB, Holders.PURPLE_TURF_STAIRS, Holders.PURPLE_TURF_WALL);
		icolors.register((stack, tintIndex) -> purple, Holders.PURPLE_TURF.asItem(), Holders.PURPLE_TURF_SLAB.asItem(), Holders.PURPLE_TURF_STAIRS.asItem(), Holders.PURPLE_TURF_WALL.asItem());
		int blue = DyeColor.BLUE.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> blue, Holders.BLUE_TURF, Holders.BLUE_TURF_SLAB, Holders.BLUE_TURF_STAIRS, Holders.BLUE_TURF_WALL);
		icolors.register((stack, tintIndex) -> blue, Holders.BLUE_TURF.asItem(), Holders.BLUE_TURF_SLAB.asItem(), Holders.BLUE_TURF_STAIRS.asItem(), Holders.BLUE_TURF_WALL.asItem());
		int brown = DyeColor.BROWN.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> brown, Holders.BROWN_TURF, Holders.BROWN_TURF_SLAB, Holders.BROWN_TURF_STAIRS, Holders.BROWN_TURF_WALL);
		icolors.register((stack, tintIndex) -> brown, Holders.BROWN_TURF.asItem(), Holders.BROWN_TURF_SLAB.asItem(), Holders.BROWN_TURF_STAIRS.asItem(), Holders.BROWN_TURF_WALL.asItem());
		int green = DyeColor.GREEN.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> green, Holders.GREEN_TURF, Holders.GREEN_TURF_SLAB, Holders.GREEN_TURF_STAIRS, Holders.GREEN_TURF_WALL);
		icolors.register((stack, tintIndex) -> green, Holders.GREEN_TURF.asItem(), Holders.GREEN_TURF_SLAB.asItem(), Holders.GREEN_TURF_STAIRS.asItem(), Holders.GREEN_TURF_WALL.asItem());
		int red = DyeColor.RED.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> red, Holders.RED_TURF, Holders.RED_TURF_SLAB, Holders.RED_TURF_STAIRS, Holders.RED_TURF_WALL);
		icolors.register((stack, tintIndex) -> red, Holders.RED_TURF.asItem(), Holders.RED_TURF_SLAB.asItem(), Holders.RED_TURF_STAIRS.asItem(), Holders.RED_TURF_WALL.asItem());
		int black = DyeColor.BLACK.getMapColor().colorValue;
		bcolors.register((state, world, pos, tintIndex) -> black, Holders.BLACK_TURF, Holders.BLACK_TURF_SLAB, Holders.BLACK_TURF_STAIRS, Holders.BLACK_TURF_WALL);
		icolors.register((stack, tintIndex) -> black, Holders.BLACK_TURF.asItem(), Holders.BLACK_TURF_SLAB.asItem(), Holders.BLACK_TURF_STAIRS.asItem(), Holders.BLACK_TURF_WALL.asItem());
	}
	
	@ObjectHolder(Turf.MODID)
	public static class Holders {
		public static final Block TURF = null;
		public static final Block TURF_SLAB = null;
		public static final Block TURF_STAIRS = null;
		public static final Block TURF_WALL = null;
		
		public static final Block WHITE_TURF = null;
		public static final Block WHITE_TURF_SLAB = null;
		public static final Block WHITE_TURF_STAIRS = null;
		public static final Block WHITE_TURF_WALL = null;
		public static final Block ORANGE_TURF = null;
		public static final Block ORANGE_TURF_SLAB = null;
		public static final Block ORANGE_TURF_STAIRS = null;
		public static final Block ORANGE_TURF_WALL = null;
		public static final Block MAGENTA_TURF = null;
		public static final Block MAGENTA_TURF_SLAB = null;
		public static final Block MAGENTA_TURF_STAIRS = null;
		public static final Block MAGENTA_TURF_WALL = null;
		public static final Block LIGHT_BLUE_TURF = null;
		public static final Block LIGHT_BLUE_TURF_SLAB = null;
		public static final Block LIGHT_BLUE_TURF_STAIRS = null;
		public static final Block LIGHT_BLUE_TURF_WALL = null;
		public static final Block YELLOW_TURF = null;
		public static final Block YELLOW_TURF_SLAB = null;
		public static final Block YELLOW_TURF_STAIRS = null;
		public static final Block YELLOW_TURF_WALL = null;
		public static final Block LIME_TURF = null;
		public static final Block LIME_TURF_SLAB = null;
		public static final Block LIME_TURF_STAIRS = null;
		public static final Block LIME_TURF_WALL = null;
		public static final Block GRAY_TURF = null;
		public static final Block GRAY_TURF_SLAB = null;
		public static final Block GRAY_TURF_STAIRS = null;
		public static final Block GRAY_TURF_WALL = null;
		public static final Block PINK_TURF = null;
		public static final Block PINK_TURF_SLAB = null;
		public static final Block PINK_TURF_STAIRS = null;
		public static final Block PINK_TURF_WALL = null;
		public static final Block LIGHT_GRAY_TURF = null;
		public static final Block LIGHT_GRAY_TURF_SLAB = null;
		public static final Block LIGHT_GRAY_TURF_STAIRS = null;
		public static final Block LIGHT_GRAY_TURF_WALL = null;
		public static final Block CYAN_TURF = null;
		public static final Block CYAN_TURF_SLAB = null;
		public static final Block CYAN_TURF_STAIRS = null;
		public static final Block CYAN_TURF_WALL = null;
		public static final Block PURPLE_TURF = null;
		public static final Block PURPLE_TURF_SLAB = null;
		public static final Block PURPLE_TURF_STAIRS = null;
		public static final Block PURPLE_TURF_WALL = null;
		public static final Block BLUE_TURF = null;
		public static final Block BLUE_TURF_SLAB = null;
		public static final Block BLUE_TURF_STAIRS = null;
		public static final Block BLUE_TURF_WALL = null;
		public static final Block BROWN_TURF = null;
		public static final Block BROWN_TURF_SLAB = null;
		public static final Block BROWN_TURF_STAIRS = null;
		public static final Block BROWN_TURF_WALL = null;
		public static final Block GREEN_TURF = null;
		public static final Block GREEN_TURF_SLAB = null;
		public static final Block GREEN_TURF_STAIRS = null;
		public static final Block GREEN_TURF_WALL = null;
		public static final Block RED_TURF = null;
		public static final Block RED_TURF_SLAB = null;
		public static final Block RED_TURF_STAIRS = null;
		public static final Block RED_TURF_WALL = null;
		public static final Block BLACK_TURF = null;
		public static final Block BLACK_TURF_SLAB = null;
		public static final Block BLACK_TURF_STAIRS = null;
		public static final Block BLACK_TURF_WALL = null;
	}
}
