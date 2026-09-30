# SEG3502 Lab 2 : Calculatrice Spring MVC + Thymeleaf

Réimplémentation de la calculatrice du labo précédent avec Spring Boot 4, Kotlin, Spring MVC et Thymeleaf.

## Prérequis
- JDK 17 ou plus récent (le projet cible Java 17)
- Aucune installation de Gradle nécessaire : le wrapper (`gradlew`) le télécharge

## Exécuter les tests
```bash
./gradlew test          # macOS / Linux
.\gradlew.bat test      # Windows
```

## Lancer l'application
```bash
./gradlew bootRun       # macOS / Linux
.\gradlew.bat bootRun   # Windows
```
Ouvrir ensuite http://localhost:8080/ 

## Structure
- `WebController.kt` : routes `/` et `/calculate` (paramètres `n1`, `n2`, `operation` = add, sub, mul, div)
- `templates/home.html` : vue Thymeleaf (formulaire, résultat, messages d'erreur)
- `static/css/style.css` : mise en forme
- `WebControllerTest.kt` : tests MockMvc des quatre opérations et des cas d'erreur