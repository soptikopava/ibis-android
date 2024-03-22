<p class="markdown-heading" dir="auto">
  <img src="https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&amp;logo=kotlin" style="max-width: 100%;">
   <a href="https://android-arsenal.com/api?level=31" rel="nofollow"><img alt="API" src="https://img.shields.io/badge/API-31%2B-brightgreen.svg?style=flat" style="max-width: 100%;">
   </a>
   <img src="https://github.com/cortinico/kotlin-android-template/workflows/Pre%20Merge%20Checks/badge.svg" alt="Pre Merge Checks" style="max-width: 100%;">
   <img src="https://img.shields.io/github/license/cortinico/kotlin-android-template.svg" style="max-width: 100%;">
</p>

# ibis-android
Ovládání displejů dopravního systému IBIS pomocí Bluetooth v mobilní aplikaci pro Android. Zejména pak ovládání displejů DOT-LED, MATRIX, FLIP-FLOP apod. Například BS210. Pomocí této aplikace lze provést změnu čísla Linky, zastávky, zobrazit vlastní texty apod.

Beta verze ke stažení zde: https://github.com/soptikopava/ibis-android/releases/tag/v1.0.0-beta

Aplikace podporuje světlé i tmavé téma. Minimální verze Android je verze 12.
Screenshot aktuální verze aplikace:


<div>
<img src="1710027836653.jpg" width="300" align="middle"  hspace="20" />
<img src="ibib-android_v1l.jpg" width="300" align="middle"  hspace="20" />
</div>

# Začínáme
Zde se pokus popsat co nejpodrobněji celý projek tak, abyste si jej mohli postavit taky. Popíšu ho včetně postupu, jak jsem na některé věci přišel.

## Požadavky
### Software 
Pro instalaci aplikace potřebujete zařízení se systémem Android verze 12 nebo vyšší. Dobře poslouží jak tablet, tak mobil. Zařízení musí mít zanuté Bluetooth. Komunikace s Bluetooth zahrnuje také váš souhlas ke zjištování pozice. Toto je dáno požadavky Androidu. Android jsem zvolil, protože mi chyběla právě tato taková aplikace. Programů pro Windows je více, například německý IBISUtil nebo český BSLoader. Nepřišlo mi však praktické, abych kvůli každému nápisu musel rozbalovat a zapojovat svůj notebook. Mobil mám u sebe nejčastěji.
### Hardware
* Samozřejmě je to displej, který potřebujeme. V mém případě se jedná o displej BS210, který jsem zakoupil od kamaráda. Nejsem nadšenec do dopravy. Spíše jsem viděl příležitost si vyrobit něco geekovského, co nemá každý 😎
* Bluetooth modul HC-06 nebo HC-05 (Já jsem použil HC-06.)
* Displej je napájený zdrojem =24V, takže diplej 24V/2A
* Protože bluetooth modul používá logiku 3.3V a displej 5V, potřebujeme rezistory buď 3 kusy 10 kOhm, nebo 1 kus 10 kOhm + 1 kus 20 kOhm. Prostě takové, co najdete v šuplíku nejčastěji. Pomocí rezistorů vytvoříme dělič napětí a přizpůsobíme tak použitou logiku na správnou hodnotu.
(Zde obrázek zapojení rezisorů jako dělič napětí.)
## Popis software
V aplikaci lze vybrat Bluetooth zařízení, na které je pak možné poslat datovou větu (tzv. payload). Ten se tvoří z příkazu IBIS. Payload pak obsahuje na předposledním pozici symbol pro návratový vozík `CR` a kontrolní součet.
Stiskem tlačítka `Odešlat na displej` se otevře sériová komunikace pomocí Bluetooth s displejem a payload se odešle přímo do sběrnice - v našem případě pomocí TTL přímo na piny procesoru.

## Popis hardware
Dovolte mi trochu zabrousit do popisu, jak jsem na to přišel.
### Díl 1. - Minulost ve znamení TTL
Abych zjistitl, jak komunikace funguje, byl mi doporučen převodník z RS232 na IBIS, který použáví 24V logiku HTL. HTL používá vždy dva signálové vodiče, kde každý z nich má vlastní odděllenou zem. Protože RS232 má obráceno logiku, proto musí mít převodník na HTL invertor, který obrací logickou  a převodník 1 na 0 a 0 na 1. Přišlo mi to až moc komplikované pro můj projekt. Proto jsem se rozhodl, že základní desku displeje prozkoumám podobněji. Zjistil jsem, že vstupy ze svorkovnice IBIS vedou na dělící člen, asi optočlen, který převádí 24V na nižší napětí a zároveň chrání další součástky proti přepětí. Bylo tedy jasné, že nejpravděpodobněji do procesoru bude přivedena logika TTL. S multimetrem jsem si ověřil, že se jedná o 5V logiku. Další součástka před procesorem je Schmittův obvod 74HC14D. Tento obvod zvyšuje napětí z dělících členů na logické úrovně 0V a 5V. Zárověň se snaží odstranit drobné rušení na vedení a skládá signál tak, aby měly logické 0 a 1 ostrou hranu a šly lépe rozpoznat. Při měření jsem našel na zákadní desce měřící body. Tyto body používají servisní technici, aby odhalili příčinu závady, když se jim na stůl dostane vadná deska. Využít tyto měřící body bylo více než příhodné. Vyhrabal jsem ze šuplíku převodník z USB na TTL Prolific PL2303 v ceně cca 35 Kč. A ejhle, fungovalo to jak s programem IBISUtil tak BSLoader.

Ukázka TTL sběrnice displeje BS210 a komunikační LED, které signalizují tok dat Rx, Tx aj.
![Sběrnice a komunikační LED na základní desce BS210:](1710027836691.jpg)
<p> </p>
Ukázka zapojení použítého převodníku TTL na USB přímo do PC:
<img src="IMG_20240310_185015.jpg" width="300" align="middle"  hspace="20"/>

Cílem tohoto projektu však není připojit displej k USB počítače bez složitých převodníků a kabelů. Cílem je ovládat displej bezdrátově skrze blouetooth v mobilu nebo tabletu.
### Díl 2. - Minulost ve znamení komunikace
Probram IBISUtils i BSLoader s TTL převodníkem fungoval bezchybně. Další fáze tedy byla zapojnení samotného blouetooth modulu. Měla to být nejnadnější fáze, ale nebyla. Modul sice data z mobilu příjmal, ale displej byl hluchý, prostě nereagoval.
Standartní komunikace na TTL linkách u pomaleších čipů bývá 9600 bit/s, U rychleších pak třeba 115200 bit/s. Vyzkoušel jsem všechny rychlosti a nic. Nezbylo nic jiného, než ze šupliku uprášit starý analyzátor a odposlechnout komunikaci s použitím opet převodníku USB na TTL. Jak je vidět na jednom z obrázku, ukázalo se, že displej komunikuje pouze s tímto nastavení sériového portu `1200 bit/s,7 bitů, sudá parita, 2 stopbyty`. 
Aby bluetooth mobul HC-06 uměl s diplejem komunikovat, je třeba jej správně nastavit. Tento modul má celkém jednoduché nastavení, které se provádí pomocí AT příkazů.
Pomocí převodníku USB nas TTL jsem provedl nastavení těmito příkazy:
``
AT+NAME=BS210-moje (nastavení názvu blouetooth zařízení)
AT+PSWD=1111       (nastavení PINu pro spárování s mobilem)
AT+UART=1200,2,2   (nastavené rychlosti portu, stopbyt a parity)

Některé verze firmware potřebují tyto příkazy:
AT+BAUD1           (nastavené rychlosti portu)
AT+PE              (nastavení sudé parity)
``

### Součastnost
Pro úpravu displeje BS210 jsem použil bezdrátový modul HC-06, který je modernější verzi staré HC-05, viz obr. vlevo. Bezdrátové moduly používají větčinou 3.3V logiku. K dipleji lze také připojit obyčejný TTL USB převodník s čipem Prolific 2303 nebo CH340, viz obr. vpravo. Tyto převodníky používají 5V logiku. S tímto převodníkem můžeme připojit displej přímo USB kabelem do PC, aniž bychom potřebovali další součástky. Je to nejsnadnější cesta jak komunikovat s displejem v kombinaci s programem BSLoader.exe nebo IBISUtils.

<p> </p>
  Ukázaka zapojení HC-06 k displeji:
  <img src="1710027836666.jpg" width="300" align="middle"  hspace="20"/>

<p> </p>
Ukázka připojení bezdrátového modulu přímo na TTL sběrnici k procesoru displeje BS210:
<img src="1710027836679.jpg"  align="middle"/>






Takto by to jednou mohlo vypadat:
![Obrázek nalezený na internetu, než jej nahradím něčím jiným](maxresdefault.jpg) Zdroj obrázku: internet

