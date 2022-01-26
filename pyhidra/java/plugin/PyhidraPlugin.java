package dc3.pyhidra.plugin;

import java.io.PrintWriter;
import java.util.function.Consumer;

import ghidra.MiscellaneousPluginPackage;
import ghidra.app.plugin.core.interpreter.InterpreterPanelService;
import ghidra.app.plugin.PluginCategoryNames;
import ghidra.app.plugin.ProgramPlugin;
import ghidra.app.script.GhidraScript;
import ghidra.app.script.GhidraState;
import ghidra.framework.plugintool.PluginInfo;
import ghidra.framework.plugintool.PluginTool;
import ghidra.framework.plugintool.util.PluginStatus;
import ghidra.program.model.address.Address;
import ghidra.program.model.listing.Program;
import ghidra.program.util.ProgramLocation;
import ghidra.program.util.ProgramSelection;

@PluginInfo(
	status = PluginStatus.UNSTABLE,
	packageName = MiscellaneousPluginPackage.NAME,
	category = PluginCategoryNames.INTERPRETERS,
	shortDescription = "pyhidra plugin",
	description = "Native Python access in Ghidra. This plugin has no effect if Ghidra was not started via pyhidraw.",
	servicesRequired = { InterpreterPanelService.class }
)
public final class PyhidraPlugin extends ProgramPlugin {

	// set via reflection
	private static Consumer<PyhidraPlugin> initializer = (a) -> {
	};
	private Runnable finalizer = () -> {
	};

	public final InterpreterGhidraScript script = new InterpreterGhidraScript();

	public PyhidraPlugin(PluginTool tool) {
		super(tool, true, true, true);
		GhidraState state = new GhidraState(tool, tool.getProject(), null, null, null, null);
		// use the copy constructor so this state doesn't fire plugin events
		script.set(new GhidraState(state), null, null);
	}

	@Override
	public void init() {
		initializer.accept(this);
	}

	@Override
	public void dispose() {
		finalizer.run();
	}

	@Override
	protected void programActivated(Program program) {
		script.setCurrentProgram(program);
	}

	@Override
	protected void programDeactivated(Program program) {
		if (script.getCurrentProgram() == program) {
			script.setCurrentProgram(null);
		}
	}

	@Override
	protected void locationChanged(ProgramLocation location) {
		script.setCurrentLocation(location);
	}

	@Override
	protected void selectionChanged(ProgramSelection selection) {
		script.setCurrentSelection(selection);
	}

	@Override
	protected void highlightChanged(ProgramSelection highlight) {
		script.setCurrentHighlight(highlight);
	}

	public static class InterpreterGhidraScript extends GhidraScript {

		private InterpreterGhidraScript() {
		}

		@Override
		public void run() {
		}

		public Address getCurrentAddress() {
			return currentAddress;
		}

		public ProgramLocation getCurrentLocation() {
			return currentLocation;
		}

		public ProgramSelection getCurrentSelection() {
			return currentSelection;
		}

		public ProgramSelection getCurrentHighlight() {
			return currentHighlight;
		}

		public PrintWriter getWriter() {
			return writer;
		}

		public void setCurrentProgram(Program program) {
			currentProgram = program;
			state.setCurrentProgram(program);
		}

		public void setCurrentAddress(Address address) {
			currentAddress = address;
			state.setCurrentAddress(address);
		}

		public void setCurrentLocation(ProgramLocation location) {
			currentLocation = location;
			currentAddress = location != null ? location.getAddress() : null;
			state.setCurrentLocation(location);
		}

		public void setCurrentSelection(ProgramSelection selection) {
			currentSelection = selection;
			state.setCurrentSelection(selection);
		}

		public void setCurrentHighlight(ProgramSelection highlight) {
			currentHighlight = highlight;
			state.setCurrentHighlight(highlight);
		}
	}
}
