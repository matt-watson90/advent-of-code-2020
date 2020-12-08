import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class DayEight
{
    enum Code {
        acc, jmp, nop
    }

    enum Sign {
        PLUS("+"), MINUS("-");

        private final String sign;

        Sign(String sign)
        {
            this.sign = sign;
        }

        private static final Map<String, Sign> lookup = new HashMap<>();

        // This isn't really necessary but I put it in here incase we ended up with lots more signs
        static
        {
            for(Sign lookupSign : Sign.values())
            {
                lookup.put(lookupSign.sign, lookupSign);
            }
        }

        public static Sign get(String sign)
        {
            return lookup.get(sign);
        }
    }


    static class Instruction
    {
        Code code;
        Integer timesExecuted;
        Sign sign;
        Integer value;

        private Instruction(Code code, Integer timesExecuted, Sign sign, Integer value)
        {
            this.code = code;
            this.timesExecuted = timesExecuted;
            this.sign = sign;
            this.value = value;
        }

        private static Instruction from(String instruction)
        {
            return new Instruction(Code.valueOf(instruction.substring(0, 3)), 0, Sign.get(instruction.substring(4, 5)), Integer.valueOf(instruction.substring(5)));
        }
    }

    public static void main(String[] args) throws IOException
    {
        Class<DayEight> clazz = DayEight.class;
        File file = new File(clazz.getResource("/dayeight/input.txt").getFile());

        var instructions = FileUtils.readLines(file, "UTF-8").stream()
                .map(Instruction::from)
                .collect(Collectors.toList());

        int accumulator = 0;
        int startingPosition = 0;
        runUntilHittingARepeatInstruction(instructions, startingPosition, accumulator);

        instructions = FileUtils.readLines(file, "UTF-8").stream()
                .map(Instruction::from)
                .collect(Collectors.toList());

        for (Instruction i : instructions)
        {
            if (i.code == Code.jmp)
            {
                i.code = Code.nop;
                if(!runUntilHittingARepeatInstruction(instructions, startingPosition, accumulator))
                {
                    break;
                }
                else
                {
                    i.code = Code.jmp;
                }
            }
            else if (i.code == Code.nop)
            {
                i.code = Code.jmp;
                if(!runUntilHittingARepeatInstruction(instructions, startingPosition, accumulator))
                {
                    break;
                }
                else
                {
                    i.code = Code.nop;
                }
            }
            // Reset the executed counters to 0 before the next loop.
            instructions.forEach(j -> j.timesExecuted =0 );
        }
    }

    private static Boolean runUntilHittingARepeatInstruction(List<Instruction> instructions, Integer instructionPointer, Integer accumulator)
    {
        if(instructionPointer >= instructions.size()- 1)
        {
            System.out.println("When instructions fell off of the list, accumulator was: " + accumulator);
            return false;
        }

        var instruction = instructions.get(instructionPointer);
        if(instruction.timesExecuted > 0)
        {
            System.out.println("When an instruction was repeated, accumulator was: " + accumulator);
            return true;
        }

        instruction.timesExecuted++;
        switch (instruction.code)
        {
        case nop:
        {
            return runUntilHittingARepeatInstruction(instructions, instructionPointer+1, accumulator);
        }
        case acc:
        {
            switch (instruction.sign)
            {
            case PLUS:
                 return runUntilHittingARepeatInstruction(instructions, instructionPointer+1, accumulator + instruction.value);
            case MINUS:
                return runUntilHittingARepeatInstruction(instructions, instructionPointer+1, accumulator - instruction.value);
            }
        }
        case jmp:
        {
            switch (instruction.sign)
            {
            case PLUS:
                return runUntilHittingARepeatInstruction(instructions, instructionPointer + instruction.value, accumulator);
            case MINUS:
                return runUntilHittingARepeatInstruction(instructions, instructionPointer - instruction.value, accumulator);
            }
        }
        default: throw new IllegalArgumentException();
        }
    }
}
