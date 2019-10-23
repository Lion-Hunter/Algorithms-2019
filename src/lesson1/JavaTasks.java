package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) { throw new NotImplementedError(); }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        int[] temps = new int[7731];
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (new FileInputStream(inputName), StandardCharsets.UTF_8));;

        try {
            String str = reader.readLine();

            while (str != null) {
                temps[(int) (new Double(str) * 10.0) + 2730]++;
                str = reader.readLine();
            } //O(n): n - number of lines in input File
        } catch (IOException e) {
            reader.close();
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(outputName), StandardCharsets.UTF_8));

        for (int i = 0; i < 7731; i++) {
            if (temps[i] > 0) {
                for (int j = 0; j < temps[i]; j++) {
                    writer.write((double) (i - 2730)/10 + "\n");
                }
            }
        } //O(t): because i = const, t <= n
        writer.close();

    } //Ресурсоемкость - О(1), трудоемкость - О(n)

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */

    static public void sortSequence(String inputName, String outputName) throws IOException {
        TreeMap<Integer, Integer> tree = new TreeMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (new FileInputStream(inputName), StandardCharsets.UTF_8));
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> result = new ArrayList<>();
        String str = reader.readLine();

        try {
            while (str != null) {
                if (!tree.containsKey(new Integer(str))) tree.put(new Integer(str), 1);
                else tree.put(new Integer(str), tree.get(new Integer(str)) + 1);
                list.add(new Integer(str));
                str = reader.readLine();
            }
        } catch (IOException e){
            reader.close();
        } //O(log(n)) n - number of strings in the input file

        int maxValue = 0;
        int minKey = 1;

        for (Integer key : tree.keySet()) {
            if (tree.get(key) > maxValue) maxValue = tree.get(key);
        } //O(m) = O(n), because m <= n

        for (Integer e : tree.keySet()) {
            if (tree.get(e) == maxValue) {
                minKey = e;
                break;
            }
        } //O(k) = O(n), k <= n

        for (Integer number : list) {
            if (number != minKey) result.add(number);
        } //O(n)

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(outputName), StandardCharsets.UTF_8));
        for (Integer r : result) {
            writer.write(r + "\n");
        } //O(n)

        for (int i = 0; i < tree.get(minKey); i++) {
            writer.write(minKey + "\n");
        } //O(n)
        writer.close();
    } // Ресурсоемкость O(n), трудоемкость O(log(n))

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) { throw new NotImplementedError(); }
}
