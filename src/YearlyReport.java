import Utils.ReadUtil;
import java.util.ArrayList;


public class YearlyReport {
    ReadUtil util =  new ReadUtil();
    ArrayList<YearlyData> yearlyRevue = new ArrayList<YearlyData>();
    ArrayList<YearlyData> yearlyExpense = new ArrayList<YearlyData>();

ArrayList<YearlyData> readYearReport(String path) { //метод считывания годового отчета
    ArrayList<YearlyData> yearReport = new ArrayList<>();
    String contents = util.readFileContentsOrNull(path);
    if (!(contents == null)) {
        String[] contentLines = contents.split("\n");
        for (int i = 1; i < contentLines.length; i++) {
            String[] splitLine = contentLines[i].split(",");
            YearlyData data = new YearlyData();
            data.month = Integer.parseInt(splitLine[0]);
            data.amount = Integer.parseInt(splitLine[1]);
            data.isExpense = Boolean.parseBoolean(splitLine[2]);

            yearReport.add(data);
       }

    }
    return yearReport;
}


void separateYearlyReport(ArrayList<YearlyData> yR) { //метод разделения годового отчета
    for (int i = 0; i < yR.size(); i++) {
        YearlyData line = yR.get(i);
        if (line.isExpense == true) {
            yearlyExpense.add(line);
        } else {
            yearlyRevue.add(line);
        }
    }
}


void getInfoAboutReport(String year,ArrayList<YearlyData> yR) {
    String[]values = year.split("\\.");
    System.out.println("Рассматриваемый год " + values[1]);

    separateYearlyReport(yR);

    for(int i=0; i < 3; i++) {
       Integer profit = yearlyRevue.get(i).amount - yearlyExpense.get(i).amount;
       System.out.println("Прибыль за " + (i+ 1) + " месяц - " + profit);
    }

    float countRevue = 0;
    float countExpense = 0;

    for (int j = 0; j < yearlyRevue.size(); j++) {
        countRevue = countRevue + (yearlyRevue.get(j)).amount;
    }

    for (int j = 0; j < yearlyExpense.size(); j++) {
        countExpense = countExpense + (yearlyExpense.get(j)).amount;
    }
    System.out.println("Средний расход за все месяцы в году - " + countExpense/yearlyExpense.size());
    System.out.println("Средний доход за все месяцы в году - " + countRevue/yearlyExpense.size());

}

}


