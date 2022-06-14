package machine;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class CoffeeMachine {
    static Scanner input = new Scanner(System.in);
    static StockManager stockManager = new StockManager();

    public static void main(String[] args) {
        Barista barista = new Barista();

        barista.requestAction();
    }

    public static class StockManager {
        public static class Storage {
            int water = 400;
            int milk = 540;
            int beans = 120;
            int cups = 9;
            int money = 550;
        }

        //  Initializing storage with default ingredient values
        Storage storage = new Storage();
        public void printStock() {
            System.out.println();
            System.out.println("The coffee machine has:");
            System.out.println(storage.water + " ml of water");
            System.out.println(storage.milk + " ml of milk");
            System.out.println(storage.beans + " g of coffee beans");
            System.out.println(storage.cups + " disposable cups");
            System.out.println("$" + storage.money + " of money");
            System.out.println();
        }

        public void refill(Map<String, Integer> replenishedIngredients) {
            storage.water += replenishedIngredients.get("water");
            storage.milk += replenishedIngredients.get("milk");
            storage.beans += replenishedIngredients.get("beans");
            storage.cups += replenishedIngredients.get("cups");
        }

        public void ejectCash() {
            System.out.println();
            System.out.println("I gave you $" + storage.money);
            System.out.println();
            storage.money = 0;
        }
    }

    public static class Barista {
        private final static class Recipes {
            static class Espresso {
                public static final int WATER = 250;
                public static final int BEANS = 16;
                public static final int MONEY = 4;
            }

            static class Latte {
                public static final int WATER = 350;
                public static final int MILK = 75;
                public static final int BEANS = 20;
                public static final int MONEY = 7;
            }

            static class Cappuccino {
                public static final int WATER = 200;
                public static final int MILK = 100;
                public static final int BEANS = 12;
                public static final int MONEY = 6;
            }
        }

        public void requestAction() {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String action = input.next();
            switch (action) {
                case "buy":
                    takeOrder();
                    requestAction();
                    break;
                case "fill":
                    handleRestock();
                    requestAction();
                    break;
                case "take":
                    stockManager.ejectCash();
                    requestAction();
                    break;
                case "remaining":
                    stockManager.printStock();
                    requestAction();
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Invalid input. Please choose: buy, fill, take, remaining, or exit");
                    requestAction();
                    break;
            }
        }

        public void takeOrder() {
            System.out.println();
            System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:");
            String variety = input.next();
            makeCoffee(variety);
        }

        public void handleRestock() {
            Map<String, Integer> replenishedIngredients = new HashMap<>();

            System.out.println("Write how many ml of water you want to add:");
            int addedWater = input.nextInt();
            replenishedIngredients.put("water", addedWater);

            System.out.println("Write how many ml of milk you want to add:");
            int addedMilk = input.nextInt();
            replenishedIngredients.put("milk", addedMilk);

            System.out.println("Write how many grams of coffee beans you want to add:");
            int addedBeans = input.nextInt();
            replenishedIngredients.put("beans", addedBeans);

            System.out.println("Write how many disposable cups of coffee you want to add:");
            int addedCups = input.nextInt();
            System.out.println();
            replenishedIngredients.put("cups", addedCups);

            stockManager.refill(replenishedIngredients);
        }

        private boolean checkStock(String variety) {
            boolean hasIngredients = false;
            String insufficientWaterMessage = "Sorry, not enough water!";
            String insufficientBeansMessage = "Sorry, not enough coffee beans!";
            String insufficientMilkMessage = "Sorry, not enough milk!";

            switch (variety) {
                case "1":
                    if (stockManager.storage.water >= Recipes.Espresso.WATER) {
                        if (stockManager.storage.beans >= Recipes.Espresso.BEANS) {
                            hasIngredients = true;
                        } else {
                            System.out.println(insufficientBeansMessage);
                            System.out.println();
                        }
                    } else {
                        System.out.println(insufficientWaterMessage);
                        System.out.println();
                    }
                    break;
                case "2":
                    if (stockManager.storage.water >= Recipes.Latte.WATER) {
                        if (stockManager.storage.beans >= Recipes.Latte.BEANS) {
                            if (stockManager.storage.milk >= Recipes.Latte.MILK) {
                                hasIngredients = true;
                            } else {
                                System.out.println(insufficientMilkMessage);
                                System.out.println();
                            }
                        } else {
                            System.out.println(insufficientBeansMessage);
                            System.out.println();
                        }
                    } else {
                        System.out.println(insufficientWaterMessage);
                        System.out.println();
                    }
                    break;
                case "3":
                    if (stockManager.storage.water >= Recipes.Cappuccino.WATER) {
                        if (stockManager.storage.beans >= Recipes.Cappuccino.BEANS) {
                            if (stockManager.storage.milk >= Recipes.Cappuccino.MILK) {
                                hasIngredients = true;
                            } else {
                                System.out.println(insufficientMilkMessage);
                                System.out.println();
                            }
                        } else {
                            System.out.println(insufficientBeansMessage);
                            System.out.println();
                        }
                    } else {
                        System.out.println(insufficientWaterMessage);
                        System.out.println();
                    }
                    break;
                default:
                    System.out.println("I don't know this recipe");
            }

            return hasIngredients;
        }

        public void makeCoffee(String variety) {
            switch (variety) {
                case "1":
                    if (checkStock("1")) {
                        System.out.println("I have enough resources, making you a coffee!");
                        System.out.println();
                        stockManager.storage.cups--;
                        stockManager.storage.water -= Recipes.Espresso.WATER;
                        stockManager.storage.beans -= Recipes.Espresso.BEANS;
                        stockManager.storage.money += Recipes.Espresso.MONEY;
                    }
                    break;
                case "2":
                    if (checkStock("2")) {
                        System.out.println("I have enough resources, making you a coffee!");
                        System.out.println();
                        stockManager.storage.cups--;
                        stockManager.storage.water -= Recipes.Latte.WATER;
                        stockManager.storage.milk -= Recipes.Latte.MILK;
                        stockManager.storage.beans -= Recipes.Latte.BEANS;
                        stockManager.storage.money += Recipes.Latte.MONEY;
                    }
                    break;
                case "3":
                    if (checkStock("3")) {
                        System.out.println("I have enough resources, making you a coffee!");
                        System.out.println();
                        stockManager.storage.cups--;
                        stockManager.storage.water -= Recipes.Cappuccino.WATER;
                        stockManager.storage.milk -= Recipes.Cappuccino.MILK;
                        stockManager.storage.beans -= Recipes.Cappuccino.BEANS;
                        stockManager.storage.money += Recipes.Cappuccino.MONEY;
                    }
                    break;
                default:
                    System.out.println("Invalid coffee choice.");

            }
        }
    }
}
