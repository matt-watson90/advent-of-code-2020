import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class DayFiveTest
{
    @ParameterizedTest
    @CsvSource({
            "BFFFBBF, 70",
            "FFFBBBF, 14",
            "BBFFBBF, 102",
            "FBFBBFF, 44"
    })
    public void rowParser(String rowString, int rowNumber)
    {
        assertThat(new DayFive.RowSolver().solve(rowString), is(rowNumber));
    }

    @ParameterizedTest
    @CsvSource({
            "RRR, 7",
            "RLL, 4",
    })
    public void columnSolver(String colString, int colNumber)
    {
        assertThat(new DayFive.ColumnSolver().solve(colString), is(colNumber));
    }
}