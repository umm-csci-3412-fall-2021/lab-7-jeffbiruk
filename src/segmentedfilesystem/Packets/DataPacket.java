package segmentedfilesystem.Packets;

import segmentedfilesystem.Packets.Packet;

import java.util.Arrays;

public class DataPacket extends Packet {
    byte status;
    byte fileID;
    int packetNumber;
    byte[] data;

    public DataPacket(byte[] bytes) {
        super(bytes);
        this.status = bytes[0];
        this.fileID = bytes[1];
        int x = Byte.toUnsignedInt(bytes[2]);
        int y = Byte.toUnsignedInt(bytes[3]);
        this.packetNumber = x * 256 + y;
        this.data = Arrays.copyOfRange(bytes, 4,bytes.length);
    }
}
