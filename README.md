# ibis-android
Ovládání displejů dopravního systému IBIS pomocí Bluetooth v mobilní aplikaci pro Android. Zejména pak displeje DOT-LED, MATRIX, FLIP-FLOP apod. Například BS210. Lze provést změnu čísla Linky, zastávky, vlastní texty apod.

Beta verze ke stažení zde: https://github.com/soptikopava/ibis-android/releases/tag/v1.0.0-beta

Aplikace podporuje světlé i tmavé téma. Minimální verze Android je verze 12. Screenshot aktuální verze aplikace:

<div>
<img src="1710027836653.jpg" width="300" align="middle"  hspace="20"/>
<img src="ibib-android_v1l.jpg" width="300" align="middle"  hspace="20"/>
</div>

V aplikaci lze vybrat Bluetooth zařízení, na které je pak možné poslat datovou větu (tzv. payload). Ten se tvoří z příkazu IBIS. Payload pak obsahuje na předposledním pozici symbol pro návratový vozík "CR" a kontrolní součet.
Stiskem tlačítka Odešli na displej se otevře sériová komunikace pomocí Bluetooth a payload se odešle přímo do sběrnice - v našem případě pomocí TTL přímo na piny procesoru.

Pro úpravu displeje BS210 jsem použil bezdrátový modul HC-06, který je modernější verzi staré HC-05.

<img src="1710027836666.jpg" width="400"/>


Ukázka připojení bezdrátového modulu přímo na TTL sběrnici k procesoru displeje BS210:
![Připojení k základní desce displeje BS210:](1710027836679.jpg)

Ukázka TTL sběrnice displeje BS210 a komunikační LED, které signalizují tok dat Rx, Tx aj.
![Sběrnice a komunikační LED na základní desce BS210:](1710027836691.jpg)

Takto by to jednou mohlo vypadat:
![Obrázek nalezený na internetu, než jej nahradím něčím jiným](maxresdefault.jpg) Zdroj obrázku: internet

