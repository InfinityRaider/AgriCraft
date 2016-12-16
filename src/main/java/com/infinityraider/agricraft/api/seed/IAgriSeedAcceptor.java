/*
 */
package com.infinityraider.agricraft.api.seed;

import com.infinityraider.agricraft.api.plant.IAgriPlant;
import com.infinityraider.agricraft.api.stat.IAgriStat;
import com.infinityraider.agricraft.api.plant.IAgriPlantAcceptor;
import com.infinityraider.agricraft.api.stat.IAgriStatAcceptor;
import java.util.Optional;

/**
 *
 *
 */
public interface IAgriSeedAcceptor extends IAgriPlantAcceptor, IAgriStatAcceptor {

    /**
     * Determines if a seed is valid for this specific instance.
     *
     * @param seed the seed to validate for the instance.
     * @return if the seed is valid for the instance.
     */
    default boolean acceptsSeed(AgriSeed seed) {
        return seed != null && acceptsPlant(seed.getPlant()) && acceptsStat(seed.getStat());
    }

    /**
     * Sets the seed associated with this instance.
     *
     * @param seed the seed to associate with this instance.
     * @return if the seed was successfully associated with the instance.
     */
    default boolean setSeed(AgriSeed seed) {
        if (seed != null && acceptsSeed(seed)) {
            return setPlant(seed.getPlant()) && setStat(seed.getStat());
        } else {
            return false;
        }
    }

    /**
     * Removes the seed associated with this instance.
     *
     * @return the removed seed, or null if no plant was removed.
     */
    default Optional<AgriSeed> removeSeed() {
        Optional<IAgriPlant> plant = removePlant();
        Optional<IAgriStat> stat = removeStat();
        if (plant.isPresent() && stat.isPresent()) {
            return Optional.of(new AgriSeed(plant.get(), stat.get()));
        } else {
            return Optional.empty();
        }
    }

}
