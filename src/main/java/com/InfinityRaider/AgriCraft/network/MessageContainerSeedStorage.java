package com.InfinityRaider.AgriCraft.network;

import com.InfinityRaider.AgriCraft.container.ContainerSeedStorageController;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.Iterator;
import java.util.List;

public class MessageContainerSeedStorage implements IMessage {
    private Item item;
    private int meta;
    private EntityPlayer player;
    private int offset;

    public MessageContainerSeedStorage() {}

    public MessageContainerSeedStorage(EntityPlayer player, Item item, int meta, int offset) {
        this.item = item;
        this.meta = meta;
        this.player = player;
        this.offset = offset;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        int itemNameLength = buf.readInt();
        String itemName = new String(buf.readBytes(itemNameLength).array());
        this.item = (Item) Item.itemRegistry.getObject(itemName);
        this.meta = buf.readInt();
        int playerNameLength = buf.readInt();
        String playerName = new String(buf.readBytes(playerNameLength).array());
        this.player = this.getPlayer(playerName);
        this.offset = buf.readInt();
    }

    private EntityPlayer getPlayer(String name) {
        List list = MinecraftServer.getServer().getConfigurationManager().playerEntityList;
        EntityPlayer player = null;
        Iterator iterator = list.iterator();
        while (iterator.hasNext() && player==null) {
            EntityPlayer nextPlayer = (EntityPlayer)iterator.next();
            if(nextPlayer.getDisplayName().equals(name)) {
                player = nextPlayer;
            }
        }
        return player;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        String itemName = Item.itemRegistry.getNameForObject(this.item);
        buf.writeInt(itemName.length());
        buf.writeBytes(itemName.getBytes());
        buf.writeInt(this.meta);
        String playerName = this.player.getDisplayName();
        buf.writeInt(playerName.length());
        buf.writeBytes(playerName.getBytes());
        buf.writeInt(this.offset);
    }

    public static class MessageHandler implements IMessageHandler<MessageContainerSeedStorage, IMessage> {
        @Override
        public IMessage onMessage(MessageContainerSeedStorage message, MessageContext context) {
            Container container = message.player.openContainer;
            if(container!=null && container instanceof ContainerSeedStorageController) {
                ContainerSeedStorageController storage = (ContainerSeedStorageController) container;
                storage.clearActiveEntries();
                storage.setActiveEntries(new ItemStack(message.item, 1, message.meta), message.offset);
                storage.detectAndSendChanges();
            }
            return null;
        }
    }
}
