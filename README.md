# Hoď kostkou

Dvě Android aplikace se stejnou funkcí (hod kostkou), implementované dvěma různými přístupy.

## Struktura repozitáře

- `003diceThrow/` – **XML + Kotlin**, šablona Empty Views Activity, přístup k prvkům přes `findViewById`.
- `003DiceThrowKotlin/` – **Jetpack Compose**, šablona Empty Activity, rozhraní definované v Kotlinu.

Obě aplikace mají stejné zadání:
- Nadpis „Hoď kostkou“, velký symbol kostky, tlačítko „Hodit“.
- Po kliknutí 10 náhodných změn kostky v intervalu 250 ms, poté výsledný hod (celkem 11 zobrazení).
- Tlačítko je po dobu animace zakázané.
- Obsah je vystředěný a nepřekrytý systémovými lištami.

## Porovnání: jak každá varianta aktualizuje zobrazenou kostku

### XML + Kotlin (`findViewById`)

Kostka je klasický `TextView` v XML layoutu. V `onCreate` se na něj jednou získá reference přes `findViewById(R.id.tvDice)`. Animace hodu běží přes `Handler(Looper.getMainLooper())`, který si sám sebe opakovaně naplánuje pomocí `postDelayed` — a v každém kroku **přímo, imperativně** přepíše `tvDice.text = diceFaces.random()`. Po doběhnutí se stejně imperativně nastaví `btnRoll.isEnabled = true/false`. Žádný automatický mechanismus nehlídá, že view odpovídá datům — je to na programátorovi.

### Jetpack Compose

Kostka nemá žádnou referenci na view. Místo toho existuje stavová proměnná `var diceFace by remember { mutableStateOf(...) }`. Klik na tlačítko spustí coroutine (`rememberCoroutineScope().launch`), která volá suspend funkci `rollDice()` — ta přes `delay(250)` v cyklu jen **mění hodnotu stavu** (`diceFace = ...`), nikdy žádný view přímo nenastavuje. Kdykoli se `diceFace` změní, Compose automaticky znovu vykreslí (rekompozice) `Text`, který ho zobrazuje. Podobně `isRolling` řídí `enabled` parametr tlačítka. Aktualizace UI je tedy **deklarativní a reaktivní** — stav je jediný zdroj pravdy, UI se mu samo přizpůsobí.

### Shrnutí rozdílu

| | XML + Kotlin | Jetpack Compose |
|---|---|---|
| Přístup k UI | `findViewById` → referenci na `View` | žádná reference, jen stav (`State`) |
| Jak se kostka mění | přímý zápis `tvDice.text = ...` | změna `mutableStateOf` → rekompozice |
| Plánování kroků | `Handler.postDelayed` (callbacky) | `suspend fun` + `delay()` (coroutines) |
| Styl aktualizace | imperativní (řekni *jak* změnit view) | deklarativní (řekni *co* zobrazit pro daný stav) |

## Screenshoty

_TODO: doplnit screenshoty obou aplikací._
