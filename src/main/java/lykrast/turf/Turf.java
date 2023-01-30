package lykrast.turf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(Turf.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Turf.MODID)
public class Turf {
	public static final String MODID = "turf";
	
	public Turf() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerBlockColors);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerItemColors);
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
	}

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	
	public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), MODID) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(turfItem.get());
		}
	};
	
	//Hey look I actually cleaned it up!
	private static List<Tuple<RegistryObject<Block>, BlockColor>> blocksToColor = new ArrayList<>();
	private static List<Tuple<RegistryObject<Item>, ItemColor>> itemsToColor = new ArrayList<>();
	
	private static RegistryObject<Item> turfItem;
	
	static {
		//Copy pasted and simplified the grass one
		BlockColor grassB = (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5, 1);
		ItemColor grassI = (stack, tintIndex) -> GrassColor.get(0.5, 1);
		
		RegistryObject<Block> turf = makeTurfBlock("turf", () -> new Block(grassProperties()), grassB, grassI);
		makeTurfBlock("turf_slab", () -> new SlabBlock(grassProperties()), grassB, grassI);
		makeTurfBlock("turf_stairs", () -> new StairBlock(() -> turf.get().defaultBlockState(), grassProperties()), grassB, grassI);
		makeTurfBlock("turf_wall", () -> new WallBlock(grassProperties()), grassB, grassI);
		
		for (TurfColor color : TurfColor.values()) {
			if (!color.shouldRegister()) continue;
			String name = color.getName();
			MaterialColor matColor = color.getMaterialColor();
			BlockColor bColor = (state, world, pos, tintIndex) -> color.getColor();
			ItemColor iColor = (stack, tintIndex) -> color.getColor();
			
			RegistryObject<Block> dyed = makeTurfBlock(name + "_turf", () -> new Block(grassProperties(matColor)), bColor, iColor);
			makeTurfBlock(name + "_turf_slab", () -> new SlabBlock(grassProperties(matColor)), bColor, iColor);
			makeTurfBlock(name + "_turf_stairs", () -> new StairBlock(() -> dyed.get().defaultBlockState(), grassProperties(matColor)), bColor, iColor);
			makeTurfBlock(name + "_turf_wall", () -> new WallBlock(grassProperties(matColor)), bColor, iColor);
		}
	}

	private static RegistryObject<Block> makeTurfBlock(String name, Supplier<Block> block, BlockColor bcolor, ItemColor icolor) {
		RegistryObject<Block> reggedBlock = BLOCKS.register(name, block);
		RegistryObject<Item> reggedItem = ITEMS.register(name, () -> new BlockItem(reggedBlock.get(), (new Item.Properties()).tab(ITEM_GROUP)));
		blocksToColor.add(new Tuple<>(reggedBlock, bcolor));
		itemsToColor.add(new Tuple<>(reggedItem, icolor));
		
		//So uh the way I did this I can't cleanly extract the turf block item without rewritting this
		//so instead here's a hack cause I know I'm making the normal turf first
		if (turfItem == null) turfItem = reggedItem;
		
		return reggedBlock;
	}
	
	private static Block.Properties grassProperties() {
		//Grass ticks randomly, I don't want that but there's no method to turn it off, so just copying stuff manually
		return Block.Properties.of(Material.GRASS).strength(0.6F).sound(SoundType.GRASS);
	}
	
	private static Block.Properties grassProperties(MaterialColor color) {
		//Grass ticks randomly, I don't want that but there's no method to turn it off, so just copying stuff manually
		return Block.Properties.of(Material.GRASS, color).strength(0.6F).sound(SoundType.GRASS);
	}
	
	private void registerBlockColors(final RegisterColorHandlersEvent.Block event) {
		for (var t : blocksToColor) event.register(t.getB(), t.getA().get());
		//Don't need them after that, hopefully that lets them be garbage collected
		blocksToColor = null;
	}
	
	private void registerItemColors(final RegisterColorHandlersEvent.Item event) {
		for (var t : itemsToColor) event.register(t.getB(), t.getA().get());
		//Don't need them after that, hopefully that lets them be garbage collected
		itemsToColor = null;
	}
}
