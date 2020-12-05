import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DayFive
{
    public static void main(String[] args) throws IOException
    {
        Class<DayFour> clazz = DayFour.class;
        File file = new File(clazz.getResource("/dayfive/input.txt").getFile());
        List<String> rows = FileUtils.readLines(file, "UTF-8");

        var rowSolver = new RowSolver();
        var colSolver = new ColumnSolver();

        var max = rows.stream()
                .map(s -> (rowSolver.solve(s.substring(0, 7)) * 8) + colSolver.solve(s.substring(7)))
                .max(Integer::compareTo);

        System.out.println("Max is: " + max);

        var orderedSeatIds = rows.stream()
                .filter(row -> rowSolver.solve(row.substring(0, 7)) != 0 && rowSolver.solve(row.substring(0, 7)) != 127)
                .map(s -> (rowSolver.solve(s.substring(0, 7)) * 8) + colSolver.solve(s.substring(7)))
                .sorted()
                .collect(Collectors.toList());

        int currentMax = orderedSeatIds.get(0);
        orderedSeatIds.remove(0);
        for (Integer seatId: orderedSeatIds)
        {
            if(seatId != currentMax + 1)
            {
                System.out.println((currentMax + 1) + " is my seat!");
                break;
            }
            currentMax = seatId;
        }

    }

    static class RowSolver
    {
        public Integer solve(String rowString)
        {
            var binaryString = rowString.replace('B', '1').replace('F', '0');
            return Integer.parseInt(binaryString, 2);
        }
    }

    static class ColumnSolver
    {
        public Integer solve(String rowString)
        {
            var binaryString = rowString.replace('R', '1').replace('L', '0');
            return Integer.parseInt(binaryString, 2);
        }
    }
}

