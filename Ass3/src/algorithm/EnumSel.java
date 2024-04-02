package algorithm;

import entity.Billboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EnumSel
{

    public ArrayList<Billboard> resultList; // this variable is used to store the billboard set of the solution. Do not change or remove it!
    private final ArrayList<Billboard> billboardList;
    private final int budget; // the budget constraint
    private final ArrayList<Billboard> billboardsProportionDesc;
    private final ArrayList<Billboard> billboardsInfDesc;

    public EnumSel(int budget, ArrayList<Billboard> billboardList)
    {
        this.budget = budget;
        this.billboardList = billboardList;
        this.resultList = new ArrayList<>();

        billboardsProportionDesc = new ArrayList<>(billboardList);
        billboardsProportionDesc.sort(Comparator.comparing(Billboard::getProportion));

        billboardsInfDesc = new ArrayList<>(billboardList);
        billboardsInfDesc.sort(Comparator.comparing(Billboard::getInf));

        Collections.reverse(billboardsInfDesc);
        Collections.reverse(billboardsProportionDesc);
    }

    public void generateSolution()
    {
        List<int[]> cm2 = Lib.generate(billboardsInfDesc.size(), 2);
        
        ArrayList<Billboard> H1 = new ArrayList<>();
        ArrayList<Billboard> H2 = new ArrayList<>();

        int infH1 = getMaxInf(cm2, billboardsInfDesc, H1);

        int budgetL = budget;

        List<int[]> cm3 = Lib.generate(billboardsInfDesc.size(), 3);

        for (int[] ints : cm3)
        {
            ArrayList<Billboard> complete = new ArrayList<>(billboardsInfDesc);
            ArrayList<Billboard> cm3billboards = new ArrayList<>();

            int price = 0;

            for (int id : ints)
            {
                cm3billboards.add(complete.get(id));
                price += complete.get(id).getPrice();
            }

            if (price < budgetL)
            {
                budgetL -= price;

                ArrayList<Billboard> maximizePro = new ArrayList<>();
                ArrayList<Billboard> maximizeInf = new ArrayList<>();

                int infA = Lib.getMaxInf(billboardsProportionDesc, maximizePro, budget);
                int infB = Lib.getMaxInf(billboardsInfDesc, maximizeInf, budget);

                ArrayList<Billboard> S = infA > infB ? maximizePro : maximizeInf;

                int infS = sumInf(S);
                int infH2 = sumInf(H2);

                if (infS > infH2)
                    H2 = S;
            }

            complete.removeAll(cm3billboards);
        }

        int infH2 = sumInf(H2);

        resultList = infH1 > infH2 ? H1 : H2;
    }

    public int getInf()
    {
        int inf = 0;

        for (Billboard billboard : resultList)
        {
            inf += billboard.getInf();
        }

        return inf;
    }

    private int sumInf(ArrayList<Billboard> billboards)
    {
        int inf = 0;

        for (Billboard billboard : billboards)
        {
            inf += billboard.getInf();
        }

        return inf;
    }

    private int getMaxInf(List<int[]> combinations, ArrayList<Billboard> billboardsInfAsc, ArrayList<Billboard> billboards)
    {
        int maxInf = 0;

        for (int[] ints : combinations)
        {
            int inf = sumInf(ints, billboardsInfAsc);

            if (inf > maxInf)
            {
                maxInf = inf;
                billboards.clear();

                for (int index : ints)
                {
                    billboards.add(billboardsInfAsc.get(index));
                }
            }
        }

        return maxInf;
    }

    private int sumInf(int[] ints, ArrayList<Billboard> billboards)
    {
        int price = 0;
        int inf = 0;

        for (int index : ints)
        {
            price += billboards.get(index).getPrice();
            inf += billboards.get(index).getInf();
        }

        return price <= budget ? inf : 0;
    }
}
