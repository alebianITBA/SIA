package ar.edu.itba.sia.c12017.g5.gridlock.utilities;

import gps.GPSEngine;
import gps.GPSNode;
import gps.api.GPSState;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphBuilder {
    final static boolean ENABLED = false;

    private List<GPSNode> graph = new ArrayList<>();
    private List<GPSNode> solutionNodes = new ArrayList<>();
    private GPSEngine engine;

    public GraphBuilder(GPSEngine engine) {
        this.engine = engine;
    }

    public void add(GPSNode node) {
        if (ENABLED) {
            graph.add(node);
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(";\n    ", "{\n    ", "\n}\n");
        sj.add("graph [fontname =\"Courier\", fontsize=10, overlap=scalexy, splines=ortho, nodesep=1, ranksep=2, ordering=\"out\"]");
        sj.add("node [shape=\"plaintext\", fontname = \"Courier\", fontsize=10]");
        sj.add("edge [fontname = \"Courier\", fontsize=10]");
        AtomicInteger nodesIndex = new AtomicInteger();
        AtomicInteger edgesIndex = new AtomicInteger();
        Map<GPSState,String> added = new HashMap<>();
        Map<Integer,List<String>> ranks = new HashMap<>();
        GPSNode sNode = engine.getSolutionNode();
        do {
            this.solutionNodes.add(sNode);
        } while ((sNode = sNode.getParent()) != null);
        graph.forEach(node -> {
            boolean inSolution = solutionNodes.contains(node);
            String cId = added.computeIfAbsent(
                    node.getState(),
                    state -> {
                        String k = String.format("n%09d", nodesIndex.incrementAndGet());
                        ranks.computeIfAbsent(node.getCost(), integer -> new ArrayList<>()).add(k);
                        sj.add(k + "["
                                + (inSolution ? "shape=\"box\", fillcolor=orange, style=\"rounded,filled\", " : "")
                                + "label=\"" + state.toString().replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n")
                                + "\"]");
                        return k;
                    }
            );
            if (node.getParent() != null) {
                String pId = added.computeIfAbsent(
                        node.getParent().getState(),
                        state -> {
                            String k = String.format("n%09d", nodesIndex.incrementAndGet());
                            ranks.computeIfAbsent(node.getParent().getCost(), integer -> new ArrayList<>()).add(k);
                            sj.add(k + "["
                                    + (inSolution ? "shape=\"box\", fillcolor=orange, style=\"rounded,filled\", " : "")
                                    + "label=\"" + state.toString().replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n")
                                    + "\"]");
                            return k;
                        }
                );
                sj.add(pId + "->" + cId + "["
                        + "headlabel=\"  " + edgesIndex.incrementAndGet() + "#" + node.getGenerationRule().getName() + "  \"]");
            }

        });
        ranks.forEach((rank, nodes) -> sj.add("{rank = same; " + String.join("; ", nodes) + "}"));
        return "digraph graphname " + sj.toString();
    }

    public void writeToFile() {
        if (ENABLED) {
            try {
                try (BufferedWriter writer = Files.newBufferedWriter(
                        Paths.get("/tmp/solution_" + engine.getClass().getSimpleName() + ".dot")
                )) {
                    writer.write(this.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
