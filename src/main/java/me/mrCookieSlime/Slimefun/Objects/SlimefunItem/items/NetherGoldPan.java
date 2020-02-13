package me.mrCookieSlime.Slimefun.Objects.SlimefunItem.items;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.cscorelib2.collections.RandomizedSet;
import io.github.thebusybiscuit.cscorelib2.protection.ProtectableAction;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SimpleSlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemUseHandler;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;

public class NetherGoldPan extends SimpleSlimefunItem<ItemUseHandler> implements RecipeDisplayItem {
	
	private final List<ItemStack> recipes;
	private final RandomizedSet<ItemStack> randomizer = new RandomizedSet<>();
	private int weights;

	public NetherGoldPan(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
		super(category, item, recipeType, recipe, 
			new String[] {"chance.QUARTZ", "chance.GOLD_NUGGET", "chance.NETHER_WART", "chance.BLAZE_POWDER", "chance.GLOWSTONE_DUST", "chance.GHAST_TEAR"},
			new Integer[] {50, 25, 10, 8, 5, 2}
		);
		
		recipes = Arrays.asList(
			new ItemStack(Material.SOUL_SAND), new ItemStack(Material.QUARTZ), 
			new ItemStack(Material.SOUL_SAND), new ItemStack(Material.GOLD_NUGGET),
			new ItemStack(Material.SOUL_SAND), new ItemStack(Material.NETHER_WART), 
			new ItemStack(Material.SOUL_SAND), new ItemStack(Material.BLAZE_POWDER), 
			new ItemStack(Material.SOUL_SAND), new ItemStack(Material.GLOWSTONE_DUST),
			new ItemStack(Material.SOUL_SAND), new ItemStack(Material.GHAST_TEAR)
		);
	}
	
	@Override
	public void postRegister() {
		add(new ItemStack(Material.QUARTZ), (int) Slimefun.getItemValue(getID(), "chance.QUARTZ"));
		add(new ItemStack(Material.GOLD_NUGGET), (int) Slimefun.getItemValue(getID(), "chance.GOLD_NUGGET"));
		add(new ItemStack(Material.NETHER_WART), (int) Slimefun.getItemValue(getID(), "chance.NETHER_WART"));
		add(new ItemStack(Material.BLAZE_POWDER), (int) Slimefun.getItemValue(getID(), "chance.BLAZE_POWDER"));
		add(new ItemStack(Material.GLOWSTONE_DUST), (int) Slimefun.getItemValue(getID(), "chance.GLOWSTONE_DUST"));
		add(new ItemStack(Material.GHAST_TEAR), (int) Slimefun.getItemValue(getID(), "chance.GHAST_TEAR"));

		if (weights < 100) {
			add(new ItemStack(Material.AIR), 100 - weights);
		}
	}
	
	private void add(ItemStack item, int chance) {
		randomizer.add(item, chance);
		weights += chance;
	}
	
	@Override
	public String getLabelLocalPath() {
		return "guide.tooltips.recipes.gold-pan";
	}
	
	@Override
	public ItemUseHandler getItemHandler() {
		return e -> {
			Optional<Block> block = e.getClickedBlock();
			
			if (block.isPresent()) {
				Block b = block.get();
				
				if (b.getType() == Material.SOUL_SAND && SlimefunPlugin.getProtectionManager().hasPermission(e.getPlayer(), b.getLocation(), ProtectableAction.BREAK_BLOCK)) {
					ItemStack output = randomizer.getRandom();

					b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
					b.setType(Material.AIR);

					if (output.getType() != Material.AIR) {
						b.getWorld().dropItemNaturally(b.getLocation(), output.clone());
					}
				}
			}
			e.cancel();
		};
	}

	@Override
	public List<ItemStack> getDisplayRecipes() {
		return recipes;
	}

}
