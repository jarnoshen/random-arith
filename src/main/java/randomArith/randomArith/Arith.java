package randomArith.randomArith;

import java.util.LinkedList;
import java.util.List;

public class Arith
{
    public LinkedList<Integer> args = new LinkedList<>();
    public LinkedList<Boolean> ops = new LinkedList<>();
    public int bound;
    public String expression = "";
    
    public static Arith create(int argLength, int bound)
    throws Exception
    {
        LinkedList<Integer> args = new LinkedList<>();
        LinkedList<Boolean> ops = new LinkedList<>();
                
        for (int i = 0; i < argLength; ++i)
        {
            args.add(RandomStore.getInstance().nextInt(bound));
        }
        for (int i = 0; i < argLength - 1; ++i)
        {
            ops.add(RandomStore.getInstance().nextBoolean());
        }
        return create(args, ops, bound);
    }

    public static Arith create(List<Integer> args, List<Boolean> ops, int bound)
    {
        Arith result = new Arith();

        result.args.addAll(args);
        result.ops.addAll(ops);
        result.bound = bound;
        
        return result;
    }
    
    private int calcTwo(StringBuilder sb) throws RuntimeException
    {
        int result;
        if (ops.get(0))
        {
            if (sb != null)
            {
                sb.append(" + ").append(args.get(1));
            }
            result = args.get(0) + args.get(1);
        }
        else
        {
            if (sb != null)
            {
                sb.append(" - ").append(args.get(1));
            }
            result = args.get(0) - args.get(1);
        }
        
        if (result >= 0 && result <= bound)
        {
            return result;
        }
        else
        {
            throw new RuntimeException();
        }
    }
    
    private int calc(StringBuilder sb) throws RuntimeException
    {
        if (args.size() == 1)
        {
            if (sb != null)
            {
                sb.append(args.get(0));
            }
            return args.get(0);
        }
        else
        {
            int result = calcTwo(sb);
            if (args.size() == 2)
            {
                if (sb != null)
                {
                    sb.append(" = ").append(result);
                }
                return result;
            }
            else
            {
                LinkedList<Integer> newArgs = new LinkedList<>(args);
                newArgs.removeFirst();
                newArgs.removeFirst();
                newArgs.addFirst(result);
                LinkedList<Boolean> newOps = new LinkedList<>(ops);
                newOps.removeFirst();
                return create(newArgs, newOps, bound).calc(sb);
            }
        }
    }
    
    public int calc() throws RuntimeException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(args.get(0));
        int result = calc(sb);
        expression = sb.toString();
        return result;
    }
    
    @Override
    public String toString()
    {
        if (expression.isEmpty())
        {
            calc();
        }
        return expression;
    }
}
