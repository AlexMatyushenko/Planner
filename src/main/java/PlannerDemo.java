public class PlannerDemo {
    public static void main(String[] args) {
        Planner test = new Planner();
        test.planTasks(".\\src\\main\\resources\\data3.txt"); //для Windows
        //test.planTasks("./src/main/resources/data1.txt"); //для Linux
    }
}
