package com.infinityraider.agricraft.api.v1.items;

import net.minecraft.item.ItemStack;
import com.infinityraider.agricraft.api.v1.stat.IAgriStat;

/**
 * An Interface for trowel objects. Likely will be modified, so that the trowel
 * instead drops something like the clippings. Probably will wind up being a marker interface instead.
 *
 * @author RlonRyan
 */
public interface ITrowel extends IAgriCraftItem {

	/**
	 * Return true if this trowel is currently carrying a plant
	 */
	boolean hasSeed(ItemStack trowel);

	/**
	 * Return true if the seed currently being carried is analyzed, return false
	 * if it is not or if there is no seed
	 */
	boolean isSeedAnalysed(ItemStack trowel);

	/**
	 * This is called to analyze the seed currently being carried
	 */
	void analyze(ItemStack trowel);

	/**
	 * This returns the seed currently on the trowel
	 */
	ItemStack getSeed(ItemStack trowel);

	/**
	 * This returns the growthStage of the plant currently being carried
	 */
	int getGrowthStage(ItemStack trowel);

	/**
	 * Sets the seed to the trowel, returns true if successful
	 */
	boolean setSeed(ItemStack trowel, ItemStack seed, int growthStage);

	/**
	 * Clears the seed from the trowel
	 */
	void clearSeed(ItemStack trowel);

	/**
	 * Gets the stats from the seed
	 */
	IAgriStat getStats(ItemStack trowel);

}