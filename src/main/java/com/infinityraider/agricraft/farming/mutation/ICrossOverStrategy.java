package com.infinityraider.agricraft.farming.mutation;

import com.infinityraider.agricraft.api.crop.IAgriCrop;
import com.infinityraider.agricraft.api.seed.AgriSeed;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nonnull;

/**
 * Base interface for different cross over strategies.
 */
public interface ICrossOverStrategy {

    @Nonnull
    Optional<AgriSeed> executeStrategy(IAgriCrop crop, Random rand);

}
