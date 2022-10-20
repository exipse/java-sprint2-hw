import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        MonthlyReport month = new MonthlyReport();
        YearlyReport year = new YearlyReport();

        HashMap<Integer, ArrayList<MonthlyData>> montlyReporters = new HashMap<>();
        ArrayList<YearlyData> yR = new ArrayList<>();

        System.out.println("1. Считать все месячные отчёты");
        System.out.println("2. Считать годовой отчёт");
        System.out.println("3. Сверить отчёты");
        System.out.println("4. Вывести информацию о всех месячных отчётах");
        System.out.println("5. Вывести информацию о годовом отчёте");
        System.out.println("6. Выход из программы");

        while(true) {
            System.out.println();
            System.out.println("Выберете пункт меню: ");
            int userInput = scanner.nextInt();

            if ((userInput < 1) || userInput > 6) {
                System.out.println("Выбран несуществующий пункт меню. Повторите ввод.");
            }
            else if (userInput == 1) {
                for (int i = 1; i <= 3; i++) {
                    ArrayList<MonthlyData> report = month.readReport("resources/m.20210" + i + ".csv");
                    for (int j = 0; j < report.size(); j++) {
                        MonthlyData rep = report.get(j);
                        System.out.println();
                        System.out.println("Отчет за " + i + " Месяц. Объект " + (j + 1));
                        System.out.println(rep.itemName);
                        System.out.println(rep.isExpense);
                        System.out.println(rep.quantity);
                        System.out.println(rep.sumOfOne);

                    }
                    if (!(report.isEmpty())) {
                        montlyReporters.put(i, report);
                    }
                }
            } else if (userInput == 2) {
                ArrayList<YearlyData> yearReport  = year.readYearReport("resources/y.2021.csv");

                System.out.println("Годовой отчет:");

                for (int i = 0; i < yearReport.size(); i++) {
                    YearlyData rep = yearReport.get(i);
                    System.out.println("");
                    System.out.println(rep.month);
                    System.out.println(rep.amount);
                    System.out.println(rep.isExpense);

                    yR.add(rep);
                }
            }else if(userInput == 3){
                month.chekData(montlyReporters,yR);
            }else if(userInput == 4){
                month.getFullInformation(montlyReporters);
            }else if(userInput == 5){
                year.getInfoAboutReport("resources/y.2021.csv", yR);
            }else if(userInput == 6){
                System.out.println("Завершение работы программы");
                break;
            }
        }

    }
}

