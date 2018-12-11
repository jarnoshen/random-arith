package randomArith.randomArith;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class App
{
    private static int COUNT = 20;
    private static int LIMIT = 100;
    private static int SIZE = 3;

    public static void main(String[] args) throws Exception
    {
        // create the command line parser
        CommandLineParser parser = new DefaultParser();

        // create the Options
        Options options = new Options();
        options.addOption(
                Option.builder("C").longOpt("count").desc("number of problems").hasArg().type(Integer.class).build());
        options.addOption(
                Option.builder("L").longOpt("limit").desc("limit of problem operands").hasArg().type(Integer.class).build());
        options.addOption(
                Option.builder("l").longOpt("length").desc("problem length").hasArg().type(Integer.class).build());

        // parse the command line arguments
        CommandLine line = parser.parse(options, args);

        // validate that block-size has been set
        if (line.hasOption("count"))
        {
            COUNT = Integer.parseInt(line.getOptionValue("count"));
        }
        if (line.hasOption("limit"))
        {
            LIMIT = Integer.parseInt(line.getOptionValue("limit"));
        }
        if (line.hasOption("length"))
        {
            SIZE = Integer.parseInt(line.getOptionValue("length"));
        }

        int i = 0;
        RandomStore.getInstance().prepare(LIMIT);
        while (i < COUNT)
        {
            System.out.print((i + 1) + ": ");
            generateOneAlgo(SIZE, LIMIT);
            ++i;
        }
    }

    private static void generateOneAlgo(int argLength, int resultBound) throws Exception
    {
        while (true)
        {
            Arith algo = Arith.create(argLength, resultBound);

            // do calculation
            try
            {
                algo.calc();
                System.out.println(algo.toString());
                break;
            } catch (Exception e)
            {
                continue;
            }
        }
    }
}
