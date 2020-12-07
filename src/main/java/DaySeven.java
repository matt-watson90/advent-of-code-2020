import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class DaySeven
{

    private static final String BAGS_CONTAIN = " bags contain";

    public static class Bag
    {
        String colour;

        public Bag(String colour)
        {
            this.colour = colour;
        }

        public String getColour()
        {
            return colour;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
                return true;
            if (!(o instanceof Bag))
                return false;
            Bag bag = (Bag) o;
            return Objects.equals(colour, bag.colour);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(colour);
        }

        @Override
        public String toString()
        {
            return new StringJoiner(", ", Bag.class.getSimpleName() + "[", "]").add("colour='" + colour + "'").toString();
        }
    }

    public static class Connection
    {
        Integer number;
        Bag connectedBag;

        public Connection(Integer number, Bag connectedBag)
        {
            this.number = number;
            this.connectedBag = connectedBag;
        }

        public Bag getConnectedBag()
        {
            return connectedBag;
        }

        public Integer getNumber()
        {
            return number;
        }

        @Override
        public String toString()
        {
            return new StringJoiner(", ", Connection.class.getSimpleName() + "[", "]").add("number=" + number).add("connectedBag=" + connectedBag).toString();
        }
    }

    public static void main(String[] args) throws IOException
    {
        Class<DaySeven> clazz = DaySeven.class;
        File file = new File(clazz.getResource("/dayseven/input.txt").getFile());
        List<String> rules = FileUtils.readLines(file, "UTF-8");

        Map<Bag, Set<Connection>> bags = parseBags(rules);
        var objects = new HashSet<Bag>();
        getBagsThatCanEventuallyContain(bags, new Bag("shiny gold"), objects);
        System.out.println(objects.size());
        System.out.println(getTotalBags(bags,new Bag("shiny gold")));
    }

    private static Map<Bag, Set<Connection>> parseBags(List<String> rules)
    {
        Map<Bag, Set<Connection>> bags = new HashMap<>();
        rules.forEach(r-> {
            var index = r.indexOf(BAGS_CONTAIN);
            var colour = r.substring(0, index);
            var bag = new Bag(colour);
            //dotted tan bags contain 1 wavy green bag, 1 dim plum bag.
            var connectionString = r.substring(index + BAGS_CONTAIN.length()+1, r.length()-1);
            var connectionStringParts = connectionString.split(",");
            var connections = Arrays.stream(connectionStringParts)
                    .map(String::trim)
                    .filter(s -> !s.equals("no other bags"))
                    .map(s -> new Connection(Integer.parseInt(s.substring(0, 1)), new Bag(s.substring(2, s.length()-4).trim())))
                    .collect(Collectors.toSet());
            bags.put(bag, connections);
        });
        return bags;
    }

    public static void getBagsThatCanEventuallyContain(Map<Bag, Set<Connection>> bags, Bag startingBag, Set<Bag> bagsSoFar)
    {
        var bagsWhichCanHold = bags.entrySet().stream()
                .filter(e -> e.getValue().stream().map(Connection::getConnectedBag).anyMatch(startingBag::equals))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        if(bagsWhichCanHold.isEmpty())
        {
            return;
        }

        bagsSoFar.addAll(bagsWhichCanHold);
        bagsWhichCanHold.forEach(bag -> getBagsThatCanEventuallyContain(bags, bag, bagsSoFar));
    }

    public static Integer getTotalBags(Map<Bag, Set<Connection>> bags, Bag startingBag)
    {
        var connections = bags.get(startingBag);
        if(startingBag == null || connections.isEmpty())
        {
            return 0;
        }
        return connections.stream().mapToInt(nextBag -> nextBag.getNumber() + (nextBag.getNumber() * getTotalBags(bags, nextBag.getConnectedBag()))).sum();
    }
}
