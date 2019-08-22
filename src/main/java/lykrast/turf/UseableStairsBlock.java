package lykrast.turf;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;

public class UseableStairsBlock extends StairsBlock {
	//Guess what, the constructor is protected
	//That means I can't even AT it to get something clean
	public UseableStairsBlock(BlockState state, Properties properties) {
		super(state, properties);
	}

}
