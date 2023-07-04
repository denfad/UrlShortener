UrlShortener
=============
Сервис сокращения ссылок
----------------
Стэк:
- Java
- Spring Boot
- Spring Boot Data
- MongoDb

Требования к БД:
- установлено MongoDb
- работает на порту 27017

Сборка проекта:
```sh
$ ./gradlew build
```
Необходимо чтобы БД уже было поднято для успешного прохождения тестов

Запуск проекта:
```sh
$  java -jar ./build/libs/UrlShortener-0.0.1-SNAPSHOT.jar
```

Сценарий использования
-
Для выполнения тестового задания и корректной работы программы необходимо реализовать следущие сценарии использования:
- При отправке POST-запроса на адрес `/generate` должен возвращаться ответ формата JSON, в теле которого находится сокращенная ссылка. В теле же запроса шлётся JSON с исходной ссылкой, которую хотим сократить.
- При GET-запросе по пути `/r/{url}`, где `url` - это сокращенная ссылка, должна происходить переадресация по длинной ссылке.
- Для получения ранжированной статитстики по колличеству переадресаций для каждой короткой ссылки выполняется GET-запрос по пути `/stats?page={}&?size={}`, где можно указать страницу списка статистики и размер страницы.
- Для всех вышеописанных сценариев должна выполняться валидация данных на корректность, например проверка на то, что передано настоящее Url или номер страницы не отрицательное число.
- При возникновении ошибок или не прохождении валидации должен существовать обработчик ошибок, который вернёт корректный ответ пользователю.

Требования к сервису
-
К сервису выдвигаются стандартные требования к REST-приложению:
- быстрое выполнение редиректа по короткой ссылке
- генерирование короткой ссылки происходит с помощью Base62
- установка ttl через конфигурационный файл
- придерживание принципов SOLID
- простота модфицирования
- заложенная возможность масштабирования
- корректность ответов при возникновении ошибок

Архитектура
-
Приложение имеет трёхуровневую организацию. Каждый уровень имеет свою область применения. 
Контроллеры занимаются только принятием запросов от пользователей, формированием ответов ему. Чтобы реализовать описанный выше сценарий работы необходимо 4 контроллера, отвечающих за генерацию ссылок, редирект по ним, сбор статистики и обработка ошибок. 

Уровнем ниже находятся сервисы. Один будет выполнять бизнеслогику для работы с ссылками (создание, редирект и статистика). Второй сервис генерирует `идентификатор` для добавляемой в базу данных длинной ссылки (в разделе "Масштабирование" объяснение зачем). 
Потом по этому идентификатору происходит создание короткой ссылки по Base62. 

Выполнение запросов к базе данных происходит через слой репозиториев. Приложение использует две сущности - это модель длинной Url ссылки и модель идентификатора.

Для соблюдения принципов SOLID всё взаимодействие между слоями происходит через интерфейсы, без конкретизации.  

Так как одно из требований к проекту - это быстрота редиректа, нам потребуется БД, умеющая эффективно читать данные. Поэтому мною была выбрана MongoDb, к тому же её очень легко масштабировать (есть встроенное шардирование) и можно "из коробки" установить ttl для документов.

Чтобы сделать взаимодействие пользователя с приложением удобнее, было сделано три `dto` объекта. Один объект отвечает за передачу длинной и возвращение короткой ссылок. Также имеется объект для передачи статистики для каждой ссылки, где показано число редиректов по ней и место в общем "зачёте". 

Масштабирование 
-
В приложение заложена возможность горизонтального масштабирования, который можно проводить в два этапа.

### Первый:

В приложении имеется интерфейс `IdentiferGenerator` с методом `generateIdentifier`, в аргумент которого передаётся строка.
При масштабировании, когда приложение работает на разных серверах, каждое запущенное приложение шлёт своё заранее определённое имя.
Данный интерфейс будет реализовывать сервис, взаимодействующий с ZooKeeper, распределяющий между серверами разные числовые промежутки. Например:
- "server1" : [0, 100000000]
- "server2" : [100000000, 200000000]
- и т.д.

Так мы гарантируем, что, при создании новой ссылки в БД, сервисы не будут записям задвать одинаковые идентификаторы.

### Второй:

В MobgoDb заложены удобные инструменты быстрого масштабирования. Например, шардирование. Но у сущностей `UrlDocument` нет такого значения, по которому MongoDb могло бы выполнять авто-шардирование.
Мы можем задать своё правило шардирования, например, если база данных развёрнута на трёх разных серверах, то каждая из них хранит документы с идентификатором удовлетворяющим условию `N = id % 3`, где N - условный номер сервера с БД. То есть в БД с номер 1 попадают документы с идентификатором, остаток от деления на 3 у которых равен 1.