import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Planner {
    private long startTime = System.currentTimeMillis();
    private long timeSpent;
    private int firstNumber, secondNumber; //вспомогательные переменные
    private String s; //поочередно считываемая строка из файла
    private Map<Integer, Set<Integer>> uniqueTasksMap = new HashMap<>(); // Карта <Уникальная задача, Зависящие от нее задачи>
    private Set<Integer> dependedTasksSet = new HashSet<>(); // Список уникальных зависящих задач
    private Set<Integer> highestPriorityTasks = new HashSet<>();//список уникальных задач наивысшего приоритета
    private Set<Integer> uniqueTasksSet = new HashSet<>();//список уникальных задач

    void planTasks(String file) {
        // Считываем построчно файл и заполняем коллекции
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((s = br.readLine()) != null) {
                String[] values = s.split(" ");
                firstNumber = Integer.parseInt(values[0]);
                secondNumber = Integer.parseInt(values[1]);
                if(!uniqueTasksMap.containsKey(firstNumber)) {
                    uniqueTasksMap.put(firstNumber, new HashSet<>());
                }
                uniqueTasksMap.get(firstNumber).add(secondNumber);
                dependedTasksSet.add(secondNumber);
                uniqueTasksSet.add(firstNumber);
                uniqueTasksSet.add(secondNumber);
            }
        } catch (IOException exc) {
            System.out.println("Ошибка чтения файла: " + exc);
        }
        // Пока список уникадьных задач не пуст
        while (!uniqueTasksMap.isEmpty()) {
            //Ищем задачи высшего приоритета
            highestPriorityTasks.addAll(uniqueTasksSet);
            highestPriorityTasks.removeAll(dependedTasksSet);

            //Очищаем коллекции от задач наивысшего приоритета
            for (Integer highestPriority : highestPriorityTasks) {
                uniqueTasksMap.remove(highestPriority);
            }
            uniqueTasksSet.removeAll(highestPriorityTasks);

            //Создаем новый список зависящих задач
            if(!uniqueTasksMap.isEmpty()) {
                dependedTasksSet.clear();
                for (Set dependedValue : uniqueTasksMap.values()) {
                    dependedTasksSet.addAll(dependedValue);
                }
            }

            //Выводим на консоль задачи с высшим приоритетом
            System.out.println(highestPriorityTasks);
            highestPriorityTasks.clear();
        }
        System.out.println(dependedTasksSet);
        timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("Время выполнения: " + timeSpent + " мс");
    }
}