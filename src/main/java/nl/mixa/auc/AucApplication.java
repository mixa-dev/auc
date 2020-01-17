package nl.mixa.auc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.purplefrog.jluadata.LuaParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nl.mixa.auc.model.auctioneer.AucScanData;
import nl.mixa.auc.model.scan.ItemScanData;
import nl.mixa.auc.parsing.IntermediateScanDataDeserializer;
import nl.mixa.auc.util.NumberUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootApplication
@AllArgsConstructor
public class AucApplication {

    public static void main(String[] args) {
        SpringApplication.run(AucApplication.class, args);
    }
/*
    @Override
    public void run(String... args) throws Exception {
        ClassPathResource resource = new ClassPathResource("/Auc-ScanData.lua");
        try (var is = resource.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));


            List<ItemScanData> flameLashItems = scanData.getScans().get("Flamelash").getRopes().stream().flatMap(it -> intermediateScanDataDeserializer.deserializeString(it, "Flamelash").stream()).collect(Collectors.toList());
            List<ItemScanData> mograineItems = scanData.getScans().get("Mograine").getRopes().stream().flatMap(it -> intermediateScanDataDeserializer.deserializeString(it, "Mograine").stream()).collect(Collectors.toList());
            Map<String, Long> flameLashCheepestOfEach = flameLashItems.stream().collect(Collectors.groupingBy(it -> it.getItemName())).entrySet().stream()
                    .map(it -> Map.entry(it.getKey(), it.getValue().stream().filter(l -> l.getBuyOut() != 0).min(Comparator.comparing(ItemScanData::pricePerItem))))
                    .filter(it -> it.getValue().isPresent())
                    .collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().get().pricePerItem()));

            Map<String, Long> mograineCheepestOfEach = mograineItems.stream().collect(Collectors.groupingBy(it -> it.getItemName())).entrySet().stream()
                    .map(it -> Map.entry(it.getKey(), it.getValue().stream().filter(l -> l.getBuyOut() != 0).min(Comparator.comparing(ItemScanData::pricePerItem))))
                    .filter(it -> it.getValue().isPresent())
                    .collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().get().pricePerItem()));

            Comparator<Map.Entry<String, ComparisonData>> comparing = Comparator.comparing(o -> o.getValue().percentageDifference());
            List<Map.Entry<String, ComparisonData>> collect = Stream.concat(flameLashCheepestOfEach.keySet().stream(), mograineCheepestOfEach.keySet().stream()).distinct()
                    .map(it -> {
                        Long mograine = mograineCheepestOfEach.get(it);
                        Long flameLash = flameLashCheepestOfEach.get(it);
                        if (mograine == null || flameLash == null) {
                            return null;
                        }
                        return Map.entry(it, new ComparisonData(flameLash, mograine));
                    })
                    .filter(Objects::nonNull)
                    .sorted(comparing.reversed())
                    .collect(Collectors.toList());

            System.out.println(IntStream.range(0,collect.size()).takeWhile(it -> !collect.get(it).getKey().equals("Arcane Crystal")).max().getAsInt());

            System.out.println("result");
            /**
             * scannedItems.stream().flatMap(it -> it.stream()).filter(it -> it.getItemName().equals("Dense Stone"))
             *         .map(it -> (long)(it.getBuyOut() / (double)it.getStacks()))
             *         .min(Long::compareTo)
             *         .map(it -> NumberUtils.toGold(it))
             *
             * */
/*
        }
    }



    @Getter
    public static class ComparisonData {
        private long flameLash;
        private long mograine;
        private String flGold;
        private String mgGold;

        public ComparisonData(long flameLash, long mograine) {
            this.flameLash = flameLash;
            this.mograine = mograine;
            this.flGold = NumberUtils.toGold(flameLash);
            this.mgGold = NumberUtils.toGold(mograine);
        }

        public Long difference() {
            return mograine - flameLash;
        }

        public Long percentageDifference() {
            return (mograine / flameLash) * 100;
        }

        public boolean isAtLeast5gDifference() {
            return difference() >= 5_00_00;
        }
    }
  */
}
