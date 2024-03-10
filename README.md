# ibis-android
Ovládání displejů dopravního systému IBIS pomocí bluetooth v mobilní aplikaci pro Android. Zejména pak displeje DOT-LED, MATRIX, FLIP-FLOP apod. Například BS210. Lze provést změnu čísla Linky, zastávky, vlastní texty apod.
Screenshot aktuální verez aplikace:
<img src="1710027836653.jpg" width="400">

V aplikaci lze vybrat bluetoot zařízení, na které je pak možné poslat datovou větu (tzv. payload). Ten se tvoří z příkazu IBIS. Payload pak obsahuje na předposledním pozici symbol pro návratový vozík "CR" a kontrolní součet.
Stiskem tlačítka Odesli na displej se otevře seriová komunikace pomocé bluetootha a payload se odešle přímo do sběrnice - v našem případě pomocí TTL přímo na piny procesoru.

Pro úpravu displeje BS210 jsem použil bezdrátový modul HC-06, který je modernější verzi staré HC-05.
<img src="1710027836666.jpg" width="400">

Ukázka připojení bezdrátového modulu přímo na TTL sběrnici procesoru displeje BS210:
![Připojení k základní desce displeje BS210:](1710027836679.jpg)

Ukázka TTL zběrnice displeje BS210 a komunikační LED, které signaluzí tok dat Rx, Tx aj.
![Sběrnice a komunikační LED na základní desce BS210:](1710027836691.jpg)

Takto by to jednou mohlo vypdat:
![Obrázek nalezený na internetu, než jej nahradím něčím jiným](maxresdefault.jpg)
