package com.example.gui_optionalassignment1_doroudianishayan.store;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.gui_optionalassignment1_doroudianishayan.model.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.gui_optionalassignment1_doroudianishayan.util.TimeUtil.parseTimeFlexible;

public class DataStore {
    /** Central in-memory store for all swimmers and results */
    private final ObservableList<Swimmer> swimmers = FXCollections.observableArrayList();
    private final ObservableList<SwimResult> results = FXCollections.observableArrayList();

    public ObservableList<Swimmer> getSwimmers() { return swimmers; }
    public ObservableList<SwimResult> getResults() { return results; }

    public void addSwimmer(Swimmer s) { swimmers.add(s); }
    public void removeSwimmer(Swimmer s) {
        swimmers.remove(s);
        results.removeIf(r -> r.getSwimmerId().equals(s.getId()));
    }

    public void addResult(SwimResult r) { results.add(r); }

    /** Return best time (PB) for swimmer in event, or empty if none */
    public OptionalDouble personalBest(String swimmerId, SwimEvent event) {
        return results.stream()
                .filter(r -> r.getSwimmerId().equals(swimmerId) && r.getEvent().equals(event))
                .mapToDouble(SwimResult::getTimeSeconds).min();
    }

    /** Top N swimmers by PB in a given event */
    public List<Map.Entry<Swimmer, Double>> topByEvent(SwimEvent event, int n) {
        Map<String, Double> best = new HashMap<>();
        for (SwimResult r : results) {
            if (r.getEvent().equals(event)) {
                best.merge(r.getSwimmerId(), r.getTimeSeconds(), Math::min);
            }
        }
        List<Map.Entry<String, Double>> sorted = best.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(n).collect(Collectors.toList());

        Map<String, Swimmer> byId = swimmers.stream().collect(Collectors.toMap(Swimmer::getId, s -> s));
        return sorted.stream()
                .map(e -> Map.entry(byId.get(e.getKey()), e.getValue()))
                .filter(e -> e.getKey() != null)
                .collect(Collectors.toList());
    }

    /** CSV format (results):
     * swimmerName,eventDistance,eventStroke,date(yyyy-MM-dd),meet,time
     * time supports "M:SS.ss" or "SS.ss"
     */
    public void importResultsCsv(File f, List<Swimmer> autoCreateOnUnknown, List<SwimEvent> knownEvents) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] t = line.split(",");
                if (t.length < 6) continue;
                String swimmerName = t[0].trim();
                int distance = Integer.parseInt(t[1].trim());
                SwimEvent.Stroke stroke = SwimEvent.Stroke.valueOf(t[2].trim().toUpperCase());
                LocalDate date = LocalDate.parse(t[3].trim());
                String meet = t[4].trim();
                String timeStr = t[5].trim();

                double secs = parseTimeFlexible(timeStr);

                SwimEvent ev = knownEvents.stream()
                        .filter(e -> e.getDistance() == distance && e.getStroke() == stroke)
                        .findFirst().orElse(new SwimEvent(distance, stroke));

                Swimmer s = swimmers.stream().filter(x -> x.getName().equalsIgnoreCase(swimmerName)).findFirst().orElse(null);
                if (s == null) {
                    s = new Swimmer(swimmerName, "N/A", "N/A", null);
                    swimmers.add(s);
                    if (autoCreateOnUnknown != null) autoCreateOnUnknown.add(s);
                }

                results.add(new SwimResult(s.getId(), ev, secs, date, meet));
            }
        }
    }

    public void exportResultsCsv(File f) throws IOException {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            pw.println("# swimmerName,distance,stroke,date(yyyy-MM-dd),meet,time");
            Map<String, Swimmer> byId = swimmers.stream().collect(Collectors.toMap(Swimmer::getId, s->s));
            for (SwimResult r : results) {
                Swimmer s = byId.get(r.getSwimmerId());
                if (s == null) continue;
                pw.printf("%s,%d,%s,%s,%s,%.2f%n",
                        s.getName(),
                        r.getEvent().getDistance(),
                        r.getEvent().getStroke().name(),
                        r.getDate() != null ? r.getDate().toString() : "",
                        r.getMeet() != null ? r.getMeet() : "",
                        r.getTimeSeconds());
            }
        }
    }
}
