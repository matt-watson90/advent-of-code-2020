import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DayOne
{
    public static void main(String[] args) throws IOException
    {
        Class clazz = DayOne.class;
        File file = new File(clazz.getResource("/dayone/input.txt").getFile());
        List<Integer> numbers = FileUtils.readLines(file, "UTF-8").stream().map(Integer::valueOf).collect(Collectors.toList());

        //Part 1
        var part1 = numbers.stream()
            .flatMap(y -> numbers.stream().filter(x -> x + y == 2020))
            .reduce(1, (a,b) -> a*b);

        System.out.println(part1);

        //Part 2
        var part2 = numbers.stream()
            .flatMap(x -> numbers.stream().flatMap(y -> numbers.stream().filter(z -> x + y + z == 2020)))
            .distinct()
            .reduce(1, (a,b) -> a*b);
        System.out.println(part2);
    }
}
