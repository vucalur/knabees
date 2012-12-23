	* pracujemy na Eclipse Juno J2EE i nie starszym, bo nie obsługują wszystkich bajerów,
	  a tym bardziej ustawień które przygotowałem
	  Jak będziecie chcieli to wrzucę na jakiś torrent ze swoją instalacją eclipse'a z wszystkim ustawionym

	* wtyczki do zainstalowania do Eclipse'a:
		* obowiązkowo: m2eclipse (wsparcie do Mavena - nie trzeba mieć zainstalowanego, eclipse ma swój wbudowany)
		* eGit (chyba że ktoś chce ręcznie odnajdywać się w zmianach, to powodzenia)
		* jakby ktoś chciał majstrować z GUI: window builder pro

	* Projekt do eclipse'a wczytujemy cały - z ustawieniami, a nie tylko źródła	
	  Ustawienia porobiłem na ile się dało project-specific, także nie powinno tu być rozbieżności
	 
	* Warto się upewnić że macie ustawione auto-save actions
      oraz formatter wbudowany, tyle że długość lini 140
	  no i przede wszystkim Javę 1.7 (jeśli tylko jest zainstalowana to pom.xml powinien ją ustawić dla tego projektu)

	* po zainstalowaniu - na Debianopochodnych (Ubuntu) pakiet: maven (nie maven2 - robimy na trójce, bo już działa przez wtyczkę do eclipse'a)
	  konieczne jest utworzenie pliku /home/user/.m2/settings.xml
	  i dodanie mirrora dla Europy, żeby jakoś w miarę szybko się ściągało.
	  Cała zawartość pliku:
		<settings>
			<mirrors>
				<mirror>
					<id>uk.maven.org</id>
					<url>http://uk.maven.org/maven2</url>
					<mirrorOf>central</mirrorOf>
				</mirror>
			</mirrors>
		</settings>

####	ODPALANIE			####
	Wersja dev:
		* w konsoli, w głównym katalogu projektu:
		  $ mvn install
		  $ mvn exec:java -Dexec.mainClass="pl.edu.agh.bo.knabees.ui.KnabeesGUI"
		  
		* eclipse:
		aplikację odpalamy jako standardową Java Application, entry point: KnabeesGUI
		
	Wersja release:
		TODO


####	TROUBLESHOOTING		####

	* jak się świecą błędy w pom.xml, to: projekt->prawoklik->Maven->Update project
	  Spróbuję doczytać co jest nie tak że się pali zawsze przy restarcie Eclipse'a
	  update: teraz już mi się nie pali
	
