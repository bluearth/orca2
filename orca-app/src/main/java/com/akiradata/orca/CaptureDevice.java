package com.akiradata.orca;

import java.util.ArrayList;
import java.util.List;

import org.controlsfx.dialog.Dialog;

public abstract class CaptureDevice {

	public enum State {
		DEFAULT, READY, CAPTURING, DISPOSED
	}

	private State currentState = State.DEFAULT;

	private List<CaptureDeviceEventListener> eventListeners = new ArrayList<CaptureDeviceEventListener>();

	public abstract void configure();

	public abstract void capture();

	public final void addEventListener(CaptureDeviceEventListener listener) {
		this.eventListeners.add(listener);
	}

	protected final void fireEvent(CaptureDeviceEvent e) {

		eventListeners.parallelStream().forEach(eventListener -> {
			switch (e.getType()) {
			case CONFIG_REQUESTED:
				eventListener.configurationRequested(e);
				break;
			case CAPTURE_STARTED:
				eventListener.captureStarted(e);
				break;
			case PAGE_STARTED:
				eventListener.pageStarted(e);
				break;
			case PAGE_COMPLETED:
				eventListener.pageCompleted(e);
				break;
			case CAPTURE_COMPLETED:
				eventListener.captureCompleted(e);
				break;
			case CAPTURE_EXCEPTION:
				eventListener.captureException(e);
				break;
			default:
				break;
			}
		});

	}

	public abstract void release();

	public final State getState() {
		return this.currentState;
	}

	public final void setState(State newState) throws CaptureDeviceException {
		// TODO validate state transition
		// some states can only be reached by transitioning from some other
		// states
		// invalid state changes should throw IllegalStateException
		this.currentState = newState;

	}

	public abstract String getName();

	public abstract String getVendor();

	public abstract String getDriverVersion();

	public abstract Dialog createConfigurationDialog(Object owner);

}
