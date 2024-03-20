<p class="markdown-heading" dir="auto">
  <img src="https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&amp;logo=kotlin" style="max-width: 100%;">
   <a href="https://android-arsenal.com/api?level=31" rel="nofollow"><img alt="API" src="https://img.shields.io/badge/API-31%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
   </a>
   <img src="https://github.com/cortinico/kotlin-android-template/workflows/Pre%20Merge%20Checks/badge.svg" alt="Pre Merge Checks" style="max-width: 100%;">
   <img src="https://img.shields.io/github/license/cortinico/kotlin-android-template.svg" style="max-width: 100%;">
</p>

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
Pro úpravu displeje BS210 jsem použil bezdrátový modul HC-06, který je modernější verzi staré HC-05, viz obr. vlevo. Bezdrátové moduly používají větčinou 3.3V logiku. K dipleji lze také připojit obyčejný TTL USB převodník s čipem Prolific 2303 nebo CH340, viz obr. vpravo. Tyto převodníky používají 5V logiku. S tímto převodníkem můžeme připojit displej přímo USB kabelem do PC, aniž bychom potřebovali další součástky. Je to nejsnadnější cesta jak komunikovat s displejem v kombinaci s programem BSLoader.exe

<div>
<img src="1710027836666.jpg" width="400" align="middle"  hspace="20"/>
<img src="IMG_20240310_185015.jpg" width="400" align="middle"  hspace="20"/>
</div>


Ukázka připojení bezdrátového modulu přímo na TTL sběrnici k procesoru displeje BS210:
<img src="1710027836679.jpg"  align="middle"/>



Ukázka TTL sběrnice displeje BS210 a komunikační LED, které signalizují tok dat Rx, Tx aj.
![Sběrnice a komunikační LED na základní desce BS210:](1710027836691.jpg)



Takto by to jednou mohlo vypadat:
![Obrázek nalezený na internetu, než jej nahradím něčím jiným](maxresdefault.jpg) Zdroj obrázku: internet

