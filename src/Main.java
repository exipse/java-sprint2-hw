import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        MonthlyReport month = new MonthlyReport();
        YearlyReport year = new YearlyReport();
        HashMap<Integer, ArrayList<MonthlyData>> montlyReporters = new HashMap<>();
        ArrayList<YearlyData> yR = new ArrayList<>();


        while (true) {
            System.out.println("");
            menu.printMenu();
            System.out.println("");

            System.out.println("Выберете пункт меню: ");
            int userInput = scanner.nextInt();

            if ((userInput < 1) || userInput > 6) {
                System.out.println("Выбран несуществующий пункт меню. Повторите ввод.");
            } else if (userInput == 1) {
                for (int i = 1; i <= 3; i++) {
                    ArrayList<MonthlyData> report = month.readReport("resources/m.20210" + i + ".csv");
                    month.printReport(report, i);
                    if (!(report.isEmpty())) {
                        montlyReporters.put(i, report);
                    }
                }
            } else if (userInput == 2) {
                ArrayList<YearlyData> yearReport = year.readYearReport("resources/y.2021.csv");

                System.out.println("Годовой отчет:");

                for (int i = 0; i < yearReport.size(); i++) {
                    YearlyData rep = year.printReport(yearReport, i);
                    yR.add(rep);
                }
            } else if (userInput == 3) {
                month.chekData(montlyReporters, yR);
            } else if (userInput == 4) {
                month.getFullInformation(montlyReporters);
            } else if (userInput == 5) {
                year.getInfoAboutReport("resources/y.2021.csv", yR);
            } else if (userInput == 6) {
                System.out.println("Завершение работы программы");
                break;
            }
        }

    }
}

