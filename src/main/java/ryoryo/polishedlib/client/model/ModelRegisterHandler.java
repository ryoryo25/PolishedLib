package ryoryo.polishedlib.client.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import com.google.common.base.Stopwatch;

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
import ryoryo.polishedlib.util.References;

public class ModelRegisterHandler {

	private static List<IModelLoader> loaders;

	private static final Function<ResourceLocation, TextureAtlasSprite> TEXTURE_GETTER = (ResourceLocation location) -> {
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());
	};

	static {
		loaders = new ArrayList<>();
	}

	public static void registerModelLoader(IModelLoader loader) {
		loaders.add(loader);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelBakeEvent event) {
		IRegistry<ModelResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
		final IBakedModel missingModel = event.getModelManager().getMissingModel();
		Set<ModelResourceLocation> modelLocations = modelRegistry.getKeys();

		PolishedLib.LOGGER.info("Start custom model loading");
		Stopwatch stopwatch = Stopwatch.createStarted();
		ProgressBar bakeBar = ProgressManager.push(References.MOD_NAME + ": registering", modelLocations.size());

		for(ModelResourceLocation location : modelLocations) {
			IModel model = null;

			bakeBar.step(location.toString());

			for(IModelLoader loader : loaders) {
				if(loader.accepts(location)) {
					try {
						model = loader.loadModel(location);
					} catch(Exception e) {
						PolishedLib.LOGGER.error("Unable to load model of " + location, e);
						break;
					}

					if(model != null && checkOverrideModel(modelRegistry, location, missingModel)) {
						modelRegistry.putObject(location, model.bake(model.getDefaultState(), DefaultVertexFormats.ITEM, TEXTURE_GETTER));
					}

					break;
				}
			}
		}

		ProgressManager.pop(bakeBar);
		stopwatch.stop();
		PolishedLib.LOGGER.info("Finish custom model loading : took " + stopwatch);
	}

	private boolean checkOverrideModel(IRegistry<ModelResourceLocation, IBakedModel> modelRegistry, ModelResourceLocation location, IBakedModel missingModel) {
		return isMissingModel(modelRegistry.getObject(location), missingModel);
	}

	private boolean isMissingModel(IBakedModel model, IBakedModel missingModel) {
		if(model == null) {
			return true;
		}
		if(model == missingModel) {
			return true;
		}
		if(missingModel.equals(model)) {
			return true;
		}
		if("net.minecraftforge.client.model.FancyMissingModel$BakedModel".equals(model.getClass().getName())) {
			return true;
		}

		return false;
	}
}