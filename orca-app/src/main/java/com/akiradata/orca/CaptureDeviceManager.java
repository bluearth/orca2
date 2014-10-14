package com.akiradata.orca;

import java.util.ArrayList;
import java.util.List;

public class CaptureDeviceManager {

	public static List<CaptureDevice> enumerate() {
		// TODO actually call JTWAIN stub to enumerate available capture devices

		ArrayList<CaptureDevice> devices = new ArrayList<>();

		DirectoryCaptureDevice capDev = new DirectoryCaptureDevice();

		devices.add(capDev);

		return devices;
	}

}
