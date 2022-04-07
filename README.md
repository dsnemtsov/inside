Project Inside
---
Packages:
- controller
    - Содержит контроллеры
- entity
    - Содержит сущности баз данных
- exception
    - Содержит кастомные исключения
- model
    - Содержит используемые модели
- repository
    - Содержит используемые репозитории баз данных
- security
    - Содержит классы, необходимые для настройки jwt токена
- service
    - Содержит сервисы
- util
    - Содержит вспомогательные классы
---
JWT token:
Если пользователь обращается к эндпоинту, содержащему в пути /messages, request должен содержать заголовок Authorization с содержимым токена в формате Bearer_token, иначе возвращается ошибка со статусом 403.
---
Исключения. В программе используется особый вид исключений: GlobalException. Данные исключения обрабатываются GlobalExceptionHandler.
Все исключения, если они будут возвращены на клиентскую сторону, должны возвращать json с содержимым: "error" : "message".
---
- [PostController](src/main/java/ru/dimas224/yandex/controller/PostController.java)
  - <span style = "color:Green"> endpoint </span>: /users; <span style = "color:Cyan"> http method </span>: POST,   <span style="color:Orange"> body </span>: name (String), message (String),   <span style="color:Red"> требования </span>: Header Authorization (Bearer_token).  
  Если в теле запроса поле "message": "history 10" - возвращает пользователю последние 10 сообщений, в противном случае добавляет сообщение в базу данных. 
- [UserController](src/main/java/ru/dimas224/yandex/controller/UserController.java)
  -  <span style = "color:Green"> endpoint </span>: /users; <span style = "color:Cyan"> http method </span>: POST,   <span style="color:Orange"> body </span>: name (String), password (String).  
  Регистрирует пользователя в базе данных, если name уникален, в противном случае выбрасывает ошибку.
- -  <span style = "color:Green"> endpoint </span>: /users/login; <span style = "color:Cyan"> http method </span>: POST,   <span style="color:Orange"> body </span>: name (String), password (String).  
  Если пользователь с таким именем и паролем существует - логинит его и возвращает jwt токен, в противном случае возвращает ошибку.
---
База данных:

![](../../../Downloads/public.png)

---
curl:
В папке /scripts находятся запросы:
- register.sh - регистрация пользователя;
- login.sh - логин, получение jwt токена;
- addMessage.sh - добавление сообщения в БД;
- getMessages.sh - получение последних 10 сообщений пользователя.