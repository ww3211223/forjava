package com.nonono.test.simple.netty.core.command;

import com.google.common.collect.Lists;
import com.nonono.test.ame.core.message.CmdDirective;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandProcessorFactory {

    private static final Map<CmdDirective, BaseCommand> commands = new HashMap<>();
    private static final List<IPongCommand> pongCommands = Lists.newArrayList();

    public static void register(BaseCommand<?> command) {
        commands.put(command.getDirective(), command);
    }

    public static void register(IPongCommand command) {
        pongCommands.add(command);
    }

    public static ICommand getCommand(CmdDirective commandName) {
        return commands.get(commandName);
    }

    public static List<IPongCommand> getPongCommands() {
        return pongCommands;
    }
}
