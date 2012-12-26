	* pracujemy na Eclipse Juno J2EE i nie starszym, bo nie obsługują wszystkich bajerów,
	  a tym bardziej ustawień które przygotowałem
	  Jak będziecie chcieli to wrzucę na jakiś torrent ze swoją instalacją eclipse'a z wszystkim ustawionym

	* wtyczki do zainstalowania do Eclipse'a:
		* m2eclipse (wsparcie do Mavena - nie trzeba mieć zainstalowanego mavena, eclipse ma swój wbudowany)
		* eGit (chyba że ktoś chce ręcznie odnajdywać się w zmianach, to powodzenia)
		* window builder pro - stanowczo odradzam

	* Projekt do eclipse'a wczytujemy cały - z ustawieniami, a nie tylko źródła	
	  Ustawienia porobiłem na ile się dało project-specific, także nie powinno tu być rozbieżności
	 
	* Warto się upewnić że macie ustawione auto-save actions
      oraz formatter wbudowany, tyle że długość lini 140
	  no i przede wszystkim Javę 1.7
	  (jeśli tylko jest zainstalowana w systemie to pom.xml powinien ją ustawić dla tego projektu)


####	BUDOWANIE			####
	Wersja release:
		* mvn assembly:assembly
		wynikowy jar (z zależnościami) w katalogu target
		
		
####	ODPALANIE			####
	Wersja dev:
		* w konsoli, w głównym katalogu projektu:
		  $ mvn clean install exec:java
		  
		* eclipse:
		aplikację odpalamy jako standardową Java Application, entry point: KnabeesGUI
		
	Wersja release:
		* potrzebne jest Java Runtime Environment 1.7. W konsoli:
		java -jar knabees-1.0-jar-with-dependencies.jar
		(albo: knabees-1.0-SNAPSHOT-jar-with-dependencies.jar)


####	TROUBLESHOOTING		####

	* jak się świecą błędy w pom.xml, to: projekt->prawoklik->Maven->Update project

	* jeśli budowanie przez mavena powieżamy wtyczce eclipsowej (m2eclipse), to
	  mogą się wypluwać jakieś głupoty przy odpalaniu każdej komendy mavena przez wtyczkę :
		  	SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
			SLF4J: Defaulting to no-operation (NOP) logger implementation
			SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
	  należy to ignorować

	* jeśli są problemy z dociąganiem mavenowego stuffu:
	  po zainstalowaniu - na Debianopochodnych (Ubuntu) pakiet: maven (nie maven2 - robimy na trójce)
	  konieczne jest utworzenie pliku /home/user/.m2/settings.xml
	  i dodanie mirrora dla Europy
	  Cała zawartość pliku settings.xml:
		<settings>
			<mirrors>
				<mirror>
					<id>uk.maven.org</id>
					<url>http://uk.maven.org/maven2</url>
					<mirrorOf>central</mirrorOf>
				</mirror>
			</mirrors>
		</settings>