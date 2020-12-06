import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DaySix
{
    public static void main(String[] args) throws IOException
    {
        Class<DayFour> clazz = DayFour.class;
        File file = new File(clazz.getResource("/daysix/input.txt").getFile());
        LinkedList<String> rows = new LinkedList<>(FileUtils.readLines(file, "UTF-8"));

        List<Integer> part1Answers = new ArrayList<>();
        List<Long> part2Answers = new ArrayList<>();
        while(!rows.isEmpty())
        {
            List<String> answers = extractGroups(rows);
            Map<Character, Long> mapOfAnswers = answers.stream().flatMap(s -> s.codePoints().mapToObj(c -> (char) c)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            var somePeopleAnsweredYes = mapOfAnswers.keySet().size();
            var everyoneAnsweredYes = mapOfAnswers.keySet().stream().filter(k -> mapOfAnswers.get(k) == answers.size()).count();
            part1Answers.add(somePeopleAnsweredYes);
            part2Answers.add(everyoneAnsweredYes);
        }

        System.out.println(part1Answers.stream().mapToLong(Integer::intValue).sum());
        System.out.println(part2Answers.stream().mapToLong(Long::longValue).sum());
    }

    private static List<String> extractGroups(LinkedList<String> rows)
    {
        List<String> answers = new ArrayList<>();
        do {
            if(!rows.getFirst().isBlank())
            {
                String first = rows.getFirst();
                answers.add(first);
            }
            rows.removeFirst();
        } while( !rows.isEmpty() && !rows.getFirst().isBlank());
        return answers;
    }
}
