package ryoryo.polishedlib.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeNameBuilder {
	private String output;
	private List<String> inputs = new ArrayList<String>();

	public RecipeNameBuilder(ItemStack output, Object... params) {
		this.output = cut(output.getUnlocalizedName().toLowerCase());
		for (Object param : params) {
			ItemStack input = ItemStack.EMPTY;
			if (param instanceof ItemStack)
				input = (ItemStack) param;
			if (param instanceof Block)
				input = new ItemStack((Block) param);
			if (param instanceof Item)
				input = new ItemStack((Item) param);

			if (!input.isEmpty())
				inputs.add(cut(input.getUnlocalizedName().toLowerCase()));
		}
	}

	public RecipeNameBuilder(String output, String... inputs) {
		this.output = output;
		this.inputs = Arrays.asList(inputs);
	}

	public String build() {
		StringBuilder builder = new StringBuilder();

		for (String input : this.inputs) {
			if (builder.toString().equals("")) {
				builder.append(input);
			} else {
				builder.append("+");
				builder.append(input);
			}
		}

		builder.append("->");
		builder.append(this.output);

		return build().toString();
	}

	// private String recipeNameBuilder(ItemStack output, Object... params)
	// {
	// String name = "";
	// for(Object param : params)
	// {
	// String append = "";
	//// if(param instanceof ItemStack)
	//// append = ((ItemStack) param).getDisplayName().toLowerCase().replace('
	// ', '_');
	//// if(param instanceof Block)
	//// append = (new ItemStack((Block)
	// param)).getDisplayName().toLowerCase().replace(' ', '_');
	//// if(param instanceof Item)
	//// append = (new ItemStack((Item)
	// param)).getDisplayName().toLowerCase().replace(' ', '_');
	// if(param instanceof ItemStack)
	// append = cut(((ItemStack) param).getUnlocalizedName().toLowerCase());
	// if(param instanceof Block)
	// append = cut((new ItemStack((Block)
	// param)).getUnlocalizedName().toLowerCase());
	// if(param instanceof Item)
	// append = cut((new ItemStack((Item)
	// param)).getUnlocalizedName().toLowerCase());
	//
	// name = append(name, append);
	// }
	//
	// return name + "->" + cut(output.getUnlocalizedName().toLowerCase());
	// }

	private String cut(String unlocalizedName) {
		// return unlocalizedName.substring(0,
		// unlocalizedName.length()-5).substring(5);
		return unlocalizedName.substring("tile.".length());
	}
}