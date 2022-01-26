package dc3.pyhidra.plugin;

import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.function.Consumer;

import dc3.pyhidra.plugin.PythonFieldExposer.ExposedFields;
import generic.jar.ResourceFile;
import ghidra.app.script.GhidraScript;
import ghidra.app.script.GhidraScriptProperties;
import ghidra.app.script.GhidraScriptUtil;
import ghidra.app.script.GhidraState;
import ghidra.app.util.headless.HeadlessScript;
import ghidra.program.model.address.Address;
import ghidra.program.model.listing.Program;
import ghidra.program.util.ProgramLocation;
import ghidra.program.util.ProgramSelection;
import ghidra.python.PythonScriptProvider;
import ghidra.util.SystemUtilities;
import ghidra.util.classfinder.ExtensionPointProperties;
import ghidra.util.task.TaskMonitor;

@ExtensionPointProperties(priority = 2)
public final class PyScriptProvider extends PythonScriptProvider {

	// set via reflection
	private static Consumer<GhidraScript> scriptRunner = null;

	@Override
	public GhidraScript getScriptInstance(ResourceFile sourceFile, PrintWriter writer)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		// check if python is running or not and if not let jython handle it
		if (scriptRunner == null || GhidraScriptUtil.isSystemScript(sourceFile)) {
			return super.getScriptInstance(sourceFile, writer);
		}

		GhidraScript script = SystemUtilities.isInHeadlessMode() ? new PyhidraHeadlessScript()
				: new PyhidraGhidraScript();
		script.setSourceFile(sourceFile);
		return script;
	}

	@ExposedFields(
		exposer = PyhidraGhidraScript._ExposedField.class,
		names = {
			"currentAddress", "currentLocation", "currentSelection",
			"currentHighlight", "currentProgram", "monitor",
			"potentialPropertiesFileLocs", "propertiesFileParams",
			"sourceFile", "state", "writer"
		},
		types = {
			Address.class, ProgramLocation.class, ProgramSelection.class,
			ProgramSelection.class, Program.class, TaskMonitor.class,
			List.class, GhidraScriptProperties.class,
			ResourceFile.class, GhidraState.class, PrintWriter.class
		}
	)
	public static class PyhidraGhidraScript extends GhidraScript implements PythonFieldExposer {

		@Override
		public void run() {
			scriptRunner.accept(this);
		}

		private static class _ExposedField extends ExposedField {

			public _ExposedField(String name, Class<?> type) {
				super(MethodHandles.lookup().in(PyhidraGhidraScript.class), name, type);
			}
		}
	}

	@ExposedFields(
		exposer = PyhidraHeadlessScript._ExposedField.class,
		names = {
			"currentAddress", "currentLocation", "currentSelection",
			"currentHighlight", "currentProgram", "monitor",
			"potentialPropertiesFileLocs", "propertiesFileParams",
			"sourceFile", "state", "writer"
		},
		types = {
			Address.class, ProgramLocation.class, ProgramSelection.class,
			ProgramSelection.class, Program.class, TaskMonitor.class,
			List.class, GhidraScriptProperties.class,
			ResourceFile.class, GhidraState.class, PrintWriter.class
		}
	)
	public static class PyhidraHeadlessScript extends HeadlessScript
			implements PythonFieldExposer {

		@Override
		public void run() {
			scriptRunner.accept(this);
		}

		private static class _ExposedField extends ExposedField {

			public _ExposedField(String name, Class<?> type) {
				super(MethodHandles.lookup().in(PyhidraHeadlessScript.class), name, type);
			}
		}
	}
}
