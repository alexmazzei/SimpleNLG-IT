# Simple-NLG-IT

  SimpleNLG-IT is an Italian adaptation of SimpleNLG-EnFr 1.1, that is
  a bilingual English/French adaption of SimpleNLG v4.2. SimpleNLG-IT
  has been developed by Alessandro Mazzei, Cristina Battagnino and
  Cristina Bosco. Plese cite -> INLG16_TOAPPEAR if you use this
  software.

+ SimpleNLG (https://github.com/simplenlg/simplenlg) is a Java library
  for text surface realization in English. It was originally developed
  by Ehud Reiter, Albert Gatt and Dave Westwater, of Aberdeen
  University. If you do not know what is "text surface realization",
  read this http://homepages.abdn.ac.uk/e.reiter/pages/book.html

+ SimpleNLG-EnFr 1.1 (https://github.com/rali-udem/SimpleNLG-EnFr) was
  originally developed by Pierre-Luc Vaudry.

+ Soon we add a more documents DOCS directory. In the while, follow
  the examples provided in the >>package tutorial.italian;<<

  ++ A very simple start:
  1. cd DOCS
  2. javac -cp .:./simplenlg-it.jar Testsimplenlgit.java
  3. java -cp .:./simplenlg-it.jar Testsimplenlgit --> "Loro erano belli."

  ++ A multilingual start:
  1. cd DOCS
  2. javac -cp .:./simplenlg-it.jar MultiLingualTest.java
  3. java -cp .:./simplenlg-it.jar MultiLingualTest --> "Trilingual love ..."

+ SimpleLEX-IT, that is the lexicon used in SimpleNLG-IT, has a specific github page:
  https://github.com/alexmazzei/SimpleLEX-IT