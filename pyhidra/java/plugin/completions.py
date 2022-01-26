import builtins
from keyword import iskeyword
from typing import Mapping, Sequence
from rlcompleter import Completer
from types import CodeType, FunctionType, MappingProxyType, MethodType, ModuleType

from docking.widgets.label import GLabel
from ghidra.app.plugin.core.console import CodeCompletion
from java.awt import Color
from java.util import Arrays, Collections
from jpype import JPackage
from jpype.types import JDouble, JFloat, JInt, JLong, JShort


NoneType = type(None)

CLASS_COLOR = Color(0, 0, 255)
CODE_COLOR = Color(0, 64, 0)
FUNCTION_COLOR = Color(0, 128, 0)
INSTANCE_COLOR = Color(128, 0, 128)
MAP_COLOR = Color(64, 96, 128)
METHOD_COLOR = Color(0, 128, 128)
NULL_COLOR = Color(255, 0, 0)
NUMBER_COLOR = Color(64, 64, 64)
PACKAGE_COLOR = Color(128, 0, 0)
SEQUENCE_COLOR = Color(128, 96, 64)

_TYPE_COLORS = {
    type: CLASS_COLOR,
    CodeType: CODE_COLOR,
    FunctionType: FUNCTION_COLOR,
    dict: MAP_COLOR,
    MappingProxyType: MAP_COLOR,
    MethodType: METHOD_COLOR,
    NoneType: NULL_COLOR,
    int: NUMBER_COLOR,
    float: NUMBER_COLOR,
    complex: NUMBER_COLOR,
    JShort: NUMBER_COLOR,
    JInt: NUMBER_COLOR,
    JLong: NUMBER_COLOR,
    JFloat: NUMBER_COLOR,
    JDouble: NUMBER_COLOR,
    ModuleType: PACKAGE_COLOR,
    JPackage: PACKAGE_COLOR
}


class PythonCodeCompleter(Completer):
    """
    Code Completer for Ghidra's Python interpreter window
    """

    _BUILTIN_ATTRIBUTE = object()
    __slots__ = ('cmd',)

    def __init__(self, py_console):
        super().__init__(py_console.locals.get_static_view())
        self.cmd: str

    def _get_label(self, i: int) -> GLabel:
        match = self.matches[i].rstrip("()")
        label = GLabel(match)
        attr = self.namespace.get(match, PythonCodeCompleter._BUILTIN_ATTRIBUTE)
        if attr is PythonCodeCompleter._BUILTIN_ATTRIBUTE:
            if iskeyword(match.rstrip()):
                return label
            attr = builtins.__dict__.get(match, PythonCodeCompleter._BUILTIN_ATTRIBUTE)
            if attr is not PythonCodeCompleter._BUILTIN_ATTRIBUTE and not match.startswith("__"):
                attr = builtins.__dict__[match]
            else:
                return label
        color = _TYPE_COLORS.get(type(attr), PythonCodeCompleter._BUILTIN_ATTRIBUTE)
        if color is PythonCodeCompleter._BUILTIN_ATTRIBUTE:
            t = type(attr)
            if isinstance(t, Sequence):
                color = SEQUENCE_COLOR
            elif isinstance(t, Mapping):
                color = MAP_COLOR
            else:
                color = INSTANCE_COLOR
        label.setForeground(color)
        return label

    def _supplier(self, i: int) -> CodeCompletion:
        insertion = self.matches[i][len(self.cmd):]
        return CodeCompletion(self.cmd, insertion, self._get_label(i))

    def get_completions(self, cmd: str):
        """
        Gets all the possible CodeCompletion(s) for the provided cmd

        :param cmd: The code to complete
        :return: A Java List of all possible CodeCompletion(s)
        """
        try:
            self.cmd = cmd
            if self.complete(cmd, 0) is None:
                return Collections.emptyList()
            res = CodeCompletion[len(self.matches)]
            Arrays.setAll(res, self._supplier)
            return Arrays.asList(res)
        except:  # pylint: disable=bare-except
            return Collections.emptyList()
