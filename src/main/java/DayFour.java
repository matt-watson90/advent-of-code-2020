import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DayFour
{
    public static class Predicates
    {

        protected static Predicate<String> birthYear = s -> Integer.parseInt(s) >= 1920 && Integer.parseInt(s) <= 2002;
        protected static Predicate<String> issueYear = s -> Integer.parseInt(s) >= 2010 && Integer.parseInt(s) <= 2020;
        protected static Predicate<String> expirationYear = s -> Integer.parseInt(s) >= 2020 && Integer.parseInt(s) <= 2030;
        protected static Predicate<String> hairColour = s -> s.matches("^[#][a-f0-9]{6}$");
        protected static Predicate<String> passportId = s -> s.matches("^[0-9]{9}$");
        protected static Predicate<String> eyeColour = s -> Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(s);

        protected static Predicate<String> height = s -> {
            var units = s.substring(s.length() - 2);
            var height = s.substring(0, s.length() - 2);

            if(units.equals("cm"))
            {
                return Integer.parseInt(height) >= 150 && Integer.parseInt(height) <= 193;
            }
            else if(units.equals("in"))
            {
                return Integer.parseInt(height) >= 59 && Integer.parseInt(height) <= 76;
            }
            else
            {
                return false;
            }
        };

    }
    public static void main(String[] args) throws IOException
    {
        Class<DayFour> clazz = DayFour.class;
        File file = new File(clazz.getResource("/dayfour/input.txt").getFile());
        LinkedList<String> rows = new LinkedList<>(FileUtils.readLines(file, "UTF-8"));

        var reqFields = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        var predicateMap = new HashMap<String, Predicate<String>>();
        predicateMap.put("byr", Predicates.birthYear);
        predicateMap.put("iyr", Predicates.issueYear);
        predicateMap.put("eyr", Predicates.expirationYear);
        predicateMap.put("hgt", Predicates.height);
        predicateMap.put("hcl", Predicates.hairColour);
        predicateMap.put("ecl", Predicates.eyeColour);
        predicateMap.put("pid", Predicates.passportId);

        Set<Map<String, String>> rowsAsKeyValuePairs = parseRows(rows);

        var count = rowsAsKeyValuePairs.stream()
            .filter(s -> s.keySet().containsAll(reqFields))
            .count();

        var count2 = rowsAsKeyValuePairs.stream()
                .filter(row -> row.keySet().containsAll(reqFields))
                .filter(row -> row.keySet().stream()
                        .map(k -> predicateMap.getOrDefault(k, (s -> true))
                        .test(row.get(k)))
                        .reduce(true, Boolean::logicalAnd))
                .count();

        System.out.println(count);
        System.out.println(count2);
    }

    private static Set<Map<String, String>> parseRows(LinkedList<String> rows)
    {
        List<String> parsedRows = new ArrayList<>();
        while(!rows.isEmpty())
        {
            List<String> passportRows = new ArrayList<>();
            do {
                if(!rows.getFirst().isBlank())
                {
                    String first = rows.getFirst();
                    passportRows.add(first);
                }
                rows.removeFirst();
            } while( !rows.isEmpty() && !rows.getFirst().isBlank());
            parsedRows.add(passportRows.stream().reduce("", (s1,s2) -> s1 + " " +s2));
        }
        return parsedRows.stream()
            .map(String::trim).map(s -> s.split("\\s")).map(Arrays::asList)
            .map(tokens -> tokens.stream().map(s -> s.split(":"))
            .collect(Collectors.toMap(s -> s[0], s -> s[1])))
            .collect(Collectors.toSet());
    }
}
