# Valiutos
Atrankos darbas

Vartojimo instrukcija:

Norint paleisti programą reikia turėti Java Development Kit. 

1. Atsidarę CMD einame į vietą, kurioje yra atsiųsta programa (cd {path}Valiutos\eisvaliutos) - {path}. eisvaliutos aplanke vykdysime sekantį žingsnį.

2. Jeigu kompiuteryje yra įrašytas Maven paleidžiame:
mvn clean install

Jeigu Maven kompiuteryje nėra: 
mvnw.cmd clean package

3. Paleidžiame programą:
java -jar -Dapple.awt.UIElement="true" target/eisvaliutos-1.0-SNAPSHOT.jar -h

4. Toliau sekant instrukcijas naudojamės programa.
