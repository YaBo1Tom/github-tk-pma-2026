# Hoď kostkou

## XML + Kotlin (findViewById)

Kostka je klasický `TextView` v XML layoutu. V `onCreate` se na něj jednou získá reference přes `findViewById(R.id.tvDice)`. Animace hodu běží přes `Handler(Looper.getMainLooper())`, který si sám sebe opakovaně naplánuje pomocí `postDelayed` — a v každém kroku **přímo, imperativně** přepíše `tvDice.text = diceFaces.random()`. Po doběhnutí se stejně imperativně nastaví `btnRoll.isEnabled = true/false`. Žádný automatický mechanismus nehlídá, že view odpovídá datům — je to na programátorovi.

## Jetpack Compose

Kostka nemá žádnou referenci na view. Místo toho existuje stavová proměnná `var diceFace by remember { mutableStateOf(...) }`. Klik na tlačítko spustí coroutine (`rememberCoroutineScope().launch`), která volá suspend funkci `rollDice()` — ta přes `delay(250)` v cyklu jen **mění hodnotu stavu** (`diceFace = ...`), nikdy žádný view přímo nenastavuje. Kdykoli se `diceFace` změní, Compose automaticky znovu vykreslí (rekompozice) `Text`, který ho zobrazuje. Podobně `isRolling` řídí `enabled` parametr tlačítka. Aktualizace UI je tedy **deklarativní a reaktivní** — stav je jediný zdroj pravdy, UI se mu samo přizpůsobí.
