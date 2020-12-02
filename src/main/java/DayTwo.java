import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class DayTwo
{
    public static void main(String[] args) throws IOException
    {
        Class clazz = DayOne.class;
        File file = new File(clazz.getResource("/daytwo/input.txt").getFile());
        List<String> passwords = FileUtils.readLines(file, "UTF-8");

        //Part 1
        var count = passwords.stream().map(s -> s.split("\\s")).filter(array -> {
            char c = array[1].charAt(0);
            String e = array[0].replace('-', ',');
            String w = array[2];
            Pattern p = Pattern.compile(String.format("^[^%c]*(?:%c[^%c]*){%s}$", c,c,c, e));
            return p.matcher(w).find();
        }).count();

        System.out.println(count);

        //Part 2
        var countPart2 = passwords.stream().map(s -> s.split("\\s")).filter(array -> {
            char c = array[1].charAt(0);
            String[] e = array[0].split("-");
            String w = array[2];
            Pattern occurencesOfFirst = Pattern.compile(String.format("^.{%d}%c.*$", Integer.parseInt(e[0])-1, c));
            Pattern occurencesOfSecond = Pattern.compile(String.format("^.{%d}%c.*$", Integer.parseInt(e[1])-1, c));
            return occurencesOfSecond.matcher(w).find() ^ occurencesOfFirst.matcher(w).find();
        }).count();

        //Part 2
        System.out.println(countPart2);
    }
}
