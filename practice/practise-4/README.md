# Занятие 4. Testing
Много тестов - меньше стресса. Реализуем тесты на все что только можно.
## Функциональные требования
1. Ничего не должно сломаться.
## Требования к реализации
1. В папке practise-4 перенести проект из 3 практики.
2. Дописать тесты на все классы, используя, [правила](../../FAQ/testing/UNIT.md).
3. Добавить jacoco плагин.

## Тестирование
1. Запустить тесты.
   - Все тесты пройдут успешно.
2. Сгенерировать отчет по тестовому покрытию.
   - Покрытие тестов должно быть больше 90% в отчете.
3. Запуск задачи checkstyleTest выдает успешный результат
## Задание на доработку
1. Поставить к себе на ноутбук/сервер приложение, которое будет делать историю гитхаба зеленой 
(делать много коммитов, желательно с java)
## Пояснения к реализации
Для внедрения плагина jacoco необходимо добавить идентификатор с его конфигурацией в build gradle:
<details>
<summary>Jacoco</summary>
```
plugins {
	jacoco
}
tasks.test {
	finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
	dependsOn(tasks.test) // tests are required to run before generating the report
}
```
</details>
Для его запуска нужно пройти в gradle.Tasks.verification.jacocoTestReport.
<details> 
<summary>Ссылки</summary>
1. https://docs.gradle.org/current/userguide/jacoco_plugin.html#sec:configuring_the_jacoco_plugin
2. https://javarush.com/groups/posts/2590-top-50-java-core-voprosov-i-otvetov-na-sobesedovanii-chastjh-1 
3. https://javarush.com/groups/posts/2592-top-50-java-core-voprosov-iotvetov-na-sobesedovanii-chastjh-2
</details>