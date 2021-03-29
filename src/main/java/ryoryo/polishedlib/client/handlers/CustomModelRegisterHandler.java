package ryoryo.polishedlib.client.handlers;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ryoryo.polishedlib.PolishedLib;
import ryoryo.polishedlib.client.model.IModelLoader;
import ryoryo.polishedlib.util.References;
import ryoryo.polishedlib.util.Utils;

public class CustomModelRegisterHandler {

	private static List<IModelLoader> loaders = Lists.newArrayList();

	private static final Function<ResourceLocation, TextureAtlasSprite> TEXTURE_GETTER = (ResourceLocation location) -> {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
	};

	public static void registerModelLoader(IModelLoader loader) {
		loaders.add(loader);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
		final IBakedModel missingModel = event.getModelManager().getMissingModel();
		Set<ModelResourceLocation> modelLocations = modelRegistry.getKeys();

		Utils.measureTime("custom model loading", () -> {

			ProgressBar bakeBar = ProgressManager.push(References.MOD_NAME + ": registering", modelLocations.size());

			for (ModelResourceLocation location : modelLocations) {
				IModel model = null;

				bakeBar.step(location.toString());

				for (IModelLoader loader : loaders) {
					if (loader.accepts(location)) {
						try {
							model = loader.loadModel(location);
						} catch (Exception e) {
							PolishedLib.LOGGER.error("Unable to load model of " + location, e);
							break;
						}

						if (model != null && checkOverrideModel(modelRegistry, location, missingModel)) {
							modelRegistry.putObject(location, model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, TEXTURE_GETTER));
						}

						break;
					}
				}
			}

			ProgressManager.pop(bakeBar);

		});
	}

	private boolean checkOverrideModel(IRegistry<ModelResourceLocation, IBakedModel> modelRegistry, ModelResourceLocation location, IBakedModel missingModel) {
		return isMissingModel(modelRegistry.getObject(location), missingModel);
	}

	private boolean isMissingModel(IBakedModel model, IBakedModel missingModel) {
		if (model == null) {
			return true;
		}
		if (model == missingModel) {
			return true;
		}
		if (missingModel.equals(model)) {
			return true;
		}
		if ("net.minecraftforge.client.model.FancyMissingModel$BakedModel".equals(model.getClass().getName())) {
			return true;
		}

		return false;
	}
}