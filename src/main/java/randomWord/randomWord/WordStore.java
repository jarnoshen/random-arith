package randomWord.randomWord;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import randomArith.randomArith.RandomStore;

import static java.lang.Math.max;
import static java.lang.Math.min;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class WordStore
{
    public static File wordsFile = new File("words.txt");

    private static WordStore instance = null;

    private List<String> words = Lists.newArrayList();
    
    public static WordStore getInstance()
    throws Exception
    {
        if (instance == null)
        {
            instance = new WordStore();
            
            int maxLength = 0;
            // read words from file
            try (BufferedReader br = new BufferedReader(new FileReader(wordsFile)))
            {
                while (true)
                {
                    String word = br.readLine();
                    if (word == null || word.isEmpty())
                    {
                        break;
                    }
                    instance.words.add(word);
                    maxLength = Math.max(maxLength, word.length());
                }
            } catch (FileNotFoundException e)
            {
                System.err.println("file is not found");
            } catch (IOException e)
            {
                System.err.println("file read error");
            }
            
            // init buffer
            if (Math.max(maxLength, instance.words.size()) > 0)
            {
                RandomStore.getInstance().prepare(Math.max(maxLength, instance.words.size()));
            }
        }
        return instance;
    }
    
    public Entry<String,String> nextWord(int numOfGaps)
    throws Exception
    {
        int i = RandomStore.getInstance().nextInt(words.size() - 1);
        String word = words.get(i);
        
        if (numOfGaps > 1)
        {
            numOfGaps = min(max(1, word.length() / 2 - 1), numOfGaps);
        }
        
        Set<Integer> gapIndexes = Sets.newTreeSet();
        
        while (gapIndexes.size() < numOfGaps)
        {
            int index = RandomStore.getInstance().nextInt(word.length() - 1);
            if (Character.isAlphabetic(word.charAt(index)))
            {
                gapIndexes.add(RandomStore.getInstance().nextInt(word.length() - 1));
            }
        }
        
        char[] chars = word.toCharArray();
        for (int g: gapIndexes)
        {
            chars[g] = '_';
        }
        
        return new AbstractMap.SimpleEntry<>(word, new String(chars));
    }
}
