package echoenchanting.registry;

import echoenchanting.EchoEnchanting;
import echoenchanting.menu.EchoEnchantingMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenuTypes {
    public static final MenuType<EchoEnchantingMenu> ECHO_ENCHANTING =
            Registry.register(BuiltInRegistries.MENU, EchoEnchanting.id("echo_enchanting"), new MenuType<>(EchoEnchantingMenu::new, FeatureFlags.DEFAULT_FLAGS));

    public static void initialize() {

    }
}
