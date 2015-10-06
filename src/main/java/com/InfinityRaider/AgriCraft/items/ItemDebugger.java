package com.InfinityRaider.AgriCraft.items;

import com.InfinityRaider.AgriCraft.entity.EntityVillagerFarmer;
import com.InfinityRaider.AgriCraft.init.WorldGen;
import com.InfinityRaider.AgriCraft.reference.Names;
import com.InfinityRaider.AgriCraft.utility.DebugHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBush;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDebugger extends ItemAgricraft {
	
	public ItemDebugger() {
		super();
		this.setMaxStackSize(1);
	}

    @Override
    protected String getInternalName() {
        return Names.Objects.debugger;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if(!player.isSneaking()) {
            DebugHelper.debug(player, world, x, y, z);
        }
        else if(world.getBlock(x, y, z) instanceof BlockBush) {
            if(player.isSneaking()) {
                world.getBlock(x, y, z).updateTick(world, x, y, z, world.rand);
            }
        }
        else {
            if(!world.isRemote) {
                EntityVillager entityvillager = new EntityVillagerFarmer(world, WorldGen.getVillagerId());
                entityvillager.setLocationAndAngles(x + 0.5, y+1.0, z + 0.5, 0.0F, 0.0F);
                world.spawnEntityInWorld(entityvillager);
            }
        }
        return false;
    }

}
