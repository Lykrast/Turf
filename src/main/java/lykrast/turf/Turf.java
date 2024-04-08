package lykrast.turf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
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
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
	}

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	
//	public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), MODID) {
//		@Override
//		public ItemStack makeIcon() {
//			return new ItemStack(turfItem.get());
//		}
//	};
	
	private static RegistryObject<Item> turfItem;
	
	//This is full of duct tape
	public static List<Tuple<RegistryObject<Block>, TurfColor>> blocksToColor = new ArrayList<>();
	public static List<Tuple<RegistryObject<Item>, TurfColor>> itemsToColor = new ArrayList<>();
	
	static {
		//null for the default turf
		RegistryObject<Block> turf = makeTurfBlock("turf", () -> new Block(grassProperties()), null);
		makeTurfBlock("turf_slab", () -> new SlabBlock(grassProperties()), null);
		makeTurfBlock("turf_stairs", () -> new StairBlock(() -> turf.get().defaultBlockState(), grassProperties()), null);
		makeTurfBlock("turf_wall", () -> new WallBlock(grassProperties()), null);
		
		for (TurfColor color : TurfColor.values()) {
			if (!color.shouldRegister()) continue;
			String name = color.getName();
			MapColor matColor = color.getMapColor();
			
			RegistryObject<Block> dyed = makeTurfBlock(name + "_turf", () -> new Block(grassProperties(matColor)), color);
			makeTurfBlock(name + "_turf_slab", () -> new SlabBlock(grassProperties(matColor)), color);
			makeTurfBlock(name + "_turf_stairs", () -> new StairBlock(() -> dyed.get().defaultBlockState(), grassProperties(matColor)), color);
			makeTurfBlock(name + "_turf_wall", () -> new WallBlock(grassProperties(matColor)), color);
		}
	}

	private static RegistryObject<Block> makeTurfBlock(String name, Supplier<Block> block, TurfColor color) {
		RegistryObject<Block> reggedBlock = BLOCKS.register(name, block);
		//RegistryObject<Item> reggedItem = ITEMS.register(name, () -> new BlockItem(reggedBlock.get(), (new Item.Properties()).tab(ITEM_GROUP)));
		RegistryObject<Item> reggedItem = ITEMS.register(name, () -> new BlockItem(reggedBlock.get(), (new Item.Properties())));
		blocksToColor.add(new Tuple<>(reggedBlock, color));
		itemsToColor.add(new Tuple<>(reggedItem, color));
		
		//So uh the way I did this I can't cleanly extract the turf block item without rewritting this
		//so instead here's a hack cause I know I'm making the normal turf first
		if (turfItem == null) turfItem = reggedItem;
		
		return reggedBlock;
	}
	
	private static Block.Properties grassProperties() {
		//Grass ticks randomly, I don't want that but there's no method to turn it off, so just copying stuff manually
		return BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.6F).sound(SoundType.GRASS);
	}
	
	private static Block.Properties grassProperties(MapColor color) {
		//Grass ticks randomly, I don't want that but there's no method to turn it off, so just copying stuff manually
		return BlockBehaviour.Properties.of().mapColor(color).strength(0.6F).sound(SoundType.GRASS);
	}
}
