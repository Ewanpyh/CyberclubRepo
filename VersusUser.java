import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class VersusUser {

    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Account loggedInAccount = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n\tVersus");
            if (loggedInAccount == null){
            System.out.println("1. Создать аккаунт"); //main menu
            System.out.println("2. Вход в аккаунт"); 
            // Add JavaFX
            }
            if (loggedInAccount != null) {
                System.out.println("1. Пополнить баланс"); // menu после авторизации
                System.out.println("2. Пакеты");
                System.out.println("3. Выйти из аккаунта");
                System.out.println("4. Выход из системы");
            } else {
                System.out.println("3. Выход из системы");
            }
            System.out.print("\nВыберите пункт меню: ");

            int choice = scanner.nextInt(); //add exception for string values
            scanner.nextLine(); // очищаем буфер

            if (loggedInAccount == null) {
                switch (choice) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Неверный выбор, попробуйте снова.");
                }
            } else {
                switch (choice) {
                    case 1:
                        topUpBalance();
                        break;
                    case 2:
                        selectPackage();
                        break;
                    case 3:
                        logout();
                        break;
                    case 4:
                        System.out.println("До свидания!");
                        return;
                    default:
                        System.out.println("Неверный выбор, попробуйте снова.");
                }
            }
        }
    }

    // Метод для создания аккаунта
    private static void createAccount() {
        System.out.print("Введите номер телефона: ");
        String phoneNumber = scanner.nextLine(); //check if phone number is correct +7 && 11 digits

        if (accounts.containsKey(phoneNumber)) {
            System.out.println("Аккаунт с таким номером телефона уже существует.");
        } else {
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();
            accounts.put(phoneNumber, new Account(phoneNumber, password));
            System.out.println("Аккаунт успешно создан!");
        }
    }

    // Метод для входа в аккаунт
    private static void login() {
        System.out.print("Введите номер телефона: ");
        String phoneNumber = scanner.nextLine();

        Account account = accounts.get(phoneNumber);
        if (account != null) {
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();

            if (account.checkPassword(password)) {
                loggedInAccount = account;
                System.out.println("Вы успешно вошли в аккаунт. Ваш баланс: " + account.getBalance() + " рублей");
            } else {
                System.out.println("Неверный пароль.");
            }
        } else {
            System.out.println("Аккаунт с таким номером телефона не найден.");
        }
    }

    // Метод для пополнения баланса
    private static void topUpBalance() {
        if (loggedInAccount != null) {
            System.out.print("Введите сумму для пополнения: ");
            double amount = scanner.nextDouble();
            if (amount > 0) {
                loggedInAccount.topUp(amount);
                System.out.println("Баланс успешно пополнен. Ваш текущий баланс: " + loggedInAccount.getBalance() + " рублей");
            } else {
                System.out.println("Сумма пополнения должна быть положительной.");
            }
        } else {
            System.out.println("Сначала войдите в аккаунт для пополнения баланса.");
        }
    }

    // Метод для выбора пакета
    private static void selectPackage() {
        if (loggedInAccount != null) {
            System.out.println("Выберите пакет:");
            System.out.println("1. 1 час - 100 рублей");
            System.out.println("2. 2 часа - 200 рублей");
            System.out.println("3. 3 часа - 250 рублей");
            System.out.println("4. 5 часов - 350 рублей");
            System.out.println("5. Сутки - 1100 рублей");
            System.out.println("6. Ночь - 350 рублей");
            System.out.println("7. Утро - 200 рублей");
            System.out.print("Выберите номер пакета: ");

            int packageChoice = scanner.nextInt();
            double packageCost = 0;

            switch (packageChoice) {
                case 1: packageCost = 100; break;
                case 2: packageCost = 200; break;
                case 3: packageCost = 250; break;
                case 4: packageCost = 350; break;
                case 5: packageCost = 1100; break;
                case 6: packageCost = 350; break;
                case 7: packageCost = 200; break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова.");
                    return;
            }

            if (loggedInAccount.getBalance() >= packageCost) {
                loggedInAccount.deductBalance(packageCost);
                System.out.println("Пакет успешно приобретен! Списано " + packageCost + " рублей. Текущий баланс: " + loggedInAccount.getBalance() + " рублей");
            } else {
                System.out.println("Недостаточно средств для покупки этого пакета.");
            }
        } else {
            System.out.println("Сначала войдите в аккаунт, чтобы выбрать пакет.");
        }
    }

    // Метод для выхода из аккаунта
    private static void logout() {
        if (loggedInAccount != null) {
            System.out.println("Вы вышли из аккаунта.");
            loggedInAccount = null;
        } else {
            System.out.println("Вы не вошли в аккаунт.");
        }
    }
}

// Класс Account, который представляет аккаунт пользователя
class Account {
    private String phoneNumber;
    private String password;
    private double balance;

    public Account(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.balance = 0;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void topUp(double amount) {
        this.balance += amount;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void deductBalance(double amount) {
        if (balance >= amount) {
            this.balance -= amount;
        }
    }
}