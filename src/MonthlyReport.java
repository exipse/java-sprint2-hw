import Utils.ReadUtil;

import java.util.ArrayList;
import java.util.HashMap;


public class MonthlyReport {

    HashMap<Integer, ArrayList<MonthlyData>> reporters = new HashMap<>();
    ReadUtil util = new ReadUtil();
    YearlyReport yReport = new YearlyReport();
    ArrayList<MonthlyData> reportJan = new ArrayList<MonthlyData>();
    ArrayList<MonthlyData> reportFeb = new ArrayList<MonthlyData>();
    ArrayList<MonthlyData> reportMarch = new ArrayList<MonthlyData>();


    ArrayList<MonthlyData> readReport(String path) { // метод считывания месячного отчета
        ArrayList<MonthlyData> valuesLines = new ArrayList<>();
        String monthContents = util.readFileContentsOrNull(path); // считываем контент в переменную
        if (monthContents != null) {

            String[] contentLines = monthContents.split("\n"); //делем контент на строки (получаем массив строк)

            for (int i = 1; i < contentLines.length; i++) { // работа с каждой строчкой в массиве
                String[] splitLine = contentLines[i].split(","); //разделяем строчку чтобы достать нужные значения

                MonthlyData data = new MonthlyData(); //создаем объект, чтобы положить туда данные строчки
                data.itemName = splitLine[0];
                data.isExpense = Boolean.parseBoolean(splitLine[1]);
                data.quantity = Integer.parseInt(splitLine[2]);
                data.sumOfOne = Integer.parseInt(splitLine[3]);

                valuesLines.add(data); //добавляем объект содержащий данные строчки в список
            }
        }

        return valuesLines;
    }

   void printReport(ArrayList<MonthlyData> report, int month){
       for (int j = 0; j < report.size(); j++) {
           MonthlyData rep = report.get(j);
           System.out.println();
           System.out.println("Отчет за " + month + " Месяц. Объект " + (j + 1));
           System.out.println(rep.itemName);
           System.out.println(rep.isExpense);
           System.out.println(rep.quantity);
           System.out.println(rep.sumOfOne);

       }
   }


    void chekData(HashMap<Integer, ArrayList<MonthlyData>> data, ArrayList<YearlyData> yR) { //метод для сверки данных
        if (!((data.size() == 0) || yR.size() == 0)) {
            System.out.println("Месячные отчеты: ");
            for (int i = 1; i <= 3; i++) {
                System.out.println(findMonth(i) + ". Доход составляет: " + findRevenueInMonth(data.get(i)) + ". Расход: " + findExpenseInMonth(data.get(i)));
            }

            yReport.separateYearlyReport(yR);

            for (int j = 0; j < 3; j++) {
                if ((!(yReport.yearlyRevue.get(j).amount == findRevenueInMonth(data.get(j + 1))))) {
                    System.out.println("Обнаружено несоответствие. Прибыль в отчете за " + findMonth(yReport.yearlyRevue.get(j).month) +
                            " составляет " + findRevenueInMonth(data.get(j + 1)) + ". Прибыль в годовом отчете " + yReport.yearlyRevue.get(j).amount);
                }
            }
            System.out.println("");
            for (int j = 0; j < 3; j++) {
                if ((!(yReport.yearlyExpense.get(j).amount == findExpenseInMonth(data.get(j + 1))))) {
                    System.out.println("Обнаружено несоответствие. Прибыль в отчете за " + findMonth(yReport.yearlyExpense.get(j).month) +
                            " составляет " + findExpenseInMonth(data.get(j + 1)) + ". Прибыль в годовом отчете " + yReport.yearlyExpense.get(j).amount);
                }
            }
            System.out.println("");
            System.out.println("Сверка завершена.");

        } else {
            System.out.println("Данных для сверки не найдено. Нужно считать месячные и годовые отчеты");
        }

    }


    int findRevenueInMonth(ArrayList<MonthlyData> revenue) { //метод по нахождению прибыли
        Integer sum = 0;
        for (int i = 0; i < revenue.size(); i++) {
            MonthlyData value = revenue.get(i);
            if (value.isExpense == false) {
                sum = sum + (value.quantity * value.sumOfOne);
            }
        }
        return sum;
    }


    int findExpenseInMonth(ArrayList<MonthlyData> expense) {  //метод по нахождению затрат
        Integer sum = 0;
        for (int i = 0; i < expense.size(); i++) {
            MonthlyData value = expense.get(i);
            if (value.isExpense == true) {
                sum = sum + (value.quantity * value.sumOfOne);
            }
        }
        return sum;
    }


    void getFullInformation(HashMap<Integer, ArrayList<MonthlyData>> montlyReporters) {
        if (!(montlyReporters.size() == 0)) {
            for (int i = 1; i <= montlyReporters.size(); i++) {
                ArrayList<MonthlyData> objMonth = montlyReporters.get(i);
                int maxCount = 0;
                String nameMax = "";
                int count = 0;
                int maxExpence = 0;
                String nameExp = "";
                for (int j = 0; j < objMonth.size(); j++) {
                    MonthlyData line = objMonth.get(j);
                    if (line.isExpense == false) {

                        count = count + (line.quantity * line.sumOfOne);
                        if (maxCount < count) {
                            maxCount = count;
                            nameMax = line.itemName;
                        }
                    } else {
                        count = count + (line.quantity * line.sumOfOne);
                        if (maxExpence < count) {
                            maxExpence = count;
                            nameExp = line.itemName;
                        }
                    }
                    count = 0;
                }
                System.out.println("Отчет за " + findMonth(i));
                System.out.println("Самый прибыльный товар - " + nameMax + ". Итого получено прибыли - " + maxCount);
                System.out.println("Самая большая трата - " + nameExp + ". Итого потрачено - " + maxExpence);
                System.out.println("");
            }
        } else {
            System.out.println("Данных для сверки не найдено. Нужно считать месячные и годовые отчеты");
        }
    }


    String findMonth(int number) {
        if (number == 1) {
            return "Январь";
        } else if (number == 2) {
            return "Февраль";
        } else if (number == 3) {
            return "Март";
        } else {
            return "" + number;
        }
    }

}


