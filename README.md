# Ensemble Disjoint

La classe **`EnsembleDisjoint`** permet la gestion de domaines restreints en limitant les valeurs possibles dans un ensemble. Ces domaines sont des ensembles de nombres réels (ℝ), représentés par une suite d’intervalles disjoints. Ce projet inclut également des tests unitaires JUnit 5 pour vérifier le fonctionnement des méthodes.

---

## Description des TDAs

Pour construire la classe **`EnsembleDisjoint`**, nous utilisons une classe complémentaire **`Intervalle`**. Ces deux classes travaillent ensemble pour fournir une représentation des ensembles et leurs manipulations.

### Classe `Intervalle`

La classe **`Intervalle`** représente des intervalles de valeurs réelles (double) et fournit des services associés.

#### Notations d'intervalles
- **[𝑑, 𝑓]** : Intervalle fermé à gauche et fermé à droite, \( \{𝑥 ∈ ℝ | 𝑑 ≤ 𝑥 ≤ 𝑓\} \)
- **[𝑑, 𝑓[** : Intervalle fermé à gauche et ouvert à droite, \( \{𝑥 ∈ ℝ | 𝑑 ≤ 𝑥 < 𝑓\} \)
- **]𝑑, 𝑓]** : Intervalle ouvert à gauche et fermé à droite, \( \{𝑥 ∈ ℝ | 𝑑 < 𝑥 ≤ 𝑓\} \)
- **]𝑑, 𝑓[** : Intervalle ouvert à gauche et ouvert à droite, \( \{𝑥 ∈ ℝ | 𝑑 < 𝑥 < 𝑓\} \)

#### Règles importantes
1. \( 𝑑 \) (borne inférieure) doit être inférieur ou égal à \( 𝑓 \) (borne supérieure).
2. Deux intervalles sont égaux si et seulement s’ils ont les mêmes bornes avec les mêmes inclusivités.

#### Méthodes importantes
- **Constructeur**
    - Accepte les deux bornes et leurs inclusivités en arguments.
    - Garantit que l’intervalle respecte \( 𝑑 \leq 𝑓 \).

- **Equals**
    - Vérifie si deux intervalles sont égaux.
    - Cette méthode est déjà fournie et ne doit pas être modifiée.

---

### Classe `EnsembleDisjoint`

La classe **`EnsembleDisjoint`** représente un ensemble de valeurs réelles définies par une suite d’intervalles disjoints \( 𝑆 = \{𝑟_1, 𝑟_2, …, 𝑟_𝑛\} \).

#### Règles importantes
1. **Deux intervalles sont disjoints** s’ils n’ont aucune valeur en commun :
   \[
   (∀ 𝑣₁ ∈ 𝑟₁, 𝑣₁ ∉ 𝑟₂) ∧ (∀ 𝑣₂ ∈ 𝑟₂, 𝑣₂ ∉ 𝑟₁)
   \]
2. La collection initiale d’intervalles peut contenir des chevauchements ou des adjacences.
    - Ces chevauchements/adjacences sont automatiquement fusionnés pour former des intervalles disjoints.

---

## Méthodes principales

### Constructeurs

1. **`EnsembleDisjoint()`**  
   Crée un ensemble disjoint vide (aucun intervalle).

2. **`EnsembleDisjoint(Collection<Intervalle> intervalles)`**  
   Crée un ensemble disjoint à partir d’une collection d’intervalles. Si la collection contient des chevauchements ou des adjacences, ceux-ci sont fusionnés.

---

### Services disponibles

#### Méthode `appartient`
- Vérifie si une valeur \( 𝑣 \) appartient à un ensemble \( 𝑆 \).
- Une valeur appartient à l’ensemble si elle appartient à l’un des intervalles qui composent l’ensemble.

#### Méthode `union`
- Construit un nouvel ensemble qui contient les valeurs de l’ensemble courant et :
    1. **Un intervalle passé en argument :**  
       \[
       𝑆𝑟 = 𝑆.𝑢𝑛𝑖𝑜𝑛(𝑟) ≜ 𝑣 ∈ 𝑆𝑟 ↔ 𝑣 ∈ 𝑆 ∨ 𝑣 ∈ 𝑟
       \]
    2. **Un autre ensemble disjoint passé en argument :**  
       \[
       𝑆𝑟 = 𝑆.𝑢𝑛𝑖𝑜𝑛(𝑆𝑎) ≜ 𝑣 ∈ 𝑆𝑟 ↔ 𝑣 ∈ 𝑆 ∨ 𝑣 ∈ 𝑆𝑎
       \]

#### Méthode `intersection`
- Construit un nouvel ensemble qui contient uniquement les valeurs communes entre l’ensemble courant et :
    1. **Un intervalle passé en argument :**  
       \[
       𝑆𝑟 = 𝑆.𝑖𝑛𝑡𝑒𝑟𝑠𝑒𝑐𝑡𝑖𝑜𝑛(𝑟) ≜ 𝑣 ∈ 𝑆𝑟 ↔ 𝑣 ∈ 𝑆 ∧ 𝑣 ∈ 𝑟
       \]
    2. **Un autre ensemble disjoint passé en argument :**  
       \[
       𝑆𝑟 = 𝑆.𝑖𝑛𝑡𝑒𝑟𝑠𝑒𝑐𝑡𝑖𝑜𝑛(𝑆𝑎) ≜ 𝑣 ∈ 𝑆𝑟 ↔ 𝑣 ∈ 𝑆 ∧ 𝑣 ∈ 𝑆𝑎
       \]

#### Méthodes `bornerSuperieur` et `bornerInferieur`
- Restreint les valeurs de l’ensemble courant en :
    1. Limitant la borne supérieure d’un ensemble à une valeur donnée.
    2. Limitant la borne inférieure d’un ensemble à une valeur donnée.

#### Méthode `appliquerFctLineaire`
- Applique une fonction linéaire aux bornes de chaque intervalle de l’ensemble courant.
- La fonction doit être strictement linéaire.

#### Méthode `getIntervallesTries`
- Retourne la liste des intervalles disjoints de l’ensemble, triée en ordre croissant.
- Les intervalles sont fusionnés si nécessaire.

---

## Tests unitaires

Ce projet inclut des tests unitaires JUnit 5 pour valider le comportement de chaque méthode. Les tests couvrent :
- Cas standards (chevauchements, adjacences, valeurs disjointes).
- Cas limites (intervalles vides, ensembles vides, etc.).
- Fonctionnement des restrictions et transformations.

---

## Instructions

1. Clonez ce dépôt.
2. Assurez-vous que votre IDE (comme IntelliJ IDEA ou Eclipse) prend en charge JUnit 5.
3. Exécutez les tests unitaires pour valider le comportement des méthodes.

---

## Auteur

Développé dans le cadre d’un projet académique. Les commentaires et contributions sont les bienvenus !