package lykrast.turf;

import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

public class DyeObjectColor implements IBlockColor, IItemColor {
	private int color;
	
	public DyeObjectColor(DyeColor dye) {
		color = dye.getMapColor().colorValue;
	}

	@Override
	public int getColor(ItemStack stack, int tintIndex) {
		return color;
	}

	@Override
	public int getColor(BlockState state, IEnviromentBlockReader world, BlockPos pos, int tintIndex) {
		return color;
	}
	
}
