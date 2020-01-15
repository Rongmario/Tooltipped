package xyz.risingkingdom.tooltipped.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;

import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.command.arguments.IdentifierArgumentType.getIdentifier;
import static net.minecraft.command.arguments.IdentifierArgumentType.identifier;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.*;

import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.risingkingdom.tooltipped.FileHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class RegistryCommand {

    //private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.registry.failed", new Object[0]));

    private static final List<String> REGISTRY_SUGGESTIONS = new ArrayList<>(Arrays.asList("biome"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("tooltipped").then(argument("registry", identifier()).suggests(suggestedRegistries())
                .executes(ctx -> execute(ctx.getSource(), getIdentifier(ctx, "registry"))))
        .executes(ctx -> {
            ctx.getSource().sendError(new LiteralText("Missing argument! {Expected: Registry Types}"));
            return 1;
        }));
    }

    private static int execute(ServerCommandSource source, Identifier registerType) throws CommandSyntaxException {
        String registry = registerType.getPath();
        try {
            boolean succeed = FileHelper.writeRegistriesToFile(registry);
            if (succeed) {
                source.sendFeedback(new LiteralText("Check ".concat(registry.concat(".txt in your Root Directory for Dump."))).formatted(Formatting.AQUA), false);
            }
            else {
                source.sendError(new LiteralText("Wrong argument! {Expected: Registry Types}"));
            }
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static SuggestionProvider<ServerCommandSource> suggestedRegistries() {
        return ((ctx, builder) -> {
            String remaining = builder.getRemaining().toLowerCase(Locale.ROOT);
            if (REGISTRY_SUGGESTIONS.isEmpty()) return Suggestions.empty();
            for (String str : REGISTRY_SUGGESTIONS) {
                if (str.toLowerCase(Locale.ROOT).startsWith(remaining)) builder.suggest(str);
            }
            return builder.buildFuture();
        });
    }

}
