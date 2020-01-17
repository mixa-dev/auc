package nl.mixa.auc.parsing;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purplefrog.jluadata.LuaParser;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import nl.mixa.auc.model.auctioneer.AucScanData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log
@AllArgsConstructor
public class LuaParserService {

    private static final Pattern removeComment_single = Pattern.compile("(--[^\\[]*^)");
    private static final Pattern removeComment_long = Pattern.compile("(--\\[\\[.*]])");

    public Map<String, Object> parse(String toParse) {
        String lua = Stream.of(toParse.split("\n")).map(this::cleanCommentFromString).collect(Collectors.joining("\n"));
        LuaParser luaParser = new LuaParser(new StringReader(lua));
        try {
            return luaParser.parseDictionary();
        } catch (Exception e) {
            log.log(Level.WARNING, e, () -> "Error parsing string into lua");
            throw new RuntimeException(e);
        }
    }

    private String cleanCommentFromString(String str) {
        Matcher matcher = removeComment_single.matcher(str);
        if (matcher.find()) {
            return matcher.replaceAll("");
        }
        matcher = removeComment_long.matcher(str);
        if (matcher.find()) {
            return matcher.replaceAll("");
        }
        return str;
    }
}
