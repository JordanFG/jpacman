# JPacman — Conventions pour Claude

Projet éducatif JPacman (cours MGL804, ÉTS). Historique de contribution basé
sur des branches par contributeur/tâche fusionnées via Pull Request vers
`master` (voir les PR précédentes du dépôt).

## Workflow de fin de tâche (automatique)

À la fin de chaque tâche de modification de code (bug fix, nouvelle
fonctionnalité, refactoring demandé dans une conversation) :

1. **Lancer la suite de tests** : `./gradlew test`.
2. **Si des tests échouent** : essayer de corriger le problème puis
   relancer les tests. Ne pas créer de Pull Request tant que la suite
   n'est pas entièrement verte. Si le blocage persiste après une tentative
   de correction raisonnable, s'arrêter et expliquer le problème plutôt que
   d'insister indéfiniment.
3. **Créer une branche dédiée à la tâche** à partir de `master`
   (`fix/...`, `feat/...`, `chore/...` selon la nature du changement) et y
   committer les changements.
4. **Pousser la branche et ouvrir une Pull Request vers `master`** avec
   `gh pr create`, avec un résumé clair des changements et la confirmation
   que les tests passent dans la description.

**Ces actions (push de branche + création de PR) sont pré-autorisées par
cette instruction** : ne pas redemander confirmation avant de pousser une
branche ou d'ouvrir une PR dans ce dépôt, tant que l'étape 2 est validée.
Continuer en revanche à toujours demander confirmation explicite avant
toute action plus sensible : force-push, suppression de branche, merge
direct dans `master`, modification de l'historique existant, ou tout ce
qui n'est pas couvert explicitement ci-dessus.

## Commandes utiles

- Tests : `./gradlew test`
- Checkstyle : `./gradlew checkstyleMain checkstyleTest checkstyleDefaultTest`
  (violations existantes ignorées volontairement par le build,
  `ignoreFailures = true` — ne pas en introduire de nouvelles sur les
  fichiers touchés).
- `gh` CLI : si `gh` n'est pas trouvé dans le PATH de la session (PATH pas
  rafraîchi après une install récente), utiliser le chemin complet
  `C:\Program Files\GitHub CLI\gh.exe`.
- Le build nécessite Java 25 (`sourceCompatibility`/`targetCompatibility`
  dans `gradle.properties`) ; un JDK 24 local fonctionne pour compiler et
  tester, mais fait échouer l'instrumentation JaCoCo (traces d'erreur sur
  des classes internes du JDK) — c'est du bruit inoffensif, pas un échec
  de test réel. Se fier au résumé `tests completed` / au contenu des
  rapports XML sous `build/test-results/`, pas à la présence de traces de
  pile JaCoCo dans la sortie.
