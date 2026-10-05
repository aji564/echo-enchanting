# Echo Enchanting
This mod aims to implement a system similar to the way enchanting works in Minecraft Dungeons 2, 
with enchanted books being reusable, but require specific ways of getting each enchantment, 
and costing experience and echo shards (or a different configurable catalyst) to apply to tools.
## Specifics
### Anvils
Any anvil operation that specifically involves an enchanted book in the addition slot has been disabled via a mixin.
### Enchanting Table
Applying enchanted books is now done via the enchanting table, and the vanilla functions and GUI for it have been changed.
There are 3 slots in the new menu: one for the item to enchant, one for enchanted books, and one for catalysts.
#### Beyond this point is still todo
There should be 2 buttons on the new menu, one that toggles between apply mode and disenchant mode, and another that
confirms changes made.
#### Apply Mode
When set to apply mode and an enchantable item and book that can be applied to it are placed in their slots, text should
appear in the GUI that show the item's current enchantments, changes that occur if the book is applied, and reasons it 
can't be applied if any (incompatible enchantment, presence of conflicting enchantments, not enough catalysts, etc.).
<br><br>
Say you place an iron sword with sharpness 1 already applied in along with an enchanted book containing Unbreaking 3.
The text should look something like this:<br><br>
Iron Sword  
Sharpness I<br>
+Unbreaking I<br><br>
Apply cost: 1 level, 1 echo shard<br><br>
Or if you try to apply an enchantment that conflicts with something already present, it should like something like this:<br><br>
Iron Sword<br>
Sharpness I<br>
+Smite I<br><br>
Cannot apply: conflicting enchantments<br><br>
The text color for the added enchantment should be green, and the color for error messages should be red.  
When applying enchantments, only one level is applied with each operation, and the level of the enchantment on the book 
denotes the maximum level it will be able to apply, so a book with Efficiency 3 can apply the Efficiency enchantment on
a tool up to level 3, but no higher.<br><br>
Application will require experience and a catalyst. By default, it will always cost 1 catalyst and experience levels
equal to the level of the enchantment being applied, so applying Unbreaking 2 would cost 2 levels and 1 catalyst.
Experience costs and catalyst costs should be configurable, and accepted catalysts should be denoted via an item tag.  
Catalyst costs should also have a non default config option to cost more for applying higher level enchantments.  
For experience, there should be a required level the player needs to apply the enchantment, based on the formula:  
[max configured required level] * [percentage to max level of the applied enchant (i.e. 2 is 40% to max if max is 5)]  
The number of consumed levels should be equal to the level of the enchantment being applied by default.  
Books themselves will be regulated in a way so they only have one enchantment at a time, but book handling should still
check for multiple enchantments and show an error if it has more than one.
#### Disenchant Mode
When in disenchant mode, players can use the enchanting table to remove specific enchantments from items.
This can be done at no cost, but doesn't return any experience of catalysts.
To remove an enchantment, players should place an enchanted item in the equipment slot to bring up in the menu a list of
the enchantments it has, select one on the same menu, and then click the confirm button.
Enchantments that are considered curses cannot be removed this way.
#### Possible Features
+ A special catalyst players can use in disenchant mode to remove curses, by default something somewhat difficult to get
and also configurable through an item tag
+ The default catalyst being echo shards means enchanting will be difficult to do, since they're sourced exclusively through
ancient cities and are non-renewable; add a config option to allow easily injection of chosen catalysts into specific loot
tables or categories of loot tables (categories can probably just be the different directories under vanilla loot table data)
and make the default be echo shards rarely dropping from normal hostile mobs killed by a player, and dropping multiple
from bosses
+ Config option to randomize the obtaining/upgrade path so it's different for every world, and have tooltips provide hints
instead of specifics for how to get/upgrade, off by default
### Enchanted Books
Enchanted books should no longer be obtainable in the usual ways through villager trading, loot tables, or fishing.
Instead, each individual enchantment should have a specific way to obtain it at level 1, with an upgrade path to get the
book to higher levels.
#### Obtaining
Ways to obtain each enchantment should be denoted on the book's tooltip (i.e. "Found in desert temple chests"), and may include:
+ Crafting
+ Villager trades
+ Loot tables (mobs, chests, bartering, etc.)

The mod should go through all of these at runtime and remove the normal randomly enchanted books.  
If other mods add enchantments, automatically search for valid places to inject a level 1 book from the namespace (mod or datapack)
that adds it from the list above, but adding villager trades in this case should only happen if a mod adds a new profession.
If a valid place for obtaining isn't found from the mod's namespace, randomly choose a vanilla place from the list above.
#### Upgrading
The way to upgrade any specific book should be denoted on its tooltip (i.e. "Craft book with 8 blocks of redstone"), and may include:
+ Crafting
+ Villager trades
+ Smithing
+ Dropping the book in a specific location (on or into a certain block, in a certain structure, in a specific dimension, etc.)
+ Having the book in a player's inventory and having the player pass a condition (on or in a certain block, in a certain 
structure, in a specific dimension, having certain potion effects, etc.)
### Config
This mod should be as configurable as possible, so server owners/modpack makers can customize the experience to their liking.
+ Catalyst cost options: what can be used as a catalyst (defaults to item tag #echo_enchanting:application_catalysts, can be
set to a list of item tags or item IDs), option to disable needing catalysts for application (default false),
base catalyst cost (default 1), option to apply a multiplier based on the enchant level being applied (default false),
multiplier from the last option (default 1, ignored if last option is false), option to apply an additive rising cost based 
on the level of enchantment being applied (default false), additive cost per level from the last option (default 1, ignored 
if last option is false)
+ Experience level requirement/cost options: maximum required level (base 30), list of enchantments that use a different 
maximum required level (default empty, will require a namespace:id combo + the new max required level, anything not on this 
list will use the master level from the previous option), base experience level cost (default 1), option to apply an additive 
rising cost based on the enchant level being applied (default true), additive cost per level from the last option (default
1, ignored if last option is false), option to apply a multiplier based on the enchant level being applied (default false), 
multiplier from the last option (default 1, ignored if last option is false)
+ Enchanting table disenchant mode options: option to enable the feature (default true), what can be used as a catalyst
for curse removal (defaults to item tag #echo_enchanting:curse_removal_catalysts, can be set to a list of item tags or IDs),
option to require removal catalysts when removing curses (default true), option to require removal catalysts to remove any
enchantment (default false), what can be used as a catalyst of enchantment removal from the previous option (defaults to
item tag #echo_enchanting:removal_catalysts, can be set to a list of item tags or IDs, ignored if last option is false),
option to allow curse removal catalysts to be usable for removing normal enchants as well (default true, ignored if option
before last is false), list of enchants that are blacklisted from being removed at all (default empty)
+ Anvil options: disable applying enchanted books with an anvil (default true), list of enchants that are still allowed to
be applied with an anvil (default empty)
+ Enchanted book obtaining options: replace randomly enchanted books (default true), option to randomize methods for obtaining
all enchantments differently per world (default false), auto-populated list of all registered enchantments with their obtain
method (vanilla defaults below, ignored if last option is true, each enchant must have at least one obtain method, must contain
obtain category from previous section and options pertaining to the category being used, something like: crafted with book
and quill and 8 redstone blocks, traded with book and quill and 40 emeralds from a butcher, injected into loot table
minecraft:chests/ancient_city)
+ Enchanted book upgrade options: auto-populated list of all registered enchantments with their upgrade methods for each
level (vanilla defaults below, ignored if randomize option is true, must contain at least one upgrade method per enchant
level up to the enchant's max level, must contain upgrade category from previous section and options pertaining to the
category being used, something like: crafted with 4 armadillo scutes and 4 turtle scutes, smithed with a netherite upgrade
template and a netherite ingot, dropped into lava while in a nether fortress, in a player's inventory while they have
dolphin's grace inside a shipwreck, etc.)
+ Client options: show obtaining tooltips (default true), show upgrading tooltips (default true)


[Fabric Documentation page](https://docs.fabricmc.net/develop/getting-started/creating-a-project#setting-up) 
