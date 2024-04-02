package algorithm;

import entity.Billboard;

import java.util.ArrayList;

public class PartSel
{

    private ArrayList<ArrayList<Billboard>> clusterList;
    public ArrayList<Billboard> resultList; // this variable is used to store the billboard set of the solution. Do not change or remove it!
    private int budget; // the budget constraint


    public PartSel(int budget, ArrayList<ArrayList<Billboard>> clusterList)
    {
        this.budget = budget;
        this.clusterList = clusterList;
        this.resultList = new ArrayList<>();
    }

    public void generateSolution()
    {
        ArrayList<Integer> before = new ArrayList<>();
        ArrayList<ArrayList<Billboard>> billBefore = new ArrayList<>();
        ArrayList<Integer> after = new ArrayList<>();
        ArrayList<ArrayList<Billboard>> billAfter = new ArrayList<>();
        int m = clusterList.size();

        //m=1
        billBefore.add(null);
        before.add(0);
        for (int l = 1; l <= budget; l++)
        {
            EnumSel enumSel = new EnumSel(l, clusterList.get(0));
            enumSel.generateSolution();
            int influence = enumSel.getInf();
            billBefore.add(enumSel.resultList);
            before.add(influence);
        }

        //m>1
        for (int i = 2; i <= m; i++)
        {
            billAfter.add(null);
            after.add(0);
            ArrayList<Integer> infList = new ArrayList<>();
            ArrayList<ArrayList<Billboard>> list = new ArrayList<>();
            infList.add(0);
            list.add(null);
            for (int a = 1; a <= budget; a++)
            {
                EnumSel enumSel = new EnumSel(a, clusterList.get(i - 1));
                enumSel.generateSolution();
                int x = enumSel.getInf();
                infList.add(x);
                list.add(enumSel.resultList);
            }
            for (int l = 1; l <= budget; l++)
            {
                billAfter.add(null);
                after.add(0);
                int influence = 0;
                for (int b = 0; b <= l; b++)
                {
                    if (infList.get(b) + before.get(l - b) > influence)
                    {
                        influence = infList.get(b) + before.get(l - b);
                        after.set(l, influence);
                        ArrayList<Billboard> list1 = new ArrayList<>();
                        if (list.get(b) != null)
                        {
                            list1.addAll(list.get(b));
                        }
                        if (billBefore.get(l - b) != null)
                        {
                            list1.addAll(billBefore.get(l - b));
                        }
                        billAfter.set(l, list1);
                    }
                }
            }
            billBefore.clear();
            ArrayList<ArrayList<Billboard>> a = (ArrayList<ArrayList<Billboard>>) billAfter.clone();
            billBefore.addAll(a);
            before.clear();
            ArrayList<Integer> inf = (ArrayList<Integer>) after.clone();
            before.addAll(inf);

            after.clear();
            billAfter.clear();
            ;
        }
        resultList = billBefore.get(budget);
    }
}