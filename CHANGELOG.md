# Changelog

## [0.5.4] - 2023-12-12
- Fix bug when running a script using the CLI. (#32)

## [0.5.3] - 2023-11-30
- Test support for Ghidra 10.4
- Improved handling of plugin installation
- Added `uninstall_plugin` script to uninstall misbehaving or unwanted plugins.

## [0.5.2] - 2023-07-17
- Added more helpful error message on failed program import.

## [0.5.1] - 2023-05-12
- Added support for Ghidra 10.3.

## [0.5.0] - 2023-04-20
- Added support for `jpype.startJVM` keyword arguments. (@clearbluejar)

## [0.4.1] - 2023-02-21
- Fixed bug causing the loading of updated plugins to fail.

## [0.4.0] - 2023-01-26
- Added manual plugin installation helper.
- Analyze the program by default, if provided, when entering the REPL with pyhidra.
- Added language and compiler arguments to `open_program` and `run_script`.
- Removed logging setup during startup (#21)

## [0.3.0] - 2022-11-29
- Added ability to install custom plugins from the launcher using `install_plugin()`.
- Added ability to register entry_points for customizing launcher before starting up.

## [0.2.1] - 2022-11-03
- Adds compatibility for Ghidra 10.2.

## [0.2.0] - 2022-09-27
- Fixed issue with terminal being taken over when running `pyhidraw` on Linux/Mac.
- Added cancel and reset buttons to the pyhidra interpreter in the Ghidra plugin.
- Force the pyhidra interpreter thread to finish and exit when the Ghidra plugin is closed.
- Honor the `safe_path` option introduced in Python 3.11. When set the script path will not be added to `sys.path` when running a script.
- Enforce command line interface requirement that the pyhidra script must be the last positional argument before the script arguments.
- Fixed bug causing `print` to be redirected in headless mode.

## [0.1.5] - 2022-08-29
- Add script path to `sys.path` while running a script to allow importing other scripts in the same directory.
- Added PyhidraBasics example script.
- Prevent exception during shutdown from checking a Java exceptions type after the JVM has terminated.
- Automatically alias Java packages by applying an underscore suffix to simplify importing when there is a name conflict.
- Fixed bug causing the extension metadata to be written as a dictionary to the extension name field.

## [0.1.4] - 2022-06-01
- Corrected server JVM library locating for openjdk on MAC
- Ignore unmatched lines in application.properties
- Prevent parsing of application.properties on import.
- Fix bug with `libjvm.debuginfo` getting chosen as JVM library.
- Added `get_current_interpreter()` function to detect and retrieve the interpreter within the Ghidra GUI.

## [0.1.3] - 2022-03-30
- Corrected server libjvm locating
- General cleanup

## [0.1.2] - 2022-03-18
- Fixed issue Java Path delimiter
- Fixed issue that caused subprocess to not run correctly on non-windows systems
- Set source target Java version to 11
- Corrected JVM path on non-windows systems
- Added Mac GUI fixes

## [0.1.1] - 2022-01-27
- Fixed issue from mishandled newline in the interpreter panel
- Fixed unstarted transaction when running code that alters a program database in the interpreter panel
- Fixed noise produced from an exception during analysis due to an analyzer using a script without acquiring a bundle host reference
- Fixed exception in open_program from attempting to use a non-public field in `FlatProgramAPI`

## 0.1.0 - 2021-06-14
- Initial release

[Unreleased]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.5.4...HEAD
[0.5.34]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.5.3...0.5.4
[0.5.3]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.5.2...0.5.3
[0.5.2]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.5.1...0.5.2
[0.5.1]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.5.0...0.5.1
[0.5.0]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.4.1...0.5.0
[0.4.1]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.4.0...0.4.1
[0.4.0]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.3.0...0.4.0
[0.3.0]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.2.1...0.3.0
[0.2.1]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.2.0...0.2.1
[0.2.0]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.1.5...0.2.0
[0.1.5]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.1.4...0.1.5
[0.1.4]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.1.3...0.1.4
[0.1.3]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.1.2...0.1.3
[0.1.2]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.1.1...0.1.2
[0.1.1]: https://github.com/dod-cyber-crime-center/pyhidra/compare/0.1.0...0.1.1
