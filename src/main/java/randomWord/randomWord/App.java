package randomWord.randomWord;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import odf.OdtStore;

public class App
{
    private static int COUNT = 20;
    private static int LIMIT = 1;
    private static String DATE;
    
    static List<String> words = Lists.newArrayList();
    static List<String> origWords = Lists.newArrayList();

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
                Option.builder("F").longOpt("file").desc("file of words").hasArg().type(String.class).build());
        options.addOption(
                Option.builder("D").longOpt("date").desc("date").hasArg().type(String.class).required().build());

        // parse the command line arguments
        CommandLine line = parser.parse(options, args);

        // validate that block-size has been set
        DATE = line.getOptionValue("date");
        if (line.hasOption("count"))
        {
            COUNT = Integer.parseInt(line.getOptionValue("count"));
        }
        if (line.hasOption("limit"))
        {
            LIMIT = Integer.parseInt(line.getOptionValue("limit"));
        }
        if (line.hasOption("file"))
        {
            WordStore.wordsFile = new File(line.getOptionValue("file"));
            Preconditions.checkArgument(WordStore.wordsFile.exists() && WordStore.wordsFile.isFile());
        }

        int i = 0;
        WordStore.getInstance();
        while (i < COUNT)
        {
            generateOneWord(LIMIT);
            ++i;
        }
        
        List<String> pageProblem = Lists.newArrayList();
        // page 1
        pageProblem.add(DATE);
        i = 0;
        while (i < COUNT)
        {
            pageProblem.add((i + 1) + ": " + words.get(i));
            ++i;
        }
        
        List<String> pageAnswer = Lists.newArrayList();
        // page 4
        pageAnswer.add(DATE);
        i = 0;
        while (i < COUNT)
        {
            pageAnswer.add((i + 1) + ": " + origWords.get(i));
            ++i;
        }
        
        List<String> pages = Lists.newArrayList();
        pages.addAll(pageProblem);
        pages.addAll(pageProblem);
        pages.addAll(pageAnswer);
        
        try (OdtStore odtStore = new OdtStore(DATE + ".odt"))
        {
            odtStore.replaceContentAndSave(pages);
        }
    }

    private static void generateOneWord(int resultBound) throws Exception
    {
        // get word 
        Entry<String,String> pair = WordStore.getInstance().nextWord(resultBound);
        origWords.add(pair.getKey());
        words.add(pair.getValue());
    }
}
