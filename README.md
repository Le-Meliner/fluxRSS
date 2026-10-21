# fluxRSS

Application Android (Java) qui lit un flux RSS et en extrait les articles : titre, date, description et image.

## Où se trouve le code intéressant

Tout le code écrit à la main est dans [`app/src/main/java/com/example/fluxrss/`](app/src/main/java/com/example/fluxrss/).

| Fichier | Rôle |
|---|---|
| [`MyRSSsaxHandle.java`](app/src/main/java/com/example/fluxrss/MyRSSsaxHandle.java) | **Cœur du projet.** Télécharge le flux RSS depuis une URL et le parse avec SAX. |
| [`Article.java`](app/src/main/java/com/example/fluxrss/Article.java) | Modèle de données d'un article : titre, date, description, URL de l'image. |
| [`activity_main.xml`](app/src/main/res/layout/activity_main.xml) | Mise en page de l'écran principal. |
| [`AndroidManifest.xml`](app/src/main/AndroidManifest.xml) | Déclaration de l'application et de son activité de lancement. |

### Comment fonctionne le parseur

`MyRSSsaxHandler` hérite de `DefaultHandler` et lit le XML du flux élément par élément :

- `processFeed()` ouvre l'URL (définie avec `setUrl()`) et lance le parsing.
- `startElement()` détecte l'entrée dans un `<item>` et dans ses balises `<title>`, `<description>` et `<pubDate>`. Il lit l'URL de l'image dans `<media:content>` ou `<enclosure>`.
- `characters()` accumule le texte de la balise en cours.
- `endElement()` crée un `Article` à la fin de chaque `<item>` et l'ajoute à la liste, que l'on récupère avec `getArticles()`.

## Le reste du projet

Le reste a été généré par Android Studio et n'a pas été modifié :

- `build.gradle.kts`, `settings.gradle.kts`, `gradle/` : configuration de compilation (minSdk 24).
- `app/src/main/res/mipmap-*`, `drawable/` : icônes de l'application.
- `app/src/test/`, `app/src/androidTest/` : tests d'exemple.
