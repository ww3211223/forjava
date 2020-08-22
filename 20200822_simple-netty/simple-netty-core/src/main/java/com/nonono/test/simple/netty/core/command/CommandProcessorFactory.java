package com.nonono.test.simple.netty.core.command;

import com.nonono.test.ame.core.message.CmdDirective;

import java.util.HashMap;
import java.util.Map;

public class CommandProcessorFactory {

    private static final Map<CmdDirective, BaseCommand> commands = new HashMap<>();

    public static void register(BaseCommand<?> command) {
        commands.put(command.getDirective(), command);
    }

    public static ICommand getCommand(CmdDirective commandName) {
        return commands.get(commandName);
    }
}
