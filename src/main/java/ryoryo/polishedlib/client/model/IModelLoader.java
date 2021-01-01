package ryoryo.polishedlib.client.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.IModel;

public interface IModelLoader {

	/*
	 * Checks if given model should be loaded by this loader.
	 * Reading file contents is inadvisable, if possible decision should be made based on the location alone.
	 */
	boolean accepts(ModelResourceLocation modelLocation);

	/*
	 * loads (or reloads) specified model
	 */
	IModel loadModel(ModelResourceLocation modelLocation) throws Exception;
}