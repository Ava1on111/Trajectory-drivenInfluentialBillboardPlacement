package algorithm;

import entity.Billboard;

import java.util.*;

public class GreedySel
{

    public ArrayList<Billboard> resultList; // this variable is used to store the billboard set of the solution. Do not change or remove it!
    private final ArrayList<Billboard> billboardsInfDesc;
    private final ArrayList<Billboard> billboardsProportionDesc;
    private final int budget; // the budget constraint

    public GreedySel(int budget, ArrayList<Billboard> billboardList)
    {
        this.budget = budget;
        this.billboardsInfDesc = new ArrayList<>(billboardList);
        this.billboardsProportionDesc = new ArrayList<>(billboardList);
        this.resultList = new ArrayList<>();

        billboardsInfDesc.sort(Comparator.comparing(Billboard::getInf));
        billboardsProportionDesc.sort(Comparator.comparing(Billboard::getProportion));

        Collections.reverse(billboardsInfDesc);
        Collections.reverse(billboardsProportionDesc);
    }

    public void generateSolution()
    {
        ArrayList<Billboard> maximizePro = new ArrayList<>();
        ArrayList<Billboard> maximizeInf = new ArrayList<>();

        int infA = Lib.getMaxInf(billboardsProportionDesc, maximizePro, budget);
        int infB = Lib.getMaxInf(billboardsInfDesc, maximizeInf, budget);

        resultList = infA > infB ? maximizePro : maximizeInf;
    }
}