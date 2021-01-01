package ryoryo.polishedlib.client.model.item;

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

public class ModelGenerated implements IModel {

	private final ResourceLocation parentLocation = new ResourceLocation("minecraft:item/generated");
	private ResourceLocation layer0;
	private static final String TEX_VAR_NAME = "layer0";

	public ModelGenerated(ResourceLocation layer0) {
		this.layer0 = layer0;
	}

	@Override
	public Collection<ResourceLocation> getDependencies() {
		return ImmutableList.of(parentLocation);
	}

	@Override
	public Collection<ResourceLocation> getTextures() {
		return ImmutableList.of(this.layer0);
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		try {
			IModel parentModel = ModelLoaderRegistry.getModel(parentLocation);
			IModel newModel = parentModel.retexture(ImmutableMap.of(TEX_VAR_NAME, this.layer0.toString()));

			return newModel.bake(state, format, bakedTextureGetter);
		} catch(Exception e) {
			PolishedLib.LOGGER.error(this.getClass().getName() + ".bake() failed due to exception: " + e);
			return ModelLoaderRegistry.getMissingModel().bake(state, format, bakedTextureGetter);
		}
	}

}