package in.derros.netfang.tcp;

import java.nio.ByteBuffer;

public class ParseValues {
	public double left;
	public double right;
	private byte[] sendingPacket;
	private boolean statusIndicator = true;
	public boolean constructArray() {
		if (left != 0 && right != 0 && statusIndicator == true) {
			ByteBuffer.wrap(sendingPacket).putDouble(0, left);
			ByteBuffer.wrap(sendingPacket).putDouble(1, right);
			return true;
		
		}
		else {
			return false;
		}
	}
}

