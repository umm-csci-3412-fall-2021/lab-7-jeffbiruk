package segmentedfilesystem;

import java.io.IOException;
import java.net.*;

public class FileRetriever {
	private String server;
	private int port;

	public FileRetriever(String server, int port) {
        // Save the server and port for use in `downloadFiles()`
        //...
		this.port = port;
		this.server = server;
	}

	public void downloadFiles() throws IOException {
        // Do all the heavy lifting here.
        // This should
        //   * Connect to the server
        //   * Download packets in some sort of loop
        //   * Handle the packets as they come in by, e.g.,
        //     handing them to some PacketManager class
        // Your loop will need to be able to ask someone
        // if you've received all the packets, and can thus
        // terminate. You might have a method like
        // PacketManager.allPacketsReceived() that you could
        // call for that, but there are a bunch of possible
        // ways.
		PacketManager packetManager = new PacketManager();
		DatagramSocket socket;

		try {
			socket = new DatagramSocket();
			byte[] buf = new byte[1028];
			DatagramPacket packet = new DatagramPacket(buf, buf.length, Inet4Address.getByName(server), port);
			socket.send(packet);
		} catch(IOException e) {
			e.printStackTrace();
			return;
		}

		while (!packetManager.haveReceivedAllPackets()){
			byte[] data = new byte[1028];
			DatagramPacket packet = new DatagramPacket(data, 1028);
			socket.receive(packet);
			packetManager.receive(packet);
		}
		packetManager.saveFiles();
	}

}
