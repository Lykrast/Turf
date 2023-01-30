package lykrast.turf;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.ModList;

public enum TurfColor {
	//2 reasons: easily add the modded dyes, and also I want the textureDiffuseColors from the vanilla dyes
	//But I need it in int form instead of the float[3] form it gets saved as, so I just copied the values from the code
	//But some of the older I used (lift the map colors) look better so I'm mixing
	//Vanilla, copied from DyeColor
	WHITE("white", 16383998, MaterialColor.SNOW),
	ORANGE("orange", 14188339, MaterialColor.COLOR_ORANGE), //Old color, new would have been 16351261
	MAGENTA("magenta", 11685080, MaterialColor.COLOR_MAGENTA), //Old color, new would have been 13061821
	LIGHT_BLUE("light_blue", 6724056, MaterialColor.COLOR_LIGHT_BLUE), //Old color, new would have been 3847130
	YELLOW("yellow", 16701501, MaterialColor.COLOR_YELLOW),
	LIME("lime", 8439583, MaterialColor.COLOR_LIGHT_GREEN),
	PINK("pink", 15892389, MaterialColor.COLOR_PINK), //Old color, new would have been 15961002
	GRAY("gray", 4673362, MaterialColor.COLOR_GRAY),
	LIGHT_GRAY("light_gray", 10066329, MaterialColor.COLOR_LIGHT_GRAY), //Old color, new would have been 10329495
	CYAN("cyan", 5013401, MaterialColor.COLOR_CYAN), //Old color, new would have been 1481884
	PURPLE("purple", 8339378, MaterialColor.COLOR_PURPLE), //Old color, new would have been 8991416
	BLUE("blue", 3949738, MaterialColor.COLOR_BLUE),
	BROWN("brown", 8606770, MaterialColor.COLOR_BROWN),
	GREEN("green", 6192150, MaterialColor.COLOR_GREEN),
	RED("red", 10040115, MaterialColor.COLOR_RED), //Old color, new would have been 11546150
	BLACK("black", 1908001, MaterialColor.COLOR_BLACK);

	private final String name;
	@Nullable private final String requiredMod;
	private final int color;
	private final MaterialColor matColor;

	private TurfColor(String name, int color, MaterialColor matColor, String requiredmod) {
		this.name = name;
		this.color = color;
		this.matColor = matColor;
		this.requiredMod = requiredmod;
	}
	
	private TurfColor(String name, int color, MaterialColor matColor) {
		this(name, color, matColor, null);
	}
	
	public String getName() {
		return name;
	}

	public boolean shouldRegister() {
		return requiredMod == null || ModList.get().isLoaded(requiredMod);
	}

	public int getColor() {
		return color;
	}

	public MaterialColor getMaterialColor() {
		return matColor;
	}
}
