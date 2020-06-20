# Тестирование приложения Интернет Банка с использованием патерна Page Object.
## Краткое описание
Приложение выполняет перевод денежных средств с карты на карту. После логина пользователь переходит на страницу со списком доступных ему карт. Нажав на кнопку "Пополнить" осуществляется переход на страницу перевода средств. При успешном переводе происходит возврат назад на страницу со списком карт.


Для тестового режима захардкожен пользователь: 

```
* login: 'vasya'
* password: 'qwerty123'
* verification code (hardcoded): '12345'
* cards:
    * first:
        * number: '5559 0000 0000 0001'
        * balance: 10 000 RUB
    * second:
        * number: '5559 0000 0000 0002'
        * balance: 10 000 RUB

```

 ## Тестовые сценарии

 Было проведено:
* позитивное и негативное тестирование
* функциональное тестирование авторизации пользователя
* функциональное тестирорвание операции перевода денежных средств


## Тестовое окружение
1. Windows11 X64
2. Браузер Google Chrome v.83.0.4103.97
3. OpenJDK 11 (LTS)
4. IntelliJ IDEA 2019.2.4 (Community Edition)
5. Junit-jupiter v.5.6.1
6. Selenide v.5.12.0
7. Javafaker v.1.0.1
8. Приложение app-ibank-build-for-testers.jar 

## Руководство использования

При разработке автотестирования использовались патерн Page Objects, Faker, Lombok, Data-классы (для группировки нужных полей) и утилитный класс-генератор данных.
* Запустите приложение командой 

```
java -jar ./artifacts/app-ibank-build-for-testers.jar 
```
* Запустите автотесты командой

```
./gradlew test -Dselenide.headless=true --info
```

* Проверить успешность сборки CI на Appveyor

[![Build status](https://ci.appveyor.com/api/projects/status/yyr30v0ic6kgyn71/branch/master?svg=true)](https://ci.appveyor.com/project/leonnika/aqa-hw6-pageobjects/branch/master)

## Результаты тестирования
В ходе тестирования были выявлены ошибки приложения. По ошибкам заведены issue:

[Ошибочный перевод на балансе на карте меньше суммы перевода](https://github.com/leonnika/aqa-hw6-PageObjects/issues/1)

[Ошибочное выполнение перевода при указании нулевого перевода](https://github.com/leonnika/aqa-hw6-PageObjects/issues/3)

[Ошибочное выполнение перевода при пустом поле перевода](https://github.com/leonnika/aqa-hw6-PageObjects/issues/4)

