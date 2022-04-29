JCNetwork, Members

Folgende Schritte um den Code für Members lokal ans Laufen zu bekommen:
1. Installationen:
   - IntelliJ installieren (https://www.jetbrains.com/de-de/idea/download/#section=windows) (Ultimate gibt es als Student kostenfrei)
   - Java JDK 15 installieren (z.B. https://cdn.azul.com/zulu/bin/zulu15.36.13-ca-jdk15.0.5-win_x64.msi)
   - MongoDB Community Server installieren (https://www.mongodb.com/try/download/community)

2. Projektordner in IntelliJ öffnen 

3. File -> New Project Setup -> Structure (for New Projects) -> Project SDK Java 15 auswählen

4. Run -> Edit Configuration 
   - add new -> Spring Boot 
     - Main class: com.jcnetwork.members.MembersApplication
     - VM options: -Djasypt.encryptor.password=<jasypt Passwort> (ohne '<' und '>')

5. MongoDB Compass öffnen
   - connection String: mongodb://localhost:27017/
   - Einträge manuell anpassen !ToDo! (bitte melden bei Leander Christmann, Eric Amann oder Justus Miller)

6. Members starten (Umschalt + 10 normal / Umschalt + 9 debug oder grünes Dreieck)

7. Applikation unter localhost:8080 im browser öffnen (das Admin-Dashboard kann eingesehen werden unter '/<Vereinsname>/admin/dashboard')


Weitere Quellen für Infos/ Links/ Tutorial/ etc.
- https://www.baeldung.com/
- https://www.youtube.com/watch?v=u8a25mQcMOI

Technologien:
- spring jpa (Backend)
- thymeleaf (Template-Engine)
- jquery
- bootstrap
- mongodb
- Model-View-Controller (Architektur-Schema)
