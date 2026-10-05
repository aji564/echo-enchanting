package echoenchanting.client;

import echoenchanting.client.screen.EchoEnchantingScreen;
import echoenchanting.registry.ModMenuTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;

public class EchoEnchantingClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		MenuScreens.register(ModMenuTypes.ECHO_ENCHANTING, EchoEnchantingScreen::new);
	}
}