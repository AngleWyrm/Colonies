package colonies.anglewyrm.src;

import net.minecraft.src.INetworkManager;
import net.minecraft.src.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;


public class PacketHandler implements IPacketHandler {
    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player)
    {
        // TODO: Add packet handling.
    }
}
