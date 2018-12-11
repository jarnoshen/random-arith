package randomArith.randomArith;

import java.util.Queue;
import java.util.Random;
import java.util.Date;
import java.util.stream.IntStream;

import org.random.api.RandomOrgCache;
import org.random.api.RandomOrgClient;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Queues;

public class RandomStore
{
    private static RandomOrgClient roc = RandomOrgClient.getRandomOrgClient(
            "e7928c51-22a0-49e5-9dd4-aff879e55287");
    
    private static Random rand = new Random(new Date().getTime());
    
    private static final int BUF_SIZE = 1000;

    private static RandomStore instance = null;
    
    private RandomOrgCache<int[]> cache;
    private Queue<Integer> buffer;
    private int max;
    
    public static RandomStore getInstance()
    {
        if (instance == null)
        {
            instance = new RandomStore();
        }
        return instance;
    }
    
    private void loadBuffer()
    throws Exception
    {
//        if (cache.getCachedValues() > 0)
//        {
            buffer = Queues.newLinkedBlockingQueue(FluentIterable.from(
                    rand.ints(BUF_SIZE, 0, max).boxed().toArray(Integer[]::new)));
//                    IntStream.of(cache.get())
//                    .boxed().toArray(Integer[]::new)));
//        }
//        else
//        {
//            buffer = Queues.newLinkedBlockingQueue(FluentIterable.from(
//                    rand.ints(BUF_SIZE, 0, max).boxed().toArray(Integer[]::new)));
//                    IntStream.of(cache.getOrWait())
//                    .boxed().toArray(Integer[]::new)));
//        }
    }
    
    public void prepare(int max)
    throws Exception
    {
        this.max = max;
//        cache = roc.createIntegerCache(BUF_SIZE, 0, max);
        loadBuffer();
    }
    
    public int nextInt(int max)
    throws Exception
    {
        if (buffer.isEmpty())
        {
            loadBuffer();
        }
        return buffer.poll() % (max + 1);
    }
    
    public boolean nextBoolean()
    throws Exception
    {
        if (buffer.isEmpty())
        {
            loadBuffer();
        }
        return buffer.poll() % 2 == 0;
    }
}
