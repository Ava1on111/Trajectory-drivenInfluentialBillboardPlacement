package algorithm;

import entity.Billboard;

import java.util.ArrayList;
import java.util.List;

public class Lib
{
    public static int getMaxInf(ArrayList<Billboard> input, ArrayList<Billboard> results, int budget)
    {
        ArrayList<Billboard> billboards = new ArrayList<>(input);

        int inf = 0;
        int sum = 0;

        while (billboards.size() > 0)
        {
            Billboard billboard = billboards.get(0);

            if (billboard.getPrice() + sum <= budget)
            {
                results.add(billboard);
                sum += billboard.getPrice();
                inf += billboard.getInf();
            }

            billboards.remove(0);
        }

        return inf;
    }

    public static List<int[]> generate(int n, int r)
    {
        List<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n - 1, 0);
        return combinations;
    }

    private static void helper(List<int[]> combinations, int[] data, int start, int end, int index)
    {
        if (index == data.length)
        {
            int[] combination = data.clone();
            combinations.add(combination);
        }
        else if (start <= end)
        {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1);
            helper(combinations, data, start + 1, end, index);
        }
    }
}