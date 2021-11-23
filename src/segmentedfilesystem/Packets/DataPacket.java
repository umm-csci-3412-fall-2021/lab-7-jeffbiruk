package segmentedfilesystem.Packets;

import segmentedfilesystem.Packets.Packet;

import java.util.Arrays;

public class DataPacket extends Packet {
    byte status;
    byte fileID;
    int packetNumber;
    byte[] data;
    int packetSize;

    public DataPacket(byte[] bytes, int packetSize) {
        super(bytes);
        this.packetSize = packetSize;
        this.status = bytes[0];
        this.fileID = bytes[1];
        int x = Byte.toUnsignedInt(bytes[2]);
        int y = Byte.toUnsignedInt(bytes[3]);
        this.packetNumber = x * 256 + y;
        this.data = Arrays.copyOfRange(bytes, 4,packetSize);
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public byte getFileID() {
        return fileID;
    }

    public void setFileID(byte fileID) {
        this.fileID = fileID;
    }

    public int getPacketNumber() {
        return packetNumber;
    }

    public void setPacketNumber(int packetNumber) {
        this.packetNumber = packetNumber;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
