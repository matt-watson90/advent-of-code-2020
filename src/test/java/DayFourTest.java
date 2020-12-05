import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class DayFourTest
{

    @ParameterizedTest
    @CsvSource({
            "2002, true",
            "2003, false"
    })
    public void birthYearPredicate(String year, boolean matches)
    {
        assertThat(DayFour.Predicates.birthYear.test(year), is(matches));
    }

    @ParameterizedTest
    @CsvSource({
            "2010, true",
            "2020, true",
            "2015, true",
            "2005, false",
            "2009, false",
    })
    public void issueYearPredicate(String year, boolean matches)
    {
        assertThat(DayFour.Predicates.issueYear.test(year), is(matches));
    }

    @ParameterizedTest
    @CsvSource({
            "2030, true",
            "2020, true",
            "2025, true",
            "2015, false",
            "2031, false",
    })
    public void expirationYear(String year, boolean matches)
    {
        assertThat(DayFour.Predicates.expirationYear.test(year), is(matches));
    }

    @ParameterizedTest
    @CsvSource({
            "60in, true",
            "190cm, true",
            "190in, false",
            "190, false",
    })
    public void height(String year, boolean matches)
    {
        assertThat(DayFour.Predicates.height.test(year), is(matches));
    }

    @ParameterizedTest
    @CsvSource({
            "'#123abc', true",
            "'#abc123', true",
            "'#888785', true",
            "'#123abz', false",
            "'123abc', false"
    })
    public void hairColour(String year, boolean matches)
    {
        assertThat(DayFour.Predicates.hairColour.test(year), is(matches));
    }

    @ParameterizedTest
    @CsvSource({
            "brn, true",
            "wat, false"
    })
    public void eyeColour(String year, boolean matches)
    {
        assertThat(DayFour.Predicates.eyeColour.test(year), is(matches));
    }

    @ParameterizedTest
    @CsvSource({
            "'000000001', true",
            "'0123456789', false"
    })
    public void passportId(String year, boolean matches)
    {
        assertThat(DayFour.Predicates.passportId.test(year), is(matches));
    }
}