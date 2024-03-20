<p class="markdown-heading" dir="auto">
    <a target="_blank" rel="noopener noreferrer nofollow" href="https://camo.githubusercontent.com/55c3be8b5692ac891d01cd59a6cf681e3cf0f8e7334e06d5ed2a69c103791c06/68747470733a2f2f696d672e736869656c64732e696f2f6769746875622f6c616e6775616765732f746f702f636f7274696e69636f2f6b6f746c696e2d616e64726f69642d74656d706c6174653f636f6c6f723d626c7565266c6f676f3d6b6f746c696e">
    <img src="https://camo.githubusercontent.com/55c3be8b5692ac891d01cd59a6cf681e3cf0f8e7334e06d5ed2a69c103791c06/68747470733a2f2f696d672e736869656c64732e696f2f6769746875622f6c616e6775616765732f746f702f636f7274696e69636f2f6b6f746c696e2d616e64726f69642d74656d706c6174653f636f6c6f723d626c7565266c6f676f3d6b6f746c696e" alt="Language" data-canonical-src="https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&amp;logo=kotlin" style="max-width: 100%;">
  </a>
   <a href="https://android-arsenal.com/api?level=31" rel="nofollow"><img alt="API" src="https://camo.githubusercontent.com/0eda703da08220e08354f624a3fc0023f10416a302565c69c3759bf6e0800d40/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4150492d32312532422d627269676874677265656e2e7376673f7374796c653d666c6174" data-canonical-src="https://img.shields.io/badge/API-31%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
   </a>
    <img src="https://github.com/cortinico/kotlin-android-template/workflows/Pre%20Merge%20Checks/badge.svg" alt="Pre Merge Checks" style="max-width: 100%;">
  <a target="_blank" rel="noopener noreferrer nofollow" href="https://camo.githubusercontent.com/796cf4deba8b007685ec9d110ada43bf49df8450c5732b82e692baf8df003df7/68747470733a2f2f696d672e736869656c64732e696f2f6769746875622f6c6963656e73652f636f7274696e69636f2f6b6f746c696e2d616e64726f69642d74656d706c6174652e737667">
    <img src="https://camo.githubusercontent.com/796cf4deba8b007685ec9d110ada43bf49df8450c5732b82e692baf8df003df7/68747470733a2f2f696d672e736869656c64732e696f2f6769746875622f6c6963656e73652f636f7274696e69636f2f6b6f746c696e2d616e64726f69642d74656d706c6174652e737667" alt="License" data-canonical-src="https://img.shields.io/github/license/cortinico/kotlin-android-template.svg" style="max-width: 100%;">
  </a>
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

