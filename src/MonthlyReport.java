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



    ArrayList<MonthlyData> readReport(String path){ // метод считывания месячного отчета

       ArrayList<MonthlyData> valuesLines = new ArrayList<>();
        String monthContents = util.readFileContentsOrNull(path); // считываем контент в переменную
        if (monthContents !=null) {

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


void chekData(HashMap<Integer, ArrayList<MonthlyData>> data, ArrayList<YearlyData> yR) { //метод для сверки данных


if(!((data.size() == 0) || yR.size()==0)){
    reportJan = data.get(1);
    reportFeb = data.get(2);
    reportMarch = data.get(3);

    System.out.println("Месячные отчеты: ");
    System.out.println("Январь. Доход составляет: " + findRevenueInMonth(reportJan) +". Расход: " + findExpenseInMonth(reportJan));
    System.out.println("Февраль. Доход составляет: " + findRevenueInMonth(reportFeb) +". Расход: " + findExpenseInMonth(reportFeb));
    System.out.println("Март. Доход составляет: " + findRevenueInMonth(reportMarch) +". Расход: " + findExpenseInMonth(reportMarch));


    yReport.separateYearlyReport(yR);
    if((!(yReport.yearlyRevue.get(0)).amount.equals(findRevenueInMonth(reportJan)))){
        System.out.println("Обнаружено несоответствие. Прибыль в отчете за месяцем "+yReport.yearlyRevue.get(0).month+
                " составляет "+ findRevenueInMonth(reportJan) + ". Прибыль в годовом отчете " + yReport.yearlyRevue.get(0).amount);
            }

    if((!(yReport.yearlyRevue.get(1)).amount.equals(findRevenueInMonth(reportFeb)))){
        System.out.println("Обнаружено несоответствие. Прибыль в отчете за месяцем "+yReport.yearlyRevue.get(1).month+
                " составляет "+ findRevenueInMonth(reportFeb) + ". Прибыль в годовом отчете " + yReport.yearlyRevue.get(1).amount);
    }
    if((!(yReport.yearlyRevue.get(2)).amount.equals(findRevenueInMonth(reportMarch)))){
        System.out.println("Обнаружено несоответствие. Прибыль в отчете за месяцем "+yReport.yearlyRevue.get(2).month+
                " составляет "+ findRevenueInMonth(reportMarch) + ". Прибыль в годовом отчете " + yReport.yearlyRevue.get(2).amount);
    }
    System.out.println("");
    if((!(yReport.yearlyExpense.get(0)).amount.equals(findExpenseInMonth(reportJan)))){
        System.out.println("Обнаружено несоответствие. Расход в отчете за месяцем "+yReport.yearlyExpense.get(0).month+
                " составляет "+ findExpenseInMonth(reportJan) + ". Расход в годовом отчете " + yReport.yearlyExpense.get(0).amount);
    }
    if((!(yReport.yearlyExpense.get(1)).amount.equals(findExpenseInMonth(reportFeb)))){
        System.out.println("Обнаружено несоответствие. Расход в отчете за месяцем "+yReport.yearlyExpense.get(1).month+
                " составляет "+ findExpenseInMonth(reportFeb) + ". Расход в годовом отчете " + yReport.yearlyExpense.get(1).amount);
    }
    if((!(yReport.yearlyExpense.get(0)).amount.equals(findExpenseInMonth(reportMarch)))){
        System.out.println("Обнаружено несоответствие. Расход в отчете за месяцем "+yReport.yearlyExpense.get(2).month+
                " составляет "+ findExpenseInMonth(reportMarch) + ". Расход в годовом отчете " + yReport.yearlyExpense.get(2).amount);
    }
    System.out.println("");
    System.out.println("Сверка завершена.");

}
else {
    System.out.println("Данных для сверки не найдено. Нужно считать месячные и годовые отчеты");
}

}



Integer findRevenueInMonth(ArrayList<MonthlyData> revenue){ //метод по нахождению прибыли
        Integer sum = 0;
   for(int i = 0; i < revenue.size(); i++){
       MonthlyData value =  revenue.get(i);
       if(value.isExpense == false) {
           sum = sum + (value.quantity * value.sumOfOne);
       }
   }
    return sum;
}


Integer findExpenseInMonth(ArrayList<MonthlyData> expense) {  //метод по нахождению затрат
    Integer sum = 0;
    for(int i = 0; i < expense.size(); i++){
        MonthlyData value =  expense.get(i);
        if(value.isExpense == true) {
            sum = sum + (value.quantity * value.sumOfOne);
        }
    }
    return sum;
}

void getFullInformation(HashMap<Integer, ArrayList<MonthlyData>> montlyReporters){

        for(int i=1; i <= montlyReporters.size(); i++ ){
            ArrayList<MonthlyData> objMonth =  montlyReporters.get(i);
            Integer maxCount = 0;
            String nameMax ="";
            Integer worthForUnit = 0;

            Integer count =0;

            Integer maxExpence = 0;
            String nameExp ="";
            Integer worthForUnitExp = 0;

            String nameMonth = "";
            for(int j=0; j < objMonth.size(); j++) {
                MonthlyData line = objMonth.get(j);
                if (line.isExpense == false) {
                    count = count + (line.quantity * line.sumOfOne);
                    if (maxCount < count) {
                        maxCount = count;
                        nameMax = line.itemName;
                        worthForUnit = line.sumOfOne;
                    }
                } else if (line.isExpense == true) {
                    count = count + (line.quantity * line.sumOfOne);
                    if (maxExpence < count) {
                        maxExpence = count;
                        nameExp = line.itemName;
                        worthForUnitExp = line.sumOfOne;

                    }
                }
                count = 0;

                if (i == 1) {
                    nameMonth = "Январь";
                } else if (i == 2) {
                    nameMonth = "Февраль";
                } else if (i == 3) {
                    nameMonth = "Март";
                } else {
                    nameMonth = nameMonth + i;
                }
            }
            System.out.println("Отчет за "+ nameMonth);
            System.out.println("Самый прибыльный товар - " + nameMax +". Его стоимость за единицу - " + worthForUnit);
            System.out.println("Самая большая трата - " + nameExp + ". Его стоимость за единицу - " + worthForUnitExp);
            System.out.println("");
        }
        

       }
        



}


