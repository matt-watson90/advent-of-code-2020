import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class DayThree
{
    public static void main(String[] args) throws IOException
    {
        Class<DayOne> clazz = DayOne.class;
        File file = new File(clazz.getResource("/daythree/input.txt").getFile());
        List<String> rows = FileUtils.readLines(file, "UTF-8");

        // Since Java doesn't have proper tuples we'll need to use lists.
        var inputs = Arrays.asList(
                Arrays.asList(1,1),
                Arrays.asList(3,1),
                Arrays.asList(5,1),
                Arrays.asList(7,1),
                Arrays.asList(1,2)
        );

        var part1 = moveDownAndAlongUntilEnd(rows, 3, 1);
        var part2 = inputs.stream().map(r -> moveDownAndAlongUntilEnd(rows, r.get(0), r.get(1))).reduce(BigInteger.ONE, BigInteger::multiply);

        System.out.println(part1);
        System.out.println(part2);
    }

    private static BigInteger moveDownAndAlongUntilEnd(List<String> rows, int along, int down)
    {
        int count = 0;
        int position = 0;
        for (int i = 0; i < rows.size(); i += down)
        {
            String currentRow = rows.get(i);
            while(position >= currentRow.length())
            {
                currentRow = rows.get(i);
                rows.set(i, currentRow.concat(currentRow));
            }

            char charAtPosition = currentRow.charAt(position);
            if(charAtPosition == '#')
            {
                count++;
            }
            position += along;
        }
        return BigInteger.valueOf(count);
    }
}
