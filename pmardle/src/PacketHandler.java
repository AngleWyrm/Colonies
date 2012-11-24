package Colonies.pmardle.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		byte typ = dat.readByte();
		boolean hasStacks = dat.readByte() != 0;
		int[] items = new int[0];
		if (hasStacks) {
			items = new int[24];
			for (int i = 0; i < items.length; i++) {
				items[i] = dat.readInt();
			}
		}
		World world = ColonyChest.proxy.getClientWorld();
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if (te instanceof TileEntityColonyChest) {
			TileEntityColonyChest icte = (TileEntityColonyChest) te;
			icte.handlePacketData(typ, items);
		}
	}

	public static Packet getPacket(TileEntityColonyChest tileEntityColonyChest) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream dos = new DataOutputStream(bos);
		int x = tileEntityColonyChest.xCoord;
		int y = tileEntityColonyChest.yCoord;
		int z = tileEntityColonyChest.zCoord;
		int typ = tileEntityColonyChest.getType().ordinal();
		int[] items = tileEntityColonyChest.buildIntDataList();
		boolean hasStacks = (items != null);
		try {
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
			dos.writeByte(typ);
			dos.writeByte(hasStacks ? 1 : 0);
			if (hasStacks) {
				for (int i = 0; i < 24; i++) {
					dos.writeInt(items[i]);
				}
			}
		} catch (IOException e) {
			// UNPOSSIBLE?
		}
		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "IronChest";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = true;
		return pkt;
	}
}