package ryoryo.polishedlib.client.model.block;

import java.util.Collection;
import java.util.function.Function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import ryoryo.polishedlib.PolishedLib;

public class ModelCross implements IModel {

	private final ResourceLocation parentLocation = new ResourceLocation("minecraft:block/cross");
	private ResourceLocation cross;
	private static final String CROSS = "cross";

	public ModelCross(ResourceLocation cross) {
		this.cross = cross;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of(parentLocation);
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableList.of(this.cross);
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		try {
			IModel parentModel = ModelLoaderRegistry.getModel(parentLocation);
			IModel blockModel = parentModel.retexture(ImmutableMap.of(CROSS, this.cross.toString()));

			return blockModel.bake(state, format, bakedTextureGetter);
		} catch(Exception e) {
			PolishedLib.LOGGER.error(this.getClass().getName() + ".bake() failed due to exception: " + e);
			return ModelLoaderRegistry.getMissingModel().bake(state, format, bakedTextureGetter);
		}
	}
}